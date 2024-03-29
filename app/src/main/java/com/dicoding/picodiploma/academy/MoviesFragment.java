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

import com.dicoding.picodiploma.academy.adapter.FilmAdapter;
import com.dicoding.picodiploma.academy.api.ApiClient;
import com.dicoding.picodiploma.academy.api.ApiInterface;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements FilmAdapter.AdapterOnClickHandler {

    private RecyclerView rvFilm;
    private ProgressBar progressBar;
    FilmAdapter filmAdapter;


    ApiInterface mApiInterface;

    private MainViewModel mainViewModel;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        getActivity().setTitle(getResources().getString(R.string.title_movies));


        progressBar = rootView.findViewById(R.id.progressBar);
        rvFilm = rootView.findViewById(R.id.rv_films);
        rvFilm.setHasFixedSize(true);

        rvFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmAdapter = new FilmAdapter(false, this);
        rvFilm.setAdapter(filmAdapter);


        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        showLoading(true);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFilms().observe(this, getFilm);
        mainViewModel.setListFilm(mApiInterface);

        return rootView;
    }

    private Observer<List<Film>> getFilm = new Observer<List<Film>>() {
        @Override
        public void onChanged(final List<Film> list_films) {
            if (list_films != null) {

                filmAdapter.setData(list_films);

                ItemClickSupport.addTo(rvFilm).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedFilm(list_films.get(position));
                    }
                });
            }

            showLoading(false);
        }
    };


    private void showSelectedFilm(Film film) {
        Intent moveWithObjectIntent = new Intent(getActivity(), DetailActivity.class);
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_FILM, film);
        startActivity(moveWithObjectIntent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteItem(int pos) {
        Log.e("aaaa", pos + "");
    }
}