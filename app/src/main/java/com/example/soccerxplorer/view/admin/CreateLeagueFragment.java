package com.example.soccerxplorer.view.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentCreateLeagueBinding;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateLeagueFragment extends Fragment {

    FragmentCreateLeagueBinding binding;
    NavController navController;
    LeagueViewModel leagueViewModel;
    LeagueModel leagueModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentCreateLeagueBinding.inflate(inflater,container,false);
        leagueViewModel = new ViewModelProvider(requireActivity()).get(LeagueViewModel.class);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInitialData();
        navController = Navigation.findNavController(view);

        binding.BookAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.BookAddBtn.getText().equals("Update"))
                {
                    UpdateLeague();
                }
                else {
                    addLeague();
                }
            }
        });
    }

    private void addLeague() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String Name = Objects.requireNonNull(binding.titlefield.getText()).toString().trim().toLowerCase(Locale.ROOT);
        String Country = binding.ccp.getSelectedCountryNameCode();
        if (Name.isEmpty()) {
            binding.titlefield.setError("Enter Title");
            binding.titlefield.requestFocus();
            return;
        }
        if (Country.isEmpty()) {
            binding.ccp.requestFocus();
            return;
        }

        leagueViewModel.CreateLeague(new LeagueModel("",Name,Country),requireContext(),navController);
    }

    private void UpdateLeague() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String Name = Objects.requireNonNull(binding.titlefield.getText()).toString().trim().toLowerCase(Locale.ROOT);
        String Country = binding.ccp.getSelectedCountryName();
        if (Name.isEmpty()) {
            binding.titlefield.setError("Enter Title");
            binding.titlefield.requestFocus();
            return;
        }
        if (Country.isEmpty()) {
            binding.ccp.requestFocus();
            return;
        }

        leagueViewModel.UpdateLeague(new LeagueModel(leagueModel.getLeagueId(),Name,Country),requireContext(),navController);
    }


    private void setInitialData() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("fixture");
            Gson g = new Gson();
            leagueModel = g.fromJson(jsonNote, LeagueModel.class);
            if (leagueModel != null) {
                binding.titlefield.setText(leagueModel.getLeagueName());
                binding.ccp.setCountryForNameCode(leagueModel.getLeagueCountry());
                binding.BookAddBtn.setText("Update");
            } else {
                binding.BookAddBtn.setText("Add");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}