package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.repository.FeedbackRepository;
import com.example.soccerxplorer.repository.LeagueRepository;

import java.util.List;

public class LeagueViewModel extends ViewModel {

    LeagueRepository repo = new LeagueRepository();
    LeagueModel leagueModel;

    public LeagueModel getLeagueModel() {
        return leagueModel;
    }

    public void setLeagueModel(LeagueModel leagueModel) {
        this.leagueModel = leagueModel;
    }

    public void CreateLeague(LeagueModel leagueModel,Context context, NavController navController) {
        repo.CreateLeague(leagueModel,context,navController);
    }

    public void UpdateLeague(LeagueModel leagueModel,Context context, NavController navController) {
        repo.UpdateLeague(leagueModel,context,navController);
    }

    public void DeleteLeague(LeagueModel leagueModel, Context context, NavController navController) {
        repo.DeleteLeague(leagueModel,context,navController);
    }

    public LiveData<List<LeagueModel>> getLeague() {
        return repo.getLeague();
    }

}
