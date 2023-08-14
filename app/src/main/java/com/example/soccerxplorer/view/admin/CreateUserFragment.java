package com.example.soccerxplorer.view.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.AdminUserAdapter;
import com.example.soccerxplorer.databinding.FragmentCreateUserBinding;
import com.example.soccerxplorer.interfaces.UserInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class CreateUserFragment extends Fragment {

    FragmentCreateUserBinding binding;
    NavController navController;
    UserViewModel  userViewModel;
    UserModel userModel;
    DatabaseReference userRef = FirebaseDatabase.
            getInstance().getReference("Users");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateUserBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setInitialData();

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        binding.statusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                statusValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean statusValidation(){
        String status = binding.statusEditText.getText().toString().trim();
        if(status.isEmpty()){
            binding.statusLayout.setError("Status is Required!");
            return false;
        }
        else {
            binding.statusLayout.setError(null);
            return true;
        }
    }

    private void validation() {
        boolean statusErr = false;
        statusErr = statusValidation();
        if((statusErr)){
            updateStatus();
        }
    }

    private boolean connectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiConn != null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())){
            return true;
        } else {
            AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
            LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
            View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_connection_error, null);
            builderTwo.setView(dialogviewTwo);
            AlertDialog alertDialogTwo = builderTwo.create();
            alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialogTwo.setCancelable(false);
            alertDialogTwo.setCanceledOnTouchOutside(false);
            alertDialogTwo.show();
            TextView messageTextViewTwo;
            messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
            messageTextViewTwo.setText("Please check your internet connection \nand try again");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialogTwo.dismiss();
                }
            },5000);
            return false;
        }
    }

    private void updateStatus()
    {
        boolean checking = false;
        checking = connectionCheck();
        if(checking){
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater =   requireActivity().getLayoutInflater();
            View dialogview = inflater.inflate(R.layout.dialog_progress_alert, null);
            builder.setView(dialogview);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            TextView messageTextView;
            messageTextView = dialogview.findViewById(R.id.messageTextView);
            messageTextView.setText("Updating Status!");
            userRef.child(userModel.getUserId()).child("userStatus").setValue(binding.statusEditText.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    alertDialog.dismiss();
                    AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
                    LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
                    View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
                    builderTwo.setView(dialogviewTwo);
                    AlertDialog alertDialogTwo = builderTwo.create();
                    alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    alertDialogTwo.setCancelable(false);
                    alertDialogTwo.setCanceledOnTouchOutside(false);
                    TextView messageTextViewTwo;
                    messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                    messageTextViewTwo.setText("Account Updated Successfully!!!");
                    alertDialogTwo.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogTwo.dismiss();
                            navController.popBackStack();
                        }
                    },2000);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    alertDialog.dismiss();
                    AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
                    LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
                    View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_warning_alert, null);
                    builderTwo.setView(dialogviewTwo);
                    AlertDialog alertDialogTwo = builderTwo.create();
                    alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    alertDialogTwo.setCancelable(false);
                    alertDialogTwo.setCanceledOnTouchOutside(false);
                    TextView messageTextViewTwo;
                    messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                    messageTextViewTwo.setText("Please! Enter Status!");
                    alertDialogTwo.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogTwo.dismiss();
                        }
                    },5000);
                }
            });
        }
    }


    private void setInitialData() {
        if (getArguments() != null) {
            String jsonNote = getArguments().getString("user");
            Gson g = new Gson();
            userModel = g.fromJson(jsonNote, UserModel.class);
            if (userModel != null) {
                binding.nameEditText.setText(userModel.getUserFullName());
                binding.contactEditText.setText(userModel.getUserContact());
                binding.EmailEditText.setText(userModel.getUserEmail());
                binding.statusEditText.setText(userModel.getUserStatus());
                Glide.with(requireContext()).load(userModel.getUserImage()).error(R.drawable.logo).dontAnimate().into(binding.image);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}