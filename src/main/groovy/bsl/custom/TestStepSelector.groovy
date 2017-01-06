package bsl.custom

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequest
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 04/01/17.
 */
interface TestStepSelector {
    List<RestTestRequest> selectMatchingRESTRequestTestSteps(RestRequest restRequest)
    List<TestStep> getAllRestRequestTestStepsInProjectWithRequestBodies(Project project)
}