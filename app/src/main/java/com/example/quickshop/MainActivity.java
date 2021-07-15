package com.example.quickshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.quickshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
         getSupportActionBar().hide();
        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            Intent i = new Intent(MainActivity.this, LoginSignup.class);
            startActivity(i);
            finish();
        }, 1500);
    }

}