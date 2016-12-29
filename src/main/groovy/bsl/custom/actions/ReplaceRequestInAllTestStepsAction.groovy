package bsl.custom.actions

import bsl.custom.RelatedTestStepsSelector
import bsl.custom.RelatedTestStepsUpdater
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.model.project.Project
import com.eviware.soapui.model.testsuite.TestStep
import com.eviware.soapui.support.action.support.AbstractSoapUIAction
import java.util.List

 public class ReplaceRequestInAllTestStepsAction extends AbstractSoapUIAction<RestRequest>{

     private RelatedTestStepsSelector relatedTestStepsSelector
     private RelatedTestStepsUpdater relatedTestStepsUpdater

    public ReplaceRequestInAllTestStepsAction() {
       super("Replace Request In All TestStep(s)", "Replaces request in ALL realated REST Request TestStep(s).")
	}
    
    @Override
    public void perform( RestRequest restRequest, Object param ) {
    	println("clicked!")
        Project project = restRequest.getProject()

        List<TestStep> relatedTestSteps = relatedTestStepsSelector.selectTestSteps(project, restRequest)

        String newRequestContent = restRequest.requestContent
        String updateAdvice = relatedTestStepsUpdater.replaceContentInRelatedTestSteps(relatedTestSteps, newRequestContent)
        println("updateAdvice="+updateAdvice)
	}
}