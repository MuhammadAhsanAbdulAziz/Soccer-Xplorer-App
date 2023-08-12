package com.example.soccerxplorer;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.adapter.AdminFixtureAdapter;
import com.example.soccerxplorer.databinding.FragmentFixtureBinding;
import com.example.soccerxplorer.interfaces.FixtureInterface;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.viewmodel.FixtureViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;

import java.util.List;

public class FixtureFragment extends Fragment implements FixtureInterface {

    FragmentFixtureBinding binding;
    NavController navController;

    AdminFixtureAdapter adp;
    FixtureViewModel fixtureViewModel;
    TeamViewModel teamViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFixtureBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        fixtureViewModel = new ViewModelProvider(requireActivity()).get(FixtureViewModel.class);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        binding.fixturesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fixturesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.resultsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.fixturesBtn.setTextColor(getResources().getColor(R.color.white));
                fixtureViewModel.getPendingFixture().observe(requireActivity(), new Observer<List<FixtureModel>>() {
                    @Override
                    public void onChanged(List<FixtureModel> fixtureModels) {
                        adp.submitList(fixtureModels);
                    }
                });

            }
        });

        binding.resultsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fixturesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.resultsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                fixtureViewModel.getCompletedFixture().observe(requireActivity(), new Observer<List<FixtureModel>>() {
                    @Override
                    public void onChanged(List<FixtureModel> fixtureModels) {
                        adp.submitList(fixtureModels);
                    }
                });
            }
        });

        adp = new AdminFixtureAdapter(requireActivity(), this,teamViewModel);

        fixtureViewModel.getCompletedFixture().observe(requireActivity(), new Observer<List<FixtureModel>>() {
            @Override
            public void onChanged(List<FixtureModel> fixtureModels) {
                adp.submitList(fixtureModels);
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.fixurelist.setLayoutManager(mLayoutManager);
        binding.fixurelist.setAdapter(adp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void FixtureDetail(FixtureModel fixture) {

    }
}