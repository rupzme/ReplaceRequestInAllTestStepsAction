#Replace Request In ALL TestSteps Action

##Phase 1 : Custom SoapUI Action extension

Adds a SoapUI menu action on the REST request that replaces the request content in all related TestSteps when clicked.

For usage and documentation please see the related blog article at:

(http://rupertanderson.com/blog/replace-request-teststeps-plugin/)

###Installation

1. To build: `./gradlew clean build` or `gradle clean build` (if you have Gradle installed)
2. Copy `<project home>/build/libs/ReplaceRequestInAllTestStepsAction-1.0-lib.jar` to `<SoapUI Home>/java/app/bin/ext/`
3. Copy `<project home>/src/main/resources/custom-actions.xml` to `<SoapUI Home>/java/app/bin/actions/`
4. Start / restart SoapUI

###Tasks Remaining
- [x] Have done basic manual testing in SoapUI
- [ ] Release to SoapUI community and get feedback
