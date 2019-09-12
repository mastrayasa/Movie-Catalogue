package com.dicoding.picodiploma.academy;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private String[] dataName;
    private String[] dataDescription;
    private String[] dataScore;
    private String[] dataTanggalRilis;
    private TypedArray dataPhoto;
    private FilmAdapter adapter;
    private ArrayList<Film> films;


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_movies, container, false);

        adapter = new FilmAdapter(getActivity());
        ListView listView = rootView.findViewById(R.id.lv_list);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, films.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailActivity.class);
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_FILM, films.get(i));
                startActivity(moveWithObjectIntent);
            }
        });

        return rootView;
    }

    private void addItem() {
        films = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Film hero = new Film();
            hero.setCover(dataPhoto.getResourceId(i, -1));
            hero.setTitle(dataName[i]);
            hero.setDes(dataDescription[i]);
            hero.setScore(dataScore[i]);
            hero.setRilis(dataTanggalRilis[i]);
            films.add(hero);
        }
        adapter.setHeroes(films);
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name);
        dataDescription = getResources().getStringArray(R.array.data_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
        dataScore = getResources().getStringArray(R.array.data_score);
        dataTanggalRilis = getResources().getStringArray(R.array.data_rilis);
    }

}
