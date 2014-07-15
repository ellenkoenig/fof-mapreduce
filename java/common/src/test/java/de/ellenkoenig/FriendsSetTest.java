package de.ellenkoenig;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FriendsSetTest {

    @Test
    public void generateFriendshipPairs_shouldReturnEmptySetForEmptyFriendSet() throws Exception {
        FriendsSet friendsSet = new FriendsSet();
        Set<FriendshipPair> result = friendsSet.generateFriendshipPairs();
        assertEquals(new HashSet<FriendshipPair>(), result);
    }

    @Test
    public void generateFriendshipPairs_shouldReturnSetOfAllPermutationsOfAGivenValidFriendSet() throws Exception {
        FriendsSet friendsSet = FriendsSet.fromString("Walter\tLisa\tSarah\n");
        Set<FriendshipPair> result = friendsSet.generateFriendshipPairs();
        Set<FriendshipPair> allPermutations = new HashSet<>();
        allPermutations.add(new FriendshipPair("Walter", "Lisa"));
        allPermutations.add(new FriendshipPair("Lisa", "Walter"));
        allPermutations.add(new FriendshipPair("Walter", "Sarah"));
        allPermutations.add(new FriendshipPair("Sarah", "Walter"));
        allPermutations.add(new FriendshipPair("Lisa", "Sarah"));
        allPermutations.add(new FriendshipPair("Sarah", "Lisa"));
        assertEquals(allPermutations, result);
    }

    @Test
    public void generateFriendshipPairs_shouldNotIncludeFriendshipWithSelfInResult() throws Exception {
        FriendsSet friendsSet = FriendsSet.fromString("Walter\tLisa\n");
        Set<FriendshipPair> result = friendsSet.generateFriendshipPairs();
        assertFalse(result.contains(new FriendshipPair("Walter", "Walter")));
        assertFalse(result.contains(new FriendshipPair("Lisa", "Lisa")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromString_shouldThrowIllegalArgumentExceptionForInvalidInput() throws Exception {
        FriendsSet.fromString("Walter\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromString_shouldThrowIllegalArgumentExceptionForNullInput() throws Exception {
        FriendsSet.fromString(null);
    }

    @Test
    public void fromString_shouldSucceedForValidInput() throws Exception {
        FriendsSet.fromString("Walter\tLisa\n");
    }
}