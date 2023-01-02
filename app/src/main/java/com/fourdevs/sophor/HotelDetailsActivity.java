package com.fourdevs.sophor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.fourdevs.sophor.databinding.ActivityHotelDetailsBinding;
import com.fourdevs.sophor.model.Hotel;
import com.fourdevs.sophor.util.Constants;
import com.fourdevs.sophor.util.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HotelDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ActivityHotelDetailsBinding binding;
    private boolean clicked1=false;
    private boolean clicked2=false;
    private Hotel hotel;
    private int roomCount = 0;
    private PreferenceManager preferenceManager;
    private String checkInDate, checkOutDate;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        hotel = (Hotel) getIntent().getSerializableExtra(Constants.KEY_HOTEL_NAME);
        binding.hotelName.setText(hotel.hotelName);
        binding.hotelDetails.setText(hotel.hotelDetails);
        binding.hotelImage.setImageBitmap(getBitmapFromEncodedString(hotel.hotelImage));
        binding.location.setText(hotel.hotelLocation);
        binding.hotelPrice.setText("BDT "+hotel.hotelPrice);

        if(hotel.wifi) {
            binding.hotelWifi.setVisibility(View.VISIBLE);
        }
        if(hotel.ac) {
            binding.hotelAc.setVisibility(View.VISIBLE);
        }
        if(hotel.restaurant) {
            binding.hotelDinning.setVisibility(View.VISIBLE);
        }
        if (hotel.parking) {
            binding.hotelParking.setVisibility(View.VISIBLE);
        }

        binding.hotelReadMore.setOnClickListener(v -> {
            if(binding.hotelDetails.getMaxLines() ==2) {
                binding.hotelDetails.setMaxLines(10);

            } else {
                binding.hotelDetails.setMaxLines(4);
            }
        });
        checkInDate = getReadableDateTime(new Date());
        binding.dateShow.setText(checkInDate);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        checkOutDate = getReadableDateTime(tomorrow);
        binding.dateShow2.setText(checkOutDate);


        binding.btPickDate.setOnClickListener(v -> {
            // Please note that use your package name here
            com.fourdevs.sophor.model.DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new com.fourdevs.sophor.model.DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            clicked1=true;
        });

        binding.btPickDate2.setOnClickListener(v -> {
            // Please note that use your package name here
            com.fourdevs.sophor.model.DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new com.fourdevs.sophor.model.DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            clicked2=true;
        });

        implementRoomNumber();
        setListeners();

    }

    private void setListeners() {
        binding.bookNow.setOnClickListener(view->{
            bookingProcess();
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(mCalendar.getTime());

        if(clicked1){
            checkInDate = selectedDate;
            binding.dateShow.setText(selectedDate);
            clicked1=false;
        }
        if(clicked2){
            checkOutDate = selectedDate;
            binding.dateShow2.setText(selectedDate);
            clicked2=false;
        }

    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Bitmap getBitmapFromEncodedString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void implementRoomNumber() {
        binding.increment.setOnClickListener(v -> {
            roomCount++;
            binding.roomCount.setText(String.valueOf(roomCount));
        });

        binding.decrement.setOnClickListener(v -> {
            if(roomCount>0) {
                roomCount--;
                binding.roomCount.setText(String.valueOf(roomCount));
            }

        });
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date);
    }

    private void bookingProcess() {
        isLoading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        @SuppressLint("SimpleDateFormat")
        int dateDifference = (int) getDateDiff(new SimpleDateFormat("MMM dd, yyyy"), checkInDate, checkOutDate);

        int totalCost = Integer.parseInt(hotel.hotelPrice) * dateDifference * roomCount;

        Map<String, Object> hotelBooking = new HashMap<>();
        hotelBooking.put(Constants.KEY_HOTEL_ID, hotel.hotelId);
        hotelBooking.put(Constants.KEY_HOTEL_NAME, hotel.hotelName);
        hotelBooking.put(Constants.KEY_HOTEL_LOCATION, hotel.hotelLocation);
        hotelBooking.put(Constants.KEY_HOTEL_PRICE, hotel.hotelPrice);
        hotelBooking.put(Constants.KEY_USER_NAME, preferenceManager.getString(Constants.KEY_USER_NAME));
        hotelBooking.put(Constants.KEY_EMAIL, preferenceManager.getString(Constants.KEY_EMAIL));
        hotelBooking.put(Constants.KEY_PHONE, preferenceManager.getString(Constants.KEY_PHONE));
        hotelBooking.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        hotelBooking.put(Constants.KEY_HOTEL_CHECK_IN, checkInDate);
        hotelBooking.put(Constants.KEY_HOTEL_CHECKOUT, checkOutDate);
        hotelBooking.put(Constants.KEY_HOTEL_ROOM_COUNT, String.valueOf(roomCount));
        hotelBooking.put(Constants.KEY_HOTEL_DAY_COUNT, String.valueOf(dateDifference));
        hotelBooking.put(Constants.KEY_HOTEL_COST, String.valueOf(totalCost));




        db.collection(Constants.KEY_HOTEL_BOOKING)
                .add(hotelBooking)
                .addOnSuccessListener(aVoid -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Congratulations ! ");
                    builder.setMessage("Your booking has been confirmed. You need to pay "+ totalCost+" TK when you arrived on hotel.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.dismiss();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    isLoading(false);
                })
                .addOnFailureListener(e -> {
                    makeToast("Failed to book this hotel - "+e.getMessage());
                    isLoading(false);
                });

    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void isLoading(Boolean loading) {
        if(loading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }


}

