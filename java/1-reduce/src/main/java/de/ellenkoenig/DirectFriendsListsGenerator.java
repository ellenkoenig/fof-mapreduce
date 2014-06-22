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

    public static void main(String[] args) {
        LOG.info("Starting step 1: Reduce");
        FriendsOfAPerson friendsOfCurrentKeyPerson = new FriendsOfAPerson();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        try {
            String inputPair = bufferedReader.readLine();

            while (inputPair != null) {
                try {
                    FriendshipPair currentFriendsPair = FriendshipPair.createFromInputLine(inputPair, "\t");
                    friendsOfCurrentKeyPerson = mergeFriendsPairIntoFriendList(friendsOfCurrentKeyPerson, currentFriendsPair);
                } catch (IllegalArgumentException e) {
                    LOG.warn("Encountered malformed input line: {}", inputPair);
                }
                inputPair = bufferedReader.readLine();
            }
            out.println(friendsOfCurrentKeyPerson.toString());

        } catch (IOException e) {
            LOG.error("Could not read input from console, cause {}:", e);
        }
    }

    private static FriendsOfAPerson mergeFriendsPairIntoFriendList(FriendsOfAPerson friendList, FriendshipPair currentPair) {
        boolean isKeyPersonOfListUnchanged = friendList.isPersonSet()
                && friendList.getPerson().equals(currentPair.getKeyPerson());
        if (isKeyPersonOfListUnchanged) {
            friendList.addFriend(currentPair.getFriend());
        } else {
            friendList = outputAndRestartFriends(friendList, currentPair);
        }
        return friendList;
    }

    private static FriendsOfAPerson outputAndRestartFriends(FriendsOfAPerson friends, FriendshipPair currentFriendsPair) {
        if (friends.hasFriends()) {
            out.println(friends.toString());
        }
        friends.reset(currentFriendsPair.getKeyPerson());
        friends.addFriend(currentFriendsPair.getFriend());
        return friends;
    }
}