package com.example.soccerxplorer.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.LeagueModel;
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

public class LeagueRepository {
    MutableLiveData<List<LeagueModel>> leaguelist;
    StorageReference storageReference;
    FirebaseStorage storage;
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

    public LiveData<List<LeagueModel>> SearchLeague(String name) {
        leaguelist = new MutableLiveData<>();
        ArrayList<LeagueModel> data = new ArrayList<LeagueModel>();
        databaseReference.orderByChild("leagueName").startAt(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    for(char ch : ds.child("leagueName").getValue(String.class).toCharArray())
                    {
                        for(char c : name.toCharArray()) {
                            if(ch == c)
                                data.add(new LeagueModel(ds.child("leagueId").getValue(String.class),
                                        ds.child("leagueName").getValue(String.class),
                                        ds.child("leagueCountry").getValue(String.class),
                                        ds.child("leagueImage").getValue(String.class)));
                            leaguelist.setValue(data);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return leaguelist;
    }
    public LiveData<List<LeagueModel>> get3League() {
        leaguelist = new MutableLiveData<>();
        ArrayList<LeagueModel> data = new ArrayList<LeagueModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            int count = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    count++;
                    data.add(new LeagueModel(ds.child("leagueId").getValue(String.class),
                            ds.child("leagueName").getValue(String.class),
                            ds.child("leagueCountry").getValue(String.class),
                            ds.child("leagueImage").getValue(String.class)));
                    leaguelist.setValue(data);
                    if(count>2){
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return leaguelist;
    }

    public void CreateLeague(LeagueModel leagueModel,Context context, NavController navController) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String uniqueID = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child("League Images/" + leagueModel.getLeagueImage());
        ref.putFile(Uri.parse(leagueModel.getLeagueImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                        addOnSuccessListener(uri -> databaseReference.child(uniqueID).
                                setValue(new LeagueModel
                                        (uniqueID,leagueModel.getLeagueName(),
                                                leagueModel.getLeagueCountry(),uri.toString())))).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("Player Images/" + leagueModel.getLeagueId());
        ref.putFile(Uri.parse(leagueModel.getLeagueId())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(leagueModel.getLeagueId()).
                        setValue(new LeagueModel
                                (leagueModel.getLeagueId(),leagueModel.getLeagueName(),
                                        leagueModel.getLeagueCountry(),leagueModel.getLeagueImage()))
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
                        })));
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