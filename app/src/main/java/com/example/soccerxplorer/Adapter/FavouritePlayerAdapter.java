package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.MatchesPlayerListviewBinding;
import com.example.soccerxplorer.databinding.PlayerFavoriteListviewBinding;
import com.example.soccerxplorer.interfaces.FavouritePlayerInterface;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FavouritePlayerAdapter extends ListAdapter<FavouritePlayerModel, FavouritePlayerAdapter.ViewHolder> {

    FavouritePlayerInterface favouritePlayerInterface;
    DatabaseReference playerRef = FirebaseDatabase.
            getInstance().getReference("Players");
    Context context;

    public FavouritePlayerAdapter(Context context, FavouritePlayerInterface favouritePlayerInterface) {
        super(FavouritePlayerModel.catItemCallBack);
        this.context = context;
        this.favouritePlayerInterface = favouritePlayerInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        PlayerFavoriteListviewBinding binding = PlayerFavoriteListviewBinding.inflate(layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouritePlayerModel data = getItem(position);
        holder.binding.setFavPlayerInterface(favouritePlayerInterface);
        holder.binding.setDetail(data);
        String id = data.getPlayerId();
        playerRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Glide.with(context).load(snapshot.child("playerImage").getValue(String.class))
                                .dontAnimate().into(holder.binding.playerImage);
                        holder.binding.playerName.setText(snapshot.child("playerName").getValue(String.class));
                        holder.binding.playerPosition.setText(snapshot.child("playerPosition").getValue(String.class));
                    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PlayerFavoriteListviewBinding binding;

        public ViewHolder(PlayerFavoriteListviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}