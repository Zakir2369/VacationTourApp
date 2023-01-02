package com.fourdevs.sophor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.fourdevs.sophor.databinding.TourPackageDetailsBinding;
import com.fourdevs.sophor.model.Tour;
import com.fourdevs.sophor.util.Constants;
import com.fourdevs.sophor.util.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class TourPackageDetailsActivity extends AppCompatActivity {

    private TourPackageDetailsBinding binding;
    private int memberCount = 0;
    private Tour tour;
    private PreferenceManager preferenceManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = TourPackageDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        implementRoomNumber();
        setListeners();

        preferenceManager = new PreferenceManager(this);
        tour = (Tour) getIntent().getSerializableExtra(Constants.KEY_TOUR_NAME);

        binding.tourPackage.setText(tour.name);
        binding.tourtLocation.setText(tour.location);
        binding.textView10.setText(tour.date);
        binding.textView11.setText(tour.price +" TK");
        binding.textView16.setText(tour.duration +" Days");
        binding.slider.setImageBitmap(getBitmapFromEncodedString(tour.mainImage));
        binding.imageView8.setImageBitmap(getBitmapFromEncodedString(tour.galleryOne));
        binding.imageView9.setImageBitmap(getBitmapFromEncodedString(tour.galleryTwo));
        binding.imageView10.setImageBitmap(getBitmapFromEncodedString(tour.galleryThree));
        binding.textView14.setText(tour.details);
        if (tour.dailyPlan!=null) {
            binding.textView14.setText(tour.dailyPlan);
        }
    }

    private void setListeners() {
        binding.btnDailyplan.setOnClickListener(view -> {

            if(binding.textView25.getVisibility() == View.GONE){
                binding.textView25.setVisibility(View.VISIBLE);
            }else {
                binding.textView25.setVisibility(View.GONE);
            }
        });

        binding.btnInclusion.setOnClickListener(view -> {
            if(binding.textView26.getVisibility() == View.GONE){
                binding.textView26.setVisibility(View.VISIBLE);
            }else {
                binding.textView26.setVisibility(View.GONE);
            }
        });

        binding.btnExclusion.setOnClickListener(view -> {

            if(binding.textView27.getVisibility() == View.GONE){
                binding.textView27.setVisibility(View.VISIBLE);
            }else {
                binding.textView27.setVisibility(View.GONE);
            }
        });

        binding.btnPolicy.setOnClickListener(view -> {
            if(binding.textView28.getVisibility() == View.GONE){
                binding.textView28.setVisibility(View.VISIBLE);
            }else {
                binding.textView28.setVisibility(View.GONE);
            }
        });

        binding.bookingButton.setOnClickListener(v -> {
            bookTourPackage();
        });
    }

    private void bookTourPackage() {
        isLoading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String personCount = binding.countTraveller.getText().toString().trim();
        Integer tourCost = Integer.parseInt(personCount) * Integer.parseInt(tour.price);

        Map<String, Object> tourBooking = new HashMap<>();
        tourBooking.put(Constants.KEY_TOUR_PACKAGE_ID, tour.packageId);
        tourBooking.put(Constants.KEY_TOUR_NAME, tour.name);
        tourBooking.put(Constants.KEY_TOUR_MAIN_IMAGE, tour.mainImage);
        tourBooking.put(Constants.KEY_TOUR_LOCATION, tour.location);
        tourBooking.put(Constants.KEY_TOUR_DATE, tour.date);
        tourBooking.put(Constants.KEY_TOUR_PRICE, tour.price);
        tourBooking.put(Constants.KEY_TOUR_PERSON_COUNT, personCount);
        tourBooking.put(Constants.KEY_TOUR_PAYMENT, String.valueOf(tourCost));
        tourBooking.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        tourBooking.put(Constants.KEY_USER_NAME, preferenceManager.getString(Constants.KEY_USER_NAME));
        tourBooking.put(Constants.KEY_PHONE, preferenceManager.getString(Constants.KEY_PHONE));

        db.collection(Constants.KEY_TOUR_BOOKING)
                .add(tourBooking)
                .addOnSuccessListener(aVoid -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Congratulations ! ");
                    builder.setMessage("Your booking has been confirmed. You need to pay "+ tourCost+" TK.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.dismiss();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    isLoading(false);
                })
                .addOnFailureListener(e -> {
                    makeToast("Failed to book this tour - "+e.getMessage());
                    isLoading(false);
                });

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

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
