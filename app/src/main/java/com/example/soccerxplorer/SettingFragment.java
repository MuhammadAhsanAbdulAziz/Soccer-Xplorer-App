package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.databinding.FragmentLoginBinding;
import com.example.soccerxplorer.databinding.FragmentSettingBinding;
import com.example.soccerxplorer.util.UtilManager;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {


    NavController navController;
    FragmentSettingBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if(UtilManager.getDefaults("userId",requireContext())==null)
        {
            binding.profileheader.setVisibility(View.GONE);
            binding.personalsection.setVisibility(View.GONE);
            binding.logoutsection.setVisibility(View.GONE);
        }
        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
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