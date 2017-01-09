package bsl.custom.actions

import bsl.custom.RelatedTestStepSelector
import bsl.custom.RelatedTestStepUpdater
import bsl.custom.TestStepSelector
import bsl.custom.TestStepUpdater
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep
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
        int updateAdvice = testStepsUpdater.replaceContentInRelatedTestSteps(relatedTestSteps, restRequest)
        scriptLogger.info("Total test steps updated="+updateAdvice)
	}
}