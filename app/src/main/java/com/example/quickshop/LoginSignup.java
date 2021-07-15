package com.example.quickshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class LoginSignup extends AppCompatActivity {

//    @BindViews({R.id.loginBtn,R.id.SignUpBtn,R.id.sendOtp,R.id.VerifyOtp,R.id.resend}) private Button loginBtn,signUpbtn,sentOtp,verifyOtpBtn,resendOtpBtn;
//    @BindViews({R.id.countryCode,R.id.countryCode,R.id.phoneNumber,R.id.otpText}) private EditText cCode,number,otpText;
//    @BindView(R.id.withGoogle) private TextView signInWithGoogle;
//    @BindViews({R.id.SignUpLogin,R.id.EnterNumberForOtp,R.id.vrifyOtp}) private ConstraintLayout loginAndSignUp,sendOtpLayout,otpVerifyLayout;

    private Button loginAndsignUpbtn,sentOtp,verifyOtpBtn,resendOtpBtn;
    private EditText cCode,number,otpText;
    private ConstraintLayout loginAndSignUp,sendOtpLayout,otpVerifyLayout;
    private PhoneAuthProvider.ForceResendingToken token;
    private FirebaseAuth fAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private  String userPhoneNumber,verificationId;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        getSupportActionBar().hide();
        loginAndsignUpbtn =findViewById(R.id.SignUpLoginBtn);
        sentOtp = findViewById(R.id.sendOtp);
        cCode = findViewById(R.id.countryCode);
        number= findViewById(R.id.phoneNumber);
        loginAndSignUp = findViewById(R.id.SignUpLogin);
        sendOtpLayout = findViewById(R.id.EnterNumberForOtp);
        otpVerifyLayout = findViewById(R.id.VerifyOtp);
        verifyOtpBtn = findViewById(R.id.vrifyOtp);
        resendOtpBtn = findViewById(R.id.resend);
        otpText = findViewById(R.id.otpText);

        fAuth = FirebaseAuth.getInstance();

        loginAndsignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAndSignUp.setVisibility(View.GONE);
                sendOtpLayout.setVisibility(View.VISIBLE);
                otpVerifyLayout.setVisibility(View.GONE);
                loginAndSignUp.setVisibility(View.GONE);
                sendOtpLayout.setVisibility(View.VISIBLE);
            }
        });


        sentOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cCode.getText().toString().isEmpty()){
                    cCode.setError("Country Code Is Required");
                    return;
                }
                if(number.getText().toString().isEmpty()){
                    number.setError("Phone Number Is Required");
                    return;
                }
                userPhoneNumber = cCode.getText().toString()+number.getText().toString();
                Toast.makeText(LoginSignup.this,""+cCode.getText().toString()+number.getText().toString(),Toast.LENGTH_SHORT).show();
                verifyPhoneNumber(userPhoneNumber);
                otpVerifyLayout.setVisibility(View.VISIBLE);
                loginAndSignUp.setVisibility(View.GONE);
                sendOtpLayout.setVisibility(View.GONE);

            }
        });


        resendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumber(userPhoneNumber);
                resendOtpBtn.setEnabled(false);
            }
        });


        verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpText.getText().toString().isEmpty()){
                    otpText.setError("Enter Otp First");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otpText.getText().toString());
                authenticateUser(credential);

            }
        });


        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authenticateUser(phoneAuthCredential);

            }
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                token = forceResendingToken;
                resendOtpBtn.setEnabled(false);
            }
            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOtpBtn.setEnabled(true);

            }
        };



        }
    public void verifyPhoneNumber(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(fAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    public void authenticateUser(PhoneAuthCredential credential){
        fAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginSignup.this, "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null)
        {
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {

        if( pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        }
        else {

            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
        }
        pressedTime = System.currentTimeMillis();


    }


}