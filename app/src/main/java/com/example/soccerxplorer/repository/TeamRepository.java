package com.example.soccerxplorer.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.util.UtilManager;
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

public class TeamRepository {
    MutableLiveData<List<TeamModel>> teamlist;
    MutableLiveData<List<String>> teamNameList;
    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Teams");
    String teamName;
    String teamImage;

    public LiveData<List<TeamModel>> getTeam() {
        teamlist = new MutableLiveData<>();
        ArrayList<TeamModel> data = new ArrayList<TeamModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new TeamModel(ds.child("teamId").getValue(String.class),
                            ds.child("teamName").getValue(String.class),
                            ds.child("teamCountry").getValue(String.class),
                            ds.child("teamImage").getValue(String.class)));
                    teamlist.setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return teamlist;
    }

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

    public String getTeamName(String id) {
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    teamName = snapshot.child("teamName").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return teamName;
    }

    public String getTeamImage(String id) {
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    teamImage = snapshot.child("teamImage").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return teamImage;
    }



    public void CreateTeam(TeamModel teamModel) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String uniqueID = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child("Team Images/" + teamModel.getTeamImage());
        ref.putFile(Uri.parse(teamModel.getTeamImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(uniqueID).
                setValue(new TeamModel(uniqueID,teamModel.getTeamName(),teamModel.getTeamCountry(),uri.toString()))));
    }

    public void UpdateTeam(TeamModel teamModel) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("Team Images/" + teamModel.getTeamImage());
        ref.putFile(Uri.parse(teamModel.getTeamImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(teamModel.getTeamId()).
                        setValue(new TeamModel(teamModel.getTeamId(),teamModel.getTeamName(),teamModel.getTeamCountry(),uri.toString()))));
    }

    public void DeleteTeam(TeamModel teamModel, Context context, NavController navController) {
        databaseReference.child(teamModel.getTeamId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                UtilManager.SuccessMessage(context,navController);
            }
        });
    }

    public LiveData<List<TeamModel>> SearchTeam(String name) {
        teamlist = new MutableLiveData<>();
        ArrayList<TeamModel> data = new ArrayList<TeamModel>();
        databaseReference.orderByChild("teamName").startAt(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    for(char ch : ds.child("title").getValue(String.class).toCharArray())
                    {
                        for(char c : name.toCharArray()) {
                            if(ch == c)
                                data.add(new TeamModel(ds.child("id").getValue(String.class),
                                        ds.child("name").getValue(String.class),
                                        ds.child("country").getValue(String.class),
                                        ds.child("image").getValue(String.class)));
                            teamlist.setValue(data);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return teamlist;
    }

}