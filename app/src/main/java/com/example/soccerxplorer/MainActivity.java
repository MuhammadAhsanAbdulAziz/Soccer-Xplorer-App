package com.example.soccerxplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    public static DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(myRef == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
        }
        startActivity(new Intent(MainActivity.this,SplashScreenActivity.class));
        finish();
    }
}