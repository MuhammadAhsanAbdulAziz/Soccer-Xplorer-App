package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.repository.TeamRepository;

import java.util.List;

public class TeamViewModel extends ViewModel {

    TeamRepository repo = new TeamRepository();
    TeamModel teamModel;

    public TeamModel getTeamModel() {
        return teamModel;
    }

    public void setTeamModel(TeamModel teamModel) {
        this.teamModel = teamModel;
    }

    public void CreateTeam(TeamModel teamModel) {
        repo.CreateTeam(teamModel);
    }

    public void UpdateTeam(TeamModel teamModel) {
        repo.UpdateTeam(teamModel);
    }

    public void DeleteTeam(TeamModel teamModel, Context context, NavController navController) {
        repo.DeleteTeam(teamModel,context,navController);
    }

    public String getTeamName(String id)
    {
        return repo.getTeamName(id);
    }

    public String getTeamImage(String id)
    {
        return repo.getTeamImage(id);
    }

    public LiveData<List<TeamModel>> getTeam() {
        return repo.getTeam();
    }
    public LiveData<List<String>> getTeamName() {
        return repo.getTeamName();
    }
    public LiveData<List<TeamModel>> SearchCountry(String name) {
        return repo.SearchTeam(name);
    }
}
