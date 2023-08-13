package com.example.soccerxplorer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.soccerxplorer.databinding.FragmentEditProfileBinding;
import com.example.soccerxplorer.model.UserModel;
import com.example.soccerxplorer.util.UtilManager;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding binding;
    NavController navController;
    UserViewModel userViewModel;
    Uri u;
    StorageReference storageReference;
    FirebaseStorage storage;
    DatabaseReference userRef = FirebaseDatabase.
            getInstance().getReference("Users");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.imageEdit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 404);
        });


        if(UtilManager.getDefaults("userId",requireContext())!=null)
        {
            userViewModel.getUserbyId(UtilManager.getDefaults("userId",requireContext())).observe(requireActivity(), new Observer<List<UserModel>>() {
                @Override
                public void onChanged(List<UserModel> userModels) {
                    binding.nameEditText.setText(userModels.get(0).getUserName());
                    binding.usernameEditText.setText(userModels.get(0).getUserName());
                    binding.emailEditText.setText(userModels.get(0).getUserEmail());
                    binding.contactEditText.setText(userModels.get(0).getUserContact());
                    Glide.with(requireContext()).load(userModels.get(0).getUserImage()).error(R.drawable.logo).
                            dontAnimate().into(binding.profilesettingimg);
                }
            });
        }


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 404) {
            if (data != null) {
                u = data.getData();
                Glide.with(requireContext()).load(u).
                        dontAnimate().into(binding.profilesettingimg);
            }
        }
    }

    private void validation() {
        boolean emailErr = false, nameErr = false, usernameErr = false, contactErr = false;
        emailErr = emailValidation();
        nameErr = nameValidation();
        contactErr = contactValidation();
        usernameErr = usernameValidation();

        if((emailErr && nameErr && usernameErr && contactErr)){
//            updateUser();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}