fof-mapreduce
=============

MapReduce (Hadoop streaming API) version of an algorithm to compute the friends of friends in a given friends graph iteratively  

To build, please run the following command (tests are currently not working due to the rewrite)
````
mvn clean assembly:assembly -DskipTests 
`````

To execute, chain the generated jars for as many iterations as you like, e.g. from the "java" directory, execute the following to run one iteration: 

````
cat ../testdata/testinput.txt | \
 java -jar map/target/map-1.0-jar-with-dependencies.jar |\
 sort -k1,1 | java -jar reduce/target/reduce-1.0-jar-with-dependencies.jar |\
 ````
 To achieve the requested output for n = 2, you can run the "run.sh" in the "java" directory.
 
 


