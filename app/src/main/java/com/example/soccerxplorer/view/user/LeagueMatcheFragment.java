package com.example.soccerxplorer.view.user;

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

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminFixtureAdapter;
import com.example.soccerxplorer.databinding.FragmentFixtureBinding;
import com.example.soccerxplorer.databinding.FragmentLeagueMatcheBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FixtureViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;

public class LeagueMatcheFragment extends Fragment implements FixtureInterface {

    FragmentLeagueMatcheBinding binding;
    NavController navController;

    AdminFixtureAdapter adp;
    FixtureViewModel fixtureViewModel;
    TeamViewModel teamViewModel;
    LeagueModel leagueModel;
    String leagueId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeagueMatcheBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setInitialData();
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        fixtureViewModel = new ViewModelProvider(requireActivity()).get(FixtureViewModel.class);
        adp = new AdminFixtureAdapter(requireActivity(), this, teamViewModel);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        fixtureViewModel.getCompletedFixturebyLeague(leagueId).observe(requireActivity(), new Observer<List<FixtureModel>>() {
            @Override
            public void onChanged(List<FixtureModel> fixtureModels) {
                adp.submitList(fixtureModels);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
                binding.fixurelist.setLayoutManager(mLayoutManager);
                binding.fixurelist.setAdapter(adp);
            }
        });

        binding.fixturesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fixturesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.resultsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.fixturesBtn.setTextColor(getResources().getColor(R.color.white));
                binding.fixurelist2.setVisibility(View.VISIBLE);
                binding.fixurelist.setVisibility(View.GONE);
                fixtureViewModel.getCompletedFixturebyLeague(leagueId).observe(requireActivity(), new Observer<List<FixtureModel>>() {
                    @Override
                    public void onChanged(List<FixtureModel> fixtureModels) {
                        adp.submitList(fixtureModels);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
                        binding.fixurelist.setLayoutManager(mLayoutManager);
                        binding.fixurelist.setAdapter(adp);
                    }
                });

            }
        });

        binding.resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fixturesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.resultsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.fixurelist2.setVisibility(View.GONE);
                binding.fixurelist.setVisibility(View.VISIBLE);
                fixtureViewModel.getPendingFixturebyLeague(leagueId).observe(requireActivity(), new Observer<List<FixtureModel>>() {
                    @Override
                    public void onChanged(List<FixtureModel> fixtureModels) {
                        adp.submitList(fixtureModels);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
                        binding.fixurelist2.setLayoutManager(mLayoutManager);
                        binding.fixurelist2.setAdapter(adp);
                    }
                });

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setInitialData() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("league");
            if (jsonNote != null) {
                Gson g = new Gson();
                leagueModel = g.fromJson(jsonNote, LeagueModel.class);
                if (leagueModel != null) {
                    leagueId = leagueModel.getLeagueId();
                    binding.leagueName.setText(leagueModel.getLeagueName());
                }
            }
        }
    }

    @Override
    public void FixtureDetail(FixtureModel fixture) {
        fixtureViewModel.setFixtureModel(fixture);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("fixture", g.toJson(fixture));
        navController.navigate(R.id.action_leagueMatcheFragment_to_fixtureDetailFragment, bundle);
    }
}