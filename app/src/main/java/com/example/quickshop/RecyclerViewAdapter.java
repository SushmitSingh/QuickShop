package com.example.quickshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<RecyclerData,RecyclerViewAdapter.RecyclerViewHolder> {
    Context mcontext;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<RecyclerData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull RecyclerData model) {
            CharSequence title=model.getTitle();
            CharSequence rate=model.getRate();
            CharSequence image=model.getImg();
        AppCompatActivity appCompatActivity=new AppCompatActivity();
        holder.pprice.setText(model.getRate());
        holder.product.setText(model.getTitle());
        Glide.with(holder.img.getContext()).load(model.getImg()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent( v.getContext(),ProductView.class);
                   in.putExtra("title",title);
                   in.putExtra("rate",rate);
                   in.putExtra("image",image);
                v.getContext().startActivity(in);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customgridview,parent,false);
      return new RecyclerViewHolder(v);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView product,pprice;
        private ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
            pprice = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.imageView);


        }

    }

}