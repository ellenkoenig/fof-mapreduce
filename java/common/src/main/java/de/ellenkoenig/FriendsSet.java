package de.ellenkoenig;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Represents all the friends of an individual person, as a set ordered lexicographically.
 * May, but need not, include a friendship relation of the person to themselves
 */
public class FriendsSet {

    private String person;
    private Set<String> friends;

    public static FriendsSet fromString(String input){
        //while I would like to split only on tabs, this seems to be unreliable while reading from System.in
        if(input != null) {
            String[] friendships = input.split("\\s+");
            if (friendships.length >= 2) {
                List<String> friends = new ArrayList<>(Arrays.asList(friendships));
                String person = friends.remove(0);
                return new FriendsSet(person, friends);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public FriendsSet() {
        this.friends = new TreeSet<>();
        this.person = null;
    }

    public FriendsSet(String person, Collection<String> friends) {
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



    public Set<FriendshipPair> generateFriendshipPairs() {
        Set<FriendshipPair> friendshipPairs = new HashSet<>();
        Set<String> allPeopleInFriendshipRelation = new TreeSet<>(friends);
        if(person != null && ! person.isEmpty()) {
            allPeopleInFriendshipRelation.add(person);
        }
        for(String friend: allPeopleInFriendshipRelation) {
            Set<String> otherFriends = new TreeSet<>(allPeopleInFriendshipRelation);
            otherFriends.remove(friend);
            for(String otherFriend : otherFriends) {
                friendshipPairs.add(new FriendshipPair(friend, otherFriend));
            }
        }
        return friendshipPairs;
    }

    @Override
    public String toString() {
        Set<String> friendsWithoutSelf = new TreeSet<>(friends);
        friendsWithoutSelf.remove(person);

        return (person != null && !friendsWithoutSelf.isEmpty()) ?
                person + "\t" + StringUtils.join(friendsWithoutSelf, "\t")
                : "";
    }

    public boolean hasFriends() {
        return ! friends.isEmpty();
    }
}
