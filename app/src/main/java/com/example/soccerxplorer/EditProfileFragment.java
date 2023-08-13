package com.example.soccerxplorer;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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


import java.util.List;
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

    UploadTask uploadTask;
    Uri imageUri;

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
        Animation clickAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation2 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation3 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation4 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation5 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation6 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);
        Animation clickAnimation7 = AnimationUtils.loadAnimation(requireContext(),R.anim.zoomin_out);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.backBtn.startAnimation(clickAnimation);
                navController.popBackStack();
            }
        });
        binding.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imageEdit.startAnimation(clickAnimation2);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 420);
            }
        });
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.image.startAnimation(clickAnimation3);
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.saveBtn.startAnimation(clickAnimation5);
                userRef.child(UtilManager.getDefaults("userId",requireContext())
                ).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            validation();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(UtilManager.getDefaults("userId",requireContext())!=null)
        {
            userViewModel.getUserbyId(UtilManager.getDefaults("userId",requireContext())).observe(requireActivity(), new Observer<List<UserModel>>() {
                @Override
                public void onChanged(List<UserModel> userModels) {
                    binding.nameEditText.setText(userModels.get(0).getUserFullName());
                    binding.usernameEditText.setText(userModels.get(0).getUserName());
                    binding.contactEditText.setText(userModels.get(0).getUserContact());
                    Glide.with(requireContext()).load(userModels.get(0).getUserImage()).error(R.drawable.logo).
                            dontAnimate().into(binding.image);
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



    private void validation() {
        boolean nameErr = false, usernameErr = false, contactErr = false;
        nameErr = nameValidation();
        contactErr = contactValidation();
        usernameErr = usernameValidation();

        if((nameErr && usernameErr && contactErr)){
            saveChanges();
        }
    }


    private void saveChanges(){
        AlertDialog.Builder builder = new AlertDialog.Builder(  requireContext());
        LayoutInflater inflater =   requireActivity().getLayoutInflater();
        View dialogview = inflater.inflate(R.layout.dialog_progress_alert, null);
        builder.setView(dialogview);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
        TextView messageTextView;
        messageTextView = dialogview.findViewById(R.id.messageTextView);
        messageTextView.setText("Updating Profile!");
        userRef.child(UtilManager.getDefaults("userId",requireContext()))
                .child("userFullName").setValue(""+binding.nameEditText.getText().toString().trim());
        userRef.child(UtilManager.getDefaults("userId",requireContext()))
                .child("userName").setValue(""+binding.usernameEditText.getText().toString().trim());
        userRef.child(UtilManager.getDefaults("userId",requireContext()))
                .child("userContact").setValue(""+binding.contactEditText.getText().toString().trim());
        if(imageUri == null){
            alertDialog.dismiss();
            AlertDialog.Builder builderTwo = new AlertDialog.Builder(  requireContext());
            LayoutInflater inflaterTwo =   requireActivity().getLayoutInflater();
            View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
            builderTwo.setView(dialogviewTwo);
            AlertDialog alertDialogTwo = builderTwo.create();
            alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialogTwo.show();
            TextView messageTextViewTwo;
            messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
            messageTextViewTwo.setText("Profile Saved Successfully!!!");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialogTwo.dismiss();
                    navController.popBackStack();
                }
            },3000);
        } else {
            storageReference.child("User Images/" +imageUri.getLastPathSegment()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.child("User Images/" +imageUri.getLastPathSegment()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            userRef.child(UtilManager.getDefaults("userId",requireContext()))
                                    .child("userImage").setValue(""+String.valueOf(uri));
                            Glide.with(requireContext()).load(""+String.valueOf(uri)).into(binding.image);
                            alertDialog.dismiss();
                            AlertDialog.Builder builderTwo = new AlertDialog.Builder(  requireContext());
                            LayoutInflater inflaterTwo =   requireActivity().getLayoutInflater();
                            View dialogviewTwo = inflaterTwo.inflate(R.layout.dialog_success_alert, null);
                            builderTwo.setView(dialogviewTwo);
                            AlertDialog alertDialogTwo = builderTwo.create();
                            alertDialogTwo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            alertDialogTwo.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            alertDialogTwo.show();
                            TextView messageTextViewTwo;
                            messageTextViewTwo = dialogviewTwo.findViewById(R.id.messageTextView);
                            messageTextViewTwo.setText("Profile Saved Successfully!!!");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialogTwo.dismiss();
                                    navController.popBackStack();
                                }
                            },3000);
                        }
                    });
                }
            });
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 420 && resultCode == RESULT_OK){
            imageUri = data.getData();
            binding.image.setImageURI(imageUri);
            Glide.with(requireContext()).load(imageUri).dontAnimate().into(binding.image);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}