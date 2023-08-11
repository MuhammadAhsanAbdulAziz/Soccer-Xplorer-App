package com.example.soccerxplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView backBtn;
    Button forgotBtn;
    EditText emailEditText;
    TextInputLayout emailLayout;
    FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        myAuth = FirebaseAuth.getInstance();
        backBtn = findViewById(R.id.backBtn);
        forgotBtn = findViewById(R.id.forgotBtn);
        emailEditText = findViewById(R.id.emailEditText);
        emailLayout = findViewById(R.id.emailLayout);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordActivity.super.onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    private void validation() {
        boolean emailErr = false, passwordErr = false;
        emailErr = emailValidation();
        if((emailErr) == true){
            forgot();
        }
    }
    private boolean connectionCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) ForgotPasswordActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo dataConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiConn != null && wifiConn.isConnected()) || (dataConn != null && dataConn.isConnected())){
            return true;
        } else {
            AlertDialog.Builder builderTwo = new AlertDialog.Builder(ForgotPasswordActivity.this);
            LayoutInflater inflaterTwo = ForgotPasswordActivity.this.getLayoutInflater();
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
    private void forgot(){
        boolean checking = false;
        checking = connectionCheck();
        if(checking == true){
            AlertDialog.Builder builder = new AlertDialog.Builder(  ForgotPasswordActivity.this);
            LayoutInflater inflater = ForgotPasswordActivity.this.getLayoutInflater();
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
            messageTextView.setText("Loading...!!!");
            myAuth.sendPasswordResetEmail(emailEditText.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                alertDialog.dismiss();
                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                LayoutInflater inflaterTwo = ForgotPasswordActivity.this.getLayoutInflater();
                                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
                                builderTwo.setView(dialogviewTwo);
                                AlertDialog alertDialogTwo = builderTwo.create();
                                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                TextView messageTextViewTwo;
                                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                                messageTextViewTwo.setText("We have sent you Email to reset your password on "+emailEditText.getText().toString().trim()+"!!!");
                                alertDialogTwo.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialogTwo.dismiss();
                                        ForgotPasswordActivity.super.onBackPressed();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    }
                                },4000);
                            } else {
                                AlertDialog.Builder builderTwo = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                LayoutInflater inflaterTwo = ForgotPasswordActivity.this.getLayoutInflater();
                                View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_warning_alert, null);
                                builderTwo.setView(dialogviewTwo);
                                AlertDialog alertDialogTwo = builderTwo.create();
                                alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                alertDialog.setCancelable(false);
                                alertDialog.setCanceledOnTouchOutside(false);
                                TextView messageTextViewTwo;
                                messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                                messageTextViewTwo.setText("Failed to send reset email!!!");
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
}