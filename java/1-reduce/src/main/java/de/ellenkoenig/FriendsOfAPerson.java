package de.ellenkoenig;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents the friend list of a person
 */
public class FriendsOfAPerson {
    private String person;
    private Set<String> friends;

    public FriendsOfAPerson() {
        this.person = null;
        friends = new TreeSet<>();
    }

    public String getPerson() {
        return person;
    }

    public boolean isPersonSet() {
        return person != null;
    }

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public boolean hasFriends(){
        return !friends.isEmpty();
    }

    public void reset(String newPerson) {
        this.person = newPerson;
        friends.clear();

    }

    @Override
    public String toString() {
         return person + "\t" + StringUtils.join(friends, ",");
    }

}
