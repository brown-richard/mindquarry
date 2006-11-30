/**
 * Copyright (C) 2006 MindQuarry GmbH, All Rights Reserved
 */
package com.mindquarry.jcr.xml.source;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.avalon.framework.configuration.Configurable;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.avalon.framework.service.ServiceManager;
import org.apache.avalon.framework.service.Serviceable;
import org.apache.avalon.framework.thread.ThreadSafe;
import org.apache.cocoon.components.source.SourceUtil;
import org.apache.excalibur.source.Source;
import org.apache.excalibur.source.SourceException;
import org.apache.excalibur.source.SourceFactory;

import com.mindquarry.common.index.IndexClient;
import com.mindquarry.jcr.client.JCRClient;
import com.mindquarry.jcr.jackrabbit.xpath.JaxenQueryHandler;

/**
 * This SourceFactory provides access to a Java Content Repository (JCR)
 * including an XML-to-JCR-nodes binding. The source implementation returned
 * is either a JCRNodeWrapperSource for direct node requests or a
 * QueryResultSource for a query result (including multiple nodes).
 * 
 * <p>
 * An URI for this source is either (i) a direct path in the repository or (ii)
 * an enclosed JCR query. The path to the repository would be given simply as
 * <code>jcr://root/folder/file</code>. For the queries:
 * <ul>
 * <li>XPATH: <code>jcr:///users?//name</code> (which maps to the xpath query
 * <code>//name</code> executed in all documents under /users)</li>
 * </ul>
 * 
 * @author <a
 *         href="mailto:alexander(dot)klimetschek(at)mindquarry(dot)com">Alexander
 *         Klimetschek</a>
 * @author <a href="mailto:alexander(dot)saar(at)mindquarry(dot)com">Alexander
 *         Saar</a>
 */
public class JCRSourceFactory extends JCRClient implements ThreadSafe,
        SourceFactory, Configurable, Serviceable {
    /**
     * Scheme, lazily computed at the first call to getSource().
     */
    protected String scheme;

    /**
     * The namespace-prefix mappings for this factory.
     */
    public static Map<String, String> configuredMappings;
    
    /**
     * The list of index exclude patterns.
     */
    public static List<String> iExcludes;

    /**
     * Index client to be used for change notifications
     */
    protected IndexClient iClient;

    // =========================================================================
    // Servicable interface
    // =========================================================================

    /**
     * Called at startup of this component.
     * 
     * @see org.apache.avalon.framework.service.Serviceable#service(org.apache.avalon.framework.service.ServiceManager)
     */
    public void service(ServiceManager manager) throws ServiceException {
        super.service(manager);

        iClient = (IndexClient) manager.lookup(IndexClient.ROLE);

        // the repository is lazily initialized to avoid circular dependency
        // between SourceResolver and JackrabbitRepository that leads to a
        // StackOverflowError at initialization time
    }
    
    public ServiceManager getServiceManager() {
        return this.manager;
    }

    // =========================================================================
    // Configurable interface
    // =========================================================================

    /**
     * Configures this component based on some <code>Configuration</code> read
     * from an XML config file. The configurable elements should be defined in
     * the class javadoc comment!
     * 
     * @see org.apache.avalon.framework.configuration.Configurable#configure(org.apache.avalon.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {
        super.configure(config);
        
        if (null == JCRSourceFactory.configuredMappings) {
            // load namespace mappings
            JCRSourceFactory.configuredMappings = new HashMap<String, String>();

            Configuration mappings = config.getChild("mappings"); //$NON-NLS-1$
            for (Configuration mapping : mappings.getChildren("mapping")) { //$NON-NLS-1$
                String namespace = mapping.getAttribute("namespace"); //$NON-NLS-1$
                String prefix = mapping.getAttribute("prefix"); //$NON-NLS-1$
                JCRSourceFactory.configuredMappings.put(namespace, prefix);
            }
            // load index excludes
            JCRSourceFactory.iExcludes = new ArrayList<String>();
            
            Configuration index = config.getChild("index"); //$NON-NLS-1$
            Configuration excludes = index.getChild("excludes"); //$NON-NLS-1$
            for (Configuration exclude : excludes.getChildren("exclude")) { //$NON-NLS-1$
                JCRSourceFactory.iExcludes.add(exclude.getValue());
            }
        }
    }

    // =========================================================================
    // SourceFactory interface
    // =========================================================================

    /**
     * Retrieves a <code>Source</code> for the given URI. The URI can also be
     * an Xpath or SQL query conforming to the JCR query support.
     * 
     * @see org.apache.excalibur.source.SourceFactory#getSource(java.lang.String,
     *      java.util.Map)
     */
    public Source getSource(String uri, Map parameters) throws IOException,
            MalformedURLException {

        // extract protocol identifier
        if (scheme == null) {
            scheme = SourceUtil.getScheme(uri);
        }
        // init session
        Session session;
        try {
            session = login();
        } catch (LoginException e) {
            throw new SourceException("Login to repository failed.", e);
        } catch (RepositoryException e) {
            throw new SourceException("Cannot access repository.", e);
        }
        
        // check for query syntax (eg. 'jcr:///users#//name' interpreted
        // as 'jcr:///jcr:root/users//name')
        if (uri.indexOf("?") != -1) { //$NON-NLS-1$
            return (QueryResultSource) executeQuery(session, SourceUtil
                    .getPath(uri), SourceUtil.getQuery(uri));
        } else {
            // standard direct hierarchy-resolving
            return (JCRNodeWrapperSource) createSource(session, SourceUtil
                    .getPath(uri));
        }
    }

    /**
     * @see org.apache.excalibur.source.SourceFactory#release(org.apache.excalibur.source.Source)
     */
    public void release(Source source) {
        // nothing to do here
    }

    // =========================================================================
    // Custom public interface
    // =========================================================================

    /**
     * Creates a new source given a session and a path
     * 
     * @param session the session
     * @param path the absolute path
     * @return a new source
     * @throws SourceException
     */
    public AbstractJCRNodeSource createSource(Session session, String path)
            throws SourceException {
        return new JCRNodeWrapperSource(this, session, path, iClient);
    }

    /**
     * Returns the scheme (probably <code>jcr</code>) for the URIs we handle.
     */
    public String getScheme() {
        return scheme;
    }

    // =========================================================================
    // Internal methods
    // =========================================================================

    /**
     * Executes an XPath Query on the JCR repository and returns a node
     * representing the query result.
     * 
     * <p>
     * Subclasses that use an extended, XMLizable JCRNodeSource should override
     * this method and return all nodes in the result.
     * 
     * @param session the session
     * @param path the path that is used as root of the query
     * @param statement the Xpath query statement
     * @param queryLang the language to use (should be Query.SQL or Query.XPATH)
     * @throws IOException when the query is wrong or the result was empty
     */
    protected Source executeQuery(Session session, String path, String statement)
            throws IOException {
        try {
            // modify path for query execution
            statement = path + statement;

            QueryManager queryManager = session.getWorkspace()
                    .getQueryManager();
            Query query = queryManager.createQuery(statement,
                    JaxenQueryHandler.FULL_XPATH);
            QueryResult result = query.execute();

            return new QueryResultSource(this, session, result.getNodes(),
                    iClient);
        } catch (RepositoryException e) {
            throw new SourceException("Cannot execute query '" + statement
                    + "'", e); //$NON-NLS-1$
        }
    }
}
