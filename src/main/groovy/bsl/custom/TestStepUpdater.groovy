package bsl.custom

import com.eviware.soapui.model.testsuite.TestStep

/**
 * Created by ubuntutest on 04/01/17.
 */
interface TestStepUpdater {
    public String replaceContentInRelatedTestSteps(List<TestStep> relatedTestSteps, newRequestContent)
}