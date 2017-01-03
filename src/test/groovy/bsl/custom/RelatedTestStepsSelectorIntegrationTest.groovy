package bsl.custom

import com.eviware.soapui.config.RestRequestStepConfig
import com.eviware.soapui.config.impl.RestRequestStepConfigImpl
import com.eviware.soapui.impl.actions.RestServiceBuilder
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.model.testsuite.TestCase
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 02/01/2017.
 */
class RelatedTestStepsSelectorIntegrationTest extends GroovyTestCase{

    RelatedTestStepsSelector relatedTestStepsSelector = new RelatedTestStepsSelector()
    //RestRequest restRequest = new RestRequest()

//    protected void setUp(){

  //  }

    //void testShouldGetTestStepsFromAllTesSuitesInRestRequestProject(){

//    }

  //  void testShouldReturnPOSTTestStepsThatMatchRestRequestEndpoint(){

//    }

    void testShouldReturnEmptyListIfProjectHasNoTestSteps(){
        def uri = "http://jsonplaceholder.typicode.com/posts/1"

        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()
        serviceBuilder.createRestService(project, uri)

        List<TestStep> testSteps = relatedTestStepsSelector.getAllTestStepsFromTesSuitesInRestRequestProject(project)

        assertTrue testSteps.size() == 0
    }

    void testShouldReturnTestStepsListIfProjectTestSteps(){
        def uri = "http://jsonplaceholder.typicode.com/posts/1"

        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()
        serviceBuilder.createRestService(project, uri)
        TestCase testCase1 = project.addNewTestSuite("TestSuite1").addNewTestCase("TestCase1")
        //RestRequestStepConfig restRequestStepConfig = RestRequestStepConfigImpl()
        //restRequestStepConfig.set
        testCase1.addTestStep("restrequest","teststep1")

        println testCase1.getTestStepCount()

        List<TestStep> testSteps = relatedTestStepsSelector.getAllTestStepsFromTesSuitesInRestRequestProject(project)

        assertTrue testSteps.size() == 1
    }

}
