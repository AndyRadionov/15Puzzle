package com.radionov.pyatnashki;

import java.util.HashSet;
import java.util.Random;

/**
 * @author Andrey Radionov
 */

public class FieldGenerator {
    private static final int TILES_NUMBER = 16;
    private static final HashSet<Integer> USED_NUMBERS = new HashSet<>(15);

    private FieldGenerator() {
    }


    public static int[] nextGameField() {
        int[] nextField = generateNext();
        while (!checkSolvability(nextField)) {
            nextField = generateNext();
        }
        return nextField;
    }

    private static int[] generateNext() {
        int[] nextField = new int[16];
        Random random = new Random();
        for (int i = 0; i < TILES_NUMBER; i++) {
            int nextInt = random.nextInt(16);
            while (USED_NUMBERS.contains(nextInt)) {
                nextInt = random.nextInt(16);
            }
            nextField[i] = nextInt;
            USED_NUMBERS.add(nextInt);
        }
        USED_NUMBERS.clear();
        return nextField;
    }

    //protected for testing purpose
    protected static boolean checkSolvability(int[] gameField) {
        int sum = 0;
        for (int i = 0; i < TILES_NUMBER; i++) {
            if (gameField[i] == 0) {
                sum += i / 4 + 1;
                continue;
            }
            for (int j = i + 1; j < TILES_NUMBER; j++) {
                if (gameField[j] != 0 && gameField[i] > gameField[j]) {
                    sum++;
                }
            }
        }
        return sum % 2 == 0;
    }
}
