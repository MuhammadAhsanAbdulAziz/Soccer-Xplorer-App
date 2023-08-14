package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class UserModel {

    String userId,userFullName,userName,userEmail,userContact,userJoining,userRole,userStatus,userImage;

    public UserModel(String userId, String userFullName, String userName, String userEmail, String userContact, String userJoining, String userRole, String userStatus, String userImage) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userJoining = userJoining;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userImage = userImage;
    }

    public UserModel(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserJoining() {
        return userJoining;
    }

    public void setUserJoining(String userJoining) {
        this.userJoining = userJoining;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return getUserId().equals(userModel.getUserId()) && getUserName().equals(userModel.getUserName()) && getUserEmail().equals(userModel.getUserEmail()) && getUserContact().equals(userModel.getUserContact());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static DiffUtil.ItemCallback<UserModel> catItemCallBack = new DiffUtil.ItemCallback<UserModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {

            return oldItem.userId.equals(newItem.userId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}

