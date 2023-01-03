package com.fourdevs.sophor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fourdevs.sophor.adapter.RecentAdapter;
import com.fourdevs.sophor.adapter.TopPlaceAdapter;
import com.fourdevs.sophor.databinding.ActivityTourBookingStatusBinding;
import com.fourdevs.sophor.listeners.TourListeners;
import com.fourdevs.sophor.model.Hotel;
import com.fourdevs.sophor.model.Tour;
import com.fourdevs.sophor.util.Constants;
import com.fourdevs.sophor.util.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TourBookingStatusActivity extends AppCompatActivity implements TourListeners {
    private ActivityTourBookingStatusBinding binding;
    private ArrayList<Tour> tourPackages;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTourBookingStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(this);
        getBookingInfo();
        setListeners();
    }

    private void setListeners() {
        binding.profileBackBtn.setOnClickListener(v-> onBackPressed());
    }

    private void getBookingInfo() {
        isLoading(true);
        tourPackages = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_TOUR_BOOKING)
                .whereEqualTo(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Tour tour = new Tour();
                            tour.packageId = document.getId();
                            tour.mainImage = document.getString(Constants.KEY_TOUR_MAIN_IMAGE);
                            tour.name = document.getString(Constants.KEY_TOUR_NAME);
                            tour.location = document.getString(Constants.KEY_TOUR_LOCATION);
                            tour.price = document.getString(Constants.KEY_TOUR_PRICE);
                            tour.date = document.getString(Constants.KEY_TOUR_DATE);
                            tourPackages.add(tour);
                        }
                        if(tourPackages.size()>0) {
                            setPackageInfo();
                        }

                        isLoading(false);
                    }
                });
    }

    private void setPackageInfo() {
        TopPlaceAdapter topPlaceAdapter = new TopPlaceAdapter(tourPackages, this);
        binding.recycler.setAdapter(topPlaceAdapter);
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserClicked(Tour tour) {

    }
}