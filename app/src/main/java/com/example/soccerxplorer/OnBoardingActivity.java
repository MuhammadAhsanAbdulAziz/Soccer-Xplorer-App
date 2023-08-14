package com.example.soccerxplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.soccerxplorer.adapter.OnboardingAdapter;
import com.example.soccerxplorer.model.OnboardingModel;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnBoardingIndicators;
    private Button btnOnBoardingAction;
    String prevStarted = "yes";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(prevStarted, false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToSecondary();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layoutOnBoardingIndicators = findViewById(R.id.layoutOnBoardingIndicators);
        btnOnBoardingAction = findViewById(R.id.btnOnBoardingAction);
        Animation clickAnimation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.zoomin_out);
        Animation clickAnimation2 = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.zoomin_out);
        setOnboardingAdapter();
        ViewPager2 onboardingViewPager = findViewById(R.id.onBoardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setLayoutOnBoardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });
        btnOnBoardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOnBoardingAction.startAnimation(clickAnimation);
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }
    private void setOnboardingAdapter(){
        List<OnboardingModel> onboardingModels = new ArrayList<>();

        OnboardingModel itemPayOnline = new OnboardingModel();
        itemPayOnline.setTitle("Stream Football Match");
        itemPayOnline.setDescription("Watch professional league football matches");
        itemPayOnline.setImage(R.drawable.screen_one);

        OnboardingModel itemPayOnline2 = new OnboardingModel();
        itemPayOnline2.setTitle("Realtime Statistics");
        itemPayOnline2.setDescription("Real-time football live scores and matches statistics");
        itemPayOnline2.setImage(R.drawable.screen_two);

        OnboardingModel itemPayOnline3 = new OnboardingModel();
        itemPayOnline3.setTitle("League Standings");
        itemPayOnline3.setDescription("Club statistics and league standings around the world");
        itemPayOnline3.setImage(R.drawable.screen_three);

        onboardingModels.add(itemPayOnline);
        onboardingModels.add(itemPayOnline2);
        onboardingModels.add(itemPayOnline3);

        onboardingAdapter = new OnboardingAdapter(onboardingModels);
    }
    private void setLayoutOnBoardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(10,0,10,0);
        for(int i = 0; i<indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnBoardingIndicators.addView(indicators[i]);
        }
    }
    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnBoardingIndicators.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnBoardingIndicators.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            btnOnBoardingAction.setText("Start");
        } else {
            btnOnBoardingAction.setText("Next");
        }
    }
    public void moveToSecondary(){
        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
        finish();
    }
}