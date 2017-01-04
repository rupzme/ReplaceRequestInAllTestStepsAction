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
import com.eviware.soapui.model.testsuite.TestStep
import com.eviware.soapui.model.testsuite.TestSuite

class RelatedTestStepSelector implements TestStepSelector{

    protected final Logger scriptLogger = Logger.getLogger("groovy.log")

    List<RestTestRequest> selectMatchingRESTRequestTestSteps(RestRequest restRequest){
        Project project = restRequest.getProject()
        getAllTestStepsFromTesSuitesInRestRequestProject(project)
        return null
    }

    List<TestStep> getAllTestStepsFromTesSuitesInRestRequestProject(Project project){
        List<TestStep> testSteps = new ArrayList<TestStep>()
        List<TestSuite> testSuites = project.getTestSuiteList()
        testSuites.each{testSuite ->
            scriptLogger.info "test suite name: "+testSuite.name
            List<TestCase> testCases = testSuite.getTestCaseList()
            testCases.each{testCase ->
                scriptLogger.info "test case name: "+testCase.name
                List<TestStep> restTestSteps = testCase.getTestStepsOfType(RestTestRequestStep.class)
                restTestSteps.each {restTestStep ->
                    scriptLogger.info "test step name: "+restTestStep.name
                    testSteps.add(restTestStep)
                }
            }
        }
        return testSteps
    }
}
