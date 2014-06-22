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
public class DirectFriendsListsGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(DirectFriendsListsGenerator.class);

    public static void main(String [ ] args) {
        LOG.info("Starting step 1: Reduce");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        FriendsOfAPerson friendsOfKeyPerson = new FriendsOfAPerson();

        try {
            String inputPair = bufferedReader.readLine();

            while (inputPair != null) {
                try {
                    FriendshipPair currentFriendsPair = FriendshipPair.createFromInputLine(inputPair, "\t");
                    friendsOfKeyPerson = mergeFriendsPairIntoFriendList(friendsOfKeyPerson, currentFriendsPair);
                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", inputPair);
                }
                inputPair = bufferedReader.readLine();
            }
            out.println(friendsOfKeyPerson.toString());

        } catch (IOException e) {
            LOG.error("Could not read input from console, cause {}:", e);
        }
    }

    private static FriendsOfAPerson mergeFriendsPairIntoFriendList(FriendsOfAPerson friendsOfKeyPerson, FriendshipPair currentFriendsPair) {
        boolean keyPersonHasNotChangedYet = friendsOfKeyPerson.isPersonSet()
                                          && friendsOfKeyPerson.getPerson().equals(currentFriendsPair.getKeyPerson());

        if(keyPersonHasNotChangedYet) {
            friendsOfKeyPerson.addFriend(currentFriendsPair.getFriend());
        } else {
            friendsOfKeyPerson = outputAndResetFriendList(friendsOfKeyPerson, currentFriendsPair);
        }
        return friendsOfKeyPerson;
    }

    private static FriendsOfAPerson outputAndResetFriendList(FriendsOfAPerson friendsOfKeyPerson, FriendshipPair currentFriendsPair) {
        if(friendsOfKeyPerson.hasFriends()) {
            out.println(friendsOfKeyPerson.toString());
        }
        friendsOfKeyPerson.reset(currentFriendsPair.getKeyPerson());
        friendsOfKeyPerson.addFriend(currentFriendsPair.getFriend());
        return friendsOfKeyPerson;
    }

}
