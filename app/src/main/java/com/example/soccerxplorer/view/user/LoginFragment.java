package com.example.soccerxplorer.view.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soccerxplorer.MainActivity;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.FragmentFavouriteBinding;
import com.example.soccerxplorer.databinding.FragmentLoginBinding;
import com.example.soccerxplorer.util.UtilManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    NavController navController;
    FragmentLoginBinding binding;
    FirebaseAuth myAuth;
    DatabaseReference userRefLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        myAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
        binding.forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavOptions.Builder navBuilder =  new NavOptions.Builder();
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
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

        navController = Navigation.findNavController(view);
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
    private void validation() {
        boolean emailErr = false, passwordErr = false;
        emailErr = emailValidation();
        passwordErr = passwordValidation();
        if((emailErr && passwordErr)){
            login();
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
            messageTextViewTwo.setText("Please Connect To The Internet To Proceed Further!!!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialogTwo.dismiss();
                }
            },3000);
            return false;
        }
    }
    private void login(){
        boolean checking = false;
        checking = connectionCheck();
        if(checking){
            AlertDialog.Builder builder = new AlertDialog.Builder(  requireContext());
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
            messageTextView.setText("Login Account...!!!");
            myAuth.signInWithEmailAndPassword(binding.emailEditText.getText().toString().trim(),binding.passwordEditText.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                alertDialog.dismiss();
                                userRefLogin = FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid());
                                userRefLogin.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                        if(datasnapshot.exists()){
                                            String Role = datasnapshot.child("userRole").getValue(String.class);
                                            String Status = datasnapshot.child("userStatus").getValue(String.class);
                                            if(Status.equals("0")){
                                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(requireContext());
                                                LayoutInflater inflaterTwo = requireActivity().getLayoutInflater();
                                                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_warning_alert, null);
                                                builderTwo.setView(dialogviewTwo);
                                                AlertDialog alertDialogTwo = builderTwo.create();
                                                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                                alertDialogTwo.setCancelable(false);
                                                alertDialogTwo.setCanceledOnTouchOutside(false);
                                                alertDialogTwo.show();
                                                TextView messageTextViewTwo;
                                                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                                                messageTextViewTwo.setText("Your Account has been BANNED by Admin!!!");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        alertDialogTwo.dismiss();
                                                    }
                                                },3000);
                                            } else if(Status.equals("1")){
                                                UtilManager.setDefaults("userId",task.getResult().getUser().getUid(),requireContext());
                                                UtilManager.setDefaults("userRole",Role,requireContext());
                                                if(Role.equals("Admin"))
                                                {
                                                    navController.navigate(R.id.action_loginFragment_to_adminHomeFragment);
                                                    UtilManager.setDefaults("s","1",requireContext());
                                                    MainActivity.getInstance().setAdminMenu();
                                                }
                                                else{
                                                    navController.navigate(R.id.action_loginFragment_to_homeFragment);
                                                    UtilManager.setDefaults("s","2",requireContext());
                                                    MainActivity.getInstance().setUserMenu();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }).addOnFailureListener( new OnFailureListener() {
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
                            alertDialogTwo.show();
                            TextView messageTextViewTwo;
                            messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                            messageTextViewTwo.setText("Your Email Address OR Password doesn't exist!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogTwo.dismiss();
                                }
                            },3000);
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