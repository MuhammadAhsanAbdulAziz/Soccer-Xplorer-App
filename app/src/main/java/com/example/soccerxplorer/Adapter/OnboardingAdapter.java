package com.example.soccerxplorer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccerxplorer.model.OnboardingModel;
import com.example.soccerxplorer.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingModel> onboardingModels;

    public OnboardingAdapter(List<OnboardingModel> onboardingModels) {
        this.onboardingModels = onboardingModels;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingModels.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingModels.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;

        OnboardingViewHolder(@NonNull View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }

        void setOnboardingData(OnboardingModel onboardingModel){
            textTitle.setText(onboardingModel.getTitle());
            textDescription.setText(onboardingModel.getDescription());
            imageOnboarding.setImageResource(onboardingModel.getImage());
        }
    }
}
