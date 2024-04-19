package com.example.soccerxplorer.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.adapter.PlayerAdapter;
import com.example.soccerxplorer.databinding.FragmentTeamDetailBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class TeamDetailFragment extends Fragment implements PlayerInterface {

    FragmentTeamDetailBinding binding;
    NavController navController;
    TeamViewModel teamViewModel;
    PlayerViewModel playerViewModel;
    TeamModel teamModel;
    PlayerAdapter playerAdapter;
    String teamId;
    String userid;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    DatabaseReference playerRef = FirebaseDatabase.
            getInstance().getReference("Players");
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Favourite Team");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTeamDetailBinding.inflate(inflater, container, false);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        playerAdapter = new PlayerAdapter(requireContext(), this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        userid = UtilManager.getDefaults("userId", requireContext());
        setData();
    }

    private void setData() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("team");
            String jsonNote2 = getArguments().getString("teamfromfav");
            if (jsonNote2 == null && jsonNote != null) {
                Gson g = new Gson();
                teamModel = g.fromJson(jsonNote, TeamModel.class);
                if (teamModel != null) {
                    teamId = teamModel.getTeamId();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (Objects.equals(ds.child("userId").getValue(String.class), userid)) {
                                    binding.filltogglefav.setVisibility(View.VISIBLE);
                                    binding.unfilltogglefav.setVisibility(View.INVISIBLE);
                                }
                                else{
                                    binding.filltogglefav.setVisibility(View.INVISIBLE);
                                    binding.unfilltogglefav.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    teamRef.child(teamModel.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Glide.with(requireContext()).load(snapshot.child("teamImage").
                                    getValue(String.class)).dontAnimate().into(binding.TeamLogo);
                            Glide.with(requireContext()).load(snapshot.child("teamImage").
                                    getValue(String.class)).dontAnimate().into(binding.teamImage);
                            binding.teamTwoName.setText(snapshot.child("teamName").getValue(String.class));
                            binding.temName.setText(snapshot.child("teamName").getValue(String.class));
                            playerViewModel.getPlayerbyTeam(teamModel.getTeamId()).observe(requireActivity(), new Observer<List<PlayerModel>>() {
                                @Override
                                public void onChanged(List<PlayerModel> playerModels) {
                                    playerAdapter.submitList(playerModels);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
                                    binding.playerlist.setLayoutManager(gridLayoutManager);
                                    binding.playerlist.setAdapter(playerAdapter);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
//            } else if (jsonNote2 != null && jsonNote == null) {
//                binding.filltogglefav.setVisibility(View.VISIBLE);
//                binding.unfilltogglefav.setVisibility(View.INVISIBLE);
//                playerRef.child(jsonNote2).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Glide.with(requireContext()).load(snapshot.child("playerImage").getValue(String.class)).dontAnimate().into(binding.playerImage);
//                        binding.playerNameBg.setText(snapshot.child("playerName").getValue(String.class));
//                        binding.playerName.setText(snapshot.child("playerName").getValue(String.class));
//                        binding.playerNumber.setText(snapshot.child("playerNumber").getValue(String.class));
//                        binding.playerType.setText(snapshot.child("playerPosition").getValue(String.class));
//                        playerId = snapshot.child("playerId").getValue(String.class);
//                        teamRef.child(snapshot.child("teamId").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Glide.with(requireContext()).load(snapshot.child("teamImage").
//                                        getValue(String.class)).dontAnimate().into(binding.teamImage);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void PlayerDetail(PlayerModel player) {

    }
}