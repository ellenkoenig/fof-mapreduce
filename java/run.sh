#!/bin/sh
cat ../testdata/testinput.txt | \
 java -jar map/target/map-1.0-jar-with-dependencies.jar |\
 sort -k1,1 | java -jar reduce/target/reduce-1.0-jar-with-dependencies.jar |\
 java -jar map/target/map-1.0-jar-with-dependencies.jar |\
 sort -k1,1 | java -jar reduce/target/reduce-1.0-jar-with-dependencies.jar