package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.repository.FeedbackRepository;
import com.example.soccerxplorer.repository.FixtureRepository;

import java.util.List;

public class FixtureViewModel extends ViewModel {

    FixtureRepository repo = new FixtureRepository();
    FixtureModel fixtureModel;

    public FixtureModel getFixtureModel() {
        return fixtureModel;
    }

    public void setFixtureModel(FixtureModel fixtureModel) {
        this.fixtureModel = fixtureModel;
    }

    public void CreateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
        repo.CreateFixture(fixtureModel,context,navController);
    }

//    public void UpdateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
//        repo.UpdateFixture(fixtureModel,context,navController);
//    }

    public LiveData<List<String>> getLeagueName() {
        return repo.getLeagueName();
    }

    public void DeleteFixture(FixtureModel fixtureModel, Context context, NavController navController) {
        repo.DeleteFixture(fixtureModel,context,navController);
    }

//    public LiveData<List<FixtureModel>> getFixture() {
//        return repo.getFixture();
//    }

}
