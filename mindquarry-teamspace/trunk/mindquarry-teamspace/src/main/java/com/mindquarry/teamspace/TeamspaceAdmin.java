/**
 * 
 */
package com.mindquarry.teamspace;

import com.mindquarry.user.UserRO;


/**
 * Provides methods to manage teamspaces (e.g. create and delete).
 *
 * @author 
 * <a href="bastian(dot)steinert(at)mindquarry(dot)com">Bastian Steinert</a>
 *
 */
public interface TeamspaceAdmin extends TeamspaceQuery {

    public static final String ROLE = TeamspaceAdmin.class.getName();
    
    /**
     * creates a new teamspace and the associated workspace (svn repo)
     * @param teamspaceId
     * @param name of the teamspace
     * @param a String describing the teamspace
     * @param the user that triggered the creation of the new teamspace 
     * @throws CouldNotCreateTeamspaceException 
     */
	TeamspaceDefinition createTeamspace(String id, String name, 
                String description, UserRO teamspaceCreator) throws CouldNotCreateTeamspaceException;
	
    TeamspaceDefinition teamspaceDefinitionForId(String teamspaceId);
    
    void updateTeamspace(TeamspaceDefinition teamspace);
    
	void removeTeamspace(String teamspaceId) throws CouldNotRemoveTeamspaceException;
}
