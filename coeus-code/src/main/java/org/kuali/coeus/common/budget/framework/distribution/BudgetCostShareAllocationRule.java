/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.common.budget.framework.distribution;

import org.kuali.coeus.common.budget.impl.distribution.BudgetCostShareAllocationEvent;
import org.kuali.rice.krad.rules.rule.BusinessRule;


public interface BudgetCostShareAllocationRule extends BusinessRule {

    /**
     * ProcessDefinitionDefinitionDefinition business rules for allocation of BudgetCostShare
     * @param addBudgetCostShareEvent
     * @return
     */
    public boolean processBudgetCostShareAllocationBusinessRules(BudgetCostShareAllocationEvent budgetCostShareEvent);
}
