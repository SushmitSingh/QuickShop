package com.example.quickshop;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quickshop.databinding.ActivityProductViewBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class ProductView extends AppCompatActivity {
 static Long value;
ActivityProductViewBinding binding;
StorageTask uploadTask;
DatabaseReference mstorageref;
FirebaseDatabase  firebaseDatabase;
ProductInfo productinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productinfo = new ProductInfo();
        Intent i= getIntent();
        String userName=i.getStringExtra("title");
        firebaseDatabase = FirebaseDatabase.getInstance();
        mstorageref = firebaseDatabase.getReference("cartProducts").child(userName);
        binding = ActivityProductViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        String rate=i.getStringExtra("rate");
        String img=i.getStringExtra("image");
        Toast.makeText(this, ""+userName, Toast.LENGTH_SHORT).show();
        binding.itemtitle.setText(userName);
        binding.itemprice.setText(rate);
        Glide.with(binding.itemimage.getContext()).load(img).into(binding.itemimage);

        binding.addTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proname = userName.toString();
                String protitle = rate.toString();
                String proImg = img.toString();
                addCartProducts(proname, protitle, proImg);
            }
        });

    }

    private void addCartProducts(String proname, String protitle, String proImg) {
        productinfo.setTitle(proname);
        productinfo.setRate(protitle);
        productinfo.setImg(proImg);
        productinfo.setNumber(1L);
        mstorageref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mstorageref.setValue(productinfo);
                Toast.makeText(ProductView.this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductView.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }


}