package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.repository.PlayerRepository;

import java.util.List;

public class PlayerViewModel extends ViewModel {

    PlayerRepository repo = new PlayerRepository();
    PlayerModel playerModel;

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void CreatePlayer(PlayerModel playerModel,Context context,NavController navController) {
        repo.CreatePlayer(playerModel,context,navController);
    }

    public void UpdatePlayer(PlayerModel playerModel,Context context,NavController navController) {
        repo.UpdatePlayer(playerModel,context,navController);
    }

    public void DeletePlayer(PlayerModel playerModel, Context context, NavController navController) {
        repo.DeletePlayer(playerModel,context,navController);
    }

    public LiveData<List<PlayerModel>> getPlayer() {
        return repo.getPlayer();
    }
    public LiveData<List<PlayerModel>> SearchPlayer(String name) {
        return repo.SearchPlayer(name);
    }
}
