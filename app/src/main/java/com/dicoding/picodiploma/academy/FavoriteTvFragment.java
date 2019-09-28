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

import com.dicoding.picodiploma.academy.adapter.TvAdapter;
import com.dicoding.picodiploma.academy.database.TvHelper;
import com.dicoding.picodiploma.academy.entitas.Tv;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements TvAdapter.AdapterOnClickHandler {

    private TvHelper tvHelper;
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

        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();


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

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);
        mainViewModel.setListTvFavorite(tvHelper);

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
        Log.e("delete", position + "" );

        Tv deleteItem = tvAdapter.getItem(position);
        tvHelper.delete( Integer.parseInt(deleteItem.getId()));

        mainViewModel.setListTvFavorite(tvHelper);
    }

}
