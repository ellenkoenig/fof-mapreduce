package de.ellenkoenig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Reduce step of the algorithm, which combines all of the friendship pairs for a person generated
 * in the map step into a list containing all direct (= 1st degree) and indirect (= n-th degree) friends.
 * The input list of friendship pairs must be sorted by the first element in the pair.
 *
 */
public class FriendsSetsGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(FriendsSetsGenerator.class);

    public static void main(String[] args) {
        LOG.info("Starting step 1: Reduce");
        FriendsSet friendsOfCurrentKeyPerson = new FriendsSet();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            String input = bufferedReader.readLine();
            while (input != null) {
                try {
                    FriendshipPair currentFriendsPair = FriendshipPair.createFromInputLine(input);
                    boolean keyPersonIsStillTheSame = friendsOfCurrentKeyPerson.isKeyPersonTheSameAs(currentFriendsPair.getFirstPersonsName());
                    if (keyPersonIsStillTheSame) {
                        friendsOfCurrentKeyPerson.addFriend(currentFriendsPair.getSecondPersonsName());
                    } else {
                        outputFriends(friendsOfCurrentKeyPerson);
                        friendsOfCurrentKeyPerson.reset(currentFriendsPair.getFirstPersonsName());
                        friendsOfCurrentKeyPerson.addFriend(currentFriendsPair.getSecondPersonsName());
                    }

                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", input);
                }
                input = bufferedReader.readLine();
            }
            outputFriends(friendsOfCurrentKeyPerson);
        } catch (IOException e) {
            LOG.error("Could not read input from console, cause {}:", e);
        }
    }

    private static void outputFriends(FriendsSet friends) {
        if (friends.hasFriends()) {
            out.println(friends.toString());
        }
    }

}