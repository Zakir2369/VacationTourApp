package com.rajendra.sophorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.rajendra.sophorapplication.databinding.ActivityProfileBinding;
import com.rajendra.sophorapplication.util.Constants;
import com.rajendra.sophorapplication.util.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        preferenceManager = new PreferenceManager(this);
        mAuth = FirebaseAuth.getInstance();
        setListeners();
        getUserData();

    }
    private void setListeners() {
        binding.profileBackBtn.setOnClickListener(view -> onBackPressed());
        binding.logOut.setOnClickListener(view -> logOut());
    }

    private void getUserData() {
        binding.userName.setText(preferenceManager.getString(Constants.KEY_USER_NAME));
        binding.email.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        binding.userEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
        binding.userPhone.setText(preferenceManager.getString(Constants.KEY_PHONE));
    }

    private void logOut() {
        preferenceManager.clear();
        mAuth.signOut();
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}