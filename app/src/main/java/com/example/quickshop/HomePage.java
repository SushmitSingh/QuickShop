package com.example.quickshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.quickshop.databinding.ActivityHomePageBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;


public class HomePage extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
       private ActivityHomePageBinding binding;
    private ArrayList<RecyclerData> ModelHeadlinesArrayList;
     private RecyclerViewAdapter myAdapter;
     RecyclerView mrecView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
         setContentView(view);
         getSupportActionBar().hide();
        mrecView=findViewById(R.id.recyclerView1);

        GridLayoutManager linearLayoutManager=new GridLayoutManager(this,2);
        FirebaseRecyclerOptions<RecyclerData> options = new FirebaseRecyclerOptions.Builder<RecyclerData>()
                .setQuery(FirebaseDatabase.getInstance().getReference("product"),RecyclerData.class)
                .build();

        // added data from arraylist to adapter class.
        myAdapter = new RecyclerViewAdapter(options);
        // at last set adapter to recycler view.
         mrecView.setLayoutManager(linearLayoutManager);
        mrecView.setAdapter(myAdapter);

        //setSupportActionBar(toolbar);
        binding.homeToolbar.inflateMenu(R.menu.homepage);
        binding.homeToolbar.setOnMenuItemClickListener(this::onMenuItemClick);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Toast.makeText(HomePage.this,"Opening Profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this,UserProfile.class));
                break;
            case R.id.addCart:
                Toast.makeText(HomePage.this,"Opening Cart",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomePage.this,addToCart.class));
                break;
            default:
        }
        return false;
    }
    @Override protected void onStart()
    {
        super.onStart();
        myAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        myAdapter.stopListening();
    }
}