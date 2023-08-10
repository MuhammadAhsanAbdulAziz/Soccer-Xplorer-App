package com.example.soccerxplorer.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.soccerxplorer.interfaces.ProfileInterface;
import com.example.soccerxplorer.model.ProfileModel;
public class ProfileAdapter extends ListAdapter<ProfileAdapter, ProfileAdapter.ViewHolder> {

    ProfileInterface profileInterface;
    Context context;
    public ProfileInterface(Context context, ProfileInterface profileInterface) {
        super(ProfileAdapter.profileInterface);
        this.context = context;
        this.profileInterface = profileInterface;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        RowBinding binding = RowBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileModel data = getItem(position);
        holder.binding.setPersoninterface(profileInterface);
        holder.binding.setprofile(data);
        Glide.with(context).load(data.getImage()).dontAnimate().into(holder.binding.teamimg);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        Binding binding;
        public ViewHolder(Binding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }
}