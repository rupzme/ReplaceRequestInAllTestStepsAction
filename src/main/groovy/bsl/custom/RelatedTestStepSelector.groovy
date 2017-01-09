/**
 * Created by Rupert Anderson on 29/12/2016.
 */
package bsl.custom

import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.model.testsuite.TestCase
import org.apache.log4j.Logger
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequest
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestSuite

class RelatedTestStepSelector implements TestStepSelector{

    protected final Logger scriptLogger = Logger.getLogger("groovy.log")

    /**
     * Find all TestSteps for a REST request
     *
     * @param restRequest - The REST request to find matching TestSteps for
     * @return List<RestTestRequestStep> - The list of matching TestSteps
     */
    List<RestTestRequestStep> selectMatchingRESTRequestTestSteps(RestRequest restRequest){
        Project project = restRequest.getProject()
        List<RestTestRequestStep> restTestSteps = getAllRestRequestTestStepsInProjectWithRequestBodies(project)
        List<RestTestRequestStep> matchingRestTestRequestSteps = null
        scriptLogger.info "---restRequest getId(): "+restRequest.getId()
        restTestSteps.each{restTestStep ->
            //scriptLogger.info "TestSTep getId(): " + restTestStep.getId()
            scriptLogger.info "getTestRequest getId(): " + restTestStep.getTestRequest().getId()
            if (restTestStep.getTestRequest().getId()==restRequest.getId())
                    matchingRestTestRequestSteps << restTestStep
        }
        return matchingRestTestRequestSteps
    }

    /**
     * Extract a List of RestTestSteps that can have request content
     *  i.e. the TestStep Type is RestTestRequestStep and has a Method of either POST,PUT,PATCH or DELETE.
     * @param project
     * @return List<TestStep>
     */
    List<RestTestRequestStep> getAllRestRequestTestStepsInProjectWithRequestBodies(Project project){
        List<RestTestRequestStep> selectedTestSteps = new ArrayList<RestTestRequestStep>()
        List<TestSuite> testSuites = project.getTestSuiteList()
        testSuites.each{testSuite ->
            scriptLogger.info "test suite name: "+testSuite.name
            List<TestCase> testCases = testSuite.getTestCaseList()
            testCases.each{testCase ->
                scriptLogger.info "test case name: "+testCase.name
                List<RestTestRequestStep> restTestSteps = testCase.getTestStepsOfType(RestTestRequestStep.class)
                restTestSteps.each {restTestStep ->
                    scriptLogger.info "test step name: "+restTestStep.name+" method: "+restTestStep.restMethod.method
                    if (restTestStep.restMethod.hasRequestBody()) selectedTestSteps.add(restTestStep)
                }
            }
        }
        return selectedTestSteps
    }
}
