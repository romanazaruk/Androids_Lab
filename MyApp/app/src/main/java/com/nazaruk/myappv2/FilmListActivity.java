package com.nazaruk.myappv2;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmListActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private RvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);
        refreshLayout = findViewById(R.id.swipe);
        recyclerView = (RecyclerView) findViewById(R.id.r_view);
        linearLayout = findViewById(R.id.main_data);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        loadData();
        refresh();
        checkInternet();
    }

    private void checkInternet() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkCheck networkCheck = new NetworkCheck(linearLayout);
        this.registerReceiver(networkCheck, filter);
    }

    private void loadData() {
        final DataApi api = getApplicationEx().getApi();
        final Call<List<Film>> call = api.getFilm();
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call,
                                   Response<List<Film>> response) {
                adapter = new RvAdapter(response.body());
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
        return (ApplicationStaff) getApplication();
    }

}
