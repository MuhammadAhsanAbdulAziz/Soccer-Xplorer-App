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

import com.example.soccerxplorer.databinding.FragmentCreateTeamBinding;
import com.example.soccerxplorer.databinding.FragmentHomeBinding;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateTeamFragment extends Fragment {

    FragmentCreateTeamBinding binding;
    NavController navController;
    Uri u;
    TeamViewModel teamViewModel;
    String Country;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTeamBinding.inflate(inflater, container, false);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.imgfield.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 404);
        });

        binding.BookAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeam();
            }
        });
    }

    private void addTeam() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String Name = Objects.requireNonNull(binding.titlefield.getText()).toString().trim().toLowerCase(Locale.ROOT);
        Country = binding.ccp.getSelectedCountryName();
        if (u == null) {
            errorMessage("Enter Team Image");
            return;
        }
        if (Name.isEmpty()) {
            binding.titlefield.setError("Enter Title");
            binding.titlefield.requestFocus();
            return;
        }
        if (Country.isEmpty()) {
            binding.ccp.requestFocus();
            return;
        }

        teamViewModel.CreateTeam(new TeamModel("",Name,Country,u.toString()));
        SuccessMessage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 404) {
            if (data != null) {
                u = data.getData();
                binding.imgfield.setImageURI(u);
            }
        }
    }

    public void SuccessMessage() {
        new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("IMPORTANT").
                setContentText("Team Added Successfully").setConfirmText("Okay").setConfirmClickListener(sweetAlertDialog -> {
                    navController.popBackStack();
                    sweetAlertDialog.cancel();
                    sweetAlertDialog.dismiss();
                }).show();

    }

    public void errorMessage(String msg) {

        new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(msg).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}