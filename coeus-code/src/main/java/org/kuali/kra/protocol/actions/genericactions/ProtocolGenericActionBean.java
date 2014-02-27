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
package org.kuali.kra.protocol.actions.genericactions;

import org.kuali.kra.protocol.actions.ProtocolActionBean;
import org.kuali.kra.protocol.actions.ProtocolOnlineReviewCommentable;
import org.kuali.kra.protocol.actions.reviewcomments.ReviewAttachmentsBeanBase;

import java.io.Serializable;
import java.sql.Date;

/**
 * This interface is really just a "form" for generic actions.
 */
public interface ProtocolGenericActionBean extends ProtocolActionBean, ProtocolOnlineReviewCommentable, Serializable {

    public String getComments();
    
    public void setComments(String comments);
    
    public Date getActionDate();
    
    public void setActionDate(Date actionDate);
    
    public String getErrorPropertyKey();

    public void setReviewAttachmentsBean(ReviewAttachmentsBeanBase reviewAttachmentsBean);
    
}
