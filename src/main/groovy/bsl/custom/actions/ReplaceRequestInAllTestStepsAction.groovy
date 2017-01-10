package bsl.custom.actions

import bsl.custom.RelatedTestStepSelector
import bsl.custom.RelatedTestStepUpdater
import bsl.custom.TestStepSelector
import bsl.custom.TestStepUpdater
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
import com.eviware.soapui.support.UISupport
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import org.apache.log4j.Logger

 public class ReplaceRequestInAllTestStepsAction extends AbstractSoapUIAction<RestRequest>{

     protected final Logger scriptLogger = Logger.getLogger("groovy.log")
     TestStepSelector testStepsSelector
     TestStepUpdater testStepsUpdater

    public ReplaceRequestInAllTestStepsAction() {
       super("Replace Request In All TestStep(s)", "Replaces request in ALL realated REST Request TestStep(s).")
        testStepsSelector = new RelatedTestStepSelector()
        testStepsUpdater = new RelatedTestStepUpdater()
	}
    
    @Override
    public void perform( RestRequest restRequest, Object param ) {
        List<RestTestRequestStep> relatedTestSteps = testStepsSelector.selectMatchingRESTRequestTestSteps(restRequest)
        int stepsUpdated = testStepsUpdater.replaceContentInRelatedTestSteps(relatedTestSteps, restRequest)
        UISupport.showInfoMessage(buildUpdateMessage(stepsUpdated, restRequest))
	}

     String buildUpdateMessage(int stepsUpdated, RestRequest restRequest){
         if (stepsUpdated>0)
            return "$stepsUpdated TestSteps for request $restRequest.name have been found and updated."
         else
             return "No TestSteps have been found for request $restRequest.name"
     }
}