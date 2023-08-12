package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.adapter.AdminPlayerAdapter;
import com.example.soccerxplorer.adapter.PlayerAdapter;
import com.example.soccerxplorer.databinding.FragmentFixtureDetailBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.viewmodel.FixtureViewModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class FixtureDetailFragment extends Fragment implements PlayerInterface {

    FragmentFixtureDetailBinding binding;
    NavController navController;
    FixtureModel fixtureModel;
    TeamViewModel teamViewModel;
    FixtureViewModel fixtureViewModel;
    PlayerViewModel playerViewModel,playerViewModel2;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    DatabaseReference leagueRef = FirebaseDatabase.
            getInstance().getReference("Leagues");
    PlayerAdapter adp1;
    PlayerAdapter adp2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        adp1 = new PlayerAdapter(requireActivity(), this);
        adp2 = new PlayerAdapter(requireActivity(), this);

        binding = FragmentFixtureDetailBinding.inflate(inflater,container,false);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        fixtureViewModel = new ViewModelProvider(requireActivity()).get(FixtureViewModel.class);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInitialData();
        navController = Navigation.findNavController(view);

        playerViewModel.getPlayerbyTeam(fixtureModel.getTeamId2()).observe(requireActivity(), new Observer<List<PlayerModel>>() {
            @Override
            public void onChanged(List<PlayerModel> playerModels) {
                playerViewModel.getPlayerbyTeam(fixtureModel.getTeamId1()).observe(requireActivity(), new Observer<List<PlayerModel>>() {
                    @Override
                    public void onChanged(List<PlayerModel> playerModels) {
                        adp1.submitList(playerModels);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
                        binding.teamOnePlayers.setLayoutManager(gridLayoutManager);
                        binding.teamOnePlayers.setAdapter(adp1);
                    }
                });
                adp2.submitList(playerModels);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
                binding.teamTwoPlayers.setLayoutManager(gridLayoutManager);
                binding.teamTwoPlayers.setAdapter(adp2);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void setInitialData() {
        if(getArguments()!=null){
            String jsonNote = getArguments().getString("fixture");
            Gson g = new Gson();
            fixtureModel = g.fromJson(jsonNote, FixtureModel.class);
            if(fixtureModel!=null)
            {
                binding.matchDate.setText(fixtureModel.getFixtureDate()+","
                        +fixtureModel.getFixtureTime());
                binding.teamOneScore.setText(fixtureModel.getTeamScore1());
                binding.teamTwoScore.setText(fixtureModel.getTeamScore2());

                teamRef.child(fixtureModel.getTeamId1()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Glide.with(requireContext()).load(snapshot.child("teamImage").getValue(String.class)).
                                dontAnimate().into(binding.teamOneLogo);
                        binding.teamOneName.setText(snapshot.child("teamName").getValue(String.class));
                        Locale l = new Locale("", snapshot.child("teamCountry").getValue(String.class));
                        if(l.getDisplayCountry().equals("UNITED KINGDOM"))
                        {
                            binding.teamOneCountry.setText("ENGLAND");
                        }
                        else binding.teamOneCountry.setText(l.getDisplayCountry());

                        Glide.with(requireContext()).load(snapshot.child("teamImage").getValue(String.class)).
                                dontAnimate().into(binding.teamOneImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                teamRef.child(fixtureModel.getTeamId2()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Glide.with(requireContext()).load(snapshot.child("teamImage").getValue(String.class)).
                                dontAnimate().into(binding.teamTwoLogo);
                        binding.teamTwoName.setText(snapshot.child("teamName").getValue(String.class));
                        Locale l = new Locale("", snapshot.child("teamCountry").getValue(String.class));
                        if(l.getDisplayCountry().equals("UNITED KINGDOM"))
                        {
                            binding.teamTwoCountry.setText("ENGLAND");
                        }
                        else binding.teamTwoCountry.setText(l.getDisplayCountry());

                        Glide.with(requireContext()).load(snapshot.child("teamImage").getValue(String.class)).
                                dontAnimate().into(binding.teamTwoImage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                leagueRef.child(fixtureModel.getLeagueId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.matchLeagueName.setText(snapshot.child("leagueName").getValue(String.class));
                        Locale l = new Locale("", snapshot.child("leagueCountry").getValue(String.class));
                        if(l.getDisplayCountry().equals("UNITED KINGDOM"))
                        {
                            binding.country.setText("ENGLAND");
                        }
                        else binding.country.setText(l.getDisplayCountry());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        }
    }

    @Override
    public void PlayerDetail(PlayerModel player) {

    }
}