package io.github.andyradionov.pyatnashki.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Andrey Radionov
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void onBannerClick(View view) {
        String url = "https://ostrovok.ru/";
        Uri webpage = Uri.parse(url);
        Intent startWebPageIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if (startWebPageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(startWebPageIntent);
        }
    }
}
