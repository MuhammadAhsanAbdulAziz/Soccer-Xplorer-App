package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminPlayerItemBinding;
import com.example.soccerxplorer.databinding.PlayerListviewBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPlayerAdapter extends ListAdapter<PlayerModel, AdminPlayerAdapter.ViewHolder> {

    PlayerInterface playerInterface;
    Context context;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    public AdminPlayerAdapter(Context context, PlayerInterface playerInterface) {
        super(PlayerModel.catItemCallBack);
        this.context = context;
        this.playerInterface = playerInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        PlayerListviewBinding binding = PlayerListviewBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayerModel data = getItem(position);
        holder.binding.setPlayerInterface(playerInterface);
        holder.binding.setDetail(data);
        teamRef.child(data.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(context).load(snapshot.child("teamImage").getValue(String.class)).dontAnimate().into(holder.binding.teamLogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Glide.with(context).load(data.getPlayerImage()).dontAnimate().into(holder.binding.teamImage);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        PlayerListviewBinding binding;
        public ViewHolder(PlayerListviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}