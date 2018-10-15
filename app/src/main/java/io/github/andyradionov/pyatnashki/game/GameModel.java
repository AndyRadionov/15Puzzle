package io.github.andyradionov.pyatnashki.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * @author Andrey Radionov
 */

public class GameModel implements Parcelable {
    public static final int TILES_NUMBER = 16;
    private static final int[] WIN_POSITION = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    private int[] mNumbers;
    private int mMovesNumber;
    private int mZeroIndex;

    private boolean mCanMove;

    public GameModel() {
        resetGame();
    }

    protected GameModel(Parcel in) {
        mNumbers = in.createIntArray();
        mMovesNumber = in.readInt();
        mZeroIndex = in.readInt();
        mCanMove = in.readByte() != 0;
    }

    public static final Creator<GameModel> CREATOR = new Creator<GameModel>() {
        @Override
        public GameModel createFromParcel(Parcel in) {
            return new GameModel(in);
        }

        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

    public int[] getNumbers() {
        return mNumbers;
    }

    public int getMovesNumber() {
        return mMovesNumber;
    }

    public int getZeroIndex() {
        return mZeroIndex;
    }

    public boolean canMove() {
        return mCanMove;
    }

    public void resetGame() {
        mNumbers = FieldGenerator.nextGameField();
        mMovesNumber = 0;
        mCanMove = true;

        for (int i = 0; i < mNumbers.length; i++) {
            if (mNumbers[i] == 0) {
                mZeroIndex = i;
                break;
            }
        }
    }

    public boolean isGameWon() {
        if (Arrays.equals(WIN_POSITION, mNumbers)) {
            mCanMove = false;
            return true;
        }

        return false;
    }

    public int move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                return moveRight();
            case LEFT:
                return moveLeft();
            case UP:
                return moveUp();
            case DOWN:
                return moveDown();
            default:
                return -1;
        }
    }

    private int move(int movableTileIndex) {
        mMovesNumber++;
        mNumbers[mZeroIndex] = mNumbers[movableTileIndex];
        mNumbers[movableTileIndex] = 0;
        mZeroIndex = movableTileIndex;
        return mZeroIndex;
    }

    private int moveRight() {
        if (mZeroIndex % 4 != 0) {
            return move(mZeroIndex - 1);
        }
        return -1;
    }

    private int moveLeft() {
        if ((mZeroIndex + 1) % 4 != 0) {
            return move(mZeroIndex + 1);
        }
        return -1;
    }

    private int moveUp() {
        if (mZeroIndex < 12) {
            return move(mZeroIndex + 4);
        }
        return -1;
    }

    private int moveDown() {
        if (mZeroIndex > 3) {
            return move(mZeroIndex - 4);
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(mNumbers);
        dest.writeInt(mMovesNumber);
        dest.writeInt(mZeroIndex);
        dest.writeByte((byte) (mCanMove ? 1 : 0));
    }
}
