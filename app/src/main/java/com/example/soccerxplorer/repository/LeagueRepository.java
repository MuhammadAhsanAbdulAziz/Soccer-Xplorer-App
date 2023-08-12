package com.example.soccerxplorer.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.util.UtilManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LeagueRepository {
    MutableLiveData<List<LeagueModel>> leaguelist;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Leagues");

    public LiveData<List<LeagueModel>> getLeague() {
        leaguelist = new MutableLiveData<>();
        ArrayList<LeagueModel> data = new ArrayList<LeagueModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new LeagueModel(ds.child("leagueId").getValue(String.class),
                            ds.child("leagueName").getValue(String.class),
                            ds.child("leagueCountry").getValue(String.class),
                            ds.child("leagueImage").getValue(String.class)));
                    leaguelist.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return leaguelist;
    }

    public void CreateLeague(LeagueModel leagueModel,Context context, NavController navController) {
        String uniqueID = UUID.randomUUID().toString();
        databaseReference.child(uniqueID).setValue(new LeagueModel
                (uniqueID,leagueModel.getLeagueName(),
                        leagueModel.getLeagueCountry(),leagueModel.getLeagueImage())).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void UpdateLeague(LeagueModel leagueModel,Context context, NavController navController) {
        databaseReference.child(leagueModel.getLeagueId()).setValue(new LeagueModel
                (leagueModel.getLeagueId(),leagueModel.getLeagueName(),
                        leagueModel.getLeagueCountry(),leagueModel.getLeagueImage())).
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

    public void DeleteLeague(LeagueModel leagueModel,Context context, NavController navController) {
        databaseReference.child(leagueModel.getLeagueId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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