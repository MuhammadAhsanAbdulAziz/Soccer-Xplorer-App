//package com.example.soccerxplorer.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.ListAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.soccerxplorer.databinding.RowBinding;
//import com.example.soccerxplorer.interfaces.PlayerInterface;
//import com.example.soccerxplorer.model.PlayerModel;
//
//public class PlayerAdapter extends ListAdapter<PlayerAdapter, PlayerAdapter.ViewHolder> {
//
//    PlayerInterface playerInterface;
//    Context context;
//    public PlayerAdapter(Context context, PlayerInterface teamInterface) {
//        super(PlayerAdapter.playerInterface);
//        this.context = context;
//        this.playerInterface = teamInterface;
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layout = LayoutInflater.from(parent.getContext());
//        RowBinding binding = RowBinding.inflate(layout,parent,false);
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        PlayerModel data = getItem(position);
//        holder.binding.setPlayerinterface(playerInterface);
//        holder.binding.setDetail(data);
//        Glide.with(context).load(data.getImage()).dontAnimate().into(holder.binding.teamimg);
//
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        RowBinding binding;
//        public ViewHolder(RowBinding binding) {
//            super(binding.getRoot());
//
//            this.binding = binding;
//
//        }
//    }
//}