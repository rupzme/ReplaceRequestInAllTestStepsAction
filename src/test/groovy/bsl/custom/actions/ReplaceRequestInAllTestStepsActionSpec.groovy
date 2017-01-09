package bsl.custom.actions

import bsl.custom.TestStepSelector
import bsl.custom.TestStepUpdater
import bsl.custom.actions.ReplaceRequestInAllTestStepsAction
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.rest.RestRequestInterface

/**
 * Created by Rupert on 02/01/2017.
 */

//TODO Improve test to stub testStepSelector.. to return a List<TestStep>
//TODO Improve test to stub restRequest.requestContent to return a string
//TODO and add this as a parameter when checking invocation of testStepUpdater.replaceContentInRelatedTestSteps
class ReplaceRequestInAllTestStepsActionSpec extends spock.lang.Specification {

    ReplaceRequestInAllTestStepsAction replaceRequestInAllTestStepsAction = new ReplaceRequestInAllTestStepsAction()
    TestStepSelector testStepSelector = Mock()
    TestStepUpdater testStepUpdater = Mock()
    RestRequest restRequest = Mock()

    def setup(){
        replaceRequestInAllTestStepsAction.testStepsSelector = testStepSelector
        replaceRequestInAllTestStepsAction.testStepsUpdater = testStepUpdater
    }

    def "Should call Selector and Updater dependencies once when perform method is called"() {
        when:
            replaceRequestInAllTestStepsAction.perform(restRequest, null)

        then:
            1 * testStepSelector.selectMatchingRESTRequestTestSteps(restRequest)
            1 * testStepUpdater.replaceContentInRelatedTestSteps(*_)
    }
}