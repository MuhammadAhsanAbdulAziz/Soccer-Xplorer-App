package com.example.soccerxplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ImageView backBtn;
    TextInputLayout emailLayout, usernameLayout, nameLayout, passwordLayout, confirmPasswordLayout, contactLayout;
    EditText emailEditText, usernameEditText, nameEditText, passwordEditText, confirmPasswordEditText, contactEditText;
    Button registerBtn;
    FirebaseAuth myAuth;
    String contactToNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myAuth = FirebaseAuth.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backBtn = findViewById(R.id.backBtn);
        emailLayout = findViewById(R.id.emailLayout);
        contactLayout = findViewById(R.id.contactLayout);
        contactEditText = findViewById(R.id.contactEditText);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        usernameLayout = findViewById(R.id.usernameLayout);
        nameLayout = findViewById(R.id.nameLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        nameEditText = findViewById(R.id.nameEditText);
        registerBtn = findViewById(R.id.registerBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        usernameEditText.addTextChangedListener(new TextWatcher() {
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

        nameEditText.addTextChangedListener(new TextWatcher() {
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
        emailEditText.addTextChangedListener(new TextWatcher() {
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
        passwordEditText.addTextChangedListener(new TextWatcher() {
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
        confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
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
        contactEditText.addTextChangedListener(new TextWatcher() {
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
    public boolean emailValidation(){
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailLayout.setError("Email Address is Required!!!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLayout.setError("Enter Valid Email Address!!!");
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }
    }
    public boolean usernameValidation(){
        String nameInput = usernameEditText.getText().toString().trim();
        if(nameInput.isEmpty()){
            usernameLayout.setError("Username is Required!!!");
            return false;
        } else if(!Pattern.compile("^[a-zA-Z\\s]*$").matcher(nameInput).matches()){
            usernameLayout.setError("Enter Username In Only Text!!!");
            return false;
        } else if(nameInput.length() < 3){
            usernameLayout.setError("Enter Username at least 3 characters!!!");
            return false;
        } else {
            usernameLayout.setError(null);
            return true;
        }
    }
    public boolean nameValidation(){
        String nameInput = nameEditText.getText().toString().trim();
        if(nameInput.isEmpty()){
            nameLayout.setError("Your Name is Required!!!");
            return false;
        } else if(!Pattern.compile("^[a-zA-Z\\s]*$").matcher(nameInput).matches()){
            nameLayout.setError("Enter Name In Only Text!!!");
            return false;
        } else if(nameInput.length() < 3){
            nameLayout.setError("Enter Name at least 3 characters!!!");
            return false;
        } else {
            nameLayout.setError(null);
            return true;
        }
    }
    public boolean passwordValidation(){
        String password = passwordEditText.getText().toString().trim();
        if(password.isEmpty()){
            passwordLayout.setError("Password is Required!!!");
            return false;
        } else if(password.length() < 8){
            passwordLayout.setError("Password at least 8 Characters!!!");
            return false;
        }
        else {
            passwordLayout.setError(null);
            return true;
        }
    }
    public boolean confirmPasswordValidation(){
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if(confirmPassword.isEmpty()){
            confirmPasswordLayout.setError("Confirm Password is Required!!!");
            return false;
        } else if(confirmPassword.length() < 8){
            confirmPasswordLayout.setError("Confirm Password at least 8 Characters!!!");
            return false;
        }else if(!confirmPassword.equals(password)){
            confirmPasswordLayout.setError("Confirm Password is Not Matched!!!");
            return false;
        }else {
            confirmPasswordLayout.setError(null);
            return true;
        }
    }
    public boolean contactValidation(){
        String contact = contactEditText.getText().toString().trim();
        if(contact.isEmpty()){
            contactLayout.setError("Contact no is Required!!!");
            return false;
        } else if(!Patterns.PHONE.matcher(contact).matches()){
            contactLayout.setError("Enter Valid Contact no!!!");
            return false;
        } else if(contact.length() < 10){
            contactLayout.setError("Contact no at least 10 Digits!!!");
            return false;
        }
        else {
            contactLayout.setError(null);
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

        if((emailErr && passwordErr && confirmPasswordErr && nameErr && usernameErr && contactErr) == true){
            signup();
        }
    }
    private boolean connectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiConn != null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())){
            return true;
        } else {
            AlertDialog.Builder builderTwo = new AlertDialog.Builder(RegisterActivity.this);
            LayoutInflater inflaterTwo = RegisterActivity.this.getLayoutInflater();
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
        String sample = contactEditText.getText().toString();
        contactToNumber = String.valueOf(sample.replaceFirst("^0+(?!$)","")+"");
        boolean checking = false;
        checking = connectionCheck();
        if(checking == true){
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            LayoutInflater inflater =   RegisterActivity.this.getLayoutInflater();
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
            myAuth.signInWithEmailAndPassword(emailEditText.getText().toString().trim(),passwordEditText.getText().toString().trim())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                myAuth.createUserWithEmailAndPassword(emailEditText.getText().toString().trim(),passwordEditText.getText().toString().trim())
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
                                                    Map<String, String> mydata = new HashMap<>();
                                                    mydata.put("image","");
                                                    mydata.put("email", ""+emailEditText.getText().toString().trim());
                                                    mydata.put("name",""+nameEditText.getText().toString().trim());
                                                    mydata.put("username",""+usernameEditText.getText().toString().trim());
                                                    mydata.put("contact", ""+contactToNumber);
                                                    mydata.put("joining",""+dateTime);
                                                    mydata.put("role","user");
                                                    mydata.put("profileStatus","false");
                                                    mydata.put("status","1");
                                                    MainActivity.myRef.child("Users").child(user.getUid()).setValue(mydata);
                                                    alertDialog.dismiss();
                                                    AlertDialog.Builder builderTwo = new AlertDialog.Builder(RegisterActivity.this);
                                                    LayoutInflater inflaterTwo = RegisterActivity.this.getLayoutInflater();
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
                                                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                                            startActivity(intent);
                                                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                                            finish();
                                                        }
                                                    },2000);
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                alertDialog.dismiss();
                                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(RegisterActivity.this);
                                                LayoutInflater inflaterTwo = RegisterActivity.this.getLayoutInflater();
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
                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(RegisterActivity.this);
                                LayoutInflater inflaterTwo = RegisterActivity.this.getLayoutInflater();
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
}