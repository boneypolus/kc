/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.common.impl.krms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.coeus.common.framework.krms.KcKrmsCacheManager;
import org.kuali.kra.krms.KcKrmsConstants;
import org.kuali.coeus.common.framework.krms.KrmsRulesContext;
import org.kuali.coeus.common.framework.krms.KrmsRulesExecutionService;
import org.kuali.rice.coreservice.framework.parameter.ParameterConstants;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.api.repository.RuleRepositoryService;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.type.ValidationActionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("krmsRulesExecutionService")
public class KrmsRulesExecutionServiceImpl implements KrmsRulesExecutionService {
    
    protected final Log LOG = LogFactory.getLog(KrmsRulesExecutionServiceImpl.class);

    @Autowired
    @Qualifier("kcKrmsCacheManager")
    private KcKrmsCacheManager kcKrmsCacheManager;

    @Autowired
    @Qualifier("ruleRepositoryService")
    private RuleRepositoryService ruleRepositoryService;

    @Autowired
    @Qualifier("rice.krms.engine")
    private Engine engine;

    public List<String> processUnitValidations(String unitNumber, KrmsRulesContext rulesContext) {
        kcKrmsCacheManager.clearCache();
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        rulesContext.populateContextQualifiers(contextQualifiers);
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                Collections.singletonMap(KcKrmsConstants.UNIT_NUMBER, unitNumber));

        Facts.Builder factsBuilder = Facts.Builder.create();
        rulesContext.addFacts(factsBuilder);

        if (engine == null) {
            LOG.error("Could not resolve KRMS Rules Engine - Unit Validations will not be evaluated!");
        } else {
            EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);
        
            // comma-delimited list of error & warning messages
            if (results != null) {
                String errors = (String) results.getAttribute(ValidationActionTypeService.VALIDATIONS_ACTION_ATTRIBUTE);
                if (errors != null) {
                    String[] errorArray = StringUtils.split(errors, ",");
                    return Arrays.asList(errorArray);
                }
            } else {
                LOG.warn("Results returned from KRMS Rules Engine was null.");
            }
        }
        return Collections.emptyList();
    }

    public List<Map<String,String>> processUnitKcValidations(String unitNumber, KrmsRulesContext rulesContext) {
        kcKrmsCacheManager.clearCache();
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        rulesContext.populateContextQualifiers(contextQualifiers);
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                Collections.singletonMap(KcKrmsConstants.UNIT_NUMBER, unitNumber));

        Facts.Builder factsBuilder = Facts.Builder.create();
        rulesContext.addFacts(factsBuilder);


        if (engine == null) {
            LOG.error("Could not resolve KRMS Rules Engine - Unit Validations will not be evaluated!");
        } else {
            EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), null);

            // comma-delimited list of error & warning messages
            if (results != null) {
                List<Map<String,String>> errors = (List<Map<String,String>>) results.getAttribute(KcKrmsConstants.ValidationAction.VALIDATIONS_ACTION_ATTRIBUTE);
                if (errors != null) {
                    return errors;
                }
            } else {
                LOG.warn("Results returned from KRMS Rules Engine was null.");
            }
        }
        return Collections.emptyList();
    }
    
    public Map<String, Boolean> runApplicableRules(List<String> ruleIds, KrmsRulesContext rulesContext, String agendaTypeId) {
        Map <String, Boolean> ruleResults = new HashMap<String, Boolean>();
        if (rulesContext != null) {
            String namespace = rulesContext.getClass().getAnnotation(ParameterConstants.NAMESPACE.class).namespace();
            Map<String, String> contextQualifiers = new HashMap<String, String>();
            rulesContext.populateContextQualifiers(contextQualifiers);
            Map<String,String> agendaQualifiers = new HashMap<String,String>();
            rulesContext.populateAgendaQualifiers(agendaQualifiers);
            agendaQualifiers.put("typeId", agendaTypeId);
    
            contextQualifiers.put("namespaceCode", namespace);
            SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers, agendaQualifiers);
    
            Facts.Builder factsBuilder = Facts.Builder.create();
            rulesContext.addFacts(factsBuilder);
    
            ExecutionOptions xOptions = new ExecutionOptions();
            xOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
    
            EngineResults results = engine.execute(selectionCriteria, factsBuilder.build(), xOptions);
    
            List<RuleDefinition> ruleDefinitions = ruleRepositoryService.getRules(ruleIds);
            Map<String, RuleDefinition> ruleMap = new HashMap<String, RuleDefinition>();
            for (RuleDefinition rule : ruleDefinitions) {
                ruleMap.put(rule.getName(), rule);
            }
            if (results.getResultsOfType(ResultEvent.RULE_EVALUATED) != null && results.getResultsOfType(ResultEvent.RULE_EVALUATED).size() > 0) {
                for (ResultEvent resultEvent : results.getResultsOfType(ResultEvent.RULE_EVALUATED)) {
                    String ruleName = ((BasicRule)resultEvent.getSource()).getName();
                    if (ruleMap.containsKey(ruleName)) {
                        ruleResults.put(ruleMap.get(ruleName).getId(), resultEvent.getResult());
                    }
                }
            }
        }
        return ruleResults;

        
    }

    public KcKrmsCacheManager getKcKrmsCacheManager() {
        return kcKrmsCacheManager;
    }

    public void setKcKrmsCacheManager(KcKrmsCacheManager kcKrmsCacheManager) {
        this.kcKrmsCacheManager = kcKrmsCacheManager;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public RuleRepositoryService getRuleRepositoryService() {
        return ruleRepositoryService;
    }

    public void setRuleRepositoryService(RuleRepositoryService ruleRepositoryService) {
        this.ruleRepositoryService = ruleRepositoryService;
    }
}
