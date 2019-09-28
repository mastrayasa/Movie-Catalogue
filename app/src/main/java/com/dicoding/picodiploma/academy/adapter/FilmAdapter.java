package com.dicoding.picodiploma.academy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dicoding.picodiploma.academy.entitas.Film;
import com.dicoding.picodiploma.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private final AdapterOnClickHandler mClickHandler;
    private List<Film> mFilmList;
    private Boolean ShowBtnDelete;

    public FilmAdapter( boolean ShowBtnDelete,AdapterOnClickHandler mClickHandler) {
        this.ShowBtnDelete = ShowBtnDelete;
        this.mClickHandler = mClickHandler;
    }

    public void setData(List<Film> FilmList) {
        mFilmList = FilmList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder categoryViewHolder,  int position) {
        categoryViewHolder.tvName.setText(mFilmList.get(position).getTitle());
        categoryViewHolder.txtDescription.setText(mFilmList.get(position).getOverview());

        String image = "https://image.tmdb.org/t/p/w342" + mFilmList.get(position).getPoster_path();

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_placeholder_error)
                .into(categoryViewHolder.imgPhoto);


        if (ShowBtnDelete) {
            categoryViewHolder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            categoryViewHolder.btnDelete.setVisibility(View.GONE);
        }

        final int pos = position;
        categoryViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("HAPUS", pos + " " );

                mClickHandler.onDeleteItem(pos);
            }
        });


        // categoryViewHolder.imgPhoto.setImageResource(mFilmList.get(position).getPoster_path());

    }



    @Override
    public int getItemCount() {

        if (null == mFilmList) return 0;

        return mFilmList.size();
    }

    public Film getItem(int pos){
        return mFilmList.get(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder    {



        TextView tvName;
        TextView txtDescription;
        ImageView imgPhoto;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }




    }




    /**
     * The interface that receives onClick messages.
     */
    public interface AdapterOnClickHandler {
        void onDeleteItem(int pos);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public FilmAdapter(AdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }





}
