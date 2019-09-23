package com.dicoding.picodiploma.academy;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dicoding.picodiploma.academy.adapter.FilmAdapter;
import com.dicoding.picodiploma.academy.adapter.TvAdapter;
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.database.TvHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {

    private TvHelper tvHelper;
    Tv tv;

    private RecyclerView rvFilm;
    private ProgressBar progressBar;
    private TextView not_found;
    private MainViewModel mainViewModel;

    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_tv, container, false);


        not_found = rootView.findViewById(R.id.not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        rvFilm = rootView.findViewById(R.id.rv_films);
        rvFilm.setHasFixedSize(true);

        showLoading(true);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);
        mainViewModel.setListTvFavorite(tvHelper);

        return rootView;
    }

    private Observer<List<Tv>> getTv = new Observer<List<Tv>>() {
        @Override
        public void onChanged(final List<Tv> list_tv) {

            Log.e("TAG", "ini tag");
            if (list_tv != null) {

                rvFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
                TvAdapter tvAdapter = new TvAdapter(list_tv);
                rvFilm.setAdapter(tvAdapter);

                ItemClickSupport.addTo(rvFilm).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedFilm(list_tv.get(position));
                    }
                });

                if(list_tv.size() == 0){
                    Log.e("TAG", "Tidak ada data");
                    TidakAdaData(true);
                }

            }

            showLoading(false);


        }
    };


    private void showSelectedFilm(Tv tv){
        Intent moveWithObjectIntent = new Intent(getActivity(), DetailTvActivity.class);
        moveWithObjectIntent.putExtra(DetailTvActivity.EXTRA_TV, tv);
        startActivity(moveWithObjectIntent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void TidakAdaData(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.GONE);
            rvFilm.setVisibility(View.GONE);
            not_found.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            rvFilm.setVisibility(View.VISIBLE);
            not_found.setVisibility(View.GONE);
        }
    }

}
