# Friend-Of-A-Friend computation

MapReduce (Hadoop streaming API) version of an algorithm to compute the friends of friends in a given friends graph iteratively. 

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

The map step goes over each input line, and for each line, computes all permutations of each each name in the line. This results in a time complexity of ````O(| lines | * | names | ^ 2)````

The reduce step goes over each of the ````| lines | * | names | ^ 2 ```` pairs generated in the map step, and handles each pair exactly once to merge it into the resulting friends list. This also results in a complexity of ````O(| lines | * | names | ^ 2)````.

The overall complexity of the algorithm is therefore ````O(| lines | * | names | ^ 2)````.
This complexity analysis ignores the complexity of the sorting step which is not implemented my my algorithm itself, but is usually be performed in ````O(n * log n), with n = | lines | * | names | ````

## Correctness of the algorithm

The correctness will be shown by complete induction.

### n = 1
For the n = 1 step, the map step of the algorithm will generate all the inputted friendship pairs, plus any missing "reverse" friendship pairs (e.g. for an input of one line "Walter Lisa", the output will be "Walter Lisa" and "Lisa Walter"). These pairs represent all first-degree friends that can be computed from the given input.

The reduce step will combine the first-degree pairs (sorted by first name) and merge all lines starting with the same name into one list (e.g. for an input of "Walter Lisa" and "Walter Sarah", the output would be "Walter Lisa Sarah"), with the resulting friendlist list being sorted lexicographically thanks to the used data structure TreeSet. The result is the needed list of all first-degree friends of each person of the input, with the output lines being also sorted lexicographically because the order of the sorted input is not changed.

### n -> n + 1
For the step from a given input of n-th degree friends, the mapper will compute all the "n+1"-th degree friendship pairs for all the people that are part of the input. This is done by computing all the permutations of the n-th degree friendship lists generated in the n-th reduce step. We can be sure that the resulting permutations are all friends of maximum n+1 degree, because all the people in the list are friends via the first person in the list (e.g. in the case of a list "Walter Lisa Sarah", Lisa and Sarah are friends via Walter). So if they previously were friends with the first person via maximum n degrees, they are now friends with everybody in that friendship list via exactly one degree more (maximum n + 1 degrees) (e.g. if Walter is friends with Lisa and Sarah via 2 degrees, Sarah and Lisa are friends via 3 degrees).

The reducer again will just combine every friendship pair starting with the same name into one list, so the pairs of maximum n + 1 degrees will be combined into list of friends of maximum n + 1 degree. The same argument about the correctness of the sorting applies here as in the n = 1 step.
