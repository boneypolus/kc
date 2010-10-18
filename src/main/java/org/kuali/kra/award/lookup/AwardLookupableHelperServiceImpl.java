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
package org.kuali.kra.award.lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.contacts.AwardPerson;
import org.kuali.kra.award.contacts.AwardUnitContact;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.bo.KcPerson;
import org.kuali.kra.bo.Rolodex;
import org.kuali.kra.bo.Unit;
import org.kuali.kra.bo.versioning.VersionHistory;
import org.kuali.kra.lookup.KraLookupableHelperServiceImpl;
import org.kuali.kra.service.KcPersonService;
import org.kuali.kra.service.VersionHistoryService;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;
import org.kuali.rice.kns.web.ui.Field;
import org.kuali.rice.kns.web.ui.Row;

/**
 * This class provides Award lookup support
 */
class AwardLookupableHelperServiceImpl extends KraLookupableHelperServiceImpl {    

    private static final String COPY_HREF_PATTERN = "../DocCopyHandler.do?docId=%s&command=displayDocSearchView&documentTypeName=%s";
    static final String PERSON_ID = "personId";
    static final String ROLODEX_ID = "rolodexId";
    static final String UNIT_NUMBER = "unitNumber";
    static final String USER_ID = "userId";
    static final String PI_NAME = "principalInvestigatorName";
    static final String OSP_ADMIN_NAME = "ospAdministratorName";
  
    private static final long serialVersionUID = 6304433555064511153L;
    
    private transient KcPersonService kcPersonService;
    private VersionHistoryService versionHistoryService; 

    @Override
    public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {
        super.setBackLocationDocFormKey(fieldValues);       // need to set backlocation & docformkey here. Otherwise, they are empty
        if (this.getParameters().containsKey(USER_ID)) {
            fieldValues.put("projectPersons.personId", ((String[]) this.getParameters().get(USER_ID))[0]);
        }
        Map<String, String> formProps = new HashMap<String, String>();
        if (!StringUtils.isEmpty(fieldValues.get("lookupOspAdministratorName"))) {
            formProps.put("fullName", fieldValues.get("lookupOspAdministratorName"));
            formProps.put("unitAdministratorTypeCode", "2");
        }
        fieldValues.remove("lookupOspAdministratorName");
        if (!formProps.isEmpty()) {
            List<Long> ids = new ArrayList<Long>();
            Collection<AwardUnitContact> persons = getLookupService().findCollectionBySearch(AwardUnitContact.class, formProps);
            if (persons.isEmpty()) {
                return new ArrayList<Award>();
            }
            for (AwardUnitContact person : persons) {
                ids.add(person.getAwardContactId());
            }
            fieldValues.put("awardUnitContacts.awardContactId", StringUtils.join(ids, '|'));
        }
        List<Award> unboundedResults = (List<Award>) super.getSearchResultsUnbounded(fieldValues);
        return filterForActiveAwards(unboundedResults);
    }

    /**
     * add 'copy' link to actions list
     * @see org.kuali.kra.lookup.KraLookupableHelperServiceImpl#getCustomActionUrls(org.kuali.rice.kns.bo.BusinessObject, java.util.List)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
        List<HtmlData> htmlDataList = super.getCustomActionUrls(businessObject, pkNames);
        AwardDocument document = ((Award) businessObject).getAwardDocument();
        htmlDataList.add(getOpenLink(document));
        addCopyLink(businessObject, pkNames, htmlDataList, COPY_HREF_PATTERN, KNSConstants.MAINTENANCE_COPY_METHOD_TO_CALL);
        htmlDataList.add(getMedusaLink(document, false));
        return htmlDataList;
    }

    /**
     * This override is reset field definitions
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getRows()
     */
    @Override
    public List<Row> getRows() {
        List<Row> rows =  super.getRows();
        for (Row row : rows) {
            for (Field field : row.getFields()) {
                if (field.getPropertyName().equals(PI_NAME)) {
                    super.updateLookupField(field, PI_NAME, AwardPerson.class.getName());
                }
            }
        }
        return rows;
    }
                 
    /**
     * Sets the KC Person Service.
     * @param kcPersonService the kc person service
     */
    public void setKcPersonService(KcPersonService kcPersonService) {
        this.kcPersonService = kcPersonService;
    }

    /**
     * @param versionHistoryService
     */
    public void setVersionHistoryService(VersionHistoryService versionHistoryService) {
       this.versionHistoryService = versionHistoryService; 
    }

    /**
     * This method is for fields that do not have inquiry created by lookup frame work.
     * @see org.kuali.core.lookup.AbstractLookupableHelperServiceImpl#getInquiryUrl(org.kuali.core.bo.BusinessObject, java.lang.String)
     */
    @Override
    public HtmlData getInquiryUrl(BusinessObject bo, String propertyName) {
        Award award = (Award) bo;
        HtmlData inquiryUrl = super.getInquiryUrl(bo, propertyName);
        if (propertyName.equals(UNIT_NUMBER)) {
            inquiryUrl = getUnitNumberInquiryUrl(award);
        } else if (propertyName.equals(PI_NAME)) {
            inquiryUrl = getPrincipalInvestigatorNameInquiryUrl(award);            
        } else if(propertyName.equals(OSP_ADMIN_NAME)) {
            inquiryUrl = getOspAdminNameInquiryUrl(award);
        }
        return inquiryUrl;
    }

    /**
     * @param document
     * @return
     */
    protected AnchorHtmlData getOpenLink(Document document) {
        AwardDocument awardDocument = (AwardDocument) document;
        AnchorHtmlData htmlData = new AnchorHtmlData();
        htmlData.setDisplayText("open");
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, KNSConstants.DOC_HANDLER_METHOD);
        parameters.put(KNSConstants.PARAMETER_COMMAND, KEWConstants.DOCSEARCH_COMMAND);
        parameters.put(KNSConstants.DOCUMENT_TYPE_NAME, getDocumentTypeName());
        parameters.put("viewDocument", "true");
        parameters.put("docOpenedFromAwardSearch", "true");
        parameters.put("docId", document.getDocumentNumber());
        String href = UrlFactory.parameterizeUrl("../"+getHtmlAction(), parameters);
        htmlData.setHref(href);
        return htmlData;
    }
    
    
    protected void addCopyLink(BusinessObject businessObject, List<String> pkNames, List<HtmlData> htmlDataList, String hrefPattern, String methodToCall) {
        AnchorHtmlData htmlData = getUrlData(businessObject, methodToCall, pkNames);
        AwardDocument document = ((Award) businessObject).getAwardDocument();
        htmlData.setHref(String.format(hrefPattern, document.getDocumentNumber(), getDocumentTypeName()));
        htmlDataList.add(htmlData);
    }
    
    protected HtmlData getOspAdminNameInquiryUrl(Award award) {
        KcPerson ospAdministrator = award.getOspAdministrator();
        if (ospAdministrator != null) {
            final KcPerson inqBo = this.kcPersonService.getKcPersonByPersonId(ospAdministrator.getPersonId());
            return super.getInquiryUrl(inqBo, PERSON_ID);
        } else {
            return null;
        }
    }

    protected HtmlData getPrincipalInvestigatorNameInquiryUrl(Award award) {
        HtmlData inquiryUrl = null;
        AwardPerson principalInvestigator = award.getPrincipalInvestigator();
        if (principalInvestigator != null) {
            if (StringUtils.isNotBlank(principalInvestigator.getPersonId())) {
                final KcPerson inqBo = this.kcPersonService.getKcPersonByPersonId(principalInvestigator.getPersonId());
                inquiryUrl = super.getInquiryUrl(inqBo, PERSON_ID);
            } else {
                if (principalInvestigator.getRolodexId() != null) {
                    Rolodex inqBo = new Rolodex();
                    inqBo.setRolodexId(principalInvestigator.getRolodexId());
                    inquiryUrl = super.getInquiryUrl(inqBo, ROLODEX_ID);
                }
            }
            
        }
        return inquiryUrl;
    }

    /**
     * This method...
     * @param bo
     * @param propertyName
     * @return
     */
    protected HtmlData getUnitNumberInquiryUrl(Award award) {
        Unit inqBo = new Unit();
        Unit leadUnit = award.getLeadUnit();
        inqBo.setUnitNumber(leadUnit != null ? leadUnit.getUnitNumber() : null);
        return super.getInquiryUrl(inqBo, UNIT_NUMBER);
    }

    @Override
    protected String getHtmlAction() {
        return "awardHome.do";
    }
    
    /**
     * @see org.kuali.kra.lookup.KraLookupableHelperServiceImpl#createdEditHtmlData(org.kuali.rice.kns.bo.BusinessObject)
     * 
     * Edit is not supported for Award lookup, so we'll just no-op
     */
    @Override
    protected void addEditHtmlData(List<HtmlData> htmlDataList, BusinessObject businessObject) {
        //no-op
    }

    @Override
    protected String getDocumentTypeName() {
        return "AwardDocument";
    }
    
    @Override
    protected String getKeyFieldName() {
        return "awardId";
    }   
    
    protected List<Award> filterForActiveAwards(Collection<Award> collectionByQuery) {
        Set<String> awardNumbers = new TreeSet<String>();
        for(Award award: collectionByQuery) {
            awardNumbers.add(award.getAwardNumber());
        }
        
        List<Award> activeAwards = new ArrayList<Award>();
        for(String versionName: awardNumbers) {
            VersionHistory versionHistory = versionHistoryService.findActiveVersion(Award.class, versionName);
            if(versionHistory != null) {
                Award activeAward = (Award) versionHistory.getSequenceOwner();
                if(activeAward != null) {
                    activeAwards.add(activeAward);
                }
            }
        }        
                
        return activeAwards;
    }

}
