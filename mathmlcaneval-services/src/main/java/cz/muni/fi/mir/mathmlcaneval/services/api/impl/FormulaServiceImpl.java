/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.services.api.impl;

import cz.muni.fi.mir.mathmlcaneval.api.FormulaService;
import cz.muni.fi.mir.mathmlcaneval.api.dto.FormulaDTO;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Service
public class FormulaServiceImpl implements FormulaService
{
    private final Set<String> hashes = Collections.<String>synchronizedSet(new HashSet<String>());
    private static final Logger LOGGER = LogManager.getLogger(FormulaServiceImpl.class);
    @Override
    @Async
    public void createFormulas(List<FormulaDTO> formulas) throws IllegalArgumentException
    {
        for(FormulaDTO f : formulas)
        {
            LOGGER.info("Hash {}, duplicate {}",f.getFormulaHash(),!hashes.add(f.getFormulaHash()));            
        }
    }
    
}