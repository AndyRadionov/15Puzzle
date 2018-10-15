package io.github.andyradionov.pyatnashki.ui.scores;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import io.github.andyradionov.pyatnashki.R;
import io.github.andyradionov.pyatnashki.game.Score;
import io.github.andyradionov.pyatnashki.ui.BaseActivity;
import io.github.andyradionov.pyatnashki.ui.game.GameActivity;
import io.github.andyradionov.pyatnashki.utils.ScoresHelper;

public class ScoresActivity extends BaseActivity {
    public static final int REQUEST_CODE = 200;
    private static final String TAG = ScoresActivity.class.getSimpleName();

    private ListView scoresDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        scoresDisplay = findViewById(R.id.lv_scores);
        scoresDisplay.setEmptyView(findViewById(android.R.id.empty));

        showScoresList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ScoresActivity.this.finish();
                return true;
            case R.id.menu_exit:
                setResult(GameActivity.RESULT_CODE_EXIT);
                ScoresActivity.this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showScoresList() {
        List<Score> scores = ScoresHelper.loadScores(this);

        ScoreAdapter scoreAdapter = new ScoreAdapter(this, scores);

        scoresDisplay.setAdapter(scoreAdapter);
    }
}
