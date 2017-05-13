GENERAL

These CQL scripts are numbered so as to indicate some logical order of precedence when running them.
As expected, we first need to create a KeySpace, then the Tables, then populate them, then query them.

The Java/Spring application requires you to run script 01_create_keyspace_hospital.cql before running the application.
This script creates the keyspace. The other scripts are not necessary, as the application creates and populates
the Tables by herself, using Java code.

You can however run all scripts yourself. Note thought that data samples for Table heart_rate_by_patient_and_time
are only a few.



HOW TO USE THE CQL SCRIPTS

* How to run the Java/Spring application so as to have the Tables created and populated:
   ./mvnw spring-boot:run -Drun.arguments="init"

* How to run the Java/Spring application without having the Tables created and populated:
   ./mvnw spring-boot:run

* How to execute the CQL Scripts:

You can use either one of these tools: cqlsh, DevCenter.
The first one comes with Cassandra (both Apache and DataStax) by default.
The last one comes only with DataStax Cassandra but it is way more user friendly. You can find it here:
http://www.datastax.com/what-we-offer/products-services/devcenter

Enjoy!
