package io.github.andyradionov.pyatnashki.game;

import java.util.HashSet;
import java.util.Random;

/**
 * @author Andrey Radionov
 */

public class FieldGenerator {
    private static final HashSet<Integer> USED_NUMBERS = new HashSet<>(GameModel.TILES_NUMBER - 1);

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
        int[] nextField = new int[GameModel.TILES_NUMBER];
        Random random = new Random();
        for (int i = 0; i < GameModel.TILES_NUMBER; i++) {
            int nextInt = random.nextInt(GameModel.TILES_NUMBER);
            while (USED_NUMBERS.contains(nextInt)) {
                nextInt = random.nextInt(GameModel.TILES_NUMBER);
            }
            nextField[i] = nextInt;
            USED_NUMBERS.add(nextInt);
        }
        USED_NUMBERS.clear();
        return nextField;
    }

    protected static boolean checkSolvability(int[] gameField) {
        int sum = 0;
        for (int i = 0; i < GameModel.TILES_NUMBER; i++) {
            if (gameField[i] == 0) {
                sum += i / 4 + 1;
                continue;
            }
            for (int j = i + 1; j < GameModel.TILES_NUMBER; j++) {
                if (gameField[j] != 0 && gameField[i] > gameField[j]) {
                    sum++;
                }
            }
        }
        return sum % 2 == 0;
    }
}
