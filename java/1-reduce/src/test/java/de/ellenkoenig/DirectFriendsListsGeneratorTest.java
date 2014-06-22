package de.ellenkoenig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DirectFriendsListsGeneratorTest {

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
        DirectFriendsListsGenerator.main(new String[]{});
    }

    @Test
    public void main_should_combinePairsWithSameKeyIntoList() throws Exception {
        String input = "Walter\tLisa\n" +
                        "Walter\tSusan\n" +
                         "Walter\tBill";
        feedInputIntoTestClass(input);

        String expectedOutput = "Walter\tBill,Lisa,Susan\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void main_should_combinePairsWithDifferentKeysIntoDifferentLists() throws Exception {
        String input = "Walter\tLisa\n" +
                "Walter\tSusan\n" +
                "Lena\tBill\n";
        feedInputIntoTestClass(input);

        String expectedOutput = "Walter\tLisa,Susan\n" +
                                "Lena\tBill\n";
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
                        "Susan\n" +
                        "Lena\tBill\n";
        feedInputIntoTestClass(input);

        String expectedOutput = "Walter\tLisa\n" +
                                "Lena\tBill\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}