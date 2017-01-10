package bsl.custom.actions

import bsl.custom.TestStepSelector
import bsl.custom.TestStepUpdater
import com.eviware.soapui.impl.rest.RestRequest

/**
 * Created by Rupert on 02/01/2017.
 */
class ReplaceRequestInAllTestStepsActionSpec extends spock.lang.Specification {

    def "Should call Selector and Updater dependencies once when perform method is called"() {
        given:
            ReplaceRequestInAllTestStepsAction replaceRequestInAllTestStepsAction = new ReplaceRequestInAllTestStepsAction()
            TestStepSelector testStepSelector = Mock()
            TestStepUpdater testStepUpdater = Mock()
            RestRequest restRequest = Mock()
            replaceRequestInAllTestStepsAction.testStepsSelector = testStepSelector
            replaceRequestInAllTestStepsAction.testStepsUpdater = testStepUpdater

        when:
            replaceRequestInAllTestStepsAction.perform(restRequest, null)

        then:
            1 * testStepSelector.selectMatchingRESTRequestTestSteps(restRequest)
            1 * testStepUpdater.replaceContentInRelatedTestSteps(*_)
    }
}