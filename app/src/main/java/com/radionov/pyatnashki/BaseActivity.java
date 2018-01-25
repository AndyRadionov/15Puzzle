package com.radionov.pyatnashki;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Andrey Radionov
 */

public class BaseActivity extends AppCompatActivity {

    protected void showAboutDialog() {
        String message = "Автор\n\n" +
                "\tАндрей Радионов\n" +
                "\tt.me/AndyRadionov\n\n" +
                "При поддержке компании RUDAT";

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle("О приложении")
                .setCancelable(false)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }
}
