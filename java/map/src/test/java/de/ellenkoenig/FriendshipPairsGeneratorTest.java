package de.ellenkoenig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FriendshipPairsGeneratorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setIn(System.in);
        System.setOut(System.out);

    }

    private void feedInputIntoTestClass(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        FriendshipPairsGenerator.main(new String[]{});
    }

    @Test
    public void main_shouldDuplicateOneGivenInputPairForValidInput() throws Exception {
        String input = "Walter\tLisa\n";
        feedInputIntoTestClass(input);

        assertTrue(outContent.toString().contains("Lisa\tWalter\n"));
        assertTrue(outContent.toString().contains("Walter\tLisa\n"));
    }

    @Test
    public void main_shouldHandleMoreThanOneInputPairProperly() throws Exception {
        String input = "Walter\tLisa\n" +
                       "Susan\tLisa\n";
        feedInputIntoTestClass(input);

        assertTrue(outContent.toString().contains("Walter\tLisa\n"));
        assertTrue(outContent.toString().contains("Lisa\tWalter\n"));
        assertTrue(outContent.toString().contains("Susan\tLisa\n"));
        assertTrue(outContent.toString().contains("Lisa\tSusan\n"));
    }

    @Test
    public void main_shouldGenerateAllPermutationsForAValidListOfFriends() throws Exception {
        String input = "Walter\tLisa\tSarah\n";
        feedInputIntoTestClass(input);

        assertTrue(outContent.toString().contains("Walter\tLisa\n"));
        assertTrue(outContent.toString().contains("Lisa\tWalter\n"));
        assertTrue(outContent.toString().contains("Walter\tSarah\n"));
        assertTrue(outContent.toString().contains("Sarah\tWalter\n"));
        assertTrue(outContent.toString().contains("Lisa\tSarah\n"));
        assertTrue(outContent.toString().contains("Sarah\tLisa\n"));
    }

    @Test
    public void main_shouldNotOutputInputValuesForMalformedInput() throws Exception {
        String input = "Walter\n" +
                       "Lisa\n";
        feedInputIntoTestClass(input);

        assertFalse(outContent.toString().contains("Walter"));
        assertFalse(outContent.toString().contains("Lisa"));
    }

    @Test
    public void main_shouldProperlyOutputAllWellFormedPairsEvenIfInputContainsMalformedPairs() throws Exception {
        String input = "Walter\tLisa\n" +
                        "Susan\n";
        feedInputIntoTestClass(input);

        assertTrue(outContent.toString().contains("Lisa\tWalter\n"));
        assertTrue(outContent.toString().contains("Walter\tLisa\n"));
    }
}