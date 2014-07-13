package de.ellenkoenig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static java.lang.System.*;

/**
 * Map  step of the algorithm, which takes a friend list of at least two people as a given input line
 * and outputs all permutations of friendship pairs that can be generated from that list.
 * In the output, each pair is one line of text.
 *
 * E.g. an input line of "Walter Lisa Fred" would generate the pairs "Walter Lisa", "Walter Fred" and "Lisa Fred",
 * plus their reverse versions such as "Lisa Walter" etc.
 */
public class FriendshipPairsGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(FriendshipPairsGenerator.class);

    public static void main(String [ ] args) {
        LOG.info("Starting step 1: Map");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            String input = bufferedReader.readLine();
            while (input != null) {
                try {
                    FriendsList friends = FriendsList.fromString(input);
                    out.print(friends.generateFriendshipPairs());
                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", input);
                }
                input = bufferedReader.readLine();
            }
        } catch (IOException e) {
            LOG.error("Could not read input from console, reason: {}", e);
        }
    }

}
