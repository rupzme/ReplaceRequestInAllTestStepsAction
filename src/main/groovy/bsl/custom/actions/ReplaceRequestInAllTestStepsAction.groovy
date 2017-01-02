package bsl.custom.actions

import bsl.custom.RelatedTestStepsSelector
import bsl.custom.RelatedTestStepsUpdater
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestStep
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import org.apache.log4j.Logger

import java.util.List

 public class ReplaceRequestInAllTestStepsAction extends AbstractSoapUIAction<RestRequest>{

     protected final Logger scriptLogger = Logger.getLogger("groovy.log")
     private RelatedTestStepsSelector relatedTestStepsSelector = new RelatedTestStepsSelector()
     private RelatedTestStepsUpdater relatedTestStepsUpdater = new RelatedTestStepsUpdater()

    public ReplaceRequestInAllTestStepsAction() {
       super("Replace Request In All TestStep(s)", "Replaces request in ALL realated REST Request TestStep(s).")
	}
    
    @Override
    public void perform( RestRequest restRequest, Object param ) {
        scriptLogger.info("clicked!")



        List<TestStep> relatedTestSteps = relatedTestStepsSelector.selectMatchingTestSteps(restRequest)

        String newRequestContent = restRequest.requestContent
        String updateAdvice = relatedTestStepsUpdater.replaceContentInRelatedTestSteps(relatedTestSteps, newRequestContent)
        scriptLogger.info("updateAdvice="+updateAdvice)
	}
}