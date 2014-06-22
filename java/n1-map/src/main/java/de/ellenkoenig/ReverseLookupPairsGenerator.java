package de.ellenkoenig;

import java.io.*;

import static java.lang.System.*;

/**
 * Map sub-step of the first half of the n->n+1 MapReduce iteration,  which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph containing all friends of the n-th degree.
 *
 * This sub-step generates two output elements per key:
 *  1) the original friendship list of the n-1 step, with friends separated by ","
 *  2) Reverse lookup pairs allowing to look up the pair by its original value.
 *     The original key (now value) is marked with the  prefix "#"
 */
public class ReverseLookupPairsGenerator {

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String inputPair = bufferedReader.readLine();
            while (inputPair != null) {
                //output friendship list sorted by key
                out.println(inputPair);

                String[] sourceAndTargetNodesPair = inputPair.split("\\s+");

                if(sourceAndTargetNodesPair.length == 2) {
                    String sourceNode = sourceAndTargetNodesPair[0];
                    String[] targetNodes = sourceAndTargetNodesPair[1].split(",");
                    for(String targetNode: targetNodes) {
                        out.println(targetNode + "\t#" + sourceNode);
                    }
                }
                inputPair = bufferedReader.readLine();
            }

        } catch (IOException e) {
            err.println("Could not read input from console, cause:" + e);
        }
    }

}
