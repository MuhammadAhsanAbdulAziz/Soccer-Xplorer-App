package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.databinding.FragmentLoginBinding;
import com.example.soccerxplorer.databinding.FragmentSettingBinding;
import com.example.soccerxplorer.util.UtilManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingFragment extends Fragment {


    NavController navController;
    FragmentSettingBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    BottomSheetDialog dialog;
    DatabaseReference userRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSettingBinding.inflate(inflater, container, false);

        userRef = FirebaseDatabase.getInstance().getReference("Users");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        if(UtilManager.getDefaults("userId",requireContext())!=null)
        {
            userRef.child(UtilManager.getDefaults("userId",requireContext())).
                    addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    binding.userName.setText(snapshot.child("userFullName").getValue(String.class));
                    binding.userEmail.setText(snapshot.child("userEmail").getValue(String.class));
                    Glide.with(requireContext()).load(snapshot.child("userImage").getValue(String.class)).error(R.drawable.logo).
                            dontAnimate().into(binding.profileImage);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        dialog = new BottomSheetDialog(requireContext());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        if(UtilManager.getDefaults("userId",requireContext())==null)
        {
            binding.profileheader.setVisibility(View.GONE);
            binding.personalsection.setVisibility(View.GONE);
            binding.logoutsection.setVisibility(View.GONE);
        }

        binding.profileeditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_settingFragment_to_editProfileFragment);
            }
        });

        binding.feedbacksection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_settingFragment_to_feedbackFragment);
            }
        });

        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.logout_bottom_dialog,null,false);
                Button logoutbtn = view.findViewById(R.id.userlogoutbtn);
                Button cancellbtn = view.findViewById(R.id.btncancel);
                logoutbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logout();
                    }
                });
                cancellbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
            }
        });
    }


    private void logout()
    {
        auth.signOut();
        UtilManager.setDefaults("userId",null,requireContext());
        UtilManager.setDefaults("userRole",null,requireContext());
        navController.popBackStack();
        dialog.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}