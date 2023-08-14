package com.example.soccerxplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soccerxplorer.R;
import com.example.soccerxplorer.databinding.AdminUserItemBinding;
import com.example.soccerxplorer.databinding.PlayerListviewBinding;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.interfaces.UserInterface;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUserAdapter extends ListAdapter<UserModel, AdminUserAdapter.ViewHolder> {

    UserInterface userInterface;
    Context context;
    DatabaseReference teamRef = FirebaseDatabase.
            getInstance().getReference("Teams");
    public AdminUserAdapter(Context context, UserInterface userInterface) {
        super(UserModel.catItemCallBack);
        this.context = context;
        this.userInterface = userInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        AdminUserItemBinding binding = AdminUserItemBinding.inflate(layout,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel data = getItem(position);
        holder.binding.setUserInterface(userInterface);
        holder.binding.setDetail(data);
        Glide.with(context).load(data.getUserImage()).error(R.drawable.logo).dontAnimate().into(holder.binding.playerImage);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdminUserItemBinding binding;
        public ViewHolder(AdminUserItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }
}