package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class LeagueModel {
    private String leagueId,leagueName,leagueCountry,leagueImage;

    public LeagueModel(String leagueId, String leagueName, String leagueCountry, String leagueImage) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.leagueCountry = leagueCountry;
        this.leagueImage = leagueImage;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getLeagueCountry() {
        return leagueCountry;
    }

    public void setLeagueCountry(String leagueCountry) {
        this.leagueCountry = leagueCountry;
    }

    public String getLeagueImage() {
        return leagueImage;
    }

    public void setLeagueImage(String leagueImage) {
        this.leagueImage = leagueImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueModel that = (LeagueModel) o;
        return getLeagueId().equals(that.getLeagueId()) && getLeagueName().equals(that.getLeagueName()) && getLeagueCountry().equals(that.getLeagueCountry());
    }

    public static DiffUtil.ItemCallback<LeagueModel> catItemCallBack = new DiffUtil.ItemCallback<LeagueModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull LeagueModel oldItem, @NonNull LeagueModel newItem) {

            return oldItem.leagueId.equals(newItem.leagueId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull LeagueModel oldItem, @NonNull LeagueModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
