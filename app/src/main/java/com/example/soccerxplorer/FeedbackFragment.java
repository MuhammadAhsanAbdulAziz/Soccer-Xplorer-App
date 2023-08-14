package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.databinding.FragmentFeedbackBinding;
import com.example.soccerxplorer.model.FeedbackModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FeedbackViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class FeedbackFragment extends Fragment {

   FragmentFeedbackBinding binding;
   NavController navController;
   FeedbackViewModel feedbackViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedbackBinding.inflate(inflater,container,false);
        feedbackViewModel = new ViewModelProvider(requireActivity()).get(FeedbackViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                descriptionValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.reviewBtn.setOnClickListener(new View.OnClickListener() {
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

    }

    private void validation() {
        boolean descriptionErr = false;
        descriptionErr = descriptionValidation();

        if((descriptionErr)){
            saveChanges();
        }
    }

    private void saveChanges() {
        String id = UtilManager.getDefaults("userId",requireContext());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        feedbackViewModel.CreateFeedback(new FeedbackModel
                ("", id,
                        binding.emailEditText.getText().toString().trim(),
                        String.valueOf(binding.ratingBar.getRating()),dateTime),requireContext(),navController,requireActivity());
    }

    private boolean descriptionValidation() {
            String nameInput = binding.emailEditText.getText().toString().trim();
            if(nameInput.isEmpty()){
                binding.emailLayout.setError("Your Name is Required!!!");
                return false;
            }
             else if(nameInput.length() < 3){
                binding.emailLayout.setError("Enter Name at least 3 characters!!!");
                return false;
            } else {
                binding.emailLayout.setError(null);
                return true;
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}