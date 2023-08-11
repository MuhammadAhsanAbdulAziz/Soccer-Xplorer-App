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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminPlayerAdapter;
import com.example.soccerxplorer.databinding.FragmentAllPlayerBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.google.gson.Gson;

import java.util.List;

public class AllPlayerFragment extends Fragment implements PlayerInterface {

    FragmentAllPlayerBinding binding;
    NavController navController;
    AdminPlayerAdapter adp;
    PlayerViewModel playerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllPlayerBinding.inflate(inflater,container,false);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        adp = new AdminPlayerAdapter(requireActivity(), this);
        playerViewModel.getPlayer().observe(requireActivity(), new Observer<List<PlayerModel>>() {
            @Override
            public void onChanged(List<PlayerModel> playerModels) {
                adp.submitList(playerModels);
            }
        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.playerList.setLayoutManager(mLayoutManager);
        binding.playerList.setAdapter(adp);

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
    public void PlayerDetail(PlayerModel player) {
        playerViewModel.setPlayerModel(player);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("player",g.toJson(player) );
        navController.navigate(R.id.action_allPlayerFragment_to_createPlayerFragment,bundle);
    }
}