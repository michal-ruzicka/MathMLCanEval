/*
 * Copyright 2015 Dominik Szalai - emptulik at gmail.com.
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
package cz.muni.fi.mir.mathmlcaneval.services;

import java.util.List;
import org.dozer.MappingException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface Mapper extends org.dozer.Mapper
{
    /**
     * Method converts given list of type U into list of type T
     * @param <T>
     * @param <U>
     * @param input to be converted
     * @param destinationClass target conversion class
     * @return converted list
     * @throws MappingException if any error occurs 
     */
    <T, U> List<T> mapList(List<U> input, Class<T> destinationClass) throws MappingException;
}