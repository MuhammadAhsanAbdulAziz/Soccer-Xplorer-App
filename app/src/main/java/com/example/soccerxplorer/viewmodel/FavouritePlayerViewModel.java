package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.repository.FavouritePlayerRepository;
import com.example.soccerxplorer.repository.PlayerRepository;

import java.util.List;

public class FavouritePlayerViewModel extends ViewModel {

    FavouritePlayerRepository repo = new FavouritePlayerRepository();
    FavouritePlayerModel favplayerModel;

    public FavouritePlayerModel getFavouritePlayerModel() {
        return favplayerModel;
    }

    public void setFavouritePlayerModel(FavouritePlayerModel favplayerModel) {
        this.favplayerModel = favplayerModel;
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

    public LiveData<List<FavouritePlayerModel>> getFavouritePlayerbyUserId(String id) {
        return repo.getFavouritePlayerbyUserId(id);
    }
//    public LiveData<List<PlayerModel>> getPlayerbyTeam(String teamId) {
//        return repo.getPlayerbyTeam(teamId);
//    }
//    public LiveData<List<PlayerModel>> SearchPlayer(String name) {
//        return repo.SearchPlayer(name);
//    }
}
