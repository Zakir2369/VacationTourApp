package com.rajendra.sophorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rajendra.sophorapplication.adapter.AllHotelAdapter;
import com.rajendra.sophorapplication.adapter.PopularHotelAdapter;
import com.rajendra.sophorapplication.listeners.HotelListener;
import com.rajendra.sophorapplication.model.AllHotelData;
import com.rajendra.sophorapplication.model.Hotel;
import com.rajendra.sophorapplication.model.HotelData;
import com.rajendra.sophorapplication.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HotelBooking extends AppCompatActivity implements HotelListener {

    RecyclerView popularhotelrecycler,allhotelrecycler;
    PopularHotelAdapter populars;
    AllHotelAdapter alls;
    private ImageButton backBtn;
    private ArrayList<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_booking);
        getHotelDataFromFirebase();
        backBtn=(ImageButton)findViewById(R.id.bakbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        }


    private void setPopularHotel() {
        popularhotelrecycler = findViewById(R.id.popular_hotel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularhotelrecycler.setLayoutManager(layoutManager);

        populars = new PopularHotelAdapter(this, hotelList, this);
        popularhotelrecycler.setAdapter(populars);
    }




    private void setAllHotel() {

        allhotelrecycler=findViewById(R.id.all_hotel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        allhotelrecycler.setLayoutManager(layoutManager);

        alls = new AllHotelAdapter(hotelList, this);
        allhotelrecycler.setAdapter(alls);
    }

    public void getHotelDataFromFirebase() {
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
                            setAllHotel();
                            setPopularHotel();
                        }
                    }
                });

    }


    @Override
    public void onUserClicked(Hotel hotel) {
        Intent intent = new Intent(this, HotelDetailsActivity.class);
        intent.putExtra(Constants.KEY_HOTEL_NAME, hotel);
        startActivity(intent);
    }


}