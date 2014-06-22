package de.ellenkoenig;

import java.io.*;

import static java.lang.System.*;

/**
 * Map sub-step of the first MapReduce step, which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph containing the direct friends.
 *
 * This sub-step duplicates the given input friendship pairs with the duplicate pair having the friends in reverse order.
 */
public class DuplicateFriendshipPairsGenerator {

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String inputPair = bufferedReader.readLine();
            while (inputPair != null) {
                String[] pair = inputPair.split("\\s+");
                if(pair.length == 2) {
                    out.println(pair[0] + "\t" + pair[1]);
                    out.println(pair[1] + "\t" + pair[0]);
                }
                inputPair = bufferedReader.readLine();
            }

        } catch (IOException e) {
            out.println("Could not read input from console, cause:" + e);
        }
    }

}
