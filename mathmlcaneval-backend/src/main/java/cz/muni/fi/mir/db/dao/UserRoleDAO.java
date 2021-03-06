/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.db.dao;

import java.util.List;

import cz.muni.fi.mir.db.domain.UserRole;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface UserRoleDAO extends GenericDAO<UserRole, Long>
{    
    /**
     * Method obtains UserRole based on its text value. Because
     * roleName is unique, only single result can be obtained
     * @param roleName of UserRole
     * @return UserRole with given roleName, null if there is no match.
     */
    UserRole getUserRoleByName(String roleName);
    
    /**
     * Method fetches all UserRoles from database in <b>DESCENDING</b> order.
     * @return List of all UserRoles, if there are no UserRole empty List is returned.
     */
    List<UserRole> getAllUserRoles();    
}
