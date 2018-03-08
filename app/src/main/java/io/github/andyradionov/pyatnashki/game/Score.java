package io.github.andyradionov.pyatnashki.game;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * @author Andrey Radionov
 */

public class Score implements Comparable<Score> {
    private int score;
    private Date date;

    public Score(int score, Date date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull Score o) {
        return score - o.score;
    }
}
