package com.radionov.pyatnashki;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GameView {

    private String mMovesText;
    private String mStartButtonText;
    private String mResetButtonText;

    private TextView mMovesNumberDisplay;
    private GridLayout mTilesContainer;

    private TextView[] mTiles;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Model();

        mMovesNumberDisplay = findViewById(R.id.tv_info_display);
        mTilesContainer = findViewById(R.id.tiles_container);

        mMovesText = getString(R.string.moves_number);
        mStartButtonText = getString(R.string.start_button);
        mResetButtonText = getString(R.string.reset_button);

        initTiles();
    }

    @Override
    public void swapTiles(int filledTileId, int emptyTileId) {
        mMovesNumberDisplay.setText(String.format(mMovesText, model.getMovesNumber()));
        String number = mTiles[filledTileId].getText().toString();
        mTiles[emptyTileId].setText(number);
        mTiles[emptyTileId].setVisibility(View.VISIBLE);
        mTiles[filledTileId].setVisibility(View.INVISIBLE);
    }

    private void initTiles() {
        mTiles = new TextView[16];
        mTiles[0] = findViewById(R.id.tile_1);
        mTiles[1] = findViewById(R.id.tile_2);
        mTiles[2] = findViewById(R.id.tile_3);
        mTiles[3] = findViewById(R.id.tile_4);
        mTiles[4] = findViewById(R.id.tile_5);
        mTiles[5] = findViewById(R.id.tile_6);
        mTiles[6] = findViewById(R.id.tile_7);
        mTiles[7] = findViewById(R.id.tile_8);
        mTiles[8] = findViewById(R.id.tile_9);
        mTiles[9] = findViewById(R.id.tile_10);
        mTiles[10] = findViewById(R.id.tile_11);
        mTiles[11] = findViewById(R.id.tile_12);
        mTiles[12] = findViewById(R.id.tile_13);
        mTiles[13] = findViewById(R.id.tile_14);
        mTiles[14] = findViewById(R.id.tile_15);
        mTiles[15] = findViewById(R.id.tile_16);
    }

    private void repaint() {
        mMovesNumberDisplay.setText(String.format(mMovesText, model.getMovesNumber()));
        for (int i = 0; i < mTiles.length; i++) {
            mTiles[i].setText(String.valueOf(model.getNumbers()[i]));
            if (model.getNumbers()[i] == 0) {
                mTiles[i].setVisibility(View.INVISIBLE);
            } else {
                mTiles[i].setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void setGameWon() {
        showScoreDialog();
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        if (button.getText().equals(mStartButtonText)) {
            repaint();
            button.setText(mResetButtonText);
            mTilesContainer.setOnTouchListener(new OnSwipeTouchListener(this, this, model));
        } else {
            model.resetGame();
            repaint();
        }
    }

    private void showScoreDialog() {
        String message = getString(R.string.result_message, model.getMovesNumber());

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(R.string.result_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.exit_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.scores_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Context context = MainActivity.this;
                        Intent startScoresActivity = new Intent(context, ScoresActivity.class);
                        startScoresActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(startScoresActivity);
                    }
                })
                .setNegativeButton(R.string.restart_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.resetGame();
                        repaint();
                    }
                })
                .create()
                .show();

    }
}
