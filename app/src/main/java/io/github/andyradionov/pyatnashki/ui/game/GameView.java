package io.github.andyradionov.pyatnashki.ui.game;

/**
 * @author Andrey Radionov
 */

public interface GameView {
    void swapTiles(int filledTileId, int emptyTileId);

    void setGameWon();
}
