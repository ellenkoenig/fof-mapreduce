package de.ellenkoenig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DuplicateFriendshipPairsGeneratorTest {

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
    public void main_shouldDuplicateGivenInputPairForValidInput() throws Exception {
        String input = "Walter\tLisa\n";
        feedInputIntoTestClass(input);

        String expectedOutput = "Walter\tLisa\n"
                                + "Lisa\tWalter\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void main_shouldHandleMoreThanOneInputPairProperly() throws Exception {
        String input = "Walter\tLisa\n" +
                       "Susan\tLisa\n";
        feedInputIntoTestClass(input);

        String expectedOutput = "Walter\tLisa\n" +
                                "Lisa\tWalter" +
                                "\nSusan\tLisa\n" +
                                "Lisa\tSusan\n";
        assertEquals(expectedOutput, outContent.toString());
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

        String expectedOutput = "Walter\tLisa\n" +
                                "Lisa\tWalter\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}