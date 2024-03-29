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
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements FilmAdapter.AdapterOnClickHandler {

    private FilmHelper filmHelper;
    private Film film;
    FilmAdapter filmAdapter;
    private RecyclerView rvFilm;
    private ProgressBar progressBar;
    private TextView not_found;

    private MainViewModel mainViewModel;


    public FavoriteMovieFragment() {

    }

   /* public static FavoriteMovieFragment newInstance() {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        filmHelper = FilmHelper.getInstance(getContext());
        filmHelper.open();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_movie, container, false);


        not_found = rootView.findViewById(R.id.not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        rvFilm = rootView.findViewById(R.id.rv_films);
        rvFilm.setHasFixedSize(true);

        rvFilm.setLayoutManager(new LinearLayoutManager(getActivity()));
        filmAdapter = new FilmAdapter(  true ,this  );
        rvFilm.setAdapter(filmAdapter);

        showLoading(true);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFilms().observe(this, getFilm);
        mainViewModel.setListFilmFavorite(filmHelper);

        return rootView;
    }


    private Observer<List<Film>> getFilm = new Observer<List<Film>>() {
        @Override
        public void onChanged(final List<Film> list_films) {

            Log.e("TAG", "ini tag");
            if (list_films != null) {

                filmAdapter.setData(list_films);

                ItemClickSupport.addTo(rvFilm).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedFilm(list_films.get(position));
                    }
                });




                if(list_films.size() == 0){
                    Log.e("TAG", "Tidak ada data");
                    DataNotFound(true);
                }


            }

            showLoading(false);

        }




    };

   /* private void deleteItem(int position){
        Film theRemovedItem = list_films.get(position);
        // remove your item from data base
        mFilmList.remove(position);  // remove the item from list
        notifyItemRemoved(position);
    }*/


    private void showSelectedFilm(Film film){
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

    private void DataNotFound(Boolean state) {
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


    @Override
    public void onDeleteItem(int position) {
        Film deleteItem = filmAdapter.getItem(position);
        filmHelper.delete( Integer.parseInt(deleteItem.getId()));

        mainViewModel.setListFilmFavorite(filmHelper);
    }
}
