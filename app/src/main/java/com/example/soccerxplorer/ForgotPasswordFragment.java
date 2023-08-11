package com.example.soccerxplorer;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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

import com.example.soccerxplorer.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    FragmentForgotPasswordBinding binding;
    NavController navController;
    FirebaseAuth myAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);


        myAuth = FirebaseAuth.getInstance();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
                ForgotPasswordActivity.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        binding.forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean emailValidation(){
        String email = binding.emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            binding.emailLayout.setError("Email Address is Required!!!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailLayout.setError("Enter Valid Email Address!!!");
            return false;
        }
        else {
            binding.emailLayout.setError(null);
            return true;
        }
    }
    private void validation() {
        boolean emailErr = false, passwordErr = false;
        emailErr = emailValidation();
        if(emailErr){
            forgot();
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
            messageTextViewTwo.setText("Please Connect To The Internet To Proceed Further!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialogTwo.dismiss();
                }
            },3000);
            return false;
        }
    }
    private void forgot(){
        boolean checking = false;
        checking = connectionCheck();
        if(checking){
            AlertDialog.Builder builder = new AlertDialog.Builder(  requireContext());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
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
            messageTextView.setText("Loading...!");
            myAuth.sendPasswordResetEmail(binding.emailEditText.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                alertDialog.dismiss();
                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
                                LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
                                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
                                builderTwo.setView(dialogviewTwo);
                                AlertDialog alertDialogTwo = builderTwo.create();
                                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                TextView messageTextViewTwo;
                                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                                messageTextViewTwo.setText("We have sent you Email to reset your password on "+binding.emailEditText.getText().toString().trim()+"!!!");
                                alertDialogTwo.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialogTwo.dismiss();
                                        navController.popBackStack();
                                    }
                                },4000);
                            } else {
                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
                                LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
                                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_warning_alert, null);
                                builderTwo.setView(dialogviewTwo);
                                AlertDialog alertDialogTwo = builderTwo.create();
                                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                TextView messageTextViewTwo;
                                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                                messageTextViewTwo.setText("Failed to send reset email!");
                                alertDialogTwo.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialogTwo.dismiss();
                                    }
                                },2000);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}