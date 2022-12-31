package com.rajendra.sophorapplication.model;

import java.io.Serializable;
import java.util.Date;

public class Hotel implements Serializable {
    String hotelName, hotelLocation, hotelReview, hotelDetails;
    String hotelWifi, HotelAc, hotelParking, hotelDining;
    Date checkIn, checkOut;
    int roomCount;
    double hotelPrice;
    String hotelImage, hotelImage1, hotelImage2, hotelImage3;


    Hotel() {

    }


}
