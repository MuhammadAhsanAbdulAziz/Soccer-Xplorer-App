package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminPlayerItemBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;

public class AdminPlayerAdapter extends ListAdapter<PlayerModel, AdminPlayerAdapter.ViewHolder> {

    PlayerInterface playerInterface;
    Context context;
    static int counter = 1;
    public AdminPlayerAdapter(Context context, PlayerInterface playerInterface) {
        super(PlayerModel.catItemCallBack);
        this.context = context;
        this.playerInterface = playerInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        AdminPlayerItemBinding binding = AdminPlayerItemBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayerModel data = getItem(position);
        holder.binding.setPlayerInterface(playerInterface);
        holder.binding.setDetail(data);
        holder.binding.counter.setText(""+counter);

        Glide.with(context).load(data.getPlayerImage()).dontAnimate().into(holder.binding.adminplayerimg);
        counter++;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdminPlayerItemBinding binding;
        public ViewHolder(AdminPlayerItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}