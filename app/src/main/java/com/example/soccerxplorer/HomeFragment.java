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
import com.example.soccerxplorer.adapter.LeagueAdapter;
import com.example.soccerxplorer.adapter.PlayerAdapter;
import com.example.soccerxplorer.adapter.TeamAdapter;
import com.example.soccerxplorer.databinding.FragmentCreateTeamBinding;
import com.example.soccerxplorer.databinding.FragmentHomeBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;


public class HomeFragment extends Fragment implements TeamInterface, LeagueInterface, PlayerInterface {

    NavController navController;
    FragmentHomeBinding binding;
    TeamAdapter adp;
    TeamViewModel teamViewModel;
    PlayerViewModel playerViewModel;
    PlayerAdapter playerAdapter;
    LeagueAdapter leagueadp;
    LeagueViewModel leagueViewModel;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        adp = new TeamAdapter(requireContext(),this);
        leagueadp = new LeagueAdapter(requireContext(),this);
        playerAdapter = new PlayerAdapter(requireContext(),this);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        leagueViewModel = new ViewModelProvider(requireActivity()).get(LeagueViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        setTopTeam();
        setTopLeague();
        setTopPlayerTeam();
        setTopPlayer();

        if(MainActivity.getInstance().bottomNavigationView.getVisibility() == View.GONE)
        {
            MainActivity.getInstance().setUserMenu();
        }

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_searchFragment);
            }
        });

        binding.homeforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("teamId","273198fc-0942-422e-97c7-7602f68a51d2");
                navController.navigate(R.id.action_homeFragment_to_teamPlayerFragment,bundle);
            }
        });
    }

    private void setTopPlayer() {
        playerViewModel.getPlayerbyTeam("273198fc-0942-422e-97c7-7602f68a51d2").observe(requireActivity(), new Observer<List<PlayerModel>>() {
            @Override
            public void onChanged(List<PlayerModel> playerModels) {
                playerAdapter.submitList(playerModels);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
                binding.teamOnePlayers.setLayoutManager(gridLayoutManager);
                binding.teamOnePlayers.setAdapter(playerAdapter);
            }
        });
    }


    private void setTopPlayerTeam() {
        teamRef.child("273198fc-0942-422e-97c7-7602f68a51d2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Glide.with(requireContext()).load(snapshot.child("teamImage").
                        getValue(String.class)).dontAnimate().into(binding.teamOneImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setTopTeam()
    {
        teamViewModel.get3Team().observe(requireActivity(), new Observer<List<TeamModel>>() {
            @Override
            public void onChanged(List<TeamModel> teamModels) {
                adp.submitList(teamModels);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,false);
                binding.topteamlist.setLayoutManager(mLayoutManager);
                binding.topteamlist.setAdapter(adp);
            }
        });
    }
    private void setTopLeague()
    {
        leagueViewModel.get3League().observe(requireActivity(), new Observer<List<LeagueModel>>() {
            @Override
            public void onChanged(List<LeagueModel> leagueModels) {
                leagueadp.submitList(leagueModels);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,false);
                binding.topleaguelist.setLayoutManager(mLayoutManager);
                binding.topleaguelist.setAdapter(leagueadp);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void TeamDetail(TeamModel team) {

    }

    @Override
    public void LeagueDetail(LeagueModel leagueModel) {
        leagueViewModel.setLeagueModel(leagueModel);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("league",g.toJson(leagueModel) );
        navController.navigate(R.id.action_homeFragment_to_leagueMatcheFragment,bundle);
    }

    @Override
    public void PlayerDetail(PlayerModel player) {

    }
}