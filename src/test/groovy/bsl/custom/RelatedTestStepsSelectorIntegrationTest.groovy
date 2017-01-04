package bsl.custom

import com.eviware.soapui.impl.actions.RestServiceBuilder
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.teststeps.registry.RestRequestStepFactory
import com.eviware.soapui.model.testsuite.TestCase
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 02/01/2017.
 */
class RelatedTestStepsSelectorIntegrationTest extends GroovyTestCase{

    RelatedTestStepSelector relatedTestStepsSelector = new RelatedTestStepSelector()
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

    //TODO Next steps:
    //-Create more Test TestSteps of different Types (check they don't break - separate test?) / methods (GET etc) / endpoints and test that selector will extract just
    //a) POST / PUT methods
    //b) only select TestSteps that match the same Service Endpoint
    //c) Assert that exactly the expected test steps are returned.
    void testShouldReturnTestStepsListIfProjectTestSteps(){
        def uri = "http://jsonplaceholder.typicode.com/posts/1"

        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()
        serviceBuilder.createRestService(project, uri)
        TestCase testCase1 = project.addNewTestSuite("TestSuite1").addNewTestCase("TestCase1")

        RestRequest restRequest1 = project.getInterfaceList()[0].getOperationList()[0].getRequestList()[0]
        restRequest1.setName("restTestStep1")

        //TODO extract creation of Test Rest TestSteps to a private method
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest1, restRequest1.getName()));

        //println testCase1.getTestStepCount()

        List<TestStep> testSteps = relatedTestStepsSelector.getAllTestStepsFromTesSuitesInRestRequestProject(project)

        assertTrue testSteps.size() == 1
    }

    /*
    RestMethod makeRestMethod() throws SoapUIException {

        new RestService(project, RestServiceConfig.Factory.newInstance())
        RestResource restResource = new RestResource(makeRestService(), RestResourceConfig.Factory.newInstance())
        RestMethod restMethod = new RestMethod(restResource, RestMethodConfig.Factory.newInstance())
        restMethod.setMethod(RestRequestInterface.HttpMethod.POST)
        return restMethod
    }


    public static RestService makeRestService() throws SoapUIException {
        return new RestService(makeWsdlProject(), RestServiceConfig.Factory.newInstance());
    }
    */

}
