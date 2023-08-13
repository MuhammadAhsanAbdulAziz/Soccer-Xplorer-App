package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.TeamFavoriteListviewBinding;
import com.example.soccerxplorer.databinding.TopTeamBinding;
import com.example.soccerxplorer.interfaces.FavouritePlayerInterface;
import com.example.soccerxplorer.interfaces.FavouriteTeamInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.FavouriteTeamModel;
import com.example.soccerxplorer.model.TeamModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class FavouriteTeamAdapter extends ListAdapter<FavouriteTeamModel, FavouriteTeamAdapter.ViewHolder> {

    FavouriteTeamInterface favouriteTeamInterface;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    Context context;

    public FavouriteTeamAdapter(Context context, FavouriteTeamInterface favouriteTeamInterface) {
        super(FavouriteTeamModel.catItemCallBack);
        this.context = context;
        this.favouriteTeamInterface = favouriteTeamInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        TeamFavoriteListviewBinding binding = TeamFavoriteListviewBinding.inflate(layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteTeamModel data = getItem(position);
        holder.binding.setFavTeamInterface(favouriteTeamInterface);
        holder.binding.setDetail(data);
        String id = data.getTeamId();
        teamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String a = ds.child("teamId").getValue(String.class);
                    if (a.equals(id)) {
                        Glide.with(context).load(ds.child("TeamImage").getValue(String.class))
                                .dontAnimate().into(holder.binding.teamImage);
                        String ab = ds.child("TeamName").getValue(String.class);
                        holder.binding.teamName.setText(ab);
                        if(ds.child("TeamCountry").getValue(String.class)!=null) {
                            Locale l = new Locale("", ds.child("TeamCountry").getValue(String.class));
                            if (l.getDisplayCountry().equals("UNITED KINGDOM")) {
                                holder.binding.teamCountry.setText("ENGLAND");
                            } else holder.binding.teamCountry.setText(l.getDisplayCountry());
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TeamFavoriteListviewBinding binding;

        public ViewHolder(TeamFavoriteListviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}