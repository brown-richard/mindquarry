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
package com.mindquarry.common.source;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is intented to mark properties of a bean/pojo
 * that should not be serialized, e.g. the password of a user.
 * 
 * It is especially used by the model-source component, which
 * enables an xml serialization of java beans/pojos by means of 
 * introspecting them. Currently only annotated property getters 
 * are considered, but annotated property fields should be supported too.
 * 
 * @author 
 * <a href="mailto:bastian(dot)steinert(at)mindquarry(dot)com">Bastian Steinert</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializationIgnore {
}