package com.nazaruk.myappv2.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nazaruk.myappv2.ApplicationStaff;
import com.nazaruk.myappv2.DataApi;
import com.nazaruk.myappv2.Film;
import com.nazaruk.myappv2.NetworkCheck;
import com.nazaruk.myappv2.R;
import com.nazaruk.myappv2.MoviesListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private MoviesListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_fragment, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        recyclerView = (RecyclerView) view.findViewById(R.id.r_view);
        linearLayout = view.findViewById(R.id.main_data);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        loadData();
        refresh();
        checkInternet();

        return view;
    }

    private void checkInternet() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkCheck networkCheck = new NetworkCheck(linearLayout);
        getActivity().registerReceiver(networkCheck, filter);
    }

    private void loadData() {
        final DataApi api = getApplicationEx().getApi();
        final Call<List<Film>> call = api.getFilm();
        Log.d("My_tag", api.getFilm().toString());
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call,
                                   Response<List<Film>> response) {
                adapter = new MoviesListAdapter(response.body(), getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {

            }
        });
    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternet();
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private ApplicationStaff getApplicationEx() {
        return (ApplicationStaff) getActivity().getApplication();
    }

}