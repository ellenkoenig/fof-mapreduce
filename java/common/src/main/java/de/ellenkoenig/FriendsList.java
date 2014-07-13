package de.ellenkoenig;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Represents all the friends of an individual person, as a set ordered lexicographically
 */
public class FriendsList {

    private String person;
    private Set<String> friends;

    public static FriendsList fromString(String input){
        //while I would like to split only on tabs, this seems to be unreliable while reading from System.in
        String[] friendships = input.split("\\s+");
        if(friendships.length >= 2) {
            List<String> friends = new ArrayList<>(Arrays.asList(friendships));
            //for the sake of generating friendship lists more easily,
            //I assume that people are friends with themselves
            String person = friends.get(0);
            return new FriendsList(person, friends);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public FriendsList() {
        this.friends = new TreeSet<>();
        this.person = null;
    }

    public FriendsList(String person, Collection<String> friends) {
        this.person = person;
        this.friends = new TreeSet<>(friends);
    }

    public boolean isKeyPersonTheSameAs(String keyPerson) {
        return person != null && person.equals(keyPerson);
    }

    public void addFriend(String friend){
        this.friends.add(friend);
    }

    public void reset(String newPerson) {
        this.person = newPerson;
        friends.clear();
        friends.add(newPerson);
    }

    public String generateFriendshipPairs() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String friend: friends) {
            Set<String> otherFriends = new TreeSet<>(friends);
            otherFriends.remove(friend);
            for(String otherFriend : otherFriends) {
                stringBuilder.append(friend).append("\t").append(otherFriend).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        Set<String> friendsWithoutSelf = new TreeSet<>(friends);
        friendsWithoutSelf.remove(person);

        return (person != null && !friendsWithoutSelf.isEmpty()) ?
                person + "\t" + StringUtils.join(friendsWithoutSelf, "\t") + "\t"
                : "";
    }

    public boolean hasFriends() {
        return ! friends.isEmpty();
    }
}
