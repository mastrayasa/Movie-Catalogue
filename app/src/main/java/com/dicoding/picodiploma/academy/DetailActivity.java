package com.dicoding.picodiploma.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";

    private FilmHelper filmHelper;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        filmHelper = FilmHelper.getInstance(getApplicationContext());
        filmHelper.open();

        film = getIntent().getParcelableExtra(EXTRA_FILM);

        setTitle(getResources().getString(R.string.title_detail_film));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        TextView txtName = findViewById(R.id.txt_name);
        TextView txtDescription = findViewById(R.id.txt_description);
        TextView txtScore = findViewById(R.id.txt_score);
        TextView txtRilis = findViewById(R.id.txt_rilis);
        ImageView imgPhoto = findViewById(R.id.img_photo);


        txtName.setText(film.getTitle());
        txtDescription.setText(film.getOverview());
        txtScore.setText(film.getPopularity());
        txtRilis.setText(film.getRelease_date());

      //  Log.e("IMG", film.getPoster_path() );

        String image = "https://image.tmdb.org/t/p/w342" + film.getPoster_path();

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder_error)
                .into(imgPhoto);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }

        else if(menuItem.getItemId() ==R.id.action_favorite){

            saveToVaforite();


        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveToVaforite(){
        long result = filmHelper.insert(film);
        Log.e("IMG", film.getPoster_path() );
        Log.e("IMG", film.getPoster_path() );

        if (result > 0) {
            Toast.makeText(DetailActivity.this, film.getTitle() + "  ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
        }
    }


}
