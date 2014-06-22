package de.ellenkoenig;

import java.io.*;

import static java.lang.System.*;

/**
 * Map sub-step of the second half of the n->n+1 MapReduce iteration,  which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph containing all friends of the n-th degree.
 *
 * This sub-step does not perform any transformation on the input, it is included only for completeness sake
 */
public class IdentityMapping {

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String inputPair = bufferedReader.readLine();
            while (inputPair != null) {
                out.println(inputPair);
                inputPair = bufferedReader.readLine();
            }
        } catch (IOException e) {
            err.println("Could not read input from console, cause:" + e);
        }
    }

}
