package com.dicoding.picodiploma.academy;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.dicoding.picodiploma.academy.adapter.FilmAdapter;
import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.database.FilmProvider;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.dicoding.picodiploma.academy.database.DatabaseContract.AUTHORITY;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.dicoding.picodiploma.academy.helper.MappingHelper.mapCursorToArrayListFilms;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements FilmAdapter.AdapterOnClickHandler, LoadFilmsCallback  {

    FilmAdapter filmAdapter;
    private RecyclerView rvFilm;
    private ProgressBar progressBar;
    private TextView not_found;
    private ContentResolver resolver;
    private MainViewModel mainViewModel;

    public FavoriteMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        resolver = getActivity().getContentResolver();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFilms().observe(this, getFilm);
       mainViewModel.setListFilmFavorite(resolver);

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


            }else{
                DataNotFound(true);
            }

            showLoading(false);

        }




    };


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

        Film filmDelete = filmAdapter.getItem(position);


        resolver.delete(  new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .appendPath(filmDelete.getId())
                .build()   , null ,null);
        Toast.makeText(getActivity(), "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();

        new getData(getActivity(), this).execute();


    }

    @Override
    public void postExecute(Cursor films) {
        final ArrayList<Film> listFilms = mapCursorToArrayListFilms(films);
        if (listFilms.size() > 0) {
            filmAdapter.setData(listFilms);

            ItemClickSupport.addTo(rvFilm).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showSelectedFilm(listFilms.get(position));
                }
            });



        } else {
            DataNotFound(true);
            Toast.makeText(getActivity(), "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
        }

        showLoading(false);
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFilmsCallback> weakCallback;

        private getData(Context context, LoadFilmsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.e("TAGGG", "doInBackground: " );
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }
        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }


}
