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
import com.dicoding.picodiploma.academy.Tv;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.CategoryViewHolder> {

    private Context context;
    private List<Tv> mTvList;

    public TvAdapter(List <Tv> TvList) {
        mTvList = TvList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.tvName.setText(mTvList.get(position).getName());
        categoryViewHolder.txtDescription.setText(mTvList.get(position).getOverview());

        String image = "http://image.tmdb.org/t/p/w342" + mTvList.get(position).getPoster_path();

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder_error)
                .into(categoryViewHolder.imgPhoto);




        // categoryViewHolder.imgPhoto.setImageResource(mFilmList.get(position).getPoster_path());

    }

    @Override
    public int getItemCount() {
        return mTvList.size();
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
