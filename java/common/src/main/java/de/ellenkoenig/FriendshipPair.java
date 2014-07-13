package de.ellenkoenig;

import java.lang.IllegalArgumentException;

/**
 * Represents a two people who are friends (either directly or indirectly)
 */
public class FriendshipPair {
    private String firstPersonsName;
    private String secondPersonsName;

    public FriendshipPair(String firstPersonsName, String secondPersonsName) {
        this.firstPersonsName = firstPersonsName;
        this.secondPersonsName = secondPersonsName;
    }

    public static FriendshipPair createFromInputLine(String inputLine) throws IllegalArgumentException {
        FriendshipPair friendshipPair;
        //while I would like to split only on tabs, this seems to be unreliable while reading from System.in
        String[] pair = inputLine.split("\\s+");
        if(pair.length == 2) {
            friendshipPair = new FriendshipPair(pair[0], pair[1]);
        } else {
            throw new IllegalArgumentException();
        }
        return friendshipPair;
    }

    public String getFirstPersonsName() {
        return firstPersonsName;
    }

    public String getSecondPersonsName() {
        return secondPersonsName;
    }
}
