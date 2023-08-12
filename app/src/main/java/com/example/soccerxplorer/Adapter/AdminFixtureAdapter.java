package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.AdminTeamItemBinding;
import com.example.soccerxplorer.databinding.MatchesListviewBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;

public class AdminFixtureAdapter extends ListAdapter<FixtureModel, AdminFixtureAdapter.ViewHolder> {

    FixtureInterface fixtureInterface;
    Context context;
    static int counter = 1;
    TeamViewModel teamViewModel;
    public AdminFixtureAdapter(Context context, FixtureInterface fixtureInterface,TeamViewModel teamViewModel) {
        super(FixtureModel.teamItemCallBack);
        this.context = context;
        this.fixtureInterface = fixtureInterface;
        this.teamViewModel = teamViewModel;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        MatchesListviewBinding binding = MatchesListviewBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FixtureModel data = getItem(position);
        holder.binding.setFixtureInterface(fixtureInterface);
        String TeamName1 = teamViewModel.getTeamName(data.getTeamId1());
        String TeamName2 = teamViewModel.getTeamName(data.getTeamId2());
        data.setTeamId1(TeamName1);
        data.setTeamId2(TeamName2);
        holder.binding.setDetail(data);
//        Glide.with(context).load(teamViewModel.getTeamImage(data.getTeamId1())).
//                dontAnimate().into(holder.binding.teamOneLogo);
//        Glide.with(context).load(teamViewModel.getTeamImage(data.getTeamId2())).
//                dontAnimate().into(holder.binding.teamTwoLogo);
        counter++;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MatchesListviewBinding binding;
        public ViewHolder(MatchesListviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}