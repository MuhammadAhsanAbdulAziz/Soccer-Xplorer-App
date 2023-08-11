package com.example.soccerxplorer.repository;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
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

public class PlayerRepository {
    MutableLiveData<List<PlayerModel>> playerlist;
    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Players");

    public LiveData<List<PlayerModel>> getPlayer() {
        playerlist = new MutableLiveData<>();
        ArrayList<PlayerModel> data = new ArrayList<PlayerModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new PlayerModel(ds.child("playerId").getValue(String.class),
                            ds.child("playerName").getValue(String.class),
                            ds.child("playerCountry").getValue(String.class),
                            ds.child("teamId").getValue(String.class),
                            ds.child("playerImage").getValue(String.class)));
                    playerlist.setValue(data);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return playerlist;
    }

    public void CreatePlayer(PlayerModel playerModel,Context context, NavController navController) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String uniqueID = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child("Player Images/" + playerModel.getPlayerImage());
        ref.putFile(Uri.parse(playerModel.getPlayerImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(uniqueID).
                setValue(new PlayerModel(uniqueID,playerModel.getPlayerName(),playerModel.getPlayerCountry(),
                        playerModel.getTeamId(),uri.toString())))).
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

    public void UpdatePlayer(PlayerModel playerModel,Context context, NavController navController) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("Player Images/" + playerModel.getPlayerImage());
        ref.putFile(Uri.parse(playerModel.getPlayerImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(playerModel.getPlayerId()).
                        setValue(new PlayerModel(
                                playerModel.getPlayerId(),
                                playerModel.getPlayerName(),
                                playerModel.getPlayerCountry(),
                                playerModel.getTeamId(),
                                uri.toString()))
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

    public void DeletePlayer(PlayerModel playerModel, Context context, NavController navController) {
        databaseReference.child(playerModel.getPlayerId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public LiveData<List<PlayerModel>> SearchPlayer(String name) {
        playerlist = new MutableLiveData<>();
        ArrayList<PlayerModel> data = new ArrayList<PlayerModel>();
        databaseReference.orderByChild("playerName").startAt(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    for(char ch : ds.child("title").getValue(String.class).toCharArray())
                    {
                        for(char c : name.toCharArray()) {
                            if(ch == c)
                                data.add(new PlayerModel(ds.child("playerId").getValue(String.class),
                                        ds.child("playerName").getValue(String.class),
                                        ds.child("playerCountry").getValue(String.class),
                                        ds.child("teamId").getValue(String.class),
                                        ds.child("playerImage").getValue(String.class)));
                            playerlist.setValue(data);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return playerlist;
    }

}