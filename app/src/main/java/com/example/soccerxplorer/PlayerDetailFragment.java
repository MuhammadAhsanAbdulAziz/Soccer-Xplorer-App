package com.example.soccerxplorer;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.FragmentPlayerDetailBinding;
import com.example.soccerxplorer.model.PlayerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class PlayerDetailFragment extends Fragment {

    FragmentPlayerDetailBinding binding;
    NavController navController;
    PlayerModel playerModel;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerDetailBinding.inflate(inflater,container,false);

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
        setInitialData();
    }


    private void setInitialData() {
        if(getArguments()!=null){
            String jsonNote = getArguments().getString("player");
            Gson g = new Gson();
            playerModel = g.fromJson(jsonNote, PlayerModel.class);
            if(playerModel!=null)
            {
                teamRef.child(playerModel.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Glide.with(requireContext()).load(snapshot.child("teamImage").
                                getValue(String.class)).dontAnimate().into(binding.teamLogo);
                        Glide.with(requireContext()).load(playerModel.getPlayerImage()).dontAnimate().into(binding.teamImage);
                        binding.playerName.setText(playerModel.getPlayerName());
                        binding.playerNumber.setText(playerModel.getPlayerNumber());
                        binding.playerType.setText(playerModel.getPlayerPosition());
                        binding.playerName.setText(playerModel.getPlayerName());
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