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

import com.example.soccerxplorer.databinding.FragmentWelcomeBinding;
import com.example.soccerxplorer.util.UtilManager;

public class WelcomeFragment extends Fragment {

    NavController navController;
    FragmentWelcomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if(UtilManager.getDefaults("userId",requireContext())!=null)
        {
            navController.navigate(R.id.action_welcomeFragment_to_homeFragment);
        }

        binding.iHaveAnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        });

        binding.getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}