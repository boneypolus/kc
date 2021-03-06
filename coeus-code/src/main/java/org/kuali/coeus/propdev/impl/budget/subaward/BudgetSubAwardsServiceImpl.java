package org.kuali.coeus.propdev.impl.budget.subaward;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.propdev.api.budget.subaward.BudgetSubAwardsContract;
import org.kuali.coeus.propdev.api.budget.subaward.BudgetSubAwardsService;
import org.kuali.coeus.propdev.impl.budget.subaward.BudgetSubAwards;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("budgetSubAwardsService")
public class BudgetSubAwardsServiceImpl implements BudgetSubAwardsService {

    @Autowired
    @Qualifier("businessObjectService")
    private BusinessObjectService businessObjectService;

    @Override
    public List<? extends BudgetSubAwardsContract> findBudgetSubAwardsByBudgetId(Long budgetId) {
        if (budgetId == null) {
            throw new IllegalArgumentException("budgetId is null");
        }

        return ListUtils.emptyIfNull((List<BudgetSubAwards>) getBusinessObjectService().findMatching(BudgetSubAwards.class, Collections.singletonMap("budgetId", budgetId)));

    }

    @Override
    public List<? extends BudgetSubAwardsContract> findBudgetSubAwardsByBudgetIdAndNamespace(Long budgetId, String namespace) {
        if (budgetId == null) {
            throw new IllegalArgumentException("budgetId is null");
        }

        if (StringUtils.isBlank(namespace)) {
            throw new IllegalArgumentException("mappingName is blank");
        }

        final Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("budgetId", budgetId);
        paramMap.put("namespace", namespace);
        return ListUtils.emptyIfNull((List<BudgetSubAwards>) getBusinessObjectService().findMatching(BudgetSubAwards.class, paramMap));
    }

    @Override
    public List<? extends BudgetSubAwardsContract> findBudgetSubAwardsByBudgetIdAndNullNamespace(Long budgetId) {
        if (budgetId == null) {
            throw new IllegalArgumentException("budgetId is null");
        }

        final Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("budgetId", budgetId);
        paramMap.put("namespace", null);
        return ListUtils.emptyIfNull((List<BudgetSubAwards>) getBusinessObjectService().findMatching(BudgetSubAwards.class, paramMap));
    }

    public BusinessObjectService getBusinessObjectService() {
        return businessObjectService;
    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }
}
