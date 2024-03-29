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

import com.dicoding.picodiploma.academy.adapter.TvAdapter;
import com.dicoding.picodiploma.academy.api.ApiClient;
import com.dicoding.picodiploma.academy.api.ApiInterface;
import com.dicoding.picodiploma.academy.entitas.Tv;
import com.dicoding.picodiploma.academy.ui.main.MainViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvAdapter.AdapterOnClickHandler {

    private RecyclerView rvTv;
    private ProgressBar progressBar;
    private TvAdapter tvAdapter;
    ApiInterface mApiInterface;

    private MainViewModel mainViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_tv_show));
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_tv_show, container, false);


        getActivity().setTitle(getResources().getString(R.string.title_tv_show));

        progressBar = rootView.findViewById(R.id.progressBar);
        rvTv = rootView.findViewById(R.id.rv_tvs);
        rvTv.setHasFixedSize(true);

        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvAdapter = new TvAdapter(false,this);
        rvTv.setAdapter(tvAdapter);




        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        showLoading(true);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);

        mainViewModel.setListTv(mApiInterface);

        return rootView;
    }


    private Observer<List<Tv>> getTv = new Observer<List<Tv>>() {
        @Override
        public void onChanged(final List<Tv> list_tvs) {
            if (list_tvs != null) {

                tvAdapter.setData(list_tvs);

                ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedFilm(list_tvs.get(position));
                    }
                });
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

    @Override
    public void onDeleteItem(int pos) {
        Log.e("delete", pos + "" );
    }


}
