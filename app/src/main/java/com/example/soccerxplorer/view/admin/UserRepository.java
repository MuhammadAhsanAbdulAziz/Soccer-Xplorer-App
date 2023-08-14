package com.example.soccerxplorer.view.admin;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.UserModel;
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

public class UserRepository {
    MutableLiveData<List<UserModel>> userlist;
    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Users");

    public LiveData<List<UserModel>> getUser() {
        userlist = new MutableLiveData<>();
        ArrayList<UserModel> data = new ArrayList<UserModel>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    data.add(new UserModel(ds.child("userId").getValue(String.class),
                            ds.child("userFullName").getValue(String.class),
                            ds.child("userName").getValue(String.class),
                            ds.child("userEmail").getValue(String.class),
                            ds.child("userContact").getValue(String.class),
                            ds.child("userJoining").getValue(String.class),
                            ds.child("userRole").getValue(String.class),
                            ds.child("userStatus").getValue(String.class),
                            ds.child("userImage").getValue(String.class)));
                    userlist.setValue(data);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userlist;
    }

    public LiveData<List<UserModel>> getUserbyId(String id) {
        userlist = new MutableLiveData<>();
        ArrayList<UserModel> data = new ArrayList<UserModel>();
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.add(new UserModel(snapshot.child("userId").getValue(String.class),
                        snapshot.child("userFullName").getValue(String.class),
                        snapshot.child("userName").getValue(String.class),
                        snapshot.child("userEmail").getValue(String.class),
                        snapshot.child("userContact").getValue(String.class),
                        snapshot.child("userJoining").getValue(String.class),
                        snapshot.child("userRole").getValue(String.class),
                        snapshot.child("userStatus").getValue(String.class),
                        snapshot.child("userImage").getValue(String.class)));
                userlist.setValue(data);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return userlist;
    }

    public void CreateUser(UserModel userModel,Context context, NavController navController) {
        databaseReference.child(userModel.getUserId()).setValue(new UserModel
                (userModel.getUserId(),userModel.getUserFullName(),userModel.getUserName(),
                        userModel.getUserEmail(),userModel.getUserContact(),
                        userModel.getUserJoining(),userModel.getUserRole(),
                        userModel.getUserStatus(),userModel.getUserImage()
                        )).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void UpdateUser(UserModel userModel,Context context, NavController navController) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("User Images/" + userModel.getUserImage());
        ref.putFile(Uri.parse(userModel.getUserImage())).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().
                addOnSuccessListener(uri -> databaseReference.child(userModel.getUserId()).setValue(new UserModel
                        (userModel.getUserId(),userModel.getUserFullName(),userModel.getUserName(),
                                userModel.getUserEmail(),userModel.getUserContact(),
                                userModel.getUserJoining(),userModel.getUserRole(),
                                userModel.getUserStatus(),userModel.getUserImage()
                        )).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void DeleteUser(UserModel userModel, Context context, NavController navController) {
        databaseReference.child(userModel.getUserId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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