package com.example.soccerxplorer.view.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentCreateFixtureBinding;
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.viewmodel.FixtureViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateFixtureFragment extends Fragment {

    NavController navController;
    private FragmentCreateFixtureBinding binding;
    private TeamViewModel teamViewModel;
    private FixtureViewModel fixtureViewModel;
    private List<String> TeamList = new ArrayList<>();
    private List<String> LeagueList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference("Teams");
    DatabaseReference databaseReference2 = FirebaseDatabase.
            getInstance().getReference("Leagues");
    private String TeamName1;
    private String TeamName2;
    private String LeagueName;
    int mYear, mMonth, mDay, mHour, mMinute;
    FixtureModel fixtureModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateFixtureBinding.inflate(inflater, container, false);

        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        fixtureViewModel = new ViewModelProvider(requireActivity()).get(FixtureViewModel.class);
        getTeamName();
        getLeagueName();

        binding.teamspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TeamName1 = TeamList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.teamspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TeamName2 = TeamList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.leaguespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeagueName = LeagueList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    private void getTeamName() {
        teamViewModel.getTeamName().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> teamName) {
                ArrayAdapter ad = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, teamName);
                binding.teamspinner1.setAdapter(ad);
                binding.teamspinner2.setAdapter(ad);
                TeamList = teamName;
            }
        });
    }

    private void getLeagueName() {
        fixtureViewModel.getLeagueName().observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> leagueName) {
                ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, leagueName);
                binding.leaguespinner.setAdapter(ad);
                LeagueList = leagueName;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setInitialData();
        binding.btnAddFixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFixture();
            }
        });

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        binding.btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });

        navController = Navigation.findNavController(view);
    }


    private void addFixture() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait..");
        String score1 = binding.team1score.getText().toString().trim();
        String score2 = binding.team2score.getText().toString().trim();
        String status = binding.status.getText().toString().trim();
        if (!(status.equals("1") || status.equals( "0"))) {
            binding.status.requestFocus();
            return;
        }
        if (TeamName1.isEmpty()) {
            binding.teamspinner1.requestFocus();
            return;
        }
        if (TeamName2.isEmpty()) {
            binding.teamspinner2.requestFocus();
            return;
        }
        if (LeagueName.isEmpty()) {
            binding.leaguespinner.requestFocus();
            return;
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("teamName").getValue(String.class);
                    if (name.equals(TeamName1)) {
                        TeamName1 = ds.child("teamId").getValue(String.class);

                    }
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("teamName").getValue(String.class);
                    if (name.equals(TeamName2)) {
                        TeamName2 = ds.child("teamId").getValue(String.class);

                    }
                }
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String name = ds.child("leagueName").getValue(String.class);
                            if (name.equals(LeagueName)) {
                                LeagueName = ds.child("leagueId").getValue(String.class);
                                String date = binding.inDate.getText().toString();
                                String time = binding.inTime.getText().toString();
                                fixtureViewModel.CreateFixture(new FixtureModel("",TeamName1,TeamName2,LeagueName,date,time,score1,score2,status),requireContext(),navController);
                            }
                        }
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

    private void pickDate()
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.inDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void pickTime()
    {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        binding.inTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setInitialData() {
        if(getArguments()!=null){
            String jsonNote = getArguments().getString("fixture");
            Gson g = new Gson();
            fixtureModel = g.fromJson(jsonNote, FixtureModel.class);
            if(fixtureModel!=null)
            {
                String teamName1 = teamViewModel.getTeamName(fixtureModel.getTeamId1());
                String teamName2 = teamViewModel.getTeamName(fixtureModel.getTeamId2());
                int team1 = 0;
                int team2 = 0;
                for(int i = 0;i<TeamList.size();i++)
                {
                    if(TeamList.get(i).equals(teamName1)) {
                        team1 = i;
                        break;
                    }
                }
                for(int i = 0;i<TeamList.size();i++)
                {
                    if(TeamList.get(i).equals(teamName2)) {
                        team2 = i;
                        break;
                    }
                }
                binding.teamspinner1.setSelection(team1);
                binding.teamspinner2.setSelection(team2);
                binding.inDate.setText(fixtureModel.getFixtureDate());
                binding.inTime.setText(fixtureModel.getFixtureTime());
                binding.team1score.setText(fixtureModel.getTeamScore1());
                binding.team2score.setText(fixtureModel.getTeamScore2());
                binding.status.setText(fixtureModel.getFixtureStatus());
                binding.btnAddFixture.setText("Update");
            }
        }
        else{
            binding.btnAddFixture.setText("Add");
        }
    }
}