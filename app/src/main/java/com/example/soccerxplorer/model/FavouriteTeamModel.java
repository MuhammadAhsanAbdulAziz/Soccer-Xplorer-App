package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FavouriteTeamModel {

    String favouriteTeamId,userId,teamId;

    public FavouriteTeamModel(String favouriteTeamId, String userId, String teamId) {
        this.favouriteTeamId = favouriteTeamId;
        this.userId = userId;
        this.teamId = teamId;
    }

    public String getFavouriteTeamId() {
        return favouriteTeamId;
    }

    public void setFavouriteTeamId(String favouriteTeamId) {
        this.favouriteTeamId = favouriteTeamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteTeamModel that = (FavouriteTeamModel) o;
        return getFavouriteTeamId().equals(that.getFavouriteTeamId());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static DiffUtil.ItemCallback<FavouriteTeamModel> catItemCallBack = new DiffUtil.ItemCallback<FavouriteTeamModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavouriteTeamModel oldItem, @NonNull FavouriteTeamModel newItem) {

            return oldItem.favouriteTeamId.equals(newItem.favouriteTeamId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavouriteTeamModel oldItem, @NonNull FavouriteTeamModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}