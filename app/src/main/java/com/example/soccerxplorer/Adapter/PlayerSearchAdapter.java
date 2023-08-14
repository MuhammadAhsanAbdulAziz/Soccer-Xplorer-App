package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.MatchesPlayerListviewBinding;
import com.example.soccerxplorer.databinding.SearchTopTeamBinding;
import com.example.soccerxplorer.databinding.TopTeamBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;

public class PlayerSearchAdapter extends ListAdapter<PlayerModel, PlayerSearchAdapter.ViewHolder> {

    PlayerInterface playerInterface;
    Context context;
    public PlayerSearchAdapter(Context context, PlayerInterface playerInterface) {
        super(PlayerModel.catItemCallBack);
        this.context = context;
        this.playerInterface = playerInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        SearchTopTeamBinding binding = SearchTopTeamBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayerModel data = getItem(position);
        holder.binding.setPlayerInterface(playerInterface);
        holder.binding.setDetail(data);
        Glide.with(context).load(data.getPlayerImage()).dontAnimate().into(holder.binding.topteamlogo);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        SearchTopTeamBinding binding;
        public ViewHolder(SearchTopTeamBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}