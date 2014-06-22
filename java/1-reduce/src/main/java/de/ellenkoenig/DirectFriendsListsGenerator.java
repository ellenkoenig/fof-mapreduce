package de.ellenkoenig;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;
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

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String currentKeyPerson = null;
        List<String> keyPersonsFriendList = new ArrayList<>();

        try {
            String inputPair = bufferedReader.readLine();

            while (inputPair != null) {
                String[] friendshipPair = inputPair.split("\\s+");
                if(friendshipPair.length == 2) {
                    String pairKeyPerson = friendshipPair[0];
                    String friend = friendshipPair[1];
                    boolean keyPersonHasNotChangedYet = currentKeyPerson != null && currentKeyPerson.equals(pairKeyPerson);

                    if(keyPersonHasNotChangedYet) {
                        keyPersonsFriendList.add(friend);
                    } else {
                        if(!keyPersonsFriendList.isEmpty()) {
                            out.println(currentKeyPerson + "\t" + StringUtils.join(keyPersonsFriendList, ","));
                        }
                        currentKeyPerson = pairKeyPerson;
                        keyPersonsFriendList.clear();
                        keyPersonsFriendList.add(friend);
                    }
                }
                inputPair = bufferedReader.readLine();
            }
            out.println(currentKeyPerson + "\t" + StringUtils.join(keyPersonsFriendList, ","));

        } catch (IOException e) {
            out.println("Could not read input from console, cause:" + e);
        }
    }

}
