package com.example.quickshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartViewAdapter extends FirebaseRecyclerAdapter<CartModelData,CartViewAdapter.CartHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    FirebaseDatabase mFirebaseDatabase;
    int quantity ;
    public CartViewAdapter(@NonNull FirebaseRecyclerOptions<CartModelData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull CartModelData model) {
              holder.carttitleText.setText(model.getTitle());
              holder.cartRateText.setText(model.getRate());
              Glide.with(holder.cartImage).load(model.getImg()).into(holder.cartImage);

              holder.add.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      quantity =model.getNumber().intValue();
                      quantity++;
                      holder.count.setText(String.valueOf(quantity));
                      mFirebaseDatabase =  FirebaseDatabase.getInstance();
                     mFirebaseDatabase.getReference("cartProducts").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // mFirebaseDatabase.getReference("cartProducts").child(model.getTitle()+"/number").setValue(quantity);
                             mFirebaseDatabase.getReference("cartProducts").child(model.getTitle()).child("number").setValue(quantity);
                         }

                         @Override

                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });
                  }
              });
              holder.minus.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      quantity =model.getNumber().intValue();
                      quantity--;
                   if(quantity<1){
                       removeCartItem( model);
                   }
                   else {

                       holder.count.setText(String.valueOf(quantity));
                       mFirebaseDatabase =  FirebaseDatabase.getInstance();
                       mFirebaseDatabase.getReference("cartProducts").addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               // mFirebaseDatabase.getReference("cartProducts").child(model.getTitle()+"/number").setValue(quantity);
                               mFirebaseDatabase.getReference("cartProducts").child(model.getTitle()).child("number").setValue(quantity);
                           }

                           @Override

                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                   }
                  }
              });
              holder.count.setText(String.valueOf(model.getNumber()));
//              holder.minus.setOnClickListener(new View.OnClickListener() {
//                  @Override
//                  public void onClick(View v) {
//                      quantity--;
//                      if(quantity<1){
//                          removeCartItem( model);
//                      }
////
////                      if(quantity>0){
////                      holder.count.setText(String.valueOf(quantity));}
////                      else
//                  }
//              });

    }

    private void removeCartItem(CartModelData model) {

        FirebaseDatabase.getInstance().getReference("cartProducts").child(model.getTitle()).removeValue();
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customcartcard,parent,false);
         return new CartHolder(v);
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private ImageView cartImage;
        private TextView carttitleText,cartRateText,add,count,minus;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartItemImage);
            carttitleText = itemView.findViewById(R.id.cartitemTitle);
            cartRateText = itemView.findViewById(R.id.cartitemPrice);
            add = itemView.findViewById(R.id.addbtn);
            count = itemView.findViewById(R.id.countnums);
            minus = itemView.findViewById(R.id.minus);
        }
    }
}
