package com.example.soccerxplorer.view.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentCreatePlayerBinding;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreatePlayerFragment extends Fragment {
    NavController navController;
    FragmentCreatePlayerBinding binding;
    Uri u;
    TeamViewModel teamViewModel;
    PlayerViewModel playerViewModel;
    String TeamName;
    List<String> TeamList = new ArrayList<>();
    PlayerModel playerModel;

    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Teams");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        getTeamName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePlayerBinding.inflate(inflater, container, false);

        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imgfield.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 404);
        });

        binding.teamspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TeamName = TeamList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.BookAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.BookAddBtn.getText().equals("Update"))
                {
                    updatePlayer();
                }
                else {
                    addPlayer();
                }
            }
        });

        navController = Navigation.findNavController(view);

        setInitialData();
    }

    private void getTeamName() {
        teamViewModel.getTeamName().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> teamName) {
                ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, teamName);
                binding.teamspinner.setAdapter(ad);
                TeamList = teamName;
            }
        });
    }

    private void addPlayer() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String Name = Objects.requireNonNull(binding.titlefield.getText()).toString().trim().toLowerCase(Locale.ROOT);
        String Country = binding.ccp.getSelectedCountryNameCode();
        String position = binding.position.getText().toString().trim().toLowerCase(Locale.ROOT);
        String number = binding.number.getText().toString().trim().toLowerCase(Locale.ROOT);
        if (u == null) {
            UtilManager.errorMessage(requireContext(), "Enter Team Image");
            return;
        }
        if (Name.isEmpty()) {
            binding.titlefield.setError("Enter Title");
            binding.titlefield.requestFocus();
            return;
        }
        if (position.isEmpty()) {
            binding.position.setError("Enter Position");
            binding.titlefield.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            binding.number.setError("Enter Player Number");
            binding.titlefield.requestFocus();
            return;
        }
        if (Country.isEmpty()) {
            binding.ccp.requestFocus();
            return;
        }
        if (TeamName.isEmpty()) {
            binding.teamspinner.requestFocus();
            return;
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("teamName").getValue(String.class);
                    if (name.equals(TeamName)) {
                        String TeamNameId = ds.child("teamId").getValue(String.class);
                        playerViewModel.CreatePlayer(new PlayerModel("", Name,Country, TeamNameId, u.toString(),position,number)
                                , requireContext(), navController);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatePlayer() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String Name = Objects.requireNonNull(binding.titlefield.getText()).toString().trim().toLowerCase(Locale.ROOT);
        String Country = binding.ccp.getSelectedCountryNameCode();
        String position = binding.position.getText().toString().trim().toLowerCase(Locale.ROOT);
        String number = binding.number.getText().toString().trim().toLowerCase(Locale.ROOT);
        if (u == null) {
            UtilManager.errorMessage(requireContext(), "Enter Team Image");
            return;
        }
        if (number.isEmpty()) {
            binding.titlefield.setError("Enter Player Number");
            binding.titlefield.requestFocus();
            return;
        }
        if (position.isEmpty()) {
            binding.position.setError("Enter Player Position");
            binding.titlefield.requestFocus();
            return;
        }
        if (Name.isEmpty()) {
            binding.titlefield.setError("Enter Title");
            binding.titlefield.requestFocus();
            return;
        }
        if (Country.isEmpty()) {
            binding.ccp.requestFocus();
            return;
        }
        if (TeamName.isEmpty()) {
            binding.teamspinner.requestFocus();
            return;
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("teamName").getValue(String.class);
                    if (name.equals(TeamName)) {
                        String TeamNameId = ds.child("teamId").getValue(String.class);
                        playerViewModel.UpdatePlayer(new PlayerModel(playerViewModel.getPlayerModel().getPlayerId(),
                                        Name,Country, TeamNameId, u.toString(),position,number)
                                , requireContext(), navController);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 404) {
            if (data != null) {
                u = data.getData();
                binding.imgfield.setImageURI(u);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setInitialData() {
        if(getArguments()!=null){
            String jsonNote = getArguments().getString("player");
            Gson g = new Gson();
            playerModel = g.fromJson(jsonNote,PlayerModel.class);
            if(playerModel!=null)
            {
                databaseReference.child(playerModel.getTeamId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String team = snapshot.child("teamName").getValue(String.class);
                        binding.teamspinner.setPrompt(team);
                        int c = 0;
                        for(int i = 0;i<TeamList.size();i++)
                        {
                            if(TeamList.get(i).equals(team)) {
                                c = i;
                                break;
                            }
                        }
                        binding.teamspinner.setSelection(c);
                        Glide.with(requireContext()).load(playerModel.getPlayerImage()).
                                error(R.drawable.ic_launcher_background).dontAnimate().into(binding.imgfield);
                        binding.titlefield.setText(playerModel.getPlayerName());
                        binding.ccp.setCountryForNameCode(playerModel.getPlayerCountry());
                        u = Uri.parse(playerModel.getPlayerImage());
                        binding.BookAddBtn.setText("Update");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        else{
            binding.BookAddBtn.setText("Add");
        }
    }
}