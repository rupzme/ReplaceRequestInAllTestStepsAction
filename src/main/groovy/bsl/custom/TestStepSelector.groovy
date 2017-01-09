package bsl.custom

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 04/01/17.
 */
interface TestStepSelector {
    List<RestTestRequestStep> selectMatchingRESTRequestTestSteps(RestRequest restRequest)
    List<TestStep> getAllRestRequestTestStepsInProjectWithRequestBodies(Project project)
}