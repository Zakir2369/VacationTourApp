package com.fourdevs.sophor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.fourdevs.sophor.adapter.HotelStatusAdapter;
import com.fourdevs.sophor.databinding.ActivityHotelBookingBinding;
import com.fourdevs.sophor.databinding.ActivityHotelBookingStatusBinding;
import com.fourdevs.sophor.model.Hotel;
import com.fourdevs.sophor.util.Constants;
import com.fourdevs.sophor.util.PreferenceManager;

import java.util.ArrayList;

public class HotelBookingStatus extends AppCompatActivity {
    private ActivityHotelBookingStatusBinding binding;
    private ArrayList<Hotel> hotelList;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelBookingStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        setListeners();
        getBookingInfo();
    }

    private void setListeners() {
        binding.profileBackBtn.setOnClickListener(view->onBackPressed());
    }

    private void getBookingInfo() {
        isLoading(true);
        hotelList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_HOTEL_BOOKING)
                .whereEqualTo(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Hotel hotel = new Hotel();
                            hotel.hotelId = document.getId();
                            hotel.hotelName = document.getString(Constants.KEY_HOTEL_NAME);
                            hotel.hotelLocation = document.getString(Constants.KEY_HOTEL_LOCATION);
                            hotel.hotelPrice = document.getString(Constants.KEY_HOTEL_PRICE);
                            hotel.customerName = document.getString(Constants.KEY_USER_NAME);
                            hotel.checkIn = document.getString(Constants.KEY_HOTEL_CHECK_IN);
                            hotel.checkOut = document.getString(Constants.KEY_HOTEL_CHECKOUT);
                            hotel.roomCount = document.getString(Constants.KEY_HOTEL_ROOM_COUNT);
                            hotel.customerStayingDay = document.getString(Constants.KEY_HOTEL_DAY_COUNT);
                            hotel.customerCost = document.getString(Constants.KEY_HOTEL_COST);
                            hotelList.add(hotel);

                        }
                        setHotelInfo();
                        isLoading(false);
                    }
                });
    }

    private void setHotelInfo() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.hotelBookingInfo.setLayoutManager(layoutManager);

        HotelStatusAdapter hotelStatusAdapter = new HotelStatusAdapter(hotelList);
        binding.hotelBookingInfo.setAdapter(hotelStatusAdapter);
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }


}