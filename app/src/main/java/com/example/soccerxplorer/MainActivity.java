package com.example.soccerxplorer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;
    FirebaseDatabase database;
    public static DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        NavHostFragment navHost = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        navController = navHost.getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.homeFragment) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
//        if(myRef == null) {
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//            database = FirebaseDatabase.getInstance();
//            myRef = database.getReference();
//        }
//        startActivity(new Intent(MainActivity.this,SplashScreenActivity.class));
//        finish();
    }
}