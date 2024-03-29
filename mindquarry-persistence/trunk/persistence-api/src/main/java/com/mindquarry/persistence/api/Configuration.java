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
package com.mindquarry.persistence.api;

import java.util.Collection;
import java.util.Map;

/**
 * This interface should be implemented by a configuration provider.
 * A configuration instance is required by the SessionFactory implementation
 * during the initialization. A configuration instance must provide 
 * all entity classes and queries that should be managed by the persistence
 * layer.   
 *
 * @author
 * <a href="mailto:bastian.steinert(at)mindquarry.com">Bastian Steinert</a>
 */
public interface Configuration {

    public Collection<Class<?>> getClasses();
    public Map<String, String> getNamedQueries();
}
