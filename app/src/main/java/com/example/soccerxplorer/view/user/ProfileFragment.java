package com.example.soccerxplorer.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentProfileBinding;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.UserViewModel;

import java.util.List;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    NavController navController;
    UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
        binding.forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_profileFragment_to_forgotPasswordFragment);
            }
        });

        if(UtilManager.getDefaults("userId",requireContext())!=null)
        {
            userViewModel.getUserbyId(UtilManager.getDefaults("userId",requireContext())).observe(requireActivity(), new Observer<List<UserModel>>() {
                @Override
                public void onChanged(List<UserModel> userModels) {
                    binding.nameEditText.setText(userModels.get(0).getUserFullName());
                    binding.usernameEditText.setText(userModels.get(0).getUserName());
                    binding.contactEditText.setText(userModels.get(0).getUserContact());
                    Glide.with(requireContext()).load(userModels.get(0).getUserImage()).error(R.drawable.logo).
                            dontAnimate().into(binding.image);
                }
            });
        }

    }

    private void setInitial()
    {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}