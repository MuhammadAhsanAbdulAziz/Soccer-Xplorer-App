package com.example.soccerxplorer.view.admin;

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

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminPlayerAdapter;
import com.example.soccerxplorer.adapter.AdminTeamAdapter;
import com.example.soccerxplorer.databinding.FragmentAllTeamBinding;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AllTeamFragment extends Fragment implements TeamInterface {

    FragmentAllTeamBinding binding;
    NavController navController;
    AdminTeamAdapter adp;
    TeamViewModel teamViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllTeamBinding.inflate(inflater,container,false);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        adp = new AdminTeamAdapter(requireActivity(), this);
        teamViewModel.getTeam().observe(requireActivity(), new Observer<List<TeamModel>>() {
            @Override
            public void onChanged(List<TeamModel> teamModels) {
                adp.submitList(teamModels);
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.teamList.setLayoutManager(mLayoutManager);
        binding.teamList.setAdapter(adp);

        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_allTeamFragment_to_createTeamFragment);
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
        teamViewModel.setTeamModel(team);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("team",g.toJson(team) );
        navController.navigate(R.id.action_allTeamFragment_to_createTeamFragment,bundle);
    }
}