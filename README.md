#Replace Request In ALL TestSteps Action

##Phase 1 : Custom SoapUI Action extension

Adds a SoapUI menu action on the REST request that replaces the request content in all related TestSteps when clicked.

For usage and documentation please see the related blog article at:

(http://rupertanderson.com/blog/replace-request-teststeps-plugin/)

###Installation

1. Build `gradle clean build` 
2. Copy `<project home>/build/libs/ReplaceRequestInAllTestStepsAction-1.0-lib.jar` to `<SoapUI Home>/java/app/bin/ext/`
2. Copy `<project home>/actions/custom-actions.xml` to `<SoapUI Home>/java/app/bin/actions/`

###Tasks
- [x] Create tests
- [x] Code functionality
- [x] Refactored tests and implementation to get 100% coverage
- [x] Have done basic manual testing in SoapUI
- [ ] Add Gradle wrapper