package com.dicoding.picodiploma.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";

    private TextView txtName;
    private TextView txtDescription;
    private TextView txtScore;
    private TextView txtRilis;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Film film = getIntent().getParcelableExtra(EXTRA_FILM);

        Toast.makeText(DetailActivity.this, film.getTitle(), Toast.LENGTH_SHORT).show();

        setTitle("Detail Film");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        txtName = findViewById(R.id.txt_name);
        txtDescription = findViewById(R.id.txt_description);
        txtScore = findViewById(R.id.txt_score);
        txtRilis = findViewById(R.id.txt_rilis);
        imgPhoto = findViewById(R.id.img_photo);


        txtName.setText(film.getTitle());
        txtDescription.setText(film.getDes());
        txtScore.setText(film.getScore());
        txtRilis.setText(film.getRilis());
        imgPhoto.setImageResource(film.getCover());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
