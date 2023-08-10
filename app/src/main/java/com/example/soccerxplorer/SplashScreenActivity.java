package com.example.soccerxplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.window.SplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView logo;
    TextView text;
    ProgressBar progressBar;
    LinearLayout lableText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        Animation clickAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top);
        Animation topDelay = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_delay);
        logo = findViewById(R.id.logo);
        text = findViewById(R.id.text);
        progressBar = findViewById(R.id.progressBar);
        lableText = findViewById(R.id.lableText);
        logo.startAnimation(clickAnimation);
        logo.animate().translationY(-100).setDuration(300).setStartDelay(2000);
        text.startAnimation(clickAnimation);
        text.animate().translationY(-100).setDuration(300).setStartDelay(2100);
        progressBar.animate().translationY(-500).setDuration(500).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lableText.startAnimation(clickAnimation);
            }
        },2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashScreenActivity.this, OnBoardingActivity.class));
//                finish();
            }
        },4000);
    }
}