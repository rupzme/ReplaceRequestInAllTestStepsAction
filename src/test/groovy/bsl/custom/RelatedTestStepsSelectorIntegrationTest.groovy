package bsl.custom

import com.eviware.soapui.impl.actions.RestServiceBuilder
import com.eviware.soapui.impl.rest.RestMethod
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestRequestInterface
import com.eviware.soapui.impl.rest.RestResource
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.teststeps.registry.RestRequestStepFactory
import com.eviware.soapui.model.testsuite.TestCase
import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by Rupert on 02/01/2017.
 *
 *  Just for reference, the general structure of a SoapUI REST service is:
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
        serviceBuilder.createRestService(project, endpointURI)

        RestRequest restRequest = project.getInterfaceList()[0].getOperationList()[0].getRequestList()[0]

        List<TestStep> testSteps = relatedTestStepsSelector.selectMatchingRESTRequestTestSteps(restRequest)

        assertTrue testSteps.size() == 0
    }

    /**
     * This test aims to test whether the selection code can match only TestSteps from the
     * selected REST request in a varied project structure.
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
     * -TestSuite1
     * --TestCase1
     * ---REST TestStep (for request1 / GET)
     * ---REST TestStep (for request2 / POST)
     * ---REST TestStep (for request3 / PUT)
     * ---REST TestStep (for request4 / PATCH - restTestStep4)
     * ---REST TestStep (for request5 / DELETE)
     * ---REST TestStep (for request6 / HEAD)
     * ---REST TestStep (for request7 / TRACE)
     * ---REST TestStep (for request8 / OPTIONS)
     *
     * -TestSuite2
     * --TestCase2
     * ---Groovy TestStep
     * ---JDBC TestStep
     * ---Properties TestStep
     * ---Transfer TestStep
     * ---Call TestCase TestStep
     * ---TestRequest TestStep
     * ---Manual TestStep
     * ---REST TestStep (for request4 / PATCH - restTestStep9)
     *
     * Selecting request4 should return only restTestStep4 and restTestStep9.
     */
    void testShouldOnlyReturnRestTestStepsThatAreRelatedToTheSelectedRestRequest(){

        //GIVEN
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
        def restTestStep4 = testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest4, "restTestStep4"))

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

        TestCase testCase2 = project.addNewTestSuite("TestSuite2").addNewTestCase("TestCase2")

        testCase2.addTestStep("groovy",'TestGroovyTestStep')
        //testCase2.addTestStep("httprequest",'TestHttpRequestTestStep') //TODO need to create with buildTestStep
        testCase2.addTestStep("jdbc",'TestJDBCTestStep')
        testCase2.addTestStep("properties",'TestPropertiesTestStep')
        testCase2.addTestStep("transfer",'TestTransferTestStep')
        testCase2.addTestStep("calltestcase",'TestCallTestcaseTestStep')
        testCase2.addTestStep("request",'TestRequestTestStep')
        testCase2.addTestStep("manualTestStep",'TestJDBCManualTestStep')
        //testCase2.addTestStep("mockresponse",'TestJDBCMockResponseTestStep')  //TODO need to create with buildTestStep
        def restTestStep9 = testCase2.addTestStep(RestRequestStepFactory.createConfig(restRequest4, "restTestStep9"))

        //WHEN
        List<TestStep> testSteps = relatedTestStepsSelector.selectMatchingRESTRequestTestSteps(restRequest4)

        //THEN
        assert testSteps.size()==2
        assert testSteps.contains(restTestStep4)
        assert testSteps.contains(restTestStep9)

    }
}
