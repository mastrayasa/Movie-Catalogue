package com.dicoding.picodiploma.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        Tv tv = getIntent().getParcelableExtra(EXTRA_TV);


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

        String image = "http://image.tmdb.org/t/p/w342" + tv.getPoster_path();

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
        return super.onOptionsItemSelected(menuItem);
    }
}
