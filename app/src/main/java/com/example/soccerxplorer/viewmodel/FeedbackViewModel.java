package com.example.soccerxplorer.viewmodel;


import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.repository.FeedbackRepository;
import com.example.soccerxplorer.repository.TeamRepository;

import java.util.List;

public class FeedbackViewModel extends ViewModel {

    FeedbackRepository repo = new FeedbackRepository();
    FeedbackModel feedbackModel;

    public FeedbackModel getFeedbackModel() {
        return feedbackModel;
    }

    public void setFeedbackModel(FeedbackModel feedbackModel) {
        this.feedbackModel = feedbackModel;
    }

    public void CreateFeedback(FeedbackModel feedbackModel, Context context, NavController navController, Activity activity) {
        repo.CreateFeedback(feedbackModel,context,navController,activity);
    }

    public void UpdateFeedback(FeedbackModel teamModel,Context context, NavController navController) {
        repo.UpdateFeedback(teamModel,context,navController);
    }

    public void DeleteFeedback(FeedbackModel teamModel, Context context, NavController navController) {
        repo.DeleteFeedback(teamModel,context,navController);
    }

    public LiveData<List<FeedbackModel>> getFeedback() {
        return repo.getFeedback();
    }

}
