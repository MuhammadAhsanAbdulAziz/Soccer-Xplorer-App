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
    MutableLiveData<List<String>> leagueNameList;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Fixtures");
    DatabaseReference databaseReference2 = FirebaseDatabase.
            getInstance().getReference("Leagues");

    public LiveData<List<FixtureModel>> getFixture() {
        fixturelist = new MutableLiveData<>();
        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new FixtureModel(
                            ds.child("fixtureId").getValue(String.class),
                            ds.child("teamId1").getValue(String.class),
                            ds.child("teamId2").getValue(String.class),
                            ds.child("leagueId").getValue(String.class),
                            ds.child("fixtureDate").getValue(String.class),
                            ds.child("fixtureTime").getValue(String.class),
                            ds.child("teamScore1").getValue(String.class),
                            ds.child("teamScore2").getValue(String.class),
                            ds.child("fixtureStatus").getValue(String.class),
                            ds.child("fixtureReferee").getValue(String.class),
                            ds.child("fixtureVenue").getValue(String.class)));
                    fixturelist.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fixturelist;
    }

    public LiveData<List<FixtureModel>> getCompletedFixture() {
        fixturelist = new MutableLiveData<>();
        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("fixtureStatus").getValue().toString().equals("1")) {
                        data.add(new FixtureModel(
                                ds.child("fixtureId").getValue(String.class),
                                ds.child("teamId1").getValue(String.class),
                                ds.child("teamId2").getValue(String.class),
                                ds.child("leagueId").getValue(String.class),
                                ds.child("fixtureDate").getValue(String.class),
                                ds.child("fixtureTime").getValue(String.class),
                                ds.child("teamScore1").getValue(String.class),
                                ds.child("teamScore2").getValue(String.class),
                                ds.child("fixtureStatus").getValue(String.class),
                                ds.child("fixtureReferee").getValue(String.class),
                                ds.child("fixtureVenue").getValue(String.class)));
                        fixturelist.setValue(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fixturelist;
    }

    public LiveData<List<FixtureModel>> getCompletedFixturebyLeague(String leagueId) {
        fixturelist = new MutableLiveData<>();
        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("fixtureStatus").getValue().toString().equals("1")
                            && ds.child("leagueId").getValue(String.class).equals(leagueId) ) {
                        data.add(new FixtureModel(
                                ds.child("fixtureId").getValue(String.class),
                                ds.child("teamId1").getValue(String.class),
                                ds.child("teamId2").getValue(String.class),
                                ds.child("leagueId").getValue(String.class),
                                ds.child("fixtureDate").getValue(String.class),
                                ds.child("fixtureTime").getValue(String.class),
                                ds.child("teamScore1").getValue(String.class),
                                ds.child("teamScore2").getValue(String.class),
                                ds.child("fixtureStatus").getValue(String.class),
                                ds.child("fixtureReferee").getValue(String.class),
                                ds.child("fixtureVenue").getValue(String.class)));
                        fixturelist.setValue(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fixturelist;
    }

    public LiveData<List<FixtureModel>> getPendingFixture() {
        fixturelist = new MutableLiveData<>();
        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("fixtureStatus").getValue().toString().equals("0")) {
                        data.add(new FixtureModel(
                                ds.child("fixtureId").getValue(String.class),
                                ds.child("teamId1").getValue(String.class),
                                ds.child("teamId2").getValue(String.class),
                                ds.child("leagueId").getValue(String.class),
                                ds.child("fixtureDate").getValue(String.class),
                                ds.child("fixtureTime").getValue(String.class),
                                ds.child("teamScore1").getValue(String.class),
                                ds.child("teamScore2").getValue(String.class),
                                ds.child("fixtureStatus").getValue(String.class),
                                ds.child("fixtureReferee").getValue(String.class),
                                ds.child("fixtureVenue").getValue(String.class)));
                        fixturelist.setValue(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fixturelist;
    }

    public LiveData<List<FixtureModel>> getPendingFixturebyLeague(String leagueId) {
        fixturelist = new MutableLiveData<>();
        ArrayList<FixtureModel> data = new ArrayList<FixtureModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("fixtureStatus").getValue().toString().equals("0")
                            && ds.child("leagueId").getValue(String.class).equals(leagueId)) {
                        data.add(new FixtureModel(
                                ds.child("fixtureId").getValue(String.class),
                                ds.child("teamId1").getValue(String.class),
                                ds.child("teamId2").getValue(String.class),
                                ds.child("leagueId").getValue(String.class),
                                ds.child("fixtureDate").getValue(String.class),
                                ds.child("fixtureTime").getValue(String.class),
                                ds.child("teamScore1").getValue(String.class),
                                ds.child("teamScore2").getValue(String.class),
                                ds.child("fixtureStatus").getValue(String.class),
                                ds.child("fixtureReferee").getValue(String.class),
                                ds.child("fixtureVenue").getValue(String.class)));
                        fixturelist.setValue(data);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return fixturelist;
    }

    public LiveData<List<String>> getLeagueName() {
        leagueNameList = new MutableLiveData<>();
        ArrayList<String> data = new ArrayList<String>();
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(ds.child("leagueName").getValue(String.class));
                    leagueNameList.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return leagueNameList;
    }



    public void CreateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
        String uniqueID = UUID.randomUUID().toString();
        databaseReference.child(uniqueID).setValue(new FixtureModel
                (uniqueID,fixtureModel.getTeamId1(),
                        fixtureModel.getTeamId2(),fixtureModel.getLeagueId()
                        ,fixtureModel.getFixtureDate(),fixtureModel.getFixtureTime(),
                        fixtureModel.getTeamScore1(),fixtureModel.getTeamScore2()
                        ,fixtureModel.getFixtureStatus(),
                        fixtureModel.getFixtureReferee(),
                        fixtureModel.getFixtureVenu()))
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

    public void UpdateFixture(FixtureModel fixtureModel,Context context, NavController navController) {
        databaseReference.child(fixtureModel.getFixtureId()).setValue(new FixtureModel
                        (fixtureModel.getFixtureId(),fixtureModel.getTeamId1(),
                                fixtureModel.getTeamId2(),fixtureModel.getLeagueId()
                                ,fixtureModel.getFixtureDate(),fixtureModel.getFixtureTime(),
                                fixtureModel.getTeamScore1(),fixtureModel.getTeamScore2()
                                ,fixtureModel.getFixtureStatus(),
                                fixtureModel.getFixtureReferee(),
                                fixtureModel.getFixtureVenu())).
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