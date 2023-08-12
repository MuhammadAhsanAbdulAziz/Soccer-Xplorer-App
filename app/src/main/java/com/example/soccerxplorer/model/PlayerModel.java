package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class PlayerModel {

    String playerId,playerName,playerCountry,teamId,playerImage,playerPosition,playerNumber;

    public PlayerModel(String playerId, String playerName, String playerCountry, String teamId, String playerImage, String playerPosition, String playerNumber) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerCountry = playerCountry;
        this.teamId = teamId;
        this.playerImage = playerImage;
        this.playerPosition = playerPosition;
        this.playerNumber = playerNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerCountry() {
        return playerCountry;
    }

    public void setPlayerCountry(String playerCountry) {
        this.playerCountry = playerCountry;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(String playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel that = (PlayerModel) o;
        return getPlayerId().equals(that.getPlayerId()) && getPlayerName().equals(that.getPlayerName());
    }


    public static DiffUtil.ItemCallback<PlayerModel> catItemCallBack = new DiffUtil.ItemCallback<PlayerModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull PlayerModel oldItem, @NonNull PlayerModel newItem) {

            return oldItem.playerId.equals(newItem.playerId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull PlayerModel oldItem, @NonNull PlayerModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}