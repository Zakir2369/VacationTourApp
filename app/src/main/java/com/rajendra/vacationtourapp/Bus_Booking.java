package com.rajendra.vacationtourapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.rajendra.vacationtourapp.R;


public class  Bus_Booking extends AppCompatActivity {


    private Button date_btn;
    private TextView select_date;
    private DatePickerDialog datePickerDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_booking);

        getSupportActionBar().hide();

    }

}