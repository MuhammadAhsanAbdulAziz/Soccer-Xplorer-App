package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class FeedbackModel {

    String feedbackId,UserId,description;
    String rating;

    public FeedbackModel(String feedbackId, String UserId, String description, String rating) {
        this.feedbackId = feedbackId;
        this.UserId = UserId;
        this.description = description;
        this.rating = rating;
    }

    public String getfeedbackId() {
        return feedbackId;
    }

    public void setfeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackModel that = (FeedbackModel) o;
        return getfeedbackId().equals(that.getfeedbackId()) && getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getfeedbackId(), getUserId());
    }


    public static DiffUtil.ItemCallback<FeedbackModel> catItemCallBack = new DiffUtil.ItemCallback<FeedbackModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FeedbackModel oldItem, @NonNull FeedbackModel newItem) {

            return oldItem.feedbackId.equals(newItem.feedbackId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull FeedbackModel oldItem, @NonNull FeedbackModel newItem) {
            return oldItem.equals(newItem);
        }
    };



}