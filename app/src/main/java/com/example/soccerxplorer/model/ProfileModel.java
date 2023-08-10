package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class ProfileModel {

    String Id,Name,email,Image;
    public ProfileModel(String Id, String Name, String email, String Image) {
        this.Id = Id;
        this.Name = Name;
        this.email = email;
        this.Image = Image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileModel profile = (ProfileModel) o;
        return Objects.equals(Id, profile.Id) && Objects.equals(Name, profile.Name) && Objects.equals(email, profile.email) && Objects.equals(Image, profile.Image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Name, email, Image);
    }
    public static DiffUtil.ItemCallback<ProfileModel> catItemCallBack = new DiffUtil.ItemCallback<ProfileModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProfileModel oldItem, @NonNull ProfileModel newItem) {

            return oldItem.Id.equals(newItem.Id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProfileModel oldItem, @NonNull ProfileModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}

