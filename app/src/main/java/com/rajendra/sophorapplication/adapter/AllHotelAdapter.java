package com.rajendra.sophorapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.sophorapplication.databinding.HotelListItemBinding;
import com.rajendra.sophorapplication.model.Hotel;
import com.rajendra.sophorapplication.listeners.HotelListener;

import java.util.List;

public class AllHotelAdapter extends RecyclerView.Adapter<AllHotelAdapter.AllHotelViewHolder > {

    private final List<Hotel> hotelList;
    private final HotelListener hotelListener;

    public AllHotelAdapter(List<Hotel> hotelList, HotelListener hotelListener) {
        this.hotelList = hotelList;
        this.hotelListener = hotelListener;
    }


    @NonNull
    @Override
    public AllHotelAdapter.AllHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotelListItemBinding hotelListItemBinding = HotelListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new AllHotelViewHolder(hotelListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllHotelAdapter.AllHotelViewHolder holder, int position) {
        holder.setHotelData(hotelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    class AllHotelViewHolder extends RecyclerView.ViewHolder{
        HotelListItemBinding binding;
        public AllHotelViewHolder(HotelListItemBinding hotelListItemBinding) {
            super(hotelListItemBinding.getRoot());
            binding = hotelListItemBinding;
        }
        @SuppressLint("SetTextI18n")
        void setHotelData(Hotel hotel){
            binding.hotelImg.setImageBitmap(getBitmapFromEncodedString(hotel.hotelImage));
            binding.hotelName.setText(hotel.hotelName);
            binding.location.setText(hotel.hotelLocation);
            binding.hotelPrice.setText("BDT "+hotel.hotelPrice);

            binding.getRoot().setOnClickListener(view -> hotelListener.onUserClicked(hotel));
        }
        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

    }
}