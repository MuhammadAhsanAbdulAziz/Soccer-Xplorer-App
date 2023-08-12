package com.example.soccerxplorer.view.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminFixtureAdapter;
import com.example.soccerxplorer.adapter.AdminPlayerAdapter;
import com.example.soccerxplorer.databinding.FragmentAllFixtureBinding;
import com.example.soccerxplorer.databinding.FragmentAllLeagueBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.FixtureViewModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AllFixtureFragment extends Fragment implements FixtureInterface {

    FragmentAllFixtureBinding binding;
    NavController navController;
    AdminFixtureAdapter adp;
    FixtureViewModel fixtureViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllFixtureBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        adp = new AdminFixtureAdapter(requireActivity(), this);
        fixtureViewModel.getFixture().observe(requireActivity(), new Observer<List<FixtureModel>>() {
            @Override
            public void onChanged(List<FixtureModel> fixtureModels) {
                adp.submitList(fixtureModels);
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.fixtureList.setLayoutManager(mLayoutManager);
        binding.fixtureList.setAdapter(adp);

        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_allPlayerFragment_to_createPlayerFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void FixtureDetail(FixtureModel fixture) {
        fixtureViewModel.setFixtureModel(fixture);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("player",g.toJson(fixture) );
        navController.navigate(R.id.action_allPlayerFragment_to_createPlayerFragment,bundle);
    }
}