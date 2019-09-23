package com.dicoding.picodiploma.academy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dicoding.picodiploma.academy.Film;
import com.dicoding.picodiploma.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CategoryViewHolder> {

    private Context context;
    private List<Film> mFilmList;

    public FilmAdapter(List <Film> FilmList) {
        mFilmList = FilmList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.tvName.setText(mFilmList.get(position).getTitle());
        categoryViewHolder.txtDescription.setText(mFilmList.get(position).getOverview());

        String image = "https://image.tmdb.org/t/p/w342" + mFilmList.get(position).getPoster_path();

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder_error)
                .into(categoryViewHolder.imgPhoto);




       // categoryViewHolder.imgPhoto.setImageResource(mFilmList.get(position).getPoster_path());

    }

    @Override
    public int getItemCount() {
        return mFilmList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView txtDescription;
        ImageView imgPhoto;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }

}
