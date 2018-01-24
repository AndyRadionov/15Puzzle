package com.radionov.pyatnashki;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Andrey Radionov
 */
public class FieldGeneratorTest {

    @Test
    public void testCheckCorrectFieldSolvability() {
        int[][] solvableFields = {
                {12, 5, 8, 7, 4, 11, 2, 14, 13, 6, 1, 0, 10, 9, 15, 3},
                {8, 13, 1, 4, 2, 14, 0, 5, 3, 12, 10, 7, 11, 6, 9, 15},
                {11, 5, 1, 2, 15, 14, 10, 7, 3, 12, 13, 9, 4, 8, 6, 0},
                {0, 8, 14, 13, 11, 12, 9, 10, 3, 7, 2, 1, 5, 15, 4, 6},
                {1, 7, 13, 14, 0, 4, 5, 8, 2, 15, 12, 3, 11, 10, 6, 9}
        };

        for (int[] solvableField : solvableFields) {
            boolean checkSolvabilityResult = FieldGenerator.checkSolvability(solvableField);
            assertTrue(checkSolvabilityResult);
        }
    }

    @Test
    public void testCheckIncorrectFieldSolvability() {
        int[][] unsolvableFields = {
                {14, 9, 6, 12, 13, 1, 15, 7, 4, 0, 10, 11, 3, 5, 2, 8},
                {8, 13, 1, 4, 2, 14, 10, 5, 3, 12, 7, 11, 6, 9, 15, 0},
                {14, 9, 8, 13, 5, 4, 0, 15, 2, 6, 7, 1, 3, 10, 12, 11},
                {0, 2, 6, 13, 4, 11, 3, 10, 9, 15, 7, 5, 8, 14, 1, 12},
                {5, 15, 12, 13, 8, 4, 1, 6, 7, 0, 10, 2, 9, 3, 11, 14}
        };

        for (int[] unsolvableField : unsolvableFields) {
            boolean checkSolvabilityResult = FieldGenerator.checkSolvability(unsolvableField);
            assertFalse(checkSolvabilityResult);
        }
    }

    @Test
    public void testNextField() {
        for (int i = 0; i < 100; i++) {
            int[] field = FieldGenerator.nextGameField();
            boolean checkSolvabilityResult = FieldGenerator.checkSolvability(field);
            assertTrue(checkSolvabilityResult);
        }
    }
}