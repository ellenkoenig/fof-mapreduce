package de.ellenkoenig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ReverseLookupPairsGeneratorTest {

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
        ReverseLookupPairsGenerator.main(new String[]{});
    }

    @Test
    public void main_shouldOutputOriginalFriendsListForValidInput() throws Exception {
        String input = "Walter\tLisa\n";
        feedInputIntoTestClass(input);
        assertTrue(outContent.toString().contains(input));
    }

    @Test
    public void main_shouldOutputReverseLookupPairsForValidInput() throws Exception {
        String input = "Walter\tLisa,Sally\n";
        feedInputIntoTestClass(input);

        String expectedPairs =  "Lisa\t#Walter" +
                                "\nSally\t#Walter\n";
        assertTrue(outContent.toString().contains(expectedPairs));
    }

    @Test
    public void main_shouldIgnoreLinesWithInvalidInput() throws Exception {
        String input = "Walter\tLisa\n" +
                          "Foo\n" +
                          "Sally\tLisa\n";
        feedInputIntoTestClass(input);
        assertTrue(outContent.toString().contains("Walter\tLisa\n"));
        assertFalse(outContent.toString().contains("Foo"));
        assertTrue(outContent.toString().contains("Sally\tLisa\n"));
    }
}