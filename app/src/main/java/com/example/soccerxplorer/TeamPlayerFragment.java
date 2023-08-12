package com.example.soccerxplorer;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.adapter.AdminPlayerAdapter;
import com.example.soccerxplorer.databinding.FragmentTeamPlayerBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;

public class TeamPlayerFragment extends Fragment implements PlayerInterface {

    FragmentTeamPlayerBinding binding;
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
        binding = FragmentTeamPlayerBinding.inflate(inflater, container, false);
        adp = new AdminPlayerAdapter(requireContext(), this);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTeamPlayers();
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setTeamPlayers() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("teamId");
            if (jsonNote != null) {
                playerViewModel.getPlayerbyTeam(jsonNote).observe(requireActivity(), new Observer<List<PlayerModel>>() {
                    @Override
                    public void onChanged(List<PlayerModel> playerModels) {
                        adp.submitList(playerModels);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
                        binding.playerlist.setLayoutManager(mLayoutManager);
                        binding.playerlist.setAdapter(adp);
                    }
                });
            }

        }
    }

    @Override
    public void PlayerDetail(PlayerModel player) {
        playerViewModel.setPlayerModel(player);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("player",g.toJson(player));
        navController.navigate(R.id.action_teamPlayerFragment_to_playerDetailFragment,bundle);
    }
}