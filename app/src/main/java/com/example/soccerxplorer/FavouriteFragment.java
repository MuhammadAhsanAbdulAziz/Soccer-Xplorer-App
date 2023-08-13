package com.example.soccerxplorer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.adapter.FavouritePlayerAdapter;
import com.example.soccerxplorer.adapter.FavouriteTeamAdapter;
import com.example.soccerxplorer.databinding.FragmentFavouriteBinding;
import com.example.soccerxplorer.databinding.FragmentHomeBinding;
import com.example.soccerxplorer.interfaces.FavouritePlayerInterface;
import com.example.soccerxplorer.interfaces.FavouriteTeamInterface;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.FavouriteTeamModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FavouritePlayerViewModel;
import com.example.soccerxplorer.viewmodel.FavouriteTeamViewModel;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouritePlayerInterface, FavouriteTeamInterface {

    NavController navController;
    FragmentFavouriteBinding binding;
    FavouritePlayerViewModel favouritePlayerViewModel;
    FavouriteTeamViewModel favouriteTeamViewModel;
    FavouritePlayerAdapter favouritePlayerAdapter;
    FavouriteTeamAdapter favouriteTeamAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        favouritePlayerViewModel = new ViewModelProvider(requireActivity()).get(FavouritePlayerViewModel.class);
        favouritePlayerAdapter = new FavouritePlayerAdapter(requireContext(),this);
        favouriteTeamViewModel = new ViewModelProvider(requireActivity()).get(FavouriteTeamViewModel.class);
        favouriteTeamAdapter = new FavouriteTeamAdapter(requireContext(),this);




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        String id = UtilManager.getDefaults("userId",requireContext());
//        favouritePlayerViewModel.getFavouritePlayerbyUserId(id).observe(requireActivity(), new Observer<List<FavouritePlayerModel>>() {
//            @Override
//            public void onChanged(List<FavouritePlayerModel> favouritePlayerModels) {
//                favouritePlayerAdapter.submitList(favouritePlayerModels);
//                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
//                binding.favplayerlist.setLayoutManager(mLayoutManager);
//                binding.favplayerlist.setAdapter(favouritePlayerAdapter);
//            }
//        });
        favouriteTeamViewModel.getFavouriteTeambyUserId(id).observe(requireActivity(), new Observer<List<FavouriteTeamModel>>() {
            @Override
            public void onChanged(List<FavouriteTeamModel> favouritePlayerModels) {
                favouriteTeamAdapter.submitList(favouritePlayerModels);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
                binding.favteamlist.setLayoutManager(mLayoutManager);
                binding.favteamlist.setAdapter(favouriteTeamAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void FavouritePlayerDetail(FavouritePlayerModel favplayer) {

    }

    @Override
    public void FavouriteTeamDetail(FavouriteTeamModel favteam) {

    }
}