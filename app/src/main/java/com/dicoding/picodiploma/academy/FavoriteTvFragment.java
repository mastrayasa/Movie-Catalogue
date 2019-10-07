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

import com.dicoding.picodiploma.academy.adapter.TvAdapter;
import com.dicoding.picodiploma.academy.database.TvHelper;
import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.entitas.Tv;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.dicoding.picodiploma.academy.database.DatabaseContract.AUTHORITY;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.TvColumns.CONTENT_URI_TV;
import static com.dicoding.picodiploma.academy.database.DatabaseContract.TvColumns.TABLE_TV;
import static com.dicoding.picodiploma.academy.helper.MappingHelper.mapCursorToArrayListFilms;
import static com.dicoding.picodiploma.academy.helper.MappingHelper.mapCursorToArrayListTvs;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements TvAdapter.AdapterOnClickHandler , LoadFilmsCallback {

    private ContentResolver resolver;
    private RecyclerView rvTv;
    private ProgressBar progressBar;
    private TextView not_found;
    private MainViewModel mainViewModel;
    private TvAdapter tvAdapter;

    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_tv, container, false);


        not_found = rootView.findViewById(R.id.not_found);
        progressBar = rootView.findViewById(R.id.progressBar);
        rvTv = rootView.findViewById(R.id.rv_films);
        rvTv.setHasFixedSize(true);

        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvAdapter = new TvAdapter(true,this);
        rvTv.setAdapter(tvAdapter);

        showLoading(true);

        resolver = getActivity().getContentResolver();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);
        mainViewModel.setListTvFavorite(resolver);

        return rootView;
    }

    private Observer<List<Tv>> getTv = new Observer<List<Tv>>() {
        @Override
        public void onChanged(final List<Tv> list_tvs) {

            Log.e("TAG", "ini tag");
            if (list_tvs != null) {

                tvAdapter.setData(list_tvs);

                ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedFilm(list_tvs.get(position));
                    }
                });

                if(list_tvs.size() == 0){
                    Log.e("TAG", "Tidak ada data");
                    DataNotFound(true);
                }

            }else{
                DataNotFound(true);
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

    private void DataNotFound(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.GONE);
            rvTv.setVisibility(View.GONE);
            not_found.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            rvTv.setVisibility(View.VISIBLE);
            not_found.setVisibility(View.GONE);
        }
    }



    @Override
    public void onDeleteItem(int position) {
        Tv tvDelete = tvAdapter.getItem(position);


        resolver.delete(  new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .appendPath(tvDelete.getId())
                .build()   , null ,null);
        Toast.makeText(getActivity(), "Satu item berhasil dihapus", Toast.LENGTH_SHORT).show();

        new FavoriteTvFragment.getData(getActivity(), this).execute();
    }

    @Override
    public void postExecute(Cursor tvs) {
        final ArrayList<Tv> listTv = mapCursorToArrayListTvs(tvs);
        if (listTv.size() > 0) {
            tvAdapter.setData(listTv);

            ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showSelectedFilm(listTv.get(position));
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
            return weakContext.get().getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }
        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }

}
