package bsl.custom

import com.eviware.soapui.impl.rest.RestRequest
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep

/**
 * Created by Rupert Anderson on 29/12/2016.
 */
class RelatedTestStepUpdater implements TestStepUpdater{
    /**
     * Update a list of REST TestSteps with new request content
     *
     * @param relatedTestSteps - The list of REST TestSteps to be changed.
     * @param restRequest - The REST request which has the updated request content.
     * @return
     */
    public String replaceContentInRelatedTestSteps(List<RestTestRequestStep> relatedTestSteps, RestRequest restRequest){
        return null
    }
}
