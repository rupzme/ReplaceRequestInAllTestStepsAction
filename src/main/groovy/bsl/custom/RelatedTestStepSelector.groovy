/**
 * Created by Rupert Anderson on 29/12/2016.
 */
package bsl.custom

import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.model.testsuite.TestCase
import org.apache.log4j.Logger
import com.eviware.soapui.impl.rest.RestRequest
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
        List<RestTestRequestStep> matchingRestTestRequestSteps = new ArrayList<RestTestRequestStep>()

        List<TestSuite> testSuites = project.testSuiteList
        testSuites.each{testSuite ->
            scriptLogger.info "Searching TestSuite name ["+testSuite.name+"] for matching TestSteps..."
            List<TestCase> testCases = testSuite.testCaseList
            testCases.each{testCase ->
                scriptLogger.info "Searching TestCase ["+testCase.name+"] for matching TestSteps..."
                List<RestTestRequestStep> restTestSteps = testCase.getTestStepsOfType(RestTestRequestStep.class)
                restTestSteps.each {restTestStep ->
                    if (restTestStep.getTestRequest().getId()==restRequest.getId()) {
                        scriptLogger.info "Found matching TestStep - " + restTestStep.name + "]"
                        matchingRestTestRequestSteps << restTestStep
                    }
                }
            }
        }
        return matchingRestTestRequestSteps
    }
}
