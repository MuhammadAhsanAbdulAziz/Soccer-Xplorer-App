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
import com.example.soccerxplorer.model.FixtureModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FavouritePlayerViewModel;
import com.example.soccerxplorer.viewmodel.FavouriteTeamViewModel;
import com.google.gson.Gson;

import java.util.List;


public class FavouriteFragment extends Fragment implements FavouritePlayerInterface, FavouriteTeamInterface {

    NavController navController;
    FragmentFavouriteBinding binding;
    FavouritePlayerViewModel favouritePlayerViewModel;
    FavouriteTeamViewModel favouriteTeamViewModel;
    FavouritePlayerAdapter favouritePlayerAdapter;
    FavouriteTeamAdapter favouriteTeamAdapter;
    private static FavouriteFragment instance = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        favouritePlayerViewModel = new ViewModelProvider(requireActivity()).get(FavouritePlayerViewModel.class);
        favouritePlayerAdapter = new FavouritePlayerAdapter(requireContext(),this);
        favouriteTeamViewModel = new ViewModelProvider(requireActivity()).get(FavouriteTeamViewModel.class);
        favouriteTeamAdapter = new FavouriteTeamAdapter(requireContext(),this);
        favouritePlayerAdapter.notifyDataSetChanged();




        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        String id = UtilManager.getDefaults("userId",requireContext());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        binding.teamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.teamsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.playersBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.teamsBtn.setTextColor(getResources().getColor(R.color.white));
                binding.favplayerlist.setVisibility(View.GONE);
                binding.favteamlist.setVisibility(View.VISIBLE);
            }
        });

        binding.playersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.teamsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.playersBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.playersBtn.setTextColor(getResources().getColor(android.R.color.white));
                binding.favteamlist.setVisibility(View.GONE);
                binding.favplayerlist.setVisibility(View.VISIBLE);
            }
        });

        favouriteTeamViewModel.getFavouriteTeambyUserId(id).observe(requireActivity(), new Observer<List<FavouriteTeamModel>>() {
            @Override
            public void onChanged(List<FavouriteTeamModel> favouritePlayerModels) {
                favouriteTeamAdapter.submitList(favouritePlayerModels);

            }
        });

        String userid = UtilManager.getDefaults("userId",requireContext());
        favouritePlayerViewModel.getFavouritePlayerbyUserId(userid).observe(requireActivity(), new Observer<List<FavouritePlayerModel>>() {
            @Override
            public void onChanged(List<FavouritePlayerModel> favouritePlayerModels) {
                favouritePlayerAdapter.submitList(favouritePlayerModels);


            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        binding.favteamlist.setLayoutManager(mLayoutManager);
        binding.favteamlist.setAdapter(favouriteTeamAdapter);


        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(requireContext());
        binding.favplayerlist.setLayoutManager(mLayoutManager2);
        binding.favplayerlist.setAdapter(favouritePlayerAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void FavouritePlayerDetail(FavouritePlayerModel favplayer) {
        Bundle bundle = new Bundle();
        bundle.putString("playerfromfav",favplayer.getPlayerId());
        navController.navigate(R.id.action_favouriteFragment_to_playerDetailFragment,bundle);
    }

    @Override
    public void FavouriteTeamDetail(FavouriteTeamModel favteam) {

    }

    public static FavouriteFragment getInstance() {
        return instance;
    }
}