package com.example.quickshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.quickshop.databinding.ActivityUserProfileBinding;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {
    private ActivityUserProfileBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        binding.userPhone.setText(user.getPhoneNumber());

        binding.saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.userName.getText().toString();
                String email = binding.Emailadd.getText().toString();
                String  address = binding.Address.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your E-mail address",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    Toast.makeText(getApplicationContext(),"Please enter your address",Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please enter your Name",Toast.LENGTH_LONG).show();
                }

                else{
                        dbRef = FirebaseDatabase.getInstance().getReference("Users");
                    HashMap<String,Object> profile = new HashMap<>();
                          profile.put("UserName", name);
                          profile.put("User", user.toString());
                          profile.put("UserMail", email);
                          profile.put("UserAdd", address);
                          dbRef.child("User_Profile").setValue(profile);
                    Toast.makeText(UserProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();

                    Intent in =new Intent(UserProfile.this,HomePage.class);
                      startActivity(in);
                    finish();
                }
            }

        });

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfile.this, "Profile Skip", Toast.LENGTH_SHORT).show();

                Intent in =new Intent(UserProfile.this,HomePage.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}