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

import com.example.soccerxplorer.MainActivity;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminFeedbackAdapter;
import com.example.soccerxplorer.databinding.FragmentAdminHomeBinding;
import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FeedbackViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminHomeFragment extends Fragment {

    FragmentAdminHomeBinding binding;
    FirebaseAuth firebaseAuth;
    NavController navController;
    FeedbackViewModel feedbackViewModel;
    AdminFeedbackAdapter adapter;

    DatabaseReference userRef = FirebaseDatabase.
            getInstance().getReference("Users");

    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");

    DatabaseReference playerRef = FirebaseDatabase.
            getInstance().getReference("Players");

    DatabaseReference leagueRef = FirebaseDatabase.
            getInstance().getReference("Leagues");

    DatabaseReference matchRef = FirebaseDatabase.
            getInstance().getReference("Fixtures");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        feedbackViewModel = new ViewModelProvider(requireActivity()).get(FeedbackViewModel.class);
        adapter = new AdminFeedbackAdapter(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        setCounts();
        if (MainActivity.getInstance().bottomNavigationView.getVisibility() == View.GONE) {
            MainActivity.getInstance().setUserMenu();
        }
        feedbackViewModel.getFeedback().observe(requireActivity(), new Observer<List<FeedbackModel>>() {
            @Override
            public void onChanged(List<FeedbackModel> feedbackModels) {
                adapter.submitList(feedbackModels);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());

            }
        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_searchFragment);
            }
        });

        binding.AddTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allTeamFragment);
            }
        });

        binding.AddPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allPlayerFragment);
            }
        });

        binding.AddFixtureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allFixtureFragment);
            }
        });

        binding.AddLeagueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allLeagueFragment);
            }
        });
        binding.userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_allUserFragment);
            }
        });

    }

    private void setCounts() {
//        userRef.addValueEventListener(new ValueEventListener() {
//            int count = 0;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    count++;
//                }
//                binding.usercount.setText("" + count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        teamRef.addValueEventListener(new ValueEventListener() {
//            int count = 0;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    count++;
//                }
//                binding.teamcount.setText("" + count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        playerRef.addValueEventListener(new ValueEventListener() {
//            int count = 0;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds:snapshot.getChildren())
//                {
//                    count++;
//                }
//                binding.playercount.setText(""+count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        leagueRef.addValueEventListener(new ValueEventListener() {
//            int count = 0;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds:snapshot.getChildren())
//                {
//                    count++;
//                }
//                binding.leaguecount.setText(""+count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        matchRef.addValueEventListener(new ValueEventListener() {
//            int count = 0;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds:snapshot.getChildren())
//                {
//                    count++;
//                }
//                binding.matchcount.setText(""+count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}