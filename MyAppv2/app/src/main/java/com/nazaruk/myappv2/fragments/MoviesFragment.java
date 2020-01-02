package com.nazaruk.myappv2.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nazaruk.myappv2.AddingFilmActivity;
import com.nazaruk.myappv2.ApplicationStaff;
import com.nazaruk.myappv2.DataApi;
import com.nazaruk.myappv2.Film;
import com.nazaruk.myappv2.MoviesListAdapter;
import com.nazaruk.myappv2.NetworkCheck;
import com.nazaruk.myappv2.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private RelativeLayout mRelativeLayout;
    private MoviesListAdapter mAdapter;
    private NetworkCheck mNetworkCheck;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);

        mRefreshLayout = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.r_view);
        mRelativeLayout = view.findViewById(R.id.main_data);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MoviesListAdapter(new ArrayList<>(), getActivity());
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AddingFilmActivity.class);

            startActivity(intent);
        });

        loadData();
        refresh();
        checkInternet();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mNetworkCheck);
    }

    private void checkInternet() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkCheck = new NetworkCheck(mRelativeLayout);
        getActivity().registerReceiver(mNetworkCheck, filter);
    }

    private void loadData() {
        final DataApi api = getApplicationEx().getApi();
        final Call<List<Film>> call = api.getFilm();

        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call,
                                   Response<List<Film>> response) {
                mAdapter.updateFilms(response.body());
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void refresh() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternet();
                loadData();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private ApplicationStaff getApplicationEx() {
        return (ApplicationStaff) getActivity().getApplication();
    }

}