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

/**
 * Created by Rupert Anderson on 09/01/17.
 */
class RelatedTestStepUpdaterTest extends GroovyTestCase{

    public static final String updatedRequest3Content = "updated requestcontent 3"
    public static final String request3Content = "request content 3"
    public static final String request2Content = "request content 2"
    public static final String request4Content = "request content 4"

    RelatedTestStepUpdater relatedTestStepUpdater = new RelatedTestStepUpdater()
    def endpointURI = "http://test.com"

    /**
     * Test that only matching teststeps are updated and that correct number of updated steps
     * is returned.
     */
    void testShouldUpdateContentForEachTestStepInListButNoOthersInProject(){

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
        restRequest2.requestContent = request2Content
        RestTestRequestStep testStep2 = testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest2, "restTestStep2"))

        //Add PUT TestStep
        RestMethod restMethod3 = restResource1.addNewMethod("method3")
        restMethod3.setMethod(RestRequestInterface.HttpMethod.PUT)
        RestRequest restRequest3 = restMethod3.addNewRequest("restRequest3")
        restRequest3.requestContent = request3Content
        RestTestRequestStep testStep3 = testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest3, "restTestStep3"))
        RestTestRequestStep testStep5 = testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest3, "restTestStep5"))

        //Add PATCH TestStep
        RestMethod restMethod4 = restResource1.addNewMethod("method4")
        restMethod4.setMethod(RestRequestInterface.HttpMethod.PATCH)
        RestRequest restRequest4 = restMethod4.addNewRequest("restRequest4")
        restRequest4.requestContent = request4Content
        RestTestRequestStep testStep4 = testCase1.addTestStep(RestRequestStepFactory.createConfig(restRequest4, "restTestStep4"))

        //WHEN
        List<RestTestRequestStep> matchingRestTestSteps = new ArrayList<RestTestRequestStep>()
        matchingRestTestSteps << testStep3
        matchingRestTestSteps << testStep5
        restRequest3.requestContent= updatedRequest3Content
        int updatedStepAdvice = relatedTestStepUpdater.replaceContentInRelatedTestSteps(matchingRestTestSteps,restRequest3)

        //THEN
        assert testStep2.httpRequest.requestContent==request2Content
        assert testStep3.httpRequest.requestContent==updatedRequest3Content
        assert testStep4.httpRequest.requestContent==request4Content
        assert testStep5.httpRequest.requestContent==updatedRequest3Content
        assert updatedStepAdvice==2
    }
}
