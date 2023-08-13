package com.example.soccerxplorer.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.FavouriteTeamModel;
import com.example.soccerxplorer.repository.FavouritePlayerRepository;
import com.example.soccerxplorer.repository.FavouriteTeamRepository;

import java.util.List;

public class FavouriteTeamViewModel extends ViewModel {

    FavouriteTeamRepository repo = new FavouriteTeamRepository();
    FavouriteTeamModel favteamModel;

    public FavouriteTeamModel getFavouriteTeamModel() {
        return favteamModel;
    }

    public void setFavouriteTeamModel(FavouriteTeamModel favteamModel) {
        this.favteamModel = favteamModel;
    }
//
//    public void CreatePlayer(PlayerModel playerModel,Context context,NavController navController) {
//        repo.CreatePlayer(playerModel,context,navController);
//    }
//
//    public void UpdatePlayer(PlayerModel playerModel,Context context,NavController navController) {
//        repo.UpdatePlayer(playerModel,context,navController);
//    }
//
//    public void DeletePlayer(PlayerModel playerModel, Context context, NavController navController) {
//        repo.DeletePlayer(playerModel,context,navController);
//    }

    public LiveData<List<FavouriteTeamModel>> getFavouriteTeambyUserId(String id) {
        return repo.getFavouriteTeambyUserId(id);
    }
//    public LiveData<List<PlayerModel>> getPlayerbyTeam(String teamId) {
//        return repo.getPlayerbyTeam(teamId);
//    }
//    public LiveData<List<PlayerModel>> SearchPlayer(String name) {
//        return repo.SearchPlayer(name);
//    }
}
