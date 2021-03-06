package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, LoginActivity.class);
                startActivity(i);
                createfolder();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void createfolder()
    {
        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/app/forms");
            file.mkdirs();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
