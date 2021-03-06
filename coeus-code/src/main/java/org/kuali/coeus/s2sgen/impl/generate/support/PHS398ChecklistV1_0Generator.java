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

import gov.grants.apply.forms.phs398ChecklistV10.PHS398ChecklistDocument;
import gov.grants.apply.forms.phs398ChecklistV10.PHS398ChecklistDocument.PHS398Checklist;
import gov.grants.apply.forms.phs398ChecklistV10.PHS398ChecklistDocument.PHS398Checklist.ApplicationType;
import gov.grants.apply.forms.phs398ChecklistV10.PHS398ChecklistDocument.PHS398Checklist.CertificationExplanation;
import gov.grants.apply.forms.phs398ChecklistV10.PHS398ChecklistDocument.PHS398Checklist.IncomeBudgetPeriod;
import gov.grants.apply.system.attachmentsV10.AttachedFileDataType;
import gov.grants.apply.system.globalLibraryV10.HumanNameDataType;
import gov.grants.apply.system.globalLibraryV10.YesNoDataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.common.api.ynq.YnqConstant;
import org.kuali.coeus.common.questionnaire.api.answer.AnswerHeaderContract;
import org.kuali.coeus.common.api.rolodex.RolodexContract;
import org.kuali.coeus.common.api.rolodex.RolodexService;
import org.kuali.coeus.common.budget.api.core.BudgetContract;
import org.kuali.coeus.common.budget.api.income.BudgetProjectIncomeContract;
import org.kuali.coeus.propdev.api.budget.ProposalDevelopmentBudgetExtContract;
import org.kuali.coeus.propdev.api.core.ProposalDevelopmentDocumentContract;
import org.kuali.coeus.propdev.api.attachment.NarrativeContract;
import org.kuali.coeus.s2sgen.impl.generate.FormGenerator;
import org.kuali.coeus.s2sgen.impl.generate.FormVersion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * This class is used to generate XML Document object for grants.gov
 * PHS398ChecklistV1.0. This form is generated using XMLBean API's generated by
 * compiling PHS398ChecklistV1.0 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
@FormGenerator("PHS398ChecklistV1_0Generator")
public class PHS398ChecklistV1_0Generator extends PHS398ChecklistBaseGenerator {
	private static final Log LOG = LogFactory
			.getLog(PHS398ChecklistV1_0Generator.class);
	List<? extends AnswerHeaderContract> answerHeaders;

    @Autowired
    @Qualifier("rolodexService")
    private RolodexService rolodexService;

	/**
	 * 
	 * This method returns PHS398ChecklistDocument object based on proposal
	 * development document which contains the PHS398ChecklistDocument
	 * informations
	 * ApplicationType,FederalID,ChangeOfPDPI,FormerPDName,ChangeOfInstitution,
	 * FormerInstitutionName,InventionsAndPatents, ProgramIncome and
	 * CertificationExplanation for a particular proposal
	 * 
	 * @return phsChecklistDocument {@link XmlObject} of type
	 *         PHS398ChecklistDocument.
	 */
	private PHS398ChecklistDocument getPHS398Checklist() {

		PHS398ChecklistDocument phsChecklistDocument = PHS398ChecklistDocument.Factory
				.newInstance();
		PHS398Checklist phsChecklist = PHS398Checklist.Factory.newInstance();
		phsChecklist.setFormVersion(FormVersion.v1_0.getVersion());
		ApplicationType.Enum appEnum = null;
		answerHeaders = getPropDevQuestionAnswerService().getQuestionnaireAnswerHeaders(pdDoc.getDevelopmentProposal().getProposalNumber());
		if (pdDoc.getDevelopmentProposal().getProposalType() != null
				&& Integer.parseInt(pdDoc.getDevelopmentProposal()
						.getProposalType().getCode()) < PROPOSAL_TYPE_CODE_6) {
			// Check <6 to ensure that if proposalType='TASK ORDER", it must not
			// set. THis is because enum ApplicationType has no
			// entry for TASK ORDER
			appEnum = ApplicationType.Enum.forInt(Integer.parseInt(pdDoc
					.getDevelopmentProposal().getProposalType().getCode()));
		}
		phsChecklist.setApplicationType(appEnum);

		String federalId = getSubmissionInfoService().getFederalId(pdDoc.getDevelopmentProposal().getProposalNumber());
		if (federalId != null) {
			phsChecklist.setFederalID(federalId);
		}
		String pIChange = getAnswer(PROPOSAL_YNQ_QUESTION_114,answerHeaders);
        String pIChangeExplanation = getAnswer(PROPOSAL_YNQ_QUESTION_115,answerHeaders);
        if (YnqConstant.YES.code().equals(pIChange)) {
            phsChecklist.setIsChangeOfPDPI(YesNoDataType.YES);
            if (pIChangeExplanation != null) {
                RolodexContract rolodex = rolodexService.getRolodex(Integer.valueOf(pIChangeExplanation));
                HumanNameDataType formerPDName = globLibV10Generator
                        .getHumanNameDataType(rolodex);
                if (formerPDName != null
                        && formerPDName.getFirstName() != null
                        && formerPDName.getLastName() != null) {
                    phsChecklist.setFormerPDName(formerPDName);
                }
            }
        } else {
            phsChecklist.setIsChangeOfPDPI(YesNoDataType.NO);
        }
		
        String institutionChange = getAnswer(PROPOSAL_YNQ_QUESTION_116,answerHeaders);
        String institutionChangeExplanation = getAnswer(PROPOSAL_YNQ_QUESTION_117,answerHeaders);
        
        if (YnqConstant.YES.code().equals(institutionChange)) {
            phsChecklist.setIsChangeOfInstitution(YesNoDataType.YES);
            if (institutionChangeExplanation != null) {
                phsChecklist.setFormerInstitutionName(institutionChangeExplanation);
            }
        } else {
            phsChecklist.setIsChangeOfInstitution(YesNoDataType.NO);
        }
		
        String renewalApplication = getAnswer(PROPOSAL_YNQ_QUESTION_118,answerHeaders);
        boolean hasSubQuestionExplanation = false;
        if (renewalApplication != null && !renewalApplication.equals(NOT_ANSWERED)) {
            if (YnqConstant.YES.code().equals(renewalApplication)) {
                String inventionsConceived = getAnswer(PROPOSAL_YNQ_QUESTION_119,answerHeaders);
                if (YnqConstant.YES.code().equals(inventionsConceived)) {
                    phsChecklist.setIsInventionsAndPatents(YesNoDataType.YES);
                    String reportedPreviously = getAnswer(PROPOSAL_YNQ_QUESTION_120,answerHeaders);
                    if (reportedPreviously != null && !reportedPreviously.equals(NOT_ANSWERED)) {
                        if (YnqConstant.YES.code().equals(reportedPreviously)) {
                            phsChecklist.setIsPreviouslyReported(YesNoDataType.YES);
                        } else {
                            phsChecklist.setIsPreviouslyReported(YesNoDataType.NO);
                        }
                        hasSubQuestionExplanation = true;
                    }
                } else {
                    phsChecklist.setIsInventionsAndPatents(YesNoDataType.YES);
                    if (hasSubQuestionExplanation) {
                        phsChecklist.setIsPreviouslyReported(YesNoDataType.NO);
                    }
                }
            } else {
                phsChecklist.setIsInventionsAndPatents(YesNoDataType.NO);
                if (hasSubQuestionExplanation) {
                    phsChecklist.setIsPreviouslyReported(YesNoDataType.NO);
                }
            }
        }

        ProposalDevelopmentBudgetExtContract budget = s2SCommonBudgetService.getBudget(pdDoc.getDevelopmentProposal());

		if (budget != null && budget.getBudgetProjectIncomes() != null
				&& budget.getBudgetProjectIncomes().size() > 0) {
			setProjectIncome(phsChecklist, budget);
		} else {
			phsChecklist.setProgramIncome(YesNoDataType.NO);
		}
		AttachedFileDataType attachedFileDataType = null;
		for (NarrativeContract narrative : pdDoc.getDevelopmentProposal()
				.getNarratives()) {
			if (narrative.getNarrativeType().getCode() != null
					&& Integer.parseInt(narrative.getNarrativeType().getCode()) == NARRATIVE_CODE_CERTIFICATIONS_ATTACHMENT) {
				attachedFileDataType = getAttachedFileType(narrative);
				if(attachedFileDataType != null){
					CertificationExplanation certificationExplanation = CertificationExplanation.Factory.newInstance();
					certificationExplanation.setCertifications(attachedFileDataType);
					phsChecklist.setCertificationExplanation(certificationExplanation);
				}
			}
		}
		phsChecklistDocument.setPHS398Checklist(phsChecklist);
		return phsChecklistDocument;
	}


	private void setProjectIncome(PHS398Checklist phsChecklist, BudgetContract budget) {
		phsChecklist.setProgramIncome(YesNoDataType.YES);

		//TreeMap Used to maintain the order of the Budget periods.
		Map<Integer, IncomeBudgetPeriod> incomeBudgetPeriodMap = new TreeMap<Integer, IncomeBudgetPeriod>();
		BigDecimal anticipatedAmount;
		for (BudgetProjectIncomeContract projectIncome : budget
				.getBudgetProjectIncomes()) {
			Integer budgetPeriodNumber = projectIncome.getBudgetPeriodNumber();
			IncomeBudgetPeriod incomeBudgPeriod = incomeBudgetPeriodMap.get(budgetPeriodNumber);
			if (incomeBudgPeriod == null) {
				incomeBudgPeriod = IncomeBudgetPeriod.Factory.newInstance();
				incomeBudgPeriod.setBudgetPeriod(budgetPeriodNumber.intValue());
				anticipatedAmount = BigDecimal.ZERO;
			} else {
				anticipatedAmount = incomeBudgPeriod.getAnticipatedAmount();
			}
			anticipatedAmount = anticipatedAmount.add(projectIncome.getProjectIncome().bigDecimalValue());
			incomeBudgPeriod.setAnticipatedAmount(anticipatedAmount);
			String description = getProjectIncomeDescription(projectIncome);
			if (description != null) {
				if (incomeBudgPeriod.getSource() != null) {
					incomeBudgPeriod.setSource(incomeBudgPeriod.getSource()
							+ ";" + description);
				} else {
					incomeBudgPeriod.setSource(description);
				}
			}
			incomeBudgetPeriodMap.put(budgetPeriodNumber, incomeBudgPeriod);
		}
		Collection<IncomeBudgetPeriod> incomeBudgetPeriodCollection = incomeBudgetPeriodMap.values();
		phsChecklist.setIncomeBudgetPeriodArray(incomeBudgetPeriodCollection.toArray(new IncomeBudgetPeriod[0]));
	}
	/*
	 * This method will get the project income description
	 */
	protected String getProjectIncomeDescription(BudgetProjectIncomeContract projectIncome) {
		String description = null;
		if (projectIncome.getDescription() != null) {
			if (projectIncome.getDescription().length() > PROJECT_INCOME_DESCRIPTION_MAX_LENGTH) {
				description = projectIncome.getDescription().substring(0,
						PROJECT_INCOME_DESCRIPTION_MAX_LENGTH);
			} else {
				description = projectIncome.getDescription();
			}
		}
		return description;
	}
	/**
	 * This method creates {@link XmlObject} of type
	 * {@link PHS398ChecklistDocument} by populating data from the given
	 * {@link ProposalDevelopmentDocumentContract}
	 * 
	 * @param proposalDevelopmentDocument
	 *            for which the {@link XmlObject} needs to be created
	 * @return {@link XmlObject} which is generated using the given
	 *         {@link ProposalDevelopmentDocumentContract}
	 */
	public XmlObject getFormObject(
			ProposalDevelopmentDocumentContract proposalDevelopmentDocument) {
		this.pdDoc = proposalDevelopmentDocument;
		return getPHS398Checklist();
	}

    public RolodexService getRolodexService() {
        return rolodexService;
    }

    public void setRolodexService(RolodexService rolodexService) {
        this.rolodexService = rolodexService;
    }
}
