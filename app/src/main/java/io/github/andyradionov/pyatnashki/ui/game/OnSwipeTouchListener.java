package io.github.andyradionov.pyatnashki.ui.game;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import io.github.andyradionov.pyatnashki.game.GameModel;
import io.github.andyradionov.pyatnashki.game.MoveDirection;

/**
 * @author Andrey Radionov
 */

public class OnSwipeTouchListener implements View.OnTouchListener {
    private GameView gameView;
    private GameModel gameModel;

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context ctx, GameView gameView, GameModel gameModel) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());

        this.gameView = gameView;
        this.gameModel = gameModel;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            moveTile(MoveDirection.RIGHT);
                        } else {
                            moveTile(MoveDirection.LEFT);
                        }
                        result = true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        moveTile(MoveDirection.DOWN);
                    } else {
                        moveTile(MoveDirection.UP);
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    private void moveTile(MoveDirection direction) {
        if (!gameModel.canMove()) {
            return;
        }

        int emptyTileIndex = gameModel.getZeroIndex();
        int movedTileIndex = gameModel.move(direction);

        if (movedTileIndex == -1) {
            return;
        }

        gameView.swapTiles(movedTileIndex, emptyTileIndex);

        if (gameModel.isGameWon()) {
            gameView.setGameWon();
        }
    }
}
