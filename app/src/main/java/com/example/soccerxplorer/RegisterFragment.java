package com.example.soccerxplorer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.soccerxplorer.databinding.FragmentRegisterBinding;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    NavController navController;
    FirebaseAuth myAuth;
    UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     binding = FragmentRegisterBinding.inflate(inflater,container,false);
     myAuth = FirebaseAuth.getInstance();
     userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
     return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPasswordValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.contactEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contactValidation();
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
        } else {
            binding.emailLayout.setError(null);
            return true;
        }
    }
    private boolean usernameValidation(){
        String nameInput = binding.usernameEditText.getText().toString().trim();
        if(nameInput.isEmpty()){
            binding.usernameLayout.setError("Username is Required!!!");
            return false;
        } else if(!Pattern.compile("^[a-zA-Z\\s]*$").matcher(nameInput).matches()){
            binding.usernameLayout.setError("Enter Username In Only Text!!!");
            return false;
        } else if(nameInput.length() < 3){
            binding.usernameLayout.setError("Enter Username at least 3 characters!!!");
            return false;
        } else {
            binding.usernameLayout.setError(null);
            return true;
        }
    }
    private boolean nameValidation(){
        String nameInput = binding.nameEditText.getText().toString().trim();
        if(nameInput.isEmpty()){
            binding.nameLayout.setError("Your Name is Required!!!");
            return false;
        } else if(!Pattern.compile("^[a-zA-Z\\s]*$").matcher(nameInput).matches()){
            binding.nameLayout.setError("Enter Name In Only Text!!!");
            return false;
        } else if(nameInput.length() < 3){
            binding.nameLayout.setError("Enter Name at least 3 characters!!!");
            return false;
        } else {
            binding.nameLayout.setError(null);
            return true;
        }
    }
    private boolean passwordValidation(){
        String password = binding.passwordEditText.getText().toString().trim();
        if(password.isEmpty()){
            binding.passwordLayout.setError("Password is Required!!!");
            return false;
        } else if(password.length() < 8){
            binding.passwordLayout.setError("Password at least 8 Characters!!!");
            return false;
        }
        else {
            binding.passwordLayout.setError(null);
            return true;
        }
    }
    private boolean confirmPasswordValidation(){
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        if(confirmPassword.isEmpty()){
            binding.confirmPasswordLayout.setError("Confirm Password is Required!!!");
            return false;
        } else if(confirmPassword.length() < 8){
            binding.confirmPasswordLayout.setError("Confirm Password at least 8 Characters!!!");
            return false;
        }else if(!confirmPassword.equals(password)){
            binding.confirmPasswordLayout.setError("Confirm Password is Not Matched!!!");
            return false;
        }else {
            binding.confirmPasswordLayout.setError(null);
            return true;
        }
    }
    private boolean contactValidation(){
        String contact = binding.contactEditText.getText().toString().trim();
        if(contact.isEmpty()){
            binding.contactLayout.setError("Contact no is Required!!!");
            return false;
        } else if(!Patterns.PHONE.matcher(contact).matches()){
            binding.contactLayout.setError("Enter Valid Contact no!!!");
            return false;
        } else if(contact.length() < 10){
            binding.contactLayout.setError("Contact no at least 10 Digits!!!");
            return false;
        }
        else {
            binding.contactLayout.setError(null);
            return true;
        }
    }
    private void validation() {
        boolean emailErr = false, passwordErr = false, confirmPasswordErr = false, nameErr = false, usernameErr = false, contactErr = false;
        emailErr = emailValidation();
        passwordErr = passwordValidation();
        confirmPasswordErr = confirmPasswordValidation();
        nameErr = nameValidation();
        contactErr = contactValidation();
        usernameErr = usernameValidation();

        if((emailErr && passwordErr && confirmPasswordErr && nameErr && usernameErr && contactErr)){
            signup();
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
    private void signup(){
        String sample = binding.contactEditText.getText().toString();
        String contactToNumber = String.valueOf(sample.replaceFirst("^0+(?!$)","")+"");
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
            messageTextView.setText("Creating Account...!!!");
            myAuth.signInWithEmailAndPassword(binding.emailEditText.getText().toString().trim(),binding.passwordEditText.getText().toString().trim())
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                myAuth.createUserWithEmailAndPassword(binding.emailEditText.getText().toString().trim(),binding.passwordEditText.getText().toString().trim())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                FirebaseUser user = authResult.getUser();
                                                if (user != null) {
                                                    Random random = new Random();
                                                    // current date picker
                                                    Calendar calendar = Calendar.getInstance();
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
                                                    String dateTime = simpleDateFormat.format(calendar.getTime());
                                                    // current date picker

                                                    userViewModel.CreateUser(new UserModel(
                                                            user.getUid(),
                                                            binding.emailEditText.getText().toString().trim(),
                                                            binding.usernameEditText.getText().toString().trim(),
                                                            binding.emailEditText.getText().toString().trim(),
                                                            contactToNumber,
                                                            dateTime,
                                                            "User",
                                                            "1",
                                                            ""),requireContext(),navController);
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
                                                    messageTextViewTwo.setText("Account Created Successfully!!!");
                                                    alertDialogTwo.show();
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            alertDialogTwo.dismiss();
                                                            navController.popBackStack();
                                                        }
                                                    },2000);
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
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
                                                messageTextViewTwo.setText("Please! Enter a valid Email Address!!!");
                                                alertDialogTwo.show();
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        alertDialogTwo.dismiss();
                                                    }
                                                },5000);
                                            }
                                        });
                            } else {
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
                                messageTextViewTwo.setText("Your Account is already Registered...!!!");
                                alertDialogTwo.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialogTwo.dismiss();
                                    }
                                },5000);
                            }
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