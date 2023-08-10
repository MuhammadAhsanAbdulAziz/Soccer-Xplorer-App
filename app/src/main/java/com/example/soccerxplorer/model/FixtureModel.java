package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class FixtureModel {

    String fixtureId,teamId1,teamId2,fixtureDate,fixtureTime;

    public FixtureModel(String fixtureId, String teamId1, String teamId2, String fixtureDate, String fixtureTime) {
        this.fixtureId = fixtureId;
        this.teamId1 = teamId1;
        this.teamId2 = teamId2;
        this.fixtureDate = fixtureDate;
        this.fixtureTime = fixtureTime;
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(String fixtureId) {
        this.fixtureId = fixtureId;
    }

    public String getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(String teamId1) {
        this.teamId1 = teamId1;
    }

    public String getTeamId2() {
        return teamId2;
    }

    public void setTeamId2(String teamId2) {
        this.teamId2 = teamId2;
    }

    public String getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(String fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public String getFixtureTime() {
        return fixtureTime;
    }

    public void setFixtureTime(String fixtureTime) {
        this.fixtureTime = fixtureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixtureModel that = (FixtureModel) o;
        return Objects.equals(getFixtureId(), that.getFixtureId()) && Objects.equals(getTeamId1(), that.getTeamId1()) && Objects.equals(getTeamId2(), that.getTeamId2());
    }

    public static DiffUtil.ItemCallback<FixtureModel> teamItemCallBack = new DiffUtil.ItemCallback<FixtureModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FixtureModel oldItem, @NonNull FixtureModel newItem) {

            return oldItem.fixtureId.equals(newItem.fixtureId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FixtureModel oldItem, @NonNull FixtureModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}