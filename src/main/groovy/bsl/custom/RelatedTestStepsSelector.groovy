/**
 * Created by Rupert Anderson on 29/12/2016.
 */
package bsl.custom

import org.apache.log4j.Logger
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequest
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestStep
import com.eviware.soapui.model.testsuite.TestSuite

import java.util.List

class RelatedTestStepsSelector {

    protected final Logger scriptLogger = Logger.getLogger("groovy.log")

    List<RestTestRequest> selectMatchingTestSteps(RestRequest restRequest){
        getTestStepsFromAllTesSuitesInRestRequestProject(restRequest)
        return null
    }

    List<TestStep> getTestStepsFromAllTesSuitesInRestRequestProject(RestRequest restRequest){
        Project project = restRequest.getProject()
        List<TestSuite> testSuites = project.getTestSuiteList()
        testSuites.each{
            scriptLogger.info "test suite name: "+it.name
        }
        return null
    }
}
