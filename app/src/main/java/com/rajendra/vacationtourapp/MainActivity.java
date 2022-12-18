package com.rajendra.vacationtourapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rajendra.vacationtourapp.adapter.RecentsAdapter;
import com.rajendra.vacationtourapp.adapter.TopPlacesAdapter;
import com.rajendra.vacationtourapp.model.RecentsData;
import com.rajendra.vacationtourapp.model.TopPlacesData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler, topPlacesRecycler;
    RecentsAdapter recentsAdapter;
    TopPlacesAdapter topPlacesAdapter;

    private ImageButton bus_book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bus_book=findViewById(R.id.bus_book);

        bus_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bus_intent = new Intent(MainActivity.this,Bus_Booking.class);
                startActivity(bus_intent);
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
