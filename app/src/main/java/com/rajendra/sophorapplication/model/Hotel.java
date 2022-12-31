package com.rajendra.sophorapplication.model;

import java.io.Serializable;
import java.util.Date;

public class Hotel implements Serializable {
    public String hotelName, hotelLocation, hotelReview, hotelDetails;
    public String hotelPhone, hotelEmail;
    public Date checkIn, checkOut;
    public int roomCount;
    public double hotelPrice;
    public String hotelImage, hotelImage1, hotelImage2, hotelImage3;
    public Boolean wifi=false, ac=false, restaurant=false, parking=false;


    public Hotel() {

    }


}
