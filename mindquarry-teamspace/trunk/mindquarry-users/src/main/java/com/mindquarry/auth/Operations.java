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

import java.util.Arrays;
import java.util.Collection;

/**
 * Add summary documentation here.
 *
 * @author 
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
public final class Operations {
    
    public static final String READ = "read";
    public static final String WRITE = "write";
    public static final String CHANGE_RIGHTS = "changeRights";
    
    private static final Collection<String> DEFAULT_OPERATIONS;
    
    private static final Collection<String> READ_WRITE;
    
    static {
        DEFAULT_OPERATIONS = Arrays.asList( 
            new String[] {READ, WRITE, CHANGE_RIGHTS});
        
        READ_WRITE = Arrays.asList(new String[] {READ, WRITE});
    }
    
    public static Collection<String> defaultOperations() {
        return DEFAULT_OPERATIONS;
    }
    
    public static Collection<String> readWrite() {
        return READ_WRITE;
    }
}
