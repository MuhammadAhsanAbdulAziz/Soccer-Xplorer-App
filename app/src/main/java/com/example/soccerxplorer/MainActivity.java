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
import android.widget.LinearLayout;

import com.example.soccerxplorer.util.UtilManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    public BottomNavigationView bottomNavigationView;

    private static MainActivity instance = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        NavHostFragment navHost = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        if(navHost!=null) {
            navController = navHost.getNavController();
        }
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

//
//        if(UtilManager.getDefaults("userRole",this)!=null)
//        {
//            if(UtilManager.getDefaults("userRole",this).equals("Admin")) {
//                LinearLayout l = findViewById(R.id.layout);
//                l.setBackgroundColor(getResources().getColor(android.R.color.white));
//            }
//
//        }
    }
    public void setUserMenu()
    {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.inflateMenu(R.menu.menu_item);
    }
    public void setAdminMenu()
    {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.inflateMenu(R.menu.admin_menu_item);
    }

    public static MainActivity getInstance() {
        return instance;
    }
}