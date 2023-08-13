package com.example.soccerxplorer.repository;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeedbackRepository {
    MutableLiveData<List<FeedbackModel>> feedbacklist;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Feedbacks");

    public LiveData<List<FeedbackModel>> getFeedback() {
        feedbacklist = new MutableLiveData<>();
        ArrayList<FeedbackModel> data = new ArrayList<FeedbackModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new FeedbackModel(ds.child("feedbackId").getValue(String.class),
                            ds.child("userId").getValue(String.class),
                            ds.child("description").getValue(String.class),
                            ds.child("rating").getValue(String.class)));
                    feedbacklist.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return feedbacklist;
    }

    public void CreateFeedback(FeedbackModel feedbackModel, Context context, NavController navController, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(  context);
        LayoutInflater inflater =   activity.getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_progress_alert, null);
        builder.setView(dialogview);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        TextView messageTextView;
        messageTextView = dialogview.findViewById(R.id.messageTextView);
        messageTextView.setText("Submitting Feedback!");
        String uniqueID = UUID.randomUUID().toString();
        databaseReference.child(uniqueID).setValue(new FeedbackModel
                        (uniqueID,feedbackModel.getUserId(),feedbackModel.getDescription(),
                                feedbackModel.getRating())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                alertDialog.dismiss();
                AlertDialog.Builder builderTwo = new AlertDialog.Builder(  context);
                LayoutInflater inflaterTwo =   activity.getLayoutInflater();
                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
                builderTwo.setView(dialogviewTwo);
                AlertDialog alertDialogTwo = builderTwo.create();
                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialogTwo.show();
                TextView messageTextViewTwo;
                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                messageTextViewTwo.setText("Feedback Submitted");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alertDialogTwo.dismiss();
                        navController.popBackStack();
                    }
                },3000);
            }
        });
    }

    public void UpdateFeedback(FeedbackModel feedbackModel,Context context, NavController navController) {
        databaseReference.child(feedbackModel.getfeedbackId()).setValue(new FeedbackModel
                (feedbackModel.getfeedbackId(),feedbackModel.getUserId(),
                        feedbackModel.getDescription(),feedbackModel.getRating())).
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UtilManager.SuccessMessage(context,navController);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UtilManager.errorMessage(context,"Error");
            }
        });
    }

    public void DeleteFeedback(FeedbackModel feedbackModel,Context context, NavController navController) {
        databaseReference.child(feedbackModel.getfeedbackId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UtilManager.SuccessMessage(context,navController);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                UtilManager.errorMessage(context,"Error");
            }
        });
    }
}