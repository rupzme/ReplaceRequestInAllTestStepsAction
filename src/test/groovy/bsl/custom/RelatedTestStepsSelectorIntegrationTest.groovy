package bsl.custom

import com.eviware.soapui.impl.actions.RestServiceBuilder
import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestRequestInterface
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.impl.wsdl.teststeps.registry.RestRequestStepFactory
import com.eviware.soapui.model.testsuite.TestCase
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 02/01/2017.
 *
 *  The structure of a SoapUI REST service is:
 *  Project (Even REST Projects are 'WsdlProject' objects)
 *      -> RestService(s) (type of 'Interface' - has an associated Endpoint)
 *          -> RestResource(s) (type of 'Operation')
 *              -> RestMethod(s)
 *                  -> RestRequest(s)
 */
class RelatedTestStepsSelectorIntegrationTest extends GroovyTestCase{

    RelatedTestStepSelector relatedTestStepsSelector = new RelatedTestStepSelector()

    def endpointURI = "http://test.com"

    /**
     * Test for case where a REST Service exists, but the project has no TestSteps
     */
    void testShouldReturnEmptyListIfProjectHasNoTestSteps(){

        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()
        serviceBuilder.createRestService(project, endpointURI) //Creates

        List<TestStep> testSteps = relatedTestStepsSelector.getAllRestRequestTestStepsInProjectWithRequestBodies(project)

        assertTrue testSteps.size() == 0
    }

    //TODO Next steps:
    //c) Assert that exactly the expected test steps are returned.
    /**
     *
     * Create a test setup like:
     * -project
     * --RestService
     * ---RestResource
     * ----RestMethod (GET)
     * -----RestRequest (restRequest1)
     * ---RestResource
     * ----RestMethod (POST)
     * -----RestRequest (restRequest2)
     *
     * -TestSuite
     * --TestCase
     * ---REST TestStep (for request1 / GET / Should NOT be selected)
     * ---REST TestStep (for request2 / POST / Should be selected)
     * ---REST TestStep (for request3 / PUT / Should be selected)
     * ---REST TestStep (for request4 / PATCH / Should be selected)
     * ---REST TestStep (for request5 / DELETE / Should be selected)
     * ---REST TestStep (for request5 / HEAD / Should NOT be selected)
     * ---REST TestStep (for request5 / TRACE / Should NOT be selected)
     * ---REST TestStep (for request5 / OPTIONS / Should NOT be selected)
     */
    //TODO Work out how a RestRequest is related to TestSteps based on it - is it the RestRequestConfig?
    //TODO How best to identify TestSteps for the RestRequest that was right-clicked? We could use:
    // -Service endpoint & method type (and possibly 'name') & resource - need to see what happens in case:
    //  -multiple RestRequests & related TestSteps under the same method - how does SoapUI differentiate related TestSteps?
    //  -Need a separate test for this edge case

    void testShouldOnlyReturnRestTestStepsThatHaveARequestBody(){

        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()

        //Create a default RestService(no endpoint)->RestResource(empty)->Method(GET)->RestRequest
        serviceBuilder.createRestService(project, endpointURI)
        TestCase testCase1 = project.addNewTestSuite("TestSuite1").addNewTestCase("TestCase1")

        RestRequest restRequest1 = project.getInterfaceList()[0].getOperationList()[0].getRequestList()[0]
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest1, "restTestStep1"))

        RestResource restResource1 = project.getInterfaceList()[0].getOperationList()[0]

        //Add POST TestStep
        RestMethod restMethod2 = restResource1.addNewMethod("method2")
        restMethod2.setMethod(RestRequestInterface.HttpMethod.POST)
        RestRequest restRequest2 = restMethod2.addNewRequest("restRequest2")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest2, "restTestStep2"))

        //Add PUT TestStep
        RestMethod restMethod3 = restResource1.addNewMethod("method3")
        restMethod3.setMethod(RestRequestInterface.HttpMethod.PUT)
        RestRequest restRequest3 = restMethod3.addNewRequest("restRequest3")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest3, "restTestStep3"))

        //Add PATCH TestStep
        RestMethod restMethod4 = restResource1.addNewMethod("method4")
        restMethod4.setMethod(RestRequestInterface.HttpMethod.PATCH)
        RestRequest restRequest4 = restMethod4.addNewRequest("restRequest4")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest4, "restTestStep4"))

        //Add DELETE TestStep
        RestMethod restMethod5 = restResource1.addNewMethod("method5")
        restMethod5.setMethod(RestRequestInterface.HttpMethod.DELETE)
        RestRequest restRequest5 = restMethod5.addNewRequest("restRequest5")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest5, "restTestStep5"))

        //Add HEAD TestStep
        RestMethod restMethod6 = restResource1.addNewMethod("method6")
        restMethod6.setMethod(RestRequestInterface.HttpMethod.HEAD)
        RestRequest restRequest6 = restMethod6.addNewRequest("restRequest6")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest6, "restTestStep6"))

        //Add TRACE TestStep
        RestMethod restMethod7 = restResource1.addNewMethod("method7")
        restMethod7.setMethod(RestRequestInterface.HttpMethod.TRACE)
        RestRequest restRequest7 = restMethod7.addNewRequest("restRequest7")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest7, "restTestStep7"))

        //Add OPTIONS TestStep
        RestMethod restMethod8 = restResource1.addNewMethod("method8")
        restMethod8.setMethod(RestRequestInterface.HttpMethod.OPTIONS)
        RestRequest restRequest8 = restMethod8.addNewRequest("restRequest8")
        testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest8, "restTestStep8"))

        println testCase1.getTestStepCount()

        List<TestStep> testSteps = relatedTestStepsSelector.getAllRestRequestTestStepsInProjectWithRequestBodies(project)

        assert testSteps.size()==4

        testSteps.each{testStep ->
            assert testStep.class == com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
            assert ((RestTestRequestStep)testStep).restMethod.hasRequestBody()
        }
    }

    /**
     * Test that non REST TestSteps are not selected (and do not break anything).
     */
    void testShouldOnlyReturnRestTestSteps(){
        WsdlProject project = new WsdlProject()
        RestServiceBuilder serviceBuilder = new RestServiceBuilder()

        //Create a default RestService(no endpoint)->RestResource(empty)->Method(GET)->RestRequest
        serviceBuilder.createRestService(project, endpointURI)
        TestCase testCase1 = project.addNewTestSuite("TestSuite1").addNewTestCase("TestCase1")

        testCase1.addTestStep("groovy",'TestGroovyTestStep')
        //testCase1.addTestStep("httprequest",'TestHttpRequestTestStep') //TODO need to create with buildTestStep
        testCase1.addTestStep("jdbc",'TestJDBCTestStep')
        testCase1.addTestStep("properties",'TestPropertiesTestStep')
        testCase1.addTestStep("transfer",'TestTransferTestStep')
        testCase1.addTestStep("calltestcase",'TestCallTestcaseTestStep')
        testCase1.addTestStep("request",'TestRequestTestStep')
        testCase1.addTestStep("manualTestStep",'TestJDBCManualTestStep')
        //testCase1.addTestStep("mockresponse",'TestJDBCMockResponseTestStep')  //TODO need to create with buildTestStep

        println testCase1.getTestStepCount()

        List<TestStep> testSteps = relatedTestStepsSelector.getAllRestRequestTestStepsInProjectWithRequestBodies(project)

        assert testSteps.size()==0
    }

    //TODO need to test selectMatchingRESTRequestTestSteps
}
