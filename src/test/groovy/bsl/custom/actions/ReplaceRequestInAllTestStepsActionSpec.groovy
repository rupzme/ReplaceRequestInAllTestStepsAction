package bsl.custom.actions

import bsl.custom.TestStepSelector
import bsl.custom.TestStepUpdater
import com.eviware.soapui.impl.rest.RestRequest

/**
 * Created by Rupert on 02/01/2017.
 */
class ReplaceRequestInAllTestStepsActionSpec extends spock.lang.Specification {

    ReplaceRequestInAllTestStepsAction replaceRequestInAllTestStepsAction = new ReplaceRequestInAllTestStepsAction()

    def "Should call Selector and Updater dependencies once when perform method is called"() {
        setup:
            TestStepSelector testStepSelector = Mock()
            TestStepUpdater testStepUpdater = Mock()
            RestRequest restRequest = Mock()
            restRequest.name >> "TestRESTRequest1"

            replaceRequestInAllTestStepsAction.testStepsSelector = testStepSelector
            replaceRequestInAllTestStepsAction.testStepsUpdater = testStepUpdater

        when:
            replaceRequestInAllTestStepsAction.perform(restRequest, null)

        then:
            1 * testStepSelector.selectMatchingRESTRequestTestSteps(restRequest)
            1 * testStepUpdater.replaceContentInRelatedTestSteps(*_)
    }

    def "If no TestSteps were updated then return message like no steps updated..."() {
        setup:
            RestRequest restRequest = Mock()
            restRequest.name >> "TestRESTRequest1"

        when:
            String message = replaceRequestInAllTestStepsAction.buildUpdateMessage(0,restRequest)

        then:
            message == "No TestSteps have been found for request TestRESTRequest1"
    }

    def "If 7 TestSteps were updated then return message like 7 TestSteps updated..."() {
        setup:
            RestRequest restRequest = Mock()
            restRequest.name >> "TestRESTRequest1"

        when:
            String message = replaceRequestInAllTestStepsAction.buildUpdateMessage(7,restRequest)

        then:
            message == "7 TestSteps for request TestRESTRequest1 have been found and updated."
    }

}