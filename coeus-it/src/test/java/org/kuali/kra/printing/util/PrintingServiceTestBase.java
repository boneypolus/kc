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
package org.kuali.kra.printing.util;

import org.junit.After;
import org.junit.Before;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.kra.test.infrastructure.KcIntegrationTestBase;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.util.GlobalVariables;

public abstract class PrintingServiceTestBase extends KcIntegrationTestBase {
    protected DocumentService documentService;

	@Before
	public void setUp() throws Exception {
		GlobalVariables.setUserSession(new UserSession("quickstart"));
        documentService = KcServiceLocator.getService(DocumentService.class);
        GlobalVariables.setUserSession(new UserSession("quickstart"));
		
	}

	@After
	public void tearDown() throws Exception {
		GlobalVariables.setUserSession(null);
	}
}
