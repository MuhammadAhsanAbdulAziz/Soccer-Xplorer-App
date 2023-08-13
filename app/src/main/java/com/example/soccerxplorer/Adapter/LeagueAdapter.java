package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminLeagueItemBinding;
import com.example.soccerxplorer.databinding.TopLeagueBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.model.LeagueModel;

public class LeagueAdapter extends ListAdapter<LeagueModel, LeagueAdapter.ViewHolder> {

    LeagueInterface leagueInterface;
    Context context;
    public LeagueAdapter(Context context, LeagueInterface leagueInterface) {
        super(LeagueModel.catItemCallBack);
        this.context = context;
        this.leagueInterface = leagueInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        TopLeagueBinding binding = TopLeagueBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeagueModel data = getItem(position);
        holder.binding.setLeagueInterface(leagueInterface);
        holder.binding.setDetail(data);
        Glide.with(context).load(data.getLeagueImage()).dontAnimate().into(holder.binding.logo);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TopLeagueBinding binding;
        public ViewHolder(TopLeagueBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}