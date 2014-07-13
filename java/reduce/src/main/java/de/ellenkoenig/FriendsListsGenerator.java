package de.ellenkoenig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Reduce sub-step of the first MapReduce step,  which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph of direct friends.
 *
 * This sub-step combines all of the friendship pairs for a person generated in the map step into a list containing
 * all first-degree friends.
 * The input list of friendship pairs must be sorted by the first element in the pair.
 *
 */
public class FriendsListsGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(FriendsListsGenerator.class);

    public static void main(String[] args) {
        LOG.info("Starting step 1: Reduce");
        FriendsList friendsOfCurrentKeyPerson = new FriendsList();
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
                        outputFriendsList(friendsOfCurrentKeyPerson);
                        friendsOfCurrentKeyPerson.reset(currentFriendsPair.getFirstPersonsName());
                        friendsOfCurrentKeyPerson.addFriend(currentFriendsPair.getSecondPersonsName());

                    }

                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", input);
                }
                input = bufferedReader.readLine();
            }
            out.println(friendsOfCurrentKeyPerson.toString());

        } catch (IOException e) {
            LOG.error("Could not read input from console, cause {}:", e);
        }
    }

    private static void outputFriendsList(FriendsList friendsList) {
        if (friendsList.hasFriends()) {
            out.println(friendsList.toString());
        }
    }

}