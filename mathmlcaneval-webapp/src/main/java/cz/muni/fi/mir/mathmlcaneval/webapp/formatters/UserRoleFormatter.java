/*
 * Copyright 2015 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.webapp.formatters;

import cz.muni.fi.mir.mathmlcaneval.api.UserRoleService;
import cz.muni.fi.mir.mathmlcaneval.services.Mapper;
import cz.muni.fi.mir.mathmlcaneval.webapp.forms.UserRoleForm;
import java.text.ParseException;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class UserRoleFormatter implements Formatter<UserRoleForm>
{
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private Mapper mapper;
    
    @Override
    public String print(UserRoleForm userRoleForm, Locale locale)
    {
        return userRoleForm.getId() != null ? userRoleForm.getId().toString() : null;
    }

    @Override
    public UserRoleForm parse(String string, Locale locale) throws ParseException
    {
        if(StringUtils.isEmpty(string))
        {
            throw new ParseException("Given text is empty", 0);
        }
        
        return mapper.map(userRoleService.getByID(Long.valueOf(string)), UserRoleForm.class);
    }
    
}