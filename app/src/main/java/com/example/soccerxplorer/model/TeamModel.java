package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class TeamModel {

    String teamId,teamName,teamCountry,teamImage;

    public TeamModel(String teamId, String teamName, String teamCountry, String teamImage) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCountry = teamCountry;
        this.teamImage = teamImage;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCountry() {
        return teamCountry;
    }

    public void setTeamCountry(String teamCountry) {
        this.teamCountry = teamCountry;
    }

    public String getTeamImage() {
        return teamImage;
    }

    public void setTeamImage(String teamImage) {
        this.teamImage = teamImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamModel that = (TeamModel) o;
        return getTeamId().equals(that.getTeamId()) && getTeamName().equals(that.getTeamName());
    }


    public static DiffUtil.ItemCallback<TeamModel> teamItemCallBack = new DiffUtil.ItemCallback<TeamModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull TeamModel oldItem, @NonNull TeamModel newItem) {

            return oldItem.teamId.equals(newItem.teamId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull TeamModel oldItem, @NonNull TeamModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}