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
 * Map sub-step of the first half of the n->n+1 MapReduce iteration,  which aims to transform the pair list given in the input file
 * into an adjacency list representation of a friendship graph containing all friends of the n-th degree.
 *
 * This step combines all of the reverse lookup friendship pairs generate in the map step
 * and the "up to n-1 degree" friendship list for each person into  a list of all friends up to the n-th degree
 * The input list must be sorted by the key (first element in each line), where a key refers to a person and the values
 * are either friendship lists (with friends separated by ","), or reverse lookup values (denoted by a "#" prefix of the value)
 * There must be exactly one friendship list per key in the input
 *
 *  TODO: min Testcases: shouldEmitOriginalFriendshipList, shouldEmitNewIndirectFriends (test with only input b-#, b-c, expected: contains c-a)

 */
public class IndirectFriendsListsGenerator {

    public static void main(String [ ] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String currentKeyPerson = null;
        List<String> currentPersonsNewIndirectFriends = new ArrayList<>();
        String currentPersonsPreviouslyComputedFriends = null;

        try {
            String inputPair = bufferedReader.readLine();

            while (inputPair != null) {
                String[] keyValuePair = inputPair.split("\\s+");
                if(keyValuePair.length == 2) {
                    String keyPerson = keyValuePair[0];
                    boolean keyPersonHasNotChangedYet = currentKeyPerson != null && currentKeyPerson.equals(keyPerson);

                    String value = keyValuePair[1];
                    boolean valueIsFriendshipList = !value.startsWith("#");

                    if(keyPersonHasNotChangedYet) {
                        if(valueIsFriendshipList) {
                            //we need to preserve the previously computed friendship list
                            // for the current person in the original format in the output
                            out.println(inputPair);
                            boolean noCopyOfThePreviousFriendsListWasFoundYet = (currentPersonsPreviouslyComputedFriends == null);
                            assert noCopyOfThePreviousFriendsListWasFoundYet;
                            currentPersonsPreviouslyComputedFriends = value;
                        } else {
                            currentPersonsNewIndirectFriends.add(value.substring(1));
                        }

                    } else {
                        outputFriendshipList(currentPersonsNewIndirectFriends, currentPersonsPreviouslyComputedFriends);
                        currentKeyPerson = keyPerson;
                        currentPersonsNewIndirectFriends.clear();

                        if(valueIsFriendshipList) {
                            currentPersonsPreviouslyComputedFriends = value;
                        } else {
                            currentPersonsPreviouslyComputedFriends = null;
                            currentPersonsNewIndirectFriends.add(value.substring(1));
                        }
                    }
                }
                inputPair = bufferedReader.readLine();
            }
            outputFriendshipList(currentPersonsNewIndirectFriends, currentPersonsPreviouslyComputedFriends);
        } catch (IOException e) {
            out.println("Could not read input from console, cause:" + e);
        }
    }

    private static void outputFriendshipList(List<String> currentPersonsNewIndirectFriends, String currentPersonsPreviouslyComputedFriends) {
        for(String indirectFriend: currentPersonsNewIndirectFriends) {
            boolean oneCopyOfThePreviousFriendsListWasFound = currentPersonsPreviouslyComputedFriends != null;
            assert oneCopyOfThePreviousFriendsListWasFound;
            out.println(indirectFriend + "\t" + currentPersonsPreviouslyComputedFriends);
        }
    }

}
