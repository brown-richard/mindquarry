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
package com.mindquarry.persistence.jcr.mapping.trafo;

import com.mindquarry.persistence.jcr.api.JcrNode;

/**
 * Add summary documentation here.
 *
 * @author 
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
public class StringTransformer implements Transformer {
    
    public Object readFromJcr(JcrNode jcrNode) {
        // TODO Auto-generated method stub
        return null;
    }

    public void writeToJcr(Object object, JcrNode jcrNode) {
        JcrNode textNode = jcrNode.addNode("text", "xt:text");
        textNode.setProperty("xt:characters", object.toString());
    }    
}
