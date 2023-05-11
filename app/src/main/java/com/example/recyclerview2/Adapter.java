package com.example.recyclerview2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
//    ProgressBar progressbar;
    private final List<Data> movielist;
    private final AdapterListener listener;
    Context context;

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDate;
        public ImageView ivLogo;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv1);
            tvDate = view.findViewById(R.id.tv2);
            ivLogo = view.findViewById(R.id.ivGambar);
            view.setOnClickListener(view1 -> listener.onDataSelected(movielist.get(getAdapterPosition())));

        }
    }

    public Adapter(Context context, List<Data> movielist, AdapterListener listener) {
        this.context = context;
        this.movielist = movielist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Data data = this.movielist.get(position);
        holder.tvName.setText(data.getNama());
        holder.tvDate.setText(data.getWebsite());
        Glide.with(holder.itemView.getContext()).load(data.getImage()).into(holder.ivLogo);
    }

    @Override
    public int getItemCount() {
        return this.movielist.size();
    }

    public interface AdapterListener {
        void onDataSelected(Data data);
    }
}
