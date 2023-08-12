package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.MatchesListviewBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AdminFixtureAdapter extends ListAdapter<FixtureModel, AdminFixtureAdapter.ViewHolder> {

    FixtureInterface fixtureInterface;
    Context context;
    static int counter = 1;
    TeamViewModel teamViewModel;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    DatabaseReference leagueRef = FirebaseDatabase.
            getInstance().getReference("Leagues");
    public AdminFixtureAdapter(Context context, FixtureInterface fixtureInterface,TeamViewModel teamViewModel
    ) {
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
//        holder.binding.teamOneName.setText(""+teamViewModel.getTeamName(data.getTeamId1()));
//        holder.binding.teamTwoName.setText(""+teamViewModel.getTeamName(data.getTeamId2()));
        holder.binding.setDetail(data);
        teamRef.child(data.getTeamId1()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.binding.teamOneName.setText(snapshot.child("teamName").getValue().toString());
                Glide.with(context).load(snapshot.child("teamImage").getValue().toString()).
                dontAnimate().into(holder.binding.teamOneLogo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        teamRef.child(data.getTeamId2()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.binding.teamTwoName.setText(snapshot.child("teamName").getValue().toString());
                Glide.with(context).load(snapshot.child("teamImage").getValue().toString()).
                        dontAnimate().into(holder.binding.teamTwoLogo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        leagueRef.child(data.getLeagueId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.binding.matchLeagueName.setText(snapshot.child("leagueName").getValue().toString());
                Locale l = new Locale("", snapshot.child("leagueCountry").getValue().toString());
                if(l.getDisplayCountry().equals("UNITED KINGDOM"))
                {
                    holder.binding.country.setText("ENGLAND");
                }
                else holder.binding.country.setText(l.getDisplayCountry());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Glide.with(context).load(teamViewModel.getTeamImage(data.getTeamId1())).
                dontAnimate().into(holder.binding.teamOneLogo);
        Glide.with(context).load(teamViewModel.getTeamImage(data.getTeamId2())).
                dontAnimate().into(holder.binding.teamTwoLogo);
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