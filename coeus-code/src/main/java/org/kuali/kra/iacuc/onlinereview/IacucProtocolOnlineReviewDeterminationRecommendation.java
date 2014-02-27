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
package org.kuali.kra.iacuc.onlinereview;

import org.kuali.kra.iacuc.actions.IacucProtocolActionType;
import org.kuali.kra.protocol.actions.submit.ProtocolReviewTypeBase;
import org.kuali.kra.protocol.onlinereview.ProtocolOnlineReviewDeterminationRecommendationBase;

public class IacucProtocolOnlineReviewDeterminationRecommendation extends ProtocolOnlineReviewDeterminationRecommendationBase {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1768290517796704487L;

    private String iacucProtocolReviewTypeCode;
    
    private ProtocolReviewTypeBase iacucProtocolReviewType;

    private IacucProtocolActionType iacucProtocolActionType;
    
    private String iacucProtocolActionTypeCode;
    
    public String getIacucProtocolReviewTypeCode() {
        return iacucProtocolReviewTypeCode;
    }

    public void setIacucProtocolReviewTypeCode(String iacucProtocolReviewTypeCode) {
        this.iacucProtocolReviewTypeCode = iacucProtocolReviewTypeCode;
    }

    public ProtocolReviewTypeBase getIacucProtocolReviewType() {
        if (iacucProtocolReviewType == null || !iacucProtocolReviewType.getReviewTypeCode().equals(iacucProtocolReviewTypeCode)) {
            refreshReferenceObject("iacucProtocolReviewType");
        }
        return iacucProtocolReviewType;
    }

    public void setIacucProtocolReviewType(ProtocolReviewTypeBase iacucProtocolReviewType) {
        this.iacucProtocolReviewType = iacucProtocolReviewType;
    }

    public IacucProtocolActionType getIacucProtocolActionType() {
        if (iacucProtocolActionType == null || !iacucProtocolActionType.getProtocolActionTypeCode().equals(iacucProtocolActionTypeCode)) {
            refreshReferenceObject("iacucProtocolActionType");
        }
        return iacucProtocolActionType;
    }

    public void setIacucProtocolActionType(IacucProtocolActionType iacucProtocolActionType) {
        this.iacucProtocolActionType = iacucProtocolActionType;
    }

    public String getIacucProtocolActionTypeCode() {
        return iacucProtocolActionTypeCode;
    }

    public void setIacucProtocolActionTypeCode(String iacucProtocolActionTypeCode) {
        this.iacucProtocolActionTypeCode = iacucProtocolActionTypeCode;
    }

}
