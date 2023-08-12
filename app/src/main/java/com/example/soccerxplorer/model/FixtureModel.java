package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class FixtureModel {

    String fixtureId,teamId1,teamId2,leagueId,fixtureDate,fixtureTime,teamScore1,teamScore2,fixtureStatus;

    public FixtureModel(String fixtureId, String teamId1, String teamId2, String leagueId, String fixtureDate, String fixtureTime, String teamScore1, String teamScore2, String fixtureStatus) {
        this.fixtureId = fixtureId;
        this.teamId1 = teamId1;
        this.teamId2 = teamId2;
        this.leagueId = leagueId;
        this.fixtureDate = fixtureDate;
        this.fixtureTime = fixtureTime;
        this.teamScore1 = teamScore1;
        this.teamScore2 = teamScore2;
        this.fixtureStatus = fixtureStatus;
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

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
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

    public String getTeamScore1() {
        return teamScore1;
    }

    public void setTeamScore1(String teamScore1) {
        this.teamScore1 = teamScore1;
    }

    public String getTeamScore2() {
        return teamScore2;
    }

    public void setTeamScore2(String teamScore2) {
        this.teamScore2 = teamScore2;
    }

    public String getFixtureStatus() {
        return fixtureStatus;
    }

    public void setFixtureStatus(String fixtureStatus) {
        this.fixtureStatus = fixtureStatus;
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