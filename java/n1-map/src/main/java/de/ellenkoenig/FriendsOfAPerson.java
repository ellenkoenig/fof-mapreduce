package de.ellenkoenig;

import java.util.*;

/**
 * Represents the friend list of a person
 */
public class FriendsOfAPerson {
    private String person;
    private Set<String> friends;

    private FriendsOfAPerson(String person, Set<String> friends) {
        this.person = person;
        this.friends = friends;
    }

    public static FriendsOfAPerson createFromInputString(String input) throws IllegalArgumentException {
        FriendsOfAPerson friendsOfAPerson = null;

        String[] personAndFriends = input.split("\t");
        if (personAndFriends.length == 2) {
            String person = personAndFriends[0];
            List<String> friendList = Arrays.asList(personAndFriends[1].split(","));
            friendsOfAPerson = new FriendsOfAPerson(person, new TreeSet<>(friendList));
        } else {
            throw new IllegalArgumentException();
        }
        return friendsOfAPerson;
    }

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public String createReverseLookupPairs() {
        StringBuilder friendsList = new StringBuilder();
        for(String friend: friends) {
            friendsList.append(friend).append("\t#").append(person).append("\n");
        }
        return friendsList.toString();
    }
}
