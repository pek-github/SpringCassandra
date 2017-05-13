HOW TO RUN THE APPLICATION

Run the Application, using your command line, after you navigate to this directory, named 'SpringCassandra',
with a single command and comma-separated arguments, according to the following instructions:

Run the application without initializing the Cassandra Database:
./mvnw spring-boot:run

Run the application and initialize the Cassandra Database:
./mvnw spring-boot:run -Drun.arguments="init"

The Database initialization will create and populate the essential Tables, but will not create the needed keyspace.
Refer to documentation in directory 'cql' for more information!



OTHER VERY USEFUL DOCUMENTS

* File  ./cql/README.txt
* File  ./documents/rest.txt



Have fun!
