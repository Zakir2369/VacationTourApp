package com.rajendra.sophorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.sophorapplication.adapter.RecentsAdapter;
import com.rajendra.sophorapplication.adapter.TopPlacesAdapter;
import com.rajendra.sophorapplication.model.RecentsData;
import com.rajendra.sophorapplication.model.TopPlacesData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;
    LinearLayout home_linear,bus_linear,hotel_linear,guide_linear,profile_linear;

    private ImageView home_icon,bus_book,hotel_icon;
    private TextView home_text,hotel_text,bus_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_icon=findViewById(R.id.home_icon);
        bus_book=findViewById(R.id.bus_book);
        hotel_icon=findViewById(R.id.hotel_icon);

        //Text View
        home_text=findViewById(R.id.home_text);
        hotel_text=findViewById(R.id.hotel_text);
        bus_text=findViewById(R.id.bus_text);

        home_linear=(LinearLayout) findViewById(R.id.home_linear);
        bus_linear=(LinearLayout) findViewById(R.id.bus_linear);
        hotel_linear=(LinearLayout)findViewById(R.id.hotel_linear);
        guide_linear=(LinearLayout)findViewById(R.id.guide_linear);
        profile_linear=(LinearLayout)findViewById(R.id.profile_linear);

      home_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"You are already Home Page", Toast.LENGTH_SHORT).show();

            }
        });

        guide_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guide_intent= new Intent(MainActivity.this,GuideActivity.class);
                startActivity(guide_intent);

            }

        });

        bus_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bus_intent = new Intent(MainActivity.this, BusBookingActivity.class);
                startActivity(bus_intent);

            }
        });

        hotel_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent hote_intent= new Intent(MainActivity.this,HotelBooking.class);
                    startActivity(hote_intent);

                }

        });

       profile_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent hote_inten= new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(hote_inten);
                    finish();
                }

        });


        // Now here we will add some dummy data in our model class

        //Hide action bar
        getSupportActionBar().hide();

        List<RecentsData> recentsDataList = new ArrayList<>();
        recentsDataList.add(new RecentsData("AM Lake","India","From $200",R.drawable.recentimage1));
        recentsDataList.add(new RecentsData("Nilgiri Hills","India","From $300",R.drawable.recentimage2));
        recentsDataList.add(new RecentsData("AM Lake","India","From $200",R.drawable.recentimage1));
        recentsDataList.add(new RecentsData("Nilgiri Hills","India","From $300",R.drawable.recentimage2));
        recentsDataList.add(new RecentsData("AM Lake","India","From $200",R.drawable.recentimage1));
        recentsDataList.add(new RecentsData("Nilgiri Hills","India","From $300",R.drawable.recentimage2));

        setRecentRecycler(recentsDataList);

        List<TopPlacesData> topPlacesDataList = new ArrayList<>();
        topPlacesDataList.add(new TopPlacesData("Kasimir Hill","India","$200 - $500",R.drawable.topplaces));
        topPlacesDataList.add(new TopPlacesData("Kasimir Hill","India","$200 - $500",R.drawable.topplaces));
        topPlacesDataList.add(new TopPlacesData("Kasimir Hill","India","$200 - $500",R.drawable.topplaces));
        topPlacesDataList.add(new TopPlacesData("Kasimir Hill","India","$200 - $500",R.drawable.topplaces));
        topPlacesDataList.add(new TopPlacesData("Kasimir Hill","India","$200 - $500",R.drawable.topplaces));

        setTopPlacesRecycler(topPlacesDataList);
    }

    private  void setRecentRecycler(List<RecentsData> recentsDataList){

        recentRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recentRecycler.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycler.setAdapter(recentsAdapter);

    }


    private  void setTopPlacesRecycler(List<TopPlacesData> topPlacesDataList){

        topPlacesRecycler = findViewById(R.id.top_places_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        topPlacesRecycler.setLayoutManager(layoutManager);
        topPlacesAdapter = new TopPlacesAdapter(this, topPlacesDataList);
        topPlacesRecycler.setAdapter(topPlacesAdapter);

    }


}
