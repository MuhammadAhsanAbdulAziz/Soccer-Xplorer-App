package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FavouritePlayerModel {

    String favouritePlayerId,userId,playerId;

    public FavouritePlayerModel(String favouritePlayerId, String userId, String playerId) {
        this.favouritePlayerId = favouritePlayerId;
        this.userId = userId;
        this.playerId = playerId;
    }

    public String getFavouritePlayerId() {
        return favouritePlayerId;
    }

    public void setFavouritePlayerId(String favouritePlayerId) {
        this.favouritePlayerId = favouritePlayerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouritePlayerModel that = (FavouritePlayerModel) o;
        return getFavouritePlayerId().equals(that.getFavouritePlayerId());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static DiffUtil.ItemCallback<FavouritePlayerModel> catItemCallBack = new DiffUtil.ItemCallback<FavouritePlayerModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavouritePlayerModel oldItem, @NonNull FavouritePlayerModel newItem) {

            return oldItem.favouritePlayerId.equals(newItem.favouritePlayerId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavouritePlayerModel oldItem, @NonNull FavouritePlayerModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}