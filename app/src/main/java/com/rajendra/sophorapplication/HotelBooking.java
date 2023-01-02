package com.rajendra.sophorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rajendra.sophorapplication.adapter.AllHotelAdapter;
import com.rajendra.sophorapplication.adapter.PopularHotelAdapter;
import com.rajendra.sophorapplication.databinding.ActivityHotelBookingBinding;
import com.rajendra.sophorapplication.listeners.HotelListener;
import com.rajendra.sophorapplication.model.AllHotelData;
import com.rajendra.sophorapplication.model.Hotel;
import com.rajendra.sophorapplication.model.HotelData;
import com.rajendra.sophorapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HotelBooking extends AppCompatActivity implements HotelListener {

    private ActivityHotelBookingBinding binding;
    private AllHotelAdapter allHotelAdapter;
    private ArrayList<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getHotelDataFromFirebase();

        setListeners();

    }

    private void setListeners() {
        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.searchButton.setOnClickListener(v -> searchAction());
    }


    private void setPopularHotel() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.popularHotel.setLayoutManager(layoutManager);
        PopularHotelAdapter populars = new PopularHotelAdapter(this, hotelList, this);
        binding.popularHotel.setAdapter(populars);
    }




    private void setAllHotel() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.allHotel.setLayoutManager(layoutManager);

        allHotelAdapter = new AllHotelAdapter(hotelList, this);
        binding.allHotel.setAdapter(allHotelAdapter);
    }

    public void getHotelDataFromFirebase() {
        isLoading(true);
        hotelList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_HOTEL_DB)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Hotel hotel = new Hotel();
                            hotel.hotelId = document.getId();
                            hotel.hotelName = document.getString(Constants.KEY_HOTEL_NAME);
                            hotel.hotelLocation = document.getString(Constants.KEY_HOTEL_LOCATION);
                            hotel.hotelDetails = document.getString(Constants.KEY_HOTEL_DETAILS);
                            hotel.hotelPrice = document.getString(Constants.KEY_HOTEL_PRICE);
                            hotel.hotelEmail = document.getString(Constants.KEY_HOTEL_EMAIL);
                            hotel.hotelPhone = document.getString(Constants.KEY_HOTEL_PHONE);
                            hotel.roomCount = document.getString(Constants.KEY_HOTEL_ROOM_COUNT);
                            hotel.wifi = document.getBoolean(Constants.KEY_HOTEL_WIFI);
                            hotel.ac = document.getBoolean(Constants.KEY_HOTEL_AC);
                            hotel.restaurant = document.getBoolean(Constants.KEY_HOTEL_DINING);
                            hotel.parking = document.getBoolean(Constants.KEY_HOTEL_PARKING);
                            hotel.hotelImage = document.getString(Constants.KEY_HOTEL_IMAGE);
                            hotel.hotelReview = document.getString(Constants.KEY_HOTEL_REVIEW);
                            hotelList.add(hotel);
                        }
                        binding.constraintLayout2.setVisibility(View.VISIBLE);
                        setAllHotel();
                        setPopularHotel();
                        isLoading(false);
                    }
                });

    }


    @Override
    public void onUserClicked(Hotel hotel) {
        Intent intent = new Intent(this, HotelDetailsActivity.class);
        intent.putExtra(Constants.KEY_HOTEL_NAME, hotel);
        startActivity(intent);
    }


    private void searchAction() {

        ArrayList<Hotel> filteredHotels = new ArrayList<>();

        String searchKey = binding.searchField.getText().toString().trim().toLowerCase();

        for(Hotel item : hotelList) {
            if(item.hotelName.toLowerCase().contains(searchKey)) {
                filteredHotels.add(item);
            } else if(item.hotelDetails.toLowerCase().contains(searchKey)) {
                filteredHotels.add(item);
            } else if(item.hotelLocation.toLowerCase().contains(searchKey)) {
                filteredHotels.add(item);
            }
        }

        if(filteredHotels.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            binding.textView3.setVisibility(View.GONE);
            binding.popularHotel.setVisibility(View.GONE);
            allHotelAdapter = new AllHotelAdapter(filteredHotels, this);
            binding.allHotel.setAdapter(allHotelAdapter);
        }
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }


}