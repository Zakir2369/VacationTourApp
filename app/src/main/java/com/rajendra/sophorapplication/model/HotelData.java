package com.rajendra.sophorapplication.model;

public class HotelData {

    String hotelName;
    String hoteLocationName;

    Integer hotellocationIcon;
    Integer hotelImg;


    public HotelData(String hotelName, String hoteLocationName, Integer hotellocationIcon, Integer hotelImg) {
        this.hotelName=hotelName;
        this.hoteLocationName=hoteLocationName;
        this.hotellocationIcon=hotellocationIcon;
        this.hotelImg=hotelImg;

    }

    public Integer getHotelImg() {

        return hotelImg;
    }

    public void setHotelImg(Integer hotelImg) {

        this.hotelImg=hotelImg;
    }




    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {

        this.hotelName=hotelName;
    }




    public String getHoteLocationName() {

        return hoteLocationName;
    }

    public void setHoteLocationName(String hoteLocationName) {

        this.hoteLocationName=hoteLocationName;
    }


    public Integer getHotellocationIcon(){
        return hotellocationIcon;
    }

    public void setHotellocationIcon(Integer hotellocationIcon){

        this.hotellocationIcon=hotellocationIcon;
    }
}
