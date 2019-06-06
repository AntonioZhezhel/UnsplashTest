package com.example.unsplashtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    FrameLayout searchLayout;
    EditText searchBar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Adapter adapter;
    Interface dataService;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_img_picker);
        searchLayout = findViewById(R.id.searchLayout);
        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        dataService = Client.getClient().create(Interface.class);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                        ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                                (event.getKeyCode() == KeyEvent.ACTION_DOWN))) {
                    search(searchBar.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(new ArrayList<Photo>(), this);
        recyclerView.setAdapter(adapter);

        loadPhotos();
    }

    private void loadPhotos() {
        progressBar.setVisibility(View.VISIBLE);
        dataService.getPhotos(page, 10, "latest").enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                List<Photo> photos = response.body();
                Log.d("Photos", "Photos Fetched " + photos.size());

                page++;
                adapter.addPhotos(photos);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void search(String query) {
        if (query != null && !query.equals("")) {
            progressBar.setVisibility(View.VISIBLE);

            dataService.searchPhotos(query, null, 10, null).enqueue(new Callback<SearchResults>() {
                @Override
                public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                    SearchResults results = response.body();
                    Log.d("Photos", "Total Results Found " + results.getTotal());
                    List<Photo> photos = results.getResults();
                    adapter = new Adapter(photos, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<SearchResults> call, Throwable t) {
                    Log.d("Unsplash", t.getLocalizedMessage());
                    progressBar.setVisibility(View.GONE);

                }
            });
        } else {
            loadPhotos();
        }
    }

}
