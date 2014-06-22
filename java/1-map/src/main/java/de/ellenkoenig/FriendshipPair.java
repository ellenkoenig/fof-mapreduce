package de.ellenkoenig;

import java.lang.IllegalArgumentException;

/**
 * Represents a friendship pair based on the string input
 */
public class FriendshipPair {
    private String firstPersonsName;
    private String secondPersonsName;

    public FriendshipPair(String firstPersonsName, String secondPersonsName) {
        this.firstPersonsName = firstPersonsName;
        this.secondPersonsName = secondPersonsName;
    }

    public static FriendshipPair createFromInputLine(String inputLine, String separator) throws IllegalArgumentException {
        FriendshipPair friendshipPair;
        String[] pair = inputLine.split(separator);
        if(pair.length == 2) {
            friendshipPair = new FriendshipPair(pair[0], pair[1]);
        } else {
            throw new IllegalArgumentException();
        }
        return friendshipPair;
    }

   public String pairInNormalOrder(String separator) {
       return firstPersonsName + separator + secondPersonsName;
   }

   public String pairInReverseOrder(String separator) {
       return secondPersonsName + separator + firstPersonsName;
   }
}
