package bsl.custom

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert Anderson on 04/01/17.
 */
interface TestStepUpdater {
    public String replaceContentInRelatedTestSteps(List<RestTestRequestStep> relatedTestSteps, RestRequest restRequest)
}