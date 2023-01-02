package com.fourdevs.sophor.model;

import java.io.Serializable;

public class Hotel implements Serializable {
    public String hotelName, hotelLocation, hotelReview, hotelDetails;
    public String hotelPhone, hotelEmail, checkIn, checkOut;
    public String roomCount, hotelId, hotelPrice, hotelImage;
    public String customerId, customerPhone, customerName;
    public String customerEmail, customerCost, customerStayingDay;
    public Boolean wifi=false, ac=false, restaurant=false, parking=false;

    public Hotel() {

    }




}
