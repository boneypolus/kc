/*
 * Copyright 2007 The Kuali Foundation.
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
package org.kuali.kra.proposaldevelopment.rules;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.core.UserSession;
import org.kuali.core.service.DocumentService;
import org.kuali.core.util.ErrorMap;
import org.kuali.core.util.ErrorMessage;
import org.kuali.core.util.GlobalVariables;
import org.kuali.kra.KraTestBase;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.rice.KNSServiceLocator;

import edu.iu.uis.eden.exception.WorkflowException;

/**
 * This class tests the ProposalDevelopmentDocumentRule class
 */
public class ProposalDevelopmentDocumentRuleTest extends KraTestBase {

    private static final String DEFAULT_PROPOSAL_SPONSOR_CODE = "005889";
    private static final String DEFAULT_PROPOSAL_TITLE = "Project title";
    private static final String DEFAULT_PROPOSAL_ACTIVITY_TYPE = "1";
    private static final String DEFAULT_PROPOSAL_OWNED_BY_UNIT = "000002";

    private static final String PROPOSAL_TYPE_NEW = "1";
    private static final String PROPOSAL_TYPE_CONTINUATION = "2";
    private static final String PROPOSAL_TYPE_RENEWAL = "3";
    private static final String PROPOSAL_TYPE_RESUBMISSION = "4";
    private static final String PROPOSAL_TYPE_REVISION = "5";
    private static final String PROPOSAL_TYPE_TASK_ORDER = "6";
    private static final String DOCUMENT_HEADER_DESCRIPTION = "ProposalDevelopmentDocumentWebTest test";
    private DocumentService documentService = null;
    private ProposalDevelopmentDocumentRule proposalDevelopmentDocumentRule = null;
    private Date defaultProposalRequestedStartDate = null;
    private Date defaultProposalRequestedEndDate = null;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        GlobalVariables.setUserSession(new UserSession("quickstart"));
        documentService = KNSServiceLocator.getDocumentService();
        proposalDevelopmentDocumentRule = new ProposalDevelopmentDocumentRule();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        defaultProposalRequestedStartDate = new Date(dateFormat.parse("08/14/2007").getTime());
        defaultProposalRequestedEndDate = new Date(dateFormat.parse("08/21/2007").getTime());
    }

    @After
    public void tearDown() throws Exception {
        GlobalVariables.setUserSession(null);
        documentService = null;
        proposalDevelopmentDocumentRule = null;
        defaultProposalRequestedStartDate = null;
        defaultProposalRequestedEndDate = null;
        super.tearDown();
    }

    @Test public void testValidNewProposalType() throws Exception {
        ProposalDevelopmentDocument document = (ProposalDevelopmentDocument) documentService.getNewDocument("ProposalDevelopmentDocument");

        setRequiredDocumentFields(document,
                DOCUMENT_HEADER_DESCRIPTION,
                DEFAULT_PROPOSAL_SPONSOR_CODE,
                DEFAULT_PROPOSAL_TITLE,
                defaultProposalRequestedStartDate,
                defaultProposalRequestedEndDate,
                DEFAULT_PROPOSAL_ACTIVITY_TYPE,
                PROPOSAL_TYPE_NEW,
                DEFAULT_PROPOSAL_OWNED_BY_UNIT);
        assertTrue("Rule shouldn't produce any errors", proposalDevelopmentDocumentRule.processCustomSaveDocumentBusinessRules(document));
        assertEquals(0, GlobalVariables.getErrorMap().size());
    }

    @Test public void testNonNewProposalTypeWithoutOriginalProposalId() throws Exception {
        processType(PROPOSAL_TYPE_NEW, false, false);
        processType(PROPOSAL_TYPE_CONTINUATION, false, true);
        processType(PROPOSAL_TYPE_RENEWAL, false, true);
        processType(PROPOSAL_TYPE_RESUBMISSION, false, false);
        processType(PROPOSAL_TYPE_REVISION, false, true);
        processType(PROPOSAL_TYPE_TASK_ORDER, false, false);
    }

    @Test public void testNonNewProposalTypeWithOriginalProposalId() throws Exception {
        processType(PROPOSAL_TYPE_NEW, true, false);
        processType(PROPOSAL_TYPE_CONTINUATION, true, false);
        processType(PROPOSAL_TYPE_RENEWAL, true, false);
        processType(PROPOSAL_TYPE_RESUBMISSION, true, false);
        processType(PROPOSAL_TYPE_REVISION, true, false);
        processType(PROPOSAL_TYPE_TASK_ORDER, true, false);
    }

    /**
     * This method does all the processing for a particular proposalTypeCode
     * @param proposalTypeCode proposalType to check
     * @param setContinuedFrom boolean whether to set original Proposal ID or not - if it's set
     * we shouldn't get any errors, but if it's missing we should get errors
     * @throws WorkflowException
     */
    private void processType(String proposalTypeCode, boolean setSponsorProposalId, boolean expectError) throws WorkflowException {
        GlobalVariables.getErrorMap().clear();
        
        ProposalDevelopmentDocument document = (ProposalDevelopmentDocument) documentService.getNewDocument("ProposalDevelopmentDocument");
        setRequiredDocumentFields(document,
                DOCUMENT_HEADER_DESCRIPTION,
                DEFAULT_PROPOSAL_SPONSOR_CODE,
                DEFAULT_PROPOSAL_TITLE,
                defaultProposalRequestedStartDate,
                defaultProposalRequestedEndDate,
                DEFAULT_PROPOSAL_ACTIVITY_TYPE,
                proposalTypeCode,
                DEFAULT_PROPOSAL_OWNED_BY_UNIT);
        
        if (setSponsorProposalId) {
            document.setSponsorProposalNumber("234567");
        }
        if (!expectError) {
            assertTrue("Rule should NOT produce any errors", proposalDevelopmentDocumentRule.processCustomSaveDocumentBusinessRules(document));
            assertEquals(0, GlobalVariables.getErrorMap().size());
        } else {
            assertFalse("Rule should produce an errors", proposalDevelopmentDocumentRule.processCustomSaveDocumentBusinessRules(document));
            assertEquals(1, GlobalVariables.getErrorMap().size());
            ErrorMap errorMap = GlobalVariables.getErrorMap();
            List<ErrorMessage> messages = errorMap.getMessages("document.sponsorProposalNumber");
            ErrorMessage errorMessage = messages.get(0);
            assertEquals(KeyConstants.ERROR_REQUIRED_PROPOSAL_SPONSOR_ID, errorMessage.getErrorKey());
            
            assertEquals("Sponsor Proposal ID (Sponsor Proposal ID)", errorMessage.getMessageParameters()[0]);
        }
    }

    /**
     * This method sets required document fields
     * @param document ProposalDevelopmentDocument to set fields for
     * @param description String financialdescription for the document header
     * @param sponsorCode String Sponsor code for the document
     * @param title String title of document
     * @param requestedStartDateInitial String start date
     * @param requestedEndDateInitila String end date
     * @param activityTypeCode String activity type code
     * @param proposalTypeCode String proposal type code
     * @param ownedByUnit String owned by unit
     */
    private void setRequiredDocumentFields(ProposalDevelopmentDocument document, String description, String sponsorCode, String title, Date requestedStartDateInitial, Date requestedEndDateInitial, String activityTypeCode, String proposalTypeCode, String ownedByUnit) {
        document.getDocumentHeader().setFinancialDocumentDescription(description);
        document.setSponsorCode(sponsorCode);
        document.setTitle(title);
        document.setRequestedStartDateInitial(requestedStartDateInitial);
        document.setRequestedEndDateInitial(requestedEndDateInitial);
        document.setActivityTypeCode(activityTypeCode);
        document.setProposalTypeCode(proposalTypeCode);
        document.setOwnedByUnitNumber(ownedByUnit);
    }

}