/*
 * Copyright (C) 2006-2007 Mindquarry GmbH, All Rights Reserved
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 */
package com.mindquarry.persistence.jcr.cmds;

import com.mindquarry.persistence.jcr.JcrNode;
import com.mindquarry.persistence.jcr.Persistence;
import com.mindquarry.persistence.jcr.Session;
import com.mindquarry.persistence.jcr.model.Model;
import com.mindquarry.persistence.jcr.trafo.TransformationManager;
import com.mindquarry.persistence.jcr.trafo.Transformer;

/**
 * Add summary documentation here.
 *
 * @author 
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
class WriteCommand implements Command {

    private Object entity_;
    private Persistence persistence_;
    
    public void initialize(Persistence persistence, Object... objects) {
        entity_ = objects[0];
        persistence_ = persistence;
    }
    
    /**
     * @see com.mindquarry.persistence.jcr.mapping.Command#execute(javax.jcr.Session)
     */
    public Object execute(Session session) {
        
        JcrNode rootNode = session.getRootNode();
        JcrNode folderNode = rootNode.findNode(entityFolderName());
        
        JcrNode entityNode = folderNode.findNode(entityId());
        entityTransformer().writeToJcr(entity_, entityNode);
        
        return entityNode;
    }
    
    private String entityId() {
        return getModel().entityId(entity_);
    }
    
    private String entityFolderName() {
        return getModel().entityFolderName(entity_);
    }
    
    private Model getModel() {
        return persistence_.getModel();
    }
    
    private Transformer entityTransformer() {
        return getTransformationManager().entityTransformer(entity_);
    }
    
    private TransformationManager getTransformationManager() {
        return persistence_.getTransformationManager();
    }
}