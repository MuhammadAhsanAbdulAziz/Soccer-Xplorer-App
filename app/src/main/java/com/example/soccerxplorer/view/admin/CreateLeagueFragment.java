package com.example.soccerxplorer.view.admin;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.soccerxplorer.databinding.FragmentCreateLeagueBinding;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;

import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateLeagueFragment extends Fragment {

    FragmentCreateLeagueBinding binding;
    NavController navController;
    LeagueViewModel leagueViewModel;

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

        navController = Navigation.findNavController(view);

        binding.BookAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLeague();
            }
        });
    }

    private void addLeague() {
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

        leagueViewModel.CreateLeague(new LeagueModel("",Name,Country),requireContext(),navController);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}