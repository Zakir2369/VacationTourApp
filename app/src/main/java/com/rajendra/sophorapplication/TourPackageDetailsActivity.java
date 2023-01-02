package com.rajendra.sophorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rajendra.sophorapplication.databinding.TourPackageDetailsBinding;
import com.rajendra.sophorapplication.model.SliderData;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class TourPackageDetailsActivity extends AppCompatActivity {

    private TourPackageDetailsBinding binding;
    private int memberCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TourPackageDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        implementRoomNumber();


    }

    private void implementRoomNumber() {
        binding.increment.setOnClickListener(v -> {
            memberCount++;
            binding.countTraveller.setText(String.valueOf(memberCount));
        });

        binding.decrement.setOnClickListener(v -> {
            if(memberCount>0) {
                memberCount--;
                binding.countTraveller.setText(String.valueOf(memberCount));
            }

        });
    }
}
