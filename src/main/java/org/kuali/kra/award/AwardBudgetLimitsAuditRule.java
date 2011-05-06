/*
 * Copyright 2005-2010 The Kuali Foundation
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
package org.kuali.kra.award;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rule.DocumentAuditRule;
import org.kuali.rice.kns.util.AuditCluster;
import org.kuali.rice.kns.util.AuditError;
import org.kuali.rice.kns.util.GlobalVariables;

/**
 * Checks the budget limits on the Budgets tab to make sure they are valid.
 */
public class AwardBudgetLimitsAuditRule implements DocumentAuditRule {

    protected String AWARD_BUDGET_LIMITS_AUDIT_ERRORS = "awardBudgetLimitAuditErrors";
    
    /**
     * @see org.kuali.rice.kns.rule.DocumentAuditRule#processRunAuditBusinessRules(org.kuali.rice.kns.document.Document)
     */
    @SuppressWarnings("unchecked")
    public boolean processRunAuditBusinessRules(Document document) {
        boolean result = true;
        Award award = ((AwardDocument) document).getAward();
        List<AuditError> auditErrors = new ArrayList<AuditError>(); 
        if (award.getTotalCostBudgetLimit().getLimit() != null) {
            if (award.getTotalCostBudgetLimit().getLimit().isGreaterThan(award.getObligatedDistributableTotal())) {
                result = false;
                auditErrors.add(new AuditError("document.award.totalCostBudgetLimit.limit",
                        KeyConstants.WARNING_AWARD_BUDGET_COST_LIMIT_EXCEEDS_OBLIGATED,
                        Constants.MAPPING_AWARD_BUDGET_VERSIONS_PAGE + "." + "BudgetLimits",
                        new String[]{award.getAwardNumber()}));                
            }
            if (award.getTotalCostBudgetLimit().getLimit().isLessThan(award.getObligatedDistributableTotal())) {
                result = false;
                auditErrors.add(new AuditError("document.award.totalCostBudgetLimit.limit",
                        KeyConstants.WARNING_AWARD_BUDGET_LIMIT_LESSTHAN_OBLIGATED,
                        Constants.MAPPING_AWARD_BUDGET_VERSIONS_PAGE + "." + "BudgetLimits"));
            }
        }
        reportAndCreateAuditCluster(auditErrors);
        return result;
    }
    
    /**
     * This method creates and adds the AuditCluster to the Global AuditErrorMap.
     */
    @SuppressWarnings("unchecked")
    protected void reportAndCreateAuditCluster( List<AuditError> auditErrors ) {
        if (auditErrors.size() > 0) {
            GlobalVariables.getAuditErrorMap().put(AWARD_BUDGET_LIMITS_AUDIT_ERRORS, new AuditCluster("Budget Limits",
                                                                                          auditErrors, Constants.AUDIT_WARNINGS));
        }
    }
}
