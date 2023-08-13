package com.example.soccerxplorer.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.FavouriteTeamModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTeamRepository {
    MutableLiveData<List<FavouriteTeamModel>> favteamlist;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Favourite Teams");

    public LiveData<List<FavouriteTeamModel>> getFavouriteTeambyUserId(String id) {
        favteamlist = new MutableLiveData<>();
        ArrayList<FavouriteTeamModel> data = new ArrayList<FavouriteTeamModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("userId").getValue(String.class).equals(id)) {
                        data.add(new FavouriteTeamModel(
                                ds.child("favouriteTeamId").getValue(String.class),
                                ds.child("userId").getValue(String.class),
                                ds.child("teamId").getValue(String.class)));
                        favteamlist.setValue(data);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return favteamlist;
    }
//
//    public LiveData<List<PlayerModel>> getPlayerbyTeam(String teamId) {
//        playerlist = new MutableLiveData<>();
//        ArrayList<PlayerModel> data = new ArrayList<PlayerModel>();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    if(ds.child("teamId").getValue(String.class).equals(teamId)) {
//                        data.add(new PlayerModel(ds.child("playerId").getValue(String.class),
//                                ds.child("playerName").getValue(String.class),
//                                ds.child("playerCountry").getValue(String.class),
//                                ds.child("teamId").getValue(String.class),
//                                ds.child("playerImage").getValue(String.class),
//                                ds.child("playerPosition").getValue(String.class),
//                                ds.child("playerNumber").getValue(String.class)));
//                        playerlist.setValue(data);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        return playerlist;
//    }
//
//    public void CreatePlayer(PlayerModel playerModel,Context context, NavController navController) {
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        String uniqueID = UUID.randomUUID().toString();
//        final StorageReference ref = storageReference.child("Player Images/" + playerModel.getPlayerImage());
//        ref.putFile(Uri.parse(playerModel.getPlayerImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
//                addOnSuccessListener(uri -> databaseReference.child(uniqueID).
//                setValue(new PlayerModel(uniqueID,playerModel.getPlayerName(),playerModel.getPlayerCountry(),
//                        playerModel.getTeamId(),uri.toString(),playerModel.getPlayerPosition(),playerModel.getPlayerNumber())))).
//                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                UtilManager.SuccessMessage(context,navController);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        UtilManager.errorMessage(context,"Error");
//                    }
//                });
//    }
//
//    public void UpdatePlayer(PlayerModel playerModel,Context context, NavController navController) {
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
//        final StorageReference ref = storageReference.child("Player Images/" + playerModel.getPlayerImage());
//        ref.putFile(Uri.parse(playerModel.getPlayerImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
//                addOnSuccessListener(uri -> databaseReference.child(playerModel.getPlayerId()).
//                        setValue(new PlayerModel(playerModel.getPlayerId(),playerModel.getPlayerName(),playerModel.getPlayerCountry(),
//                                playerModel.getTeamId(),uri.toString(),playerModel.getPlayerPosition(),playerModel.getPlayerNumber()))
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                UtilManager.SuccessMessage(context,navController);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                UtilManager.errorMessage(context,"Error");
//                            }
//                        })));
//    }
//
//    public void DeletePlayer(PlayerModel playerModel, Context context, NavController navController) {
//        databaseReference.child(playerModel.getPlayerId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                UtilManager.SuccessMessage(context,navController);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                UtilManager.errorMessage(context,"Error");
//            }
//        });
//    }
//
//    public LiveData<List<PlayerModel>> SearchPlayer(String name) {
//        playerlist = new MutableLiveData<>();
//        ArrayList<PlayerModel> data = new ArrayList<PlayerModel>();
//        databaseReference.orderByChild("playerName").startAt(name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    for(char ch : ds.child("title").getValue(String.class).toCharArray())
//                    {
//                        for(char c : name.toCharArray()) {
//                            if(ch == c)
//                                data.add(new PlayerModel(ds.child("playerId").getValue(String.class),
//                                        ds.child("playerName").getValue(String.class),
//                                        ds.child("playerCountry").getValue(String.class),
//                                        ds.child("teamId").getValue(String.class),
//                                        ds.child("playerImage").getValue(String.class),
//                                        ds.child("playerPosition").getValue(String.class),
//                                        ds.child("playerNumber").getValue(String.class)));
//                            playerlist.setValue(data);
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
//        return playerlist;
//    }

}