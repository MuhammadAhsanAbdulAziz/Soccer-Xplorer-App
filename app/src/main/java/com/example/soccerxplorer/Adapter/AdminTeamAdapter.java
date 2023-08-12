package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminPlayerItemBinding;
import com.example.soccerxplorer.databinding.AdminTeamItemBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;

public class AdminTeamAdapter extends ListAdapter<TeamModel, AdminTeamAdapter.ViewHolder> {

    TeamInterface teamInterface;
    Context context;
    static int counter = 1;
    public AdminTeamAdapter(Context context, TeamInterface teamInterface) {
        super(TeamModel.teamItemCallBack);
        this.context = context;
        this.teamInterface = teamInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        AdminTeamItemBinding binding = AdminTeamItemBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamModel data = getItem(position);
        holder.binding.setTeamInterface(teamInterface);
        holder.binding.setDetail(data);
        holder.binding.counter.setText(""+counter);

        Glide.with(context).load(data.getTeamImage()).dontAnimate().into(holder.binding.adminplayerimg);
        counter++;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdminTeamItemBinding binding;
        public ViewHolder(AdminTeamItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}