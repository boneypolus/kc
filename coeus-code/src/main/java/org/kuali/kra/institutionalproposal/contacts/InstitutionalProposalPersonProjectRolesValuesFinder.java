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
package org.kuali.kra.institutionalproposal.contacts;

import java.util.List;

import org.kuali.coeus.common.framework.person.PropAwardPersonRoleValuesFinder;
import org.kuali.kra.institutionalproposal.web.struts.form.InstitutionalProposalForm;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.uif.view.ViewModel;

public class InstitutionalProposalPersonProjectRolesValuesFinder extends PropAwardPersonRoleValuesFinder {

	@Override
	protected String getSponsorCodeFromModel(ViewModel model) {
		return ((InstitutionalProposalForm) model).getInstitutionalProposalDocument().getInstitutionalProposal().getSponsorCode();
	}

    @Override
    public List<KeyValue> getKeyValues(){
    	InstitutionalProposalForm form = (InstitutionalProposalForm) KNSGlobalVariables.getKualiForm();
		return getKeyValues(form.getInstitutionalProposalDocument().getInstitutionalProposal().getSponsorCode());
    }

}
