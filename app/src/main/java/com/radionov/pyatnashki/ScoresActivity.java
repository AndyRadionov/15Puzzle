package com.radionov.pyatnashki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScoresActivity extends BaseActivity {
    public static final int REQUEST_CODE = 200;
    private static final String TAG = ScoresActivity.class.getSimpleName();

    private ListView scoresDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            case R.id.menu_about:
                showAboutDialog();
                return true;
            case R.id.menu_exit:
                setResult(MainActivity.RESULT_CODE_EXIT);
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
