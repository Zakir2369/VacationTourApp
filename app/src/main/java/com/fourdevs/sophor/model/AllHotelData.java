package com.fourdevs.sophor.model;

public class AllHotelData {

    String hotelName1;
    String hoteLocationName1;
    Integer hotellocationIcon1;
    Integer hotelImg1;
    String prices;
    Integer sideImg;

    public AllHotelData(String hotelName1, String hoteLocationName1, Integer hotellocationIcon1, Integer hotelImg1,String prices,Integer sideImg) {
        this.hotelName1=hotelName1;
        this.hoteLocationName1=hoteLocationName1;
        this.hotellocationIcon1=hotellocationIcon1;
        this.hotelImg1=hotelImg1;
        this.prices=prices;
        this.sideImg=sideImg;

    }

    public Integer getHotelImg() {

        return hotelImg1;
    }

    public void setHotelImg(Integer hotelImg1) {

        this.hotelImg1=hotelImg1;
    }



    public String getHotelName() {
        return hotelName1;
    }

    public void setHotelName(String hotelName1) {

        this.hotelName1=hotelName1;
    }



    public String getHoteLocationName() {

        return hoteLocationName1;
    }

    public void setHoteLocationName(String hoteLocationName1) {

        this.hoteLocationName1=hoteLocationName1;
    }


    public Integer getHotellocationIcon(){
        return hotellocationIcon1;
    }

    public void setHotellocationIcon(Integer hotellocationIcon1){

        this.hotellocationIcon1=hotellocationIcon1;
    }

    public String getPrices(){
        return prices;
    }
    public void setPrices(String prices){
        this.prices=prices;
    }

    public Integer getSideImg(){
        return sideImg;
    }

    public void setSideImg(Integer sideImg){
        this.sideImg=sideImg;
    }
}
