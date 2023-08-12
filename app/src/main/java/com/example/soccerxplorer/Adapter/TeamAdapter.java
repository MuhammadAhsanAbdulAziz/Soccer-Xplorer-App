package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminTeamItemBinding;
import com.example.soccerxplorer.databinding.TopTeamBinding;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.TeamModel;

public class TeamAdapter extends ListAdapter<TeamModel, TeamAdapter.ViewHolder> {

    TeamInterface teamInterface;
    Context context;
    public TeamAdapter(Context context, TeamInterface teamInterface) {
        super(TeamModel.teamItemCallBack);
        this.context = context;
        this.teamInterface = teamInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        TopTeamBinding binding = TopTeamBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamModel data = getItem(position);
        holder.binding.setTeamInterface(teamInterface);
        holder.binding.setDetail(data);

        Glide.with(context).load(data.getTeamImage()).dontAnimate().into(holder.binding.topteamlogo);
        holder.binding.topteamname.setText(data.getTeamName());

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TopTeamBinding binding;
        public ViewHolder(TopTeamBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}