package com.example.soccerxplorer.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class FeedbackModel {

    String feedbackId,UserId,description,rating,feedbackdate;

    public FeedbackModel(String feedbackId, String userId, String description, String rating, String feedbackdate) {
        this.feedbackId = feedbackId;
        UserId = userId;
        this.description = description;
        this.rating = rating;
        this.feedbackdate = feedbackdate;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
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

    public String getFeedbackdate() {
        return feedbackdate;
    }

    public void setFeedbackdate(String feedbackdate) {
        this.feedbackdate = feedbackdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackModel that = (FeedbackModel) o;
        return getFeedbackId().equals(that.getFeedbackId()) && getUserId().equals(that.getUserId());
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