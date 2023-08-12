package com.example.soccerxplorer.view.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentAdminHomeBinding;
import com.example.soccerxplorer.util.UtilManager;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeFragment extends Fragment {

    FragmentAdminHomeBinding binding;
    FirebaseAuth firebaseAuth;
    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.AddTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allTeamFragment);
            }
        });

        binding.AddPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allPlayerFragment);
            }
        });

        binding.AddFixtureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allFixtureFragment);
            }
        });

        binding.AddLeagueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allLeagueFragment);
            }
        });

        binding.Logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                UtilManager.setDefaults("userId",null,requireContext());
                UtilManager.setDefaults("userRole",null,requireContext());
                navController.popBackStack();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}