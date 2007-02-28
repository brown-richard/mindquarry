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
package com.mindquarry.user;



/**
 * Provides methods to manage user (e.g. create and delete) 
 * and assign them to groups.
 *
 * @author 
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
public interface UserAdmin extends UserQuery {

    public static final String ROLE = UserAdmin.class.getName();
    
    /**
     * creates a new user account
     * @param userId
     * @param password
     * @param first name of the new user
     * @param last name of the new user
     * @param the user's email address
     * @param the skills of the user
     */
    User createUser(String id, String password, 
            String name, String surName, String email, String skills);
    
    User userById(String userId);
    
    void updateUser(User user);
    
    void deleteUser(User user);
    
    
    GroupRO createGroup(String groupId);
    
    void deleteGroup(GroupRO group);
    
    
    void addUser(AbstractUserRO user, GroupRO group);
    
    void removeUser(AbstractUserRO user, GroupRO group);
}
