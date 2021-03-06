/*
 * Copyright 2005-2014 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
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
package org.kuali.coeus.s2sgen.impl.generate.support;

import gov.grants.apply.forms.budgetV11.BudgetNarrativeAttachmentsDocument;
import gov.grants.apply.forms.budgetV11.BudgetNarrativeAttachmentsDocument.BudgetNarrativeAttachments;
import gov.grants.apply.system.attachmentsV10.AttachedFileDataType;
import gov.grants.apply.system.attachmentsV10.AttachmentGroupMin1Max100DataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.propdev.api.core.ProposalDevelopmentDocumentContract;
import org.kuali.coeus.propdev.api.attachment.NarrativeContract;
import org.kuali.coeus.s2sgen.impl.generate.FormGenerator;
import org.kuali.coeus.s2sgen.impl.generate.FormVersion;
import org.kuali.coeus.s2sgen.impl.generate.S2SBaseFormGenerator;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is used to generate XML Document object for grants.gov BudgetV1.1. This form is generated using XMLBean API's
 * generated by compiling BudgetV1.1 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
@FormGenerator("BudgetV1_1Generator")
public class BudgetV1_1Generator extends S2SBaseFormGenerator {

    private static final Log LOG = LogFactory.getLog(ProjectV1_0Generator.class);
    private static final int BUDGET_ATTACHMENTS = 57;

    /**
     * 
     * This method is used to get Narrative Attachments for Particular Budget
     * 
     * @return budgetNarrativeAttachmentsDocument(BudgetNarrativeAttachmentsDocument) {@link XmlObject} of type
     *         BudgetNarrativeAttachmentsDocument.
     */
    private BudgetNarrativeAttachmentsDocument getBudgetNarrativeAttachmentsDocument() {

        BudgetNarrativeAttachmentsDocument budgetNarrativeAttachmentsDocument = BudgetNarrativeAttachmentsDocument.Factory
                .newInstance();
        BudgetNarrativeAttachments budgetNarrativeAttachments = BudgetNarrativeAttachments.Factory.newInstance();
        budgetNarrativeAttachments.setFormVersion(FormVersion.v1_1.getVersion());

        AttachmentGroupMin1Max100DataType attachmentGroupMin1Max100DataType = AttachmentGroupMin1Max100DataType.Factory
                .newInstance();
        attachmentGroupMin1Max100DataType.setAttachedFileArray(getAttachedFileDataTypes());
        budgetNarrativeAttachments.setAttachments(attachmentGroupMin1Max100DataType);

        budgetNarrativeAttachmentsDocument.setBudgetNarrativeAttachments(budgetNarrativeAttachments);
        return budgetNarrativeAttachmentsDocument;
    }

    /**
     * 
     * This method is used to get List of attachments from NarrativeAttachment
     * 
     * @return attachedFileDataTypes(AttachedFileDataType[])- returns an array of AttachedFileDataType if the NarrativeTypeCode is
     *         BUDGET_ATTACHMENTS
     */
    private AttachedFileDataType[] getAttachedFileDataTypes() {
        LOG.debug("Getting AttachedFileDataType ");
        List<AttachedFileDataType> attachedFileDataTypes = new ArrayList<AttachedFileDataType>();
        AttachedFileDataType attachedFileDataType = null;
        for (NarrativeContract narrative : pdDoc.getDevelopmentProposal().getNarratives()) {
            if (narrative.getNarrativeType().getCode() != null
                    && Integer.parseInt(narrative.getNarrativeType().getCode()) == BUDGET_ATTACHMENTS ) {
            	attachedFileDataType = getAttachedFileType(narrative);
            	if (attachedFileDataType != null) {
					attachedFileDataTypes.add(attachedFileDataType);
					LOG.debug("Attachmentcount" + attachedFileDataTypes.size());
				}
            }
        }
        return attachedFileDataTypes.toArray(new AttachedFileDataType[0]);
    }

    /**
     * This method creates {@link XmlObject} of type {@link BudgetNarrativeAttachmentsDocument} by populating data from the given
     * {@link ProposalDevelopmentDocumentContract}
     * 
     * @param proposalDevelopmentDocument for which the {@link XmlObject} needs to be created
     * @return {@link XmlObject} which is generated using the given {@link ProposalDevelopmentDocumentContract}
     */
    public XmlObject getFormObject(ProposalDevelopmentDocumentContract proposalDevelopmentDocument) {
        this.pdDoc = proposalDevelopmentDocument;
        return getBudgetNarrativeAttachmentsDocument();
    }
}
