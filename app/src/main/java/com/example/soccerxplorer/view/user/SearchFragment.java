package com.example.soccerxplorer.view.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soccerxplorer.R;
import com.example.soccerxplorer.adapter.FavouritePlayerAdapter;
import com.example.soccerxplorer.adapter.FavouriteTeamAdapter;
import com.example.soccerxplorer.adapter.LeagueAdapter;
import com.example.soccerxplorer.adapter.PlayerAdapter;
import com.example.soccerxplorer.adapter.PlayerSearchAdapter;
import com.example.soccerxplorer.adapter.TeamAdapter;
import com.example.soccerxplorer.databinding.FragmentFavouriteBinding;
import com.example.soccerxplorer.databinding.FragmentSearchBinding;
import com.example.soccerxplorer.interfaces.LeagueInterface;
import com.example.soccerxplorer.interfaces.PlayerInterface;
import com.example.soccerxplorer.interfaces.TeamInterface;
import com.example.soccerxplorer.model.FavouritePlayerModel;
import com.example.soccerxplorer.model.FavouriteTeamModel;
import com.example.soccerxplorer.model.LeagueModel;
import com.example.soccerxplorer.model.PlayerModel;
import com.example.soccerxplorer.model.TeamModel;
import com.example.soccerxplorer.util.UtilManager;
import com.example.soccerxplorer.viewmodel.FavouritePlayerViewModel;
import com.example.soccerxplorer.viewmodel.FavouriteTeamViewModel;
import com.example.soccerxplorer.viewmodel.LeagueViewModel;
import com.example.soccerxplorer.viewmodel.PlayerViewModel;
import com.example.soccerxplorer.viewmodel.TeamViewModel;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements PlayerInterface, TeamInterface, LeagueInterface {

    NavController navController;
    FragmentSearchBinding binding;
    PlayerViewModel playerViewModel;
    TeamViewModel teamViewModel;
    LeagueViewModel leagueViewModel;
    PlayerSearchAdapter playerAdapter;
    TeamAdapter teamAdapter;
    LeagueAdapter leagueAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        teamViewModel = new ViewModelProvider(requireActivity()).get(TeamViewModel.class);
        leagueViewModel = new ViewModelProvider(requireActivity()).get(LeagueViewModel.class);
        playerAdapter = new PlayerSearchAdapter(requireContext(),this);
        teamAdapter = new TeamAdapter(requireContext(),this);
        leagueAdapter = new LeagueAdapter(requireContext(),this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        binding.searchtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String c = s.toString().trim().toLowerCase(Locale.ROOT);
                if(binding.favplayerlist.getVisibility() == View.VISIBLE) {
                    playerViewModel.SearchPlayer(c).observe(requireActivity(),
                            playerModels -> playerAdapter.submitList(playerModels));
                }
                else if(binding.favteamlist.getVisibility() == View.VISIBLE) {
                    teamViewModel.SearchTeam(c).observe(requireActivity(),
                            teamModels -> teamAdapter.submitList(teamModels));
                }
                else if(binding.favleaguelist.getVisibility() == View.VISIBLE) {
                    leagueViewModel.searchLeague(c).observe(requireActivity(),
                            leagueModels -> leagueAdapter.submitList(leagueModels));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.teamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamViewModel.getTeam().observe(requireActivity(), new Observer<List<TeamModel>>() {
                    @Override
                    public void onChanged(List<TeamModel> teamModels) {
                        teamAdapter.submitList(teamModels);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
                        binding.favteamlist.setLayoutManager(staggeredGridLayoutManager);
                        binding.favteamlist.setAdapter(teamAdapter);
                    }
                });
                binding.teamsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.playersBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.leaguesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.teamsBtn.setTextColor(getResources().getColor(R.color.white));
                binding.favplayerlist.setVisibility(View.GONE);
                binding.favleaguelist.setVisibility(View.GONE);
                binding.favteamlist.setVisibility(View.VISIBLE);
            }
        });

        binding.playersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerViewModel.getPlayer().observe(requireActivity(), new Observer<List<PlayerModel>>() {
                    @Override
                    public void onChanged(List<PlayerModel> playerModels) {
                        playerAdapter.submitList(playerModels);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
                        binding.favplayerlist.setLayoutManager(staggeredGridLayoutManager);
                        binding.favplayerlist.setAdapter(playerAdapter);
                    }
                });
                binding.teamsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.leaguesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.playersBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.playersBtn.setTextColor(getResources().getColor(android.R.color.white));
                binding.favteamlist.setVisibility(View.GONE);
                binding.favleaguelist.setVisibility(View.GONE);
                binding.favplayerlist.setVisibility(View.VISIBLE);
            }
        });

        binding.leaguesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leagueViewModel.getLeague().observe(requireActivity(), new Observer<List<LeagueModel>>() {
                    @Override
                    public void onChanged(List<LeagueModel> leagueModels) {
                        leagueAdapter.submitList(leagueModels);
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
                        binding.favleaguelist.setLayoutManager(staggeredGridLayoutManager);
                        binding.favleaguelist.setAdapter(leagueAdapter);
                    }
                });
                binding.teamsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.playersBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_inactive));
                binding.leaguesBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_active));
                binding.leaguesBtn.setTextColor(getResources().getColor(android.R.color.white));
                binding.favteamlist.setVisibility(View.GONE);
                binding.favplayerlist.setVisibility(View.GONE);
                binding.favleaguelist.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void PlayerDetail(PlayerModel player) {
        playerViewModel.setPlayerModel(player);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("player",g.toJson(player) );
        navController.navigate(R.id.action_searchFragment_to_playerDetailFragment,bundle);
    }

    @Override
    public void TeamDetail(TeamModel team) {
        teamViewModel.setTeamModel(team);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("team",g.toJson(team) );
        navController.navigate(R.id.action_searchFragment_to_teamDetailFragment,bundle);
    }

    @Override
    public void LeagueDetail(LeagueModel leagueModel) {
        leagueViewModel.setLeagueModel(leagueModel);
        Bundle bundle = new Bundle();
        Gson g = new Gson();
        bundle.putString("league", g.toJson(leagueModel));
        navController.navigate(R.id.action_searchFragment_to_leagueMatcheFragment, bundle);
    }
}