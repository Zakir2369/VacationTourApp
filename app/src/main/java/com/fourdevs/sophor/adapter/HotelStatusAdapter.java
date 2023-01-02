package com.fourdevs.sophor.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fourdevs.sophor.databinding.ItemContainerHotelBookingInfoBinding;
import com.fourdevs.sophor.model.Hotel;

import java.util.List;

public class HotelStatusAdapter extends RecyclerView.Adapter<HotelStatusAdapter.HotelViewHolder>{

    private final List<Hotel> hotelList;

    public HotelStatusAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }


    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerHotelBookingInfoBinding itemView =
                ItemContainerHotelBookingInfoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
        return new HotelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        holder.setHotelData(hotelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder{
        ItemContainerHotelBookingInfoBinding binding;

        public HotelViewHolder(ItemContainerHotelBookingInfoBinding itemView) {
            super(itemView.getRoot());
            binding =itemView;
        }

        @SuppressLint("SetTextI18n")
        void setHotelData(Hotel hotel) {
            //binding.hotelImage.setImageBitmap(getBitmapFromEncodedString(hotel.hotelImage));
            binding.hotelName.setText(hotel.hotelName);
            binding.hotelLocation.setText(hotel.hotelLocation);
            binding.checkinDate.setText(hotel.checkIn);
            binding.checkoutDate.setText(hotel.checkOut);
            binding.nightCount.setText(hotel.customerStayingDay+" Night");
            binding.roomCount.setText(hotel.roomCount+" Room");
            binding.totalCost.setText(hotel.customerCost+" Tk");
        }

        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
