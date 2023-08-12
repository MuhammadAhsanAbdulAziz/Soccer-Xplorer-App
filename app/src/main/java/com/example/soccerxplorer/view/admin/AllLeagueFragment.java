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
import com.example.soccerxplorer.adapter.AdminLeagueAdapter;
import com.example.soccerxplorer.adapter.AdminTeamAdapter;
import com.example.soccerxplorer.databinding.FragmentAllLeagueBinding;
import com.example.soccerxplorer.databinding.FragmentAllTeamBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AllLeagueFragment extends Fragment implements LeagueInterface {

    FragmentAllLeagueBinding binding;
    NavController navController;
    AdminLeagueAdapter adp;
    LeagueViewModel leagueViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllLeagueBinding.inflate(inflater,container,false);
        leagueViewModel = new ViewModelProvider(requireActivity()).get(LeagueViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        navController = Navigation.findNavController(view);

        adp = new AdminLeagueAdapter(requireActivity(), this);
        leagueViewModel.getLeague().observe(requireActivity(), new Observer<List<LeagueModel>>() {
            @Override
            public void onChanged(List<LeagueModel> leagueModels) {
                adp.submitList(leagueModels);
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.teamList.setLayoutManager(mLayoutManager);
        binding.teamList.setAdapter(adp);

        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_allLeagueFragment_to_createLeagueFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void LeagueDetail(LeagueModel leagueModel) {
        leagueViewModel.setLeagueModel(leagueModel);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("fixture",g.toJson(leagueModel) );
        navController.navigate(R.id.action_allLeagueFragment_to_createLeagueFragment,bundle);
    }
}