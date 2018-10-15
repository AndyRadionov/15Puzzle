package io.github.andyradionov.pyatnashki.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.github.andyradionov.pyatnashki.BuildConfig;

/**
 * @author Andrey Radionov
 */

public abstract class BaseActivity extends AppCompatActivity {

    public void onBannerClick(View view) {
        Uri advWebPage = Uri.parse(BuildConfig.ADV_URL);
        Intent startWebPageIntent = new Intent(Intent.ACTION_VIEW, advWebPage);
        if (startWebPageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(startWebPageIntent);
        }
    }
}
