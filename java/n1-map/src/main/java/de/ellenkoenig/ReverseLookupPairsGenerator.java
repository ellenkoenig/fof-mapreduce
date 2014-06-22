package de.ellenkoenig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(ReverseLookupPairsGenerator.class);

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String input = bufferedReader.readLine();
            while (input != null) {
                try {
                    FriendsOfAPerson friendsOfAPerson = FriendsOfAPerson.createFromInputString(input);
                    //output original friendship list sorted by key
                    out.println(input);
                    //output reverse lookup pairs based on the friendship list
                    out.print(friendsOfAPerson.createReverseLookupPairs());
                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", input);
                }
                input = bufferedReader.readLine();
            }
        } catch (IOException e) {
            LOG.error("Could not read input from console, cause {}:", e);
        }
    }

}
