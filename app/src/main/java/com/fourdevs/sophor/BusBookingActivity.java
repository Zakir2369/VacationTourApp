package com.fourdevs.sophor;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.fourdevs.sophor.util.PreferenceManager;
import com.fourdevs.sophor.databinding.ActivityBusBookingBinding;


public class BusBookingActivity extends AppCompatActivity {


    private ActivityBusBookingBinding binding;
    private PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);

        setListeners();

    }

    private void setListeners(){
        binding.bakbtn.setOnClickListener(view -> onBackPressed());
    }

}