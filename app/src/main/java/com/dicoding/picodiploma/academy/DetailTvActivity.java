package com.dicoding.picodiploma.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.academy.database.FilmHelper;
import com.dicoding.picodiploma.academy.database.TvHelper;
import com.squareup.picasso.Picasso;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    private TvHelper tvHelper;
    Tv tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

         tv = getIntent().getParcelableExtra(EXTRA_TV);


        setTitle(getResources().getString(R.string.title_detail_tv_show));


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        TextView txtName = findViewById(R.id.txt_name);
        TextView txtDescription = findViewById(R.id.txt_description);
        TextView txtScore = findViewById(R.id.txt_score);
        TextView txtRilis = findViewById(R.id.txt_rilis);
        ImageView imgPhoto = findViewById(R.id.img_photo);


        txtName.setText(tv.getOriginal_name());
        txtDescription.setText(tv.getOverview());
        txtScore.setText(tv.getPopularity());
        txtRilis.setText(tv.getRelease_date());

        String image = "https://image.tmdb.org/t/p/w342" + tv.getPoster_path();

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

            saveToFavorite();


        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveToFavorite(){
        long result = tvHelper.insert(tv);

        if (result > 0) {
            Toast.makeText(DetailTvActivity.this, tv.getName() + "  ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailTvActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
        }
    }
}
