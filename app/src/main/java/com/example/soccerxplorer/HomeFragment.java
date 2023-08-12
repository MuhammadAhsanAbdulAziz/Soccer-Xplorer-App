package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.adapter.LeagueAdapter;
import com.example.soccerxplorer.adapter.TeamAdapter;
import com.example.soccerxplorer.databinding.FragmentCreateTeamBinding;
import com.example.soccerxplorer.databinding.FragmentHomeBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;

import java.util.List;


public class HomeFragment extends Fragment implements TeamInterface, LeagueInterface {

    NavController navController;
    FragmentHomeBinding binding;
    TeamAdapter adp;
    TeamViewModel teamViewModel;
    LeagueAdapter leagueadp;
    LeagueViewModel leagueViewModel;


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
    }

    private void setTopTeam()
    {
        teamViewModel.getTeam().observe(requireActivity(), new Observer<List<TeamModel>>() {
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
        leagueViewModel.getLeague().observe(requireActivity(), new Observer<List<LeagueModel>>() {
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

    }
}