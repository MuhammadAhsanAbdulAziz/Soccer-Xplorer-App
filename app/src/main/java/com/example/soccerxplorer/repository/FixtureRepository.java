package com.example.soccerxplorer.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.TeamModel;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FixtureRepository {
    MutableLiveData<List<FixtureModel>> fixturelist;
    MutableLiveData<List<String>> teamNameList;
    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Fixtures");

//    public LiveData<List<FixtureModel>> getFixture() {
//        fixturelist = new MutableLiveData<>();
//        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    data.add(new FixtureModel(
//                            ds.child("fixtureId").getValue(String.class),
//                            ds.child("teamId1").getValue(String.class),
//                            ds.child("teamId2").getValue(String.class)));
//                    fixturelist.setValue(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return fixturelist;
//    }

    public LiveData<List<String>> getTeamName() {
        teamNameList = new MutableLiveData<>();
        ArrayList<String> data = new ArrayList<String>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(ds.child("teamName").getValue(String.class));
                    teamNameList.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return teamNameList;
    }



    public void CreateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
        String uniqueID = UUID.randomUUID().toString();
        databaseReference.child(uniqueID).setValue(new FixtureModel
                (uniqueID,fixtureModel.getTeamId1(),fixtureModel.getTeamId2()
                        ,fixtureModel.getFixtureDate(),fixtureModel.getFixtureTime()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
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

//    public void UpdateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
//        databaseReference.child(fixtureModel.getFixtureId()).setValue(new FixtureModel
//                        (fixtureModel.getFixtureId(),fixtureModel.getTeamId1(),
//                                fixtureModel.getTeamId1())).
//                addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        UtilManager.SuccessMessage(context,navController);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        UtilManager.errorMessage(context,"Error");
//                    }
//                });
//    }

    public void DeleteFixture(FixtureModel fixtureModel,Context context, NavController navController) {
        databaseReference.child(fixtureModel.getFixtureId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UtilManager.SuccessMessage(context,navController);
            }
        });
    }

//    public LiveData<List<TeamModel>> SearchTeam(String name) {
//        teamlist = new MutableLiveData<>();
//        ArrayList<TeamModel> data = new ArrayList<TeamModel>();
//        databaseReference.orderByChild("teamName").startAt(name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    for(char ch : ds.child("title").getValue(String.class).toCharArray())
//                    {
//                        for(char c : name.toCharArray()) {
//                            if(ch == c)
//                                data.add(new TeamModel(ds.child("id").getValue(String.class),
//                                        ds.child("name").getValue(String.class),
//                                        ds.child("country").getValue(String.class),
//                                        ds.child("image").getValue(String.class)));
//                            teamlist.setValue(data);
//                        }
//                        break;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return teamlist;
//    }

}