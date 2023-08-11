package com.example.soccerxplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    ImageView backBtn;
    Button loginBtn, forgotBtn, registerBtn;
    TextInputLayout emailLayout, passwordLayout;
    EditText emailEditText, passwordEditText;
    CheckBox rememberMe;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseAuth myAuth;
    FirebaseUser userId;
    DatabaseReference userRefLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences = getSharedPreferences("myData", MODE_PRIVATE);
        editor = preferences.edit();
        userId = FirebaseAuth.getInstance().getCurrentUser();
        myAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.backBtn);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMe = findViewById(R.id.rememberMe);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        forgotBtn = findViewById(R.id.forgotBtn);
        if (!preferences.getString("loginState","").isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                validation();
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
    }
    private boolean emailValidation(){
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailLayout.setError("Email Address is Required!!!");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLayout.setError("Enter Valid Email Address!!!");
            return false;
        }
        else {
            emailLayout.setError(null);
            return true;
        }
    }
    private boolean passwordValidation(){
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
    private void validation() {
        boolean emailErr = false, passwordErr = false;
        emailErr = emailValidation();
        passwordErr = passwordValidation();
        if((emailErr && passwordErr) == true){
            login();
        }
    }
    private boolean connectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiConn != null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())){
            return true;
        } else {
            AlertDialog.Builder builderTwo = new AlertDialog.Builder(LoginActivity.this);
            LayoutInflater inflaterTwo = LoginActivity.this.getLayoutInflater();
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
        if(checking == true){
            AlertDialog.Builder builder = new AlertDialog.Builder(  LoginActivity.this);
            LayoutInflater inflater =   LoginActivity.this.getLayoutInflater();
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
            myAuth.signInWithEmailAndPassword(emailEditText.getText().toString().trim(),passwordEditText.getText().toString().trim())
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                alertDialog.dismiss();
                                FirebaseUser userId = myAuth.getCurrentUser();
                                userRefLogin = FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid());
                                userRefLogin.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                        if(datasnapshot.exists()){
                                            String Role = datasnapshot.child("role").getValue().toString();
                                            String Status = datasnapshot.child("status").getValue().toString();
                                            if (rememberMe.isChecked()) {
                                                editor.clear();
                                                editor.commit();
                                                editor.putString("id", ""+task.getResult().getUser().getUid());
                                                editor.putString("role",""+Role);
                                                editor.putString("loginState","1");
                                                editor.commit();
                                            }
                                            if(Status.equals("0")){
                                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(LoginActivity.this);
                                                LayoutInflater inflaterTwo = LoginActivity.this.getLayoutInflater();
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
                                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                                editor.putString("id", ""+task.getResult().getUser().getUid());
                                                editor.putString("role",""+Role);
                                                editor.commit();
                                                startActivity(intent);
                                                finish();
                                                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                            AlertDialog.Builder builderTwo = new AlertDialog.Builder(LoginActivity.this);
                            LayoutInflater inflaterTwo = LoginActivity.this.getLayoutInflater();
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
                            messageTextViewTwo.setText("Your Email Address OR Password is does't exist!!!");
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
}