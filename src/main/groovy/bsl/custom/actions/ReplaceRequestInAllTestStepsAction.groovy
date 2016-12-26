package bsl.custom.actions 
 
import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.support.action.support.AbstractSoapUIAction

 public class ReplaceRequestInAllTestStepsAction extends AbstractSoapUIAction<RestRequest>{
    
    public ReplaceRequestInAllTestStepsAction() {
       super("Replace Request In All TestStep(s)", "Replaces request in ALL realated REST Request TestStep(s).")
	}
    
    @Override
    public void perform( RestRequest restRequest, Object param ) {
    	System.out.println("clicked!")
	} 
}