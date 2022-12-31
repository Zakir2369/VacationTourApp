package com.rajendra.sophorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rajendra.sophorapplication.model.SliderData;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class TourPackageDetailsActivity extends AppCompatActivity {

    String url1 = "https://tbbd-flight.s3.ap-southeast-1.amazonaws.com/blog4dKAKeKwaLG__IpVCQXRBfc84THl_hTO.jpg";
    String url2 = "https://i.pinimg.com/736x/7d/e4/6e/7de46e832eec81c15d454c01efdc768d.jpg";
    String url3 = "https://upload.wikimedia.org/wikipedia/commons/c/cf/Cox%27s_Bazaar_Sunset_Sep2019.jpg";

    ImageButton show1,show2,show3,show4,show5;
    TextView textView25,textView26,textView27,textView28,textView29;
    private  boolean textviewVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_package_details);

        show1=(ImageButton) findViewById(R.id.btn_dailyplan);
        show2=(ImageButton) findViewById(R.id.btn_inclusion);
        show3=(ImageButton) findViewById(R.id.btn_exclusion);
        show4=(ImageButton) findViewById(R.id.btn_policy);


        textView25=(TextView)findViewById(R.id.textView25);
        textView26=(TextView)findViewById(R.id.textView26);
        textView27=(TextView)findViewById(R.id.textView27);
        textView28=(TextView)findViewById(R.id.textView28);

        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textviewVisible==false)
                {
                    textView25.setVisibility(View.VISIBLE);
                    textviewVisible = true;
                }

                else if(textviewVisible)
                {
                    textView25.setVisibility(View.GONE);
                    textviewVisible = false;
                }
            }
        });

        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textviewVisible==false)
                {
                    textView26.setVisibility(View.VISIBLE);
                    textviewVisible = true;
                }

                else if(textviewVisible)
                {
                    textView26.setVisibility(View.GONE);
                    textviewVisible = false;
                }
            }

        });

        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textviewVisible==false)
                {
                    textView27.setVisibility(View.VISIBLE);
                    textviewVisible = true;
                }

                else if(textviewVisible)
                {
                    textView27.setVisibility(View.GONE);
                    textviewVisible = false;
                }
            }

        });

        show4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(textviewVisible==false)
                {
                    textView28.setVisibility(View.VISIBLE);
                    textviewVisible = true;
                }

                else if(textviewVisible)
                {
                    textView28.setVisibility(View.GONE);
                    textviewVisible = false;
                }
            }

        });


        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();


        ///////Item show and hide///////


    }
}
