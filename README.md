# Friend-Of-A-Friend computation

MapReduce (Hadoop streaming API) version of an algorithm to compute the friends of friends in a given friends graph iteratively  

## Building and running the project

To build, please run the following command:
````
mvn clean assembly:assembly
`````

To execute, chain the generated jars for as many iterations as you like. E.g. from the *"java"* directory, execute the following to run one iteration: 

````
cat ../testdata/testinput.txt | \
 java -jar map/target/map-1.0-jar-with-dependencies.jar |\
 sort -k1,1 | java -jar reduce/target/reduce-1.0-jar-with-dependencies.jar |\
 ````
 To see the requested output for n = 2, you can also run the "run.sh" in the "java" directory.
 
 
## Complexity of the implemented algorithm

The map step goes over each input line, and for each line, computes all permutations of each each name in the line. This results in a time complexity of O(|lines| * |names| ^ 2)

The reduce step goes over each of the |lines| * |names| ^ 2 pairs generated in the map step, and handles each pair exactly once to merge it into the resulting friends list. This also results in a complexity of  O(|lines| * |names| ^ 2).

The overall complexity of the algorithm is therefore  O(|lines| * |names| ^ 2)
 


