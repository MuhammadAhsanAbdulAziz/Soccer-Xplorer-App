package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminLeagueItemBinding;
import com.example.soccerxplorer.databinding.AdminTeamItemBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.TeamModel;

import java.util.Locale;

public class AdminLeagueAdapter extends ListAdapter<LeagueModel, AdminLeagueAdapter.ViewHolder> {

    LeagueInterface leagueInterface;
    Context context;
    static int counter = 1;
    public AdminLeagueAdapter(Context context, LeagueInterface leagueInterface) {
        super(LeagueModel.catItemCallBack);
        this.context = context;
        this.leagueInterface = leagueInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        AdminLeagueItemBinding binding = AdminLeagueItemBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeagueModel data = getItem(position);
        holder.binding.setLeagueInterface(leagueInterface);
        holder.binding.setDetail(data);
        Locale l = new Locale("", data.getLeagueCountry());
        if (l.getDisplayCountry().equals("UNITED KINGDOM")) {
            holder.binding.country.setText("ENGLAND");
        } else holder.binding.country.setText(l.getDisplayCountry());
        Glide.with(context).load(data.getLeagueImage())
                .dontAnimate().into(holder.binding.playerImage);
        counter++;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdminLeagueItemBinding binding;
        public ViewHolder(AdminLeagueItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}