package com.rajendra.sophorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rajendra.sophorapplication.adapter.RecentAdapter;
import com.rajendra.sophorapplication.adapter.TopPlaceAdapter;
import com.rajendra.sophorapplication.databinding.ActivityMainBinding;
import com.rajendra.sophorapplication.listeners.TourListeners;
import com.rajendra.sophorapplication.model.Tour;
import com.rajendra.sophorapplication.util.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TourListeners {

    private ActivityMainBinding binding;
    private TopPlaceAdapter topPlacesAdapter;
    private ArrayList<Tour> tourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
        getTourHotelData();
    }

    private void getTourHotelData() {
        tourList = new ArrayList<>();
        isLoading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_TOUR_DB)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Tour tour = new Tour();
                            tour.packageId = document.getId();
                            tour.name = document.getString(Constants.KEY_TOUR_NAME);
                            tour.date = document.getString(Constants.KEY_TOUR_DATE);
                            tour.price = document.getString(Constants.KEY_TOUR_PRICE);
                            tour.location = document.getString(Constants.KEY_TOUR_LOCATION);
                            tour.duration = document.getString(Constants.KEY_TOUR_DURATION);
                            tour.details = document.getString(Constants.KEY_TOUR_DETAILS);
                            tour.dailyPlan = document.getString(Constants.KEY_TOUR_DAILY_PLAN);
                            tour.mainImage = document.getString(Constants.KEY_TOUR_MAIN_IMAGE);
                            tour.galleryOne = document.getString(Constants.KEY_TOUR_GALLERY_ONE);
                            tour.galleryTwo = document.getString(Constants.KEY_TOUR_GALLERY_TWO);
                            tour.galleryThree = document.getString(Constants.KEY_TOUR_GALLERY_THREE);
                            tourList.add(tour);
                        }
                        binding.constraintLayout2.setVisibility(View.VISIBLE);
                        setRecentRecycler();
                        setTopPlacesRecycler();
                        isLoading(false);
                    }
                });

    }


    private  void setRecentRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.popularTour.setLayoutManager(layoutManager);
        RecentAdapter recentAdapter = new RecentAdapter(tourList, this);
        binding.popularTour.setAdapter(recentAdapter);
    }


    private  void setTopPlacesRecycler(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.allTour.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlaceAdapter(tourList, this);
        binding.allTour.setAdapter(topPlacesAdapter);

    }

    private void setListeners() {
        binding.hotelLinear.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"You are already Home Page", Toast.LENGTH_SHORT).show());

        binding.guideLinear.setOnClickListener(view -> {
            Intent guide_intent= new Intent(MainActivity.this,GuideActivity.class);
            startActivity(guide_intent);

        });

        binding.busLinear.setOnClickListener(view -> {
            Intent bus_intent = new Intent(MainActivity.this, BusBookingActivity.class);
            startActivity(bus_intent);

        });

        binding.hotelLinear.setOnClickListener(view -> {
            Intent hote_intent= new Intent(MainActivity.this,HotelBooking.class);
            startActivity(hote_intent);

        });

        binding.profileLinear.setOnClickListener(view -> {
            Intent hote_inten= new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(hote_inten);
            finish();
        });

        binding.searchButton.setOnClickListener(v -> searchAction());

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onUserClicked(Tour tour) {
        Intent intent = new Intent(this, TourPackageDetailsActivity.class);
        intent.putExtra(Constants.KEY_TOUR_NAME, tour);
        startActivity(intent);
    }

    private void searchAction() {

        ArrayList<Tour> filteredPackages = new ArrayList<>();

        String searchKey = binding.searchField.getText().toString().trim().toLowerCase();

        for(Tour item : tourList) {
            if(item.name.toLowerCase().contains(searchKey)) {
                filteredPackages.add(item);
            } else if(item.location.toLowerCase().contains(searchKey)) {
                filteredPackages.add(item);
            } else if(item.details.toLowerCase().contains(searchKey)) {
                filteredPackages.add(item);
            }
        }

        if(filteredPackages.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            binding.textView3.setVisibility(View.GONE);
            binding.popularTour.setVisibility(View.GONE);
            topPlacesAdapter = new TopPlaceAdapter(filteredPackages, this);
            binding.allTour.setAdapter(topPlacesAdapter);
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
