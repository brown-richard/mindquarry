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
package com.mindquarry.auth;

import com.mindquarry.user.AbstractUserRO;

/**
 * Check if a particular user is privileged to fulfil 
 * an operation at a resource.
 *
 * @author 
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
public interface AuthorizationCheck {
    
    public static final String READ = "read";
    public static final String WRITE = "write";
    public static final String CHANGE_RIGHTS = "changeRights";
    
    public static final String ROLE = AuthorizationCheck.class.getName();

    boolean mayPerform(String resource, String operation, String userId);
    
    boolean mayPerform(String resource, String operation, AbstractUserRO user);
}
