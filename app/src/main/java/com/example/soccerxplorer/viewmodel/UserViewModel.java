package com.example.soccerxplorer.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.repository.FeedbackRepository;
import com.example.soccerxplorer.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    UserRepository repo = new UserRepository();
    UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void CreateUser(UserModel userModel,Context context, NavController navController) {
        repo.CreateUser(userModel,context,navController);
    }
//
//    public void UpdateFeedback(FeedbackModel teamModel,Context context, NavController navController) {
//        repo.UpdateFeedback(teamModel,context,navController);
//    }
//
//    public void DeleteFeedback(FeedbackModel teamModel, Context context, NavController navController) {
//        repo.DeleteFeedback(teamModel,context,navController);
//    }
//
//    public LiveData<List<FeedbackModel>> getFeedback() {
//        return repo.getFeedback();
//    }

}
