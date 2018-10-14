package io.github.andyradionov.pyatnashki.ui.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import io.github.andyradionov.pyatnashki.R;
import io.github.andyradionov.pyatnashki.game.GameModel;
import io.github.andyradionov.pyatnashki.ui.BaseActivity;
import io.github.andyradionov.pyatnashki.ui.scores.ScoresActivity;
import io.github.andyradionov.pyatnashki.utils.ScoresHelper;

public class GameActivity extends BaseActivity implements GameView {
    public static final int RESULT_CODE_EXIT = 100;
    private static final String MODEL_KEY = "model_key";
    private static final int TILES_SIZE = 16;

    private String mMovesText;
    private String mStartButtonText;
    private String mResetButtonText;

    private TextView mMovesNumberDisplay;
    private GridLayout mTilesContainer;

    private TextView[] mTiles;

    private GameModel gameModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        initTiles();

        gameModel = new GameModel();

        mMovesNumberDisplay = findViewById(R.id.tv_info_display);
        mTilesContainer = findViewById(R.id.tiles_container);

        mMovesText = getString(R.string.moves_number);
        mStartButtonText = getString(R.string.start_button);
        mResetButtonText = getString(R.string.reset_button);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameModel = savedInstanceState.getParcelable(MODEL_KEY);

        if (gameModel == null) {
            return;
        }

        if (gameModel.isGameWon()) {
            gameModel.resetGame();
            repaint();
            return;
        }

        if (gameModel.getMovesNumber() > 0) {
            repaint();
            Button button = findViewById(R.id.btn_start);
            button.setText(mResetButtonText);
            mTilesContainer.setOnTouchListener(new OnSwipeTouchListener(this, this, gameModel));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MODEL_KEY, gameModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scores_table:
                startScoresActivity();
                return true;
            case R.id.menu_exit:
                exitApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void swapTiles(int filledTileId, int emptyTileId) {
        mMovesNumberDisplay.setText(String.format(mMovesText, gameModel.getMovesNumber()));
        String number = mTiles[filledTileId].getText().toString();
        mTiles[emptyTileId].setText(number);
        mTiles[emptyTileId].setVisibility(View.VISIBLE);
        mTiles[filledTileId].setVisibility(View.INVISIBLE);
    }

    @Override
    public void setGameWon() {
        ScoresHelper.updateScoresList(this, gameModel.getMovesNumber());
        showScoreDialog();
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        if (button.getText().equals(mStartButtonText)) {
            repaint();
            button.setText(mResetButtonText);
            mTilesContainer.setOnTouchListener(new OnSwipeTouchListener(this, this, gameModel));
        } else {
            gameModel.resetGame();
            repaint();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ScoresActivity.REQUEST_CODE == requestCode) {
            if (RESULT_CODE_EXIT == resultCode) {
                exitApp();
            }
        }
    }

    @Override
    public void onBannerClick(View view) {
        super.onBannerClick(view);
    }

    private void showScoreDialog() {
        String message = getString(R.string.result_message, gameModel.getMovesNumber());

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(R.string.result_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.scores_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startScoresActivity();
                    }
                })
                .setNegativeButton(R.string.restart_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameModel.resetGame();
                        repaint();
                    }
                })
                .create()
                .show();

    }

    private void startScoresActivity() {
        Intent startScoresActivity = new Intent(this, ScoresActivity.class);
        startActivityForResult(startScoresActivity, ScoresActivity.REQUEST_CODE);
    }

    private void exitApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finishAffinity();
        }
    }

    private void initTiles() {
        mTiles = new TextView[TILES_SIZE];
        GridLayout tilesContainer = findViewById(R.id.tiles_container);
        for (int i = 0; i < mTiles.length - 1; i++) {
            initTile(R.layout.tile, i, tilesContainer);
        }
        initTile(R.layout.empty_tile, TILES_SIZE - 1, tilesContainer);
    }

    private void initTile(int tileLayout, int tileNumber, GridLayout tilesContainer) {
        TextView tile = (TextView) LayoutInflater.from(this)
                .inflate(tileLayout, tilesContainer, false);
        tile.setText(String.valueOf(tileNumber + 1));
        mTiles[tileNumber] = tile;
        tilesContainer.addView(tile);
    }

    private void repaint() {
        mMovesNumberDisplay.setText(String.format(mMovesText, gameModel.getMovesNumber()));
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i].setText(String.valueOf(gameModel.getNumbers()[i]));
            if (gameModel.getNumbers()[i] == 0) {
                mTiles[i].setVisibility(View.INVISIBLE);
            } else {
                mTiles[i].setVisibility(View.VISIBLE);
            }
        }
    }
}
