package com.example.soccerxplorer;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.FragmentPlayerDetailBinding;
import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.UUID;

public class PlayerDetailFragment extends Fragment {

    FragmentPlayerDetailBinding binding;
    NavController navController;
    PlayerModel playerModel;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    DatabaseReference playerRef = FirebaseDatabase.
            getInstance().getReference("Players");
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Favourite Players");

    String playerId;
    String userid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerDetailBinding.inflate(inflater, container, false);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        userid = UtilManager.getDefaults("userId",requireContext());


        if(UtilManager.getDefaults("userId",requireContext())==null)
        {
            binding.filltogglefav.setVisibility(View.INVISIBLE);
            binding.unfilltogglefav.setVisibility(View.INVISIBLE);
        }

        binding.unfilltogglefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueID = UUID.randomUUID().toString();
                binding.unfilltogglefav.setVisibility(View.GONE);
                binding.filltogglefav.setVisibility(View.VISIBLE);
                databaseReference.child(uniqueID).setValue(
                        new FavouritePlayerModel(uniqueID, UtilManager.getDefaults("userId", requireContext()), playerId));
                if(navController.findDestination(R.id.favouriteFragment)!=null){
                    navController.popBackStack(R.id.favouriteFragment, true);
                }
                navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                        if(navDestination.getId() == R.id.homeFragment) {
//                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                        else if(navDestination.getId()==R.id.adminHomeFragment){
//                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        binding.filltogglefav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.unfilltogglefav.setVisibility(View.VISIBLE);
                binding.filltogglefav.setVisibility(View.GONE);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren())
                        {
                            if(ds.child("playerId").getValue(String.class).equals(playerId))
                            {
                                databaseReference.child(ds.getKey()).removeValue();
                                if(navController.clearBackStack(R.id.favouriteFragment)) {
                                    navController.popBackStack(R.id.favouriteFragment, true);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        setInitialData();
    }

    private void setInitialData() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("player");
            String jsonNote2 = getArguments().getString("playerfromfav");
            if (jsonNote2 == null && jsonNote!=null) {
                Gson g = new Gson();
                playerModel = g.fromJson(jsonNote, PlayerModel.class);
                if (playerModel != null) {
                    playerId = playerModel.getPlayerId();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds:snapshot.getChildren())
                            {
                                if(ds.child("userId").getValue(String.class)
                                        .equals(userid))
                                {
                                    binding.filltogglefav.setVisibility(View.VISIBLE);
                                    binding.unfilltogglefav.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    teamRef.child(playerModel.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Glide.with(requireContext()).load(snapshot.child("teamImage").
                                    getValue(String.class)).dontAnimate().into(binding.teamImage);
                            Glide.with(requireContext()).load(playerModel.getPlayerImage()).dontAnimate().into(binding.playerImage);
                            binding.playerNameBg.setText(playerModel.getPlayerName());
                            binding.playerName.setText(playerModel.getPlayerName());
                            binding.playerNumber.setText(playerModel.getPlayerNumber());
                            binding.playerType.setText(playerModel.getPlayerPosition());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            } else if (jsonNote2 != null && jsonNote == null) {
                binding.filltogglefav.setVisibility(View.VISIBLE);
                binding.unfilltogglefav.setVisibility(View.INVISIBLE);
                playerRef.child(jsonNote2).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Glide.with(requireContext()).load(snapshot.child("playerImage").getValue(String.class)).dontAnimate().into(binding.playerImage);
                        binding.playerNameBg.setText(snapshot.child("playerName").getValue(String.class));
                        binding.playerName.setText(snapshot.child("playerName").getValue(String.class));
                        binding.playerNumber.setText(snapshot.child("playerNumber").getValue(String.class));
                        binding.playerType.setText(snapshot.child("playerPosition").getValue(String.class));
                        playerId = snapshot.child("playerId").getValue(String.class);
                        teamRef.child(snapshot.child("teamId").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Glide.with(requireContext()).load(snapshot.child("teamImage").
                                        getValue(String.class)).dontAnimate().into(binding.teamImage);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}