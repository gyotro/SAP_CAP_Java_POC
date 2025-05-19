<h1>CAP with Java Project:</h1>

* Run archetype: *mvn archetype:generate -DarchetypeGroupId=com.sap.cds -DarchetypeArtifactId=cds-services-archetype -DarchetypeVersion=RELEASE*
* add CF support: *mvn com.sap.cds:cds-maven-plugin:add -Dfeature=CF*
* npm install
* npm add -g @sap/cds-dk
* npm ci
* run with:  *mvn spring-boot:run
 *optional: insert kotlin support: add dependency, kotlin version and plugin in maven pom

 

