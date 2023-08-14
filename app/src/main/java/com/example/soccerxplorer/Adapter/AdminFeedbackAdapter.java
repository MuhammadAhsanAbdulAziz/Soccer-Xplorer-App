package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.FeedbackListviewBinding;
import com.example.soccerxplorer.databinding.MatchesListviewBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AdminFeedbackAdapter extends ListAdapter<FeedbackModel, AdminFeedbackAdapter.ViewHolder> {

    Context context;
    DatabaseReference userRef = FirebaseDatabase.
            getInstance().getReference("Users");
    public AdminFeedbackAdapter(Context context) {
        super(FeedbackModel.catItemCallBack);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        FeedbackListviewBinding binding = FeedbackListviewBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedbackModel data = getItem(position);
        holder.binding.setDetail(data);
        holder.binding.ratingBar.setRating(Float.parseFloat(data.getRating()));
        userRef.child(data.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.binding.feedbackname.setText(snapshot.child("userFullName").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        FeedbackListviewBinding binding;
        public ViewHolder(FeedbackListviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}