package com.radionov.pyatnashki;

/**
 * @author Andrey Radionov
 */

public interface GameView {
    void swapTiles(int filledTileId, int emptyTileId);

    void setGameWon();
}
