package com.dicoding.picodiploma.academy;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView not_found;
    ApiInterface mApiInterface;
    private MainViewModel mainViewModel;

    //private List<Film> temp_list_films;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        getActivity().setTitle(getResources().getString(R.string.title_movies));

        setHasOptionsMenu(true);

        not_found = rootView.findViewById(R.id.not_found);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

       SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

       if (searchManager != null) {
           Log.e("SEARCH CLOSE", "CLOSE" );
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo( getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
           searchView.setIconifiedByDefault(true);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    DataNotFound(false);
                    //mainViewModel.clearMovie();
                    showLoading(true);
                    mainViewModel.searchFilm(mApiInterface,query);
                    showLoading(true);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query) {
                    //Log.e("change", query );
                    return false;
                }


            });

           /*searchView.setOnCloseListener(new SearchView.OnCloseListener() {
               @Override
               public boolean onClose() {

                   Log.e("SEARCH CLOSE", "CLOSE" );
                   filmAdapter.setData(temp_list_films);
                   filmAdapter.notifyDataSetChanged();

                   ItemClickSupport.addTo(rvFilm).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                       @Override
                       public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                           showSelectedFilm(temp_list_films.get(position));
                       }
                   });


                   return false;
               }
           });*/









        }
    }






    private Observer<List<Film>> getFilm = new Observer<List<Film>>() {
        @Override
        public void onChanged(final List<Film> list_films) {
            if (list_films != null) {

                filmAdapter.setData(list_films);
                //temp_list_films = list_films;

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
            Log.e("onChanged", "getFilm" );
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
}