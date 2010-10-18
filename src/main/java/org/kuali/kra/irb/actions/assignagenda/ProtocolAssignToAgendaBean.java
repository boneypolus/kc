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
package org.kuali.kra.irb.actions.assignagenda;

import java.io.Serializable;
import java.sql.Date;

import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.irb.Protocol;
import org.kuali.kra.irb.actions.ActionHelper;
import org.kuali.kra.irb.actions.genericactions.ProtocolGenericActionBean;
import org.kuali.kra.printing.Printable;

/**
 * This class is really just a "form" for assigning a protocol to an agenda.
 */
@SuppressWarnings("serial")
public class ProtocolAssignToAgendaBean extends ProtocolGenericActionBean implements Serializable {
    
    private String committeeId = "";
    private String committeName = "";
    private String scheduleDate = "";
    private boolean protocolAssigned = false;
    private Date actionDate = new Date(System.currentTimeMillis());

    private transient ProtocolAssignToAgendaService agendaService;

    /**
     * 
     * Constructs a ProtocolAssignToAgendaBean.java.
     * 
     * @param actionHelper an ActionHelper object
     */
    public ProtocolAssignToAgendaBean(ActionHelper actionHelper) {
        super(actionHelper);

    }
    
    
    public Date getActionDate() {
        return actionDate;
    }


    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }


    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }


    public void setCommitteName(String committeName) {
        this.committeName = committeName;
    }


    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }


    public void setProtocolAssigned(boolean protocolAssigned) {
        this.protocolAssigned = protocolAssigned;
    }

    private ProtocolAssignToAgendaService getProtocolAssigntoAgendaService() {
        if (this.agendaService == null){
            this.agendaService = KraServiceLocator.getService(ProtocolAssignToAgendaService.class);
        }
        return this.agendaService;
    }


    public String getCommitteeId() {
        return committeeId;
    }


    public String getCommitteName() {
        return committeName;
    }


    public String getScheduleDate() {
        return scheduleDate;
    }


    public boolean isProtocolAssigned() {
        return protocolAssigned;
    }

    /**
     * Prepare the Assign to Committee and Schedule for rendering with JSP.
     */
    public void prepareView() {
        Protocol protocol = getActionHelper().getProtocol();
        if (protocol != null && protocol.getProtocolNumber() != null) {
            String assignedCommitteeId = getProtocolAssigntoAgendaService().getAssignedCommitteeId(protocol);
            if (assignedCommitteeId != null) {
                this.committeeId = assignedCommitteeId;
                this.committeName = getProtocolAssigntoAgendaService().getAssignedCommitteeName(protocol);
                this.setComments(getProtocolAssigntoAgendaService().getAssignToAgendaComments(protocol));
                this.protocolAssigned = getProtocolAssigntoAgendaService().isAssignedToAgenda(protocol);
                this.scheduleDate = getProtocolAssigntoAgendaService().getAssignedScheduleDate(protocol);
            }
        }
        
        /*
         * The Assign to Agenda has to work with and without JavaScript. When JavaScript is enabled, the newly selected committee
         * and schedule are what we want to continue to display. When JavaScript is disabled, we have to change the schedule dates
         * that we display if the committee has changed.
         * Note: no known javascript issues at this, but leaving the code in place so it will be easy to put in if/when an issue arises.
         */
        if (getActionHelper().getProtocolForm().isJavaScriptEnabled()) {
        } else {
        }
    }
    
    /**
     * 
     * This method returns the appropriate printable for this class
     * @return a Printable
     */
    public Printable getCorrespondence() {
        AssignToAgendaCorrespondence correspondence = new AssignToAgendaCorrespondence();
        return correspondence;
    }
}
