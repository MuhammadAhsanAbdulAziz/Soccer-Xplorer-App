package com.example.soccerxplorer.view.admin;

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

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminUserAdapter;
import com.example.soccerxplorer.databinding.FragmentAllUserBinding;
import com.example.soccerxplorer.databinding.FragmentCreateUserBinding;
import com.example.soccerxplorer.interfaces.UserInterface;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AllUserFragment extends Fragment implements UserInterface {

    FragmentAllUserBinding binding;
    NavController navController;
    AdminUserAdapter adapter;
    UserViewModel userViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllUserBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        adapter = new AdminUserAdapter(requireContext(),this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        userViewModel.getUser().observe(requireActivity(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> userModels) {
                adapter.submitList(userModels);

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.userList.setLayoutManager(mLayoutManager);
        binding.userList.setAdapter(adapter);

        binding.addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_allUserFragment_to_createUserFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void UserDetail(UserModel userModel) {
        userViewModel.setUserModel(userModel);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("user",g.toJson(userModel) );
        navController.navigate(R.id.action_allUserFragment_to_createUserFragment,bundle);
    }
}