package de.ellenkoenig;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.*;
import static java.lang.System.out;

/**
 * Map sub-step of the second half of the n->n+1 MapReduce iteration,  which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph containing all friends of the n-th degree.
 *
 * This step combines all of the friendship lists generated previously into one list per key person,
 * with all duplicates removed and the friends in each list sorted lexicographically
 *
 */
public class CompleteFriendsListsGenerator {

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String currentKeyPerson = null;
        Set<String> keyPersonsFriendList = new TreeSet<>();

        try {
            String inputPair = bufferedReader.readLine();

            while (inputPair != null) {
                String[] friendshipPair = inputPair.split("\\s+");
                if(friendshipPair.length == 2) {
                    String pairKeyPerson = friendshipPair[0];
                    String[] friends = friendshipPair[1].split(",");
                    boolean keyPersonHasNotChangedYet = currentKeyPerson != null && currentKeyPerson.equals(pairKeyPerson);

                    if(keyPersonHasNotChangedYet) {
                        keyPersonsFriendList.addAll(Arrays.asList(friends));
                    } else {
                        outputFriendsList(currentKeyPerson, keyPersonsFriendList);
                        currentKeyPerson = pairKeyPerson;
                        keyPersonsFriendList.clear();
                        keyPersonsFriendList.addAll(Arrays.asList(friends));                    }
                }
                inputPair = bufferedReader.readLine();
            }
            outputFriendsList(currentKeyPerson, keyPersonsFriendList);

        } catch (IOException e) {
            out.println("Could not read input from console, cause:" + e);
        }

    }

    private static void outputFriendsList(String currentKeyPerson, Set<String> keyPersonsFriendList) {
        if(currentKeyPerson != null) {
            keyPersonsFriendList.remove(currentKeyPerson);
        }
        if(!keyPersonsFriendList.isEmpty()) {
            out.println(currentKeyPerson + "\t" + StringUtils.join(keyPersonsFriendList, "\t"));
        }
    }

}
