package com.fourdevs.sophor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fourdevs.sophor.databinding.HotelItemViewBinding;
import com.fourdevs.sophor.listeners.HotelListener;
import com.fourdevs.sophor.model.Hotel;

import java.util.List;

public class PopularHotelAdapter extends RecyclerView.Adapter<PopularHotelAdapter.PopularHotelViewHolder > {

    private List<Hotel> hotelList;
    private Context context;
    private HotelListener hotelListener;

    public PopularHotelAdapter(Context context, List<Hotel> hotelList, HotelListener hotelListener) {
        this.context = context;
        this.hotelList = hotelList;
        this.hotelListener = hotelListener;
    }


    @NonNull
    @Override
    public PopularHotelAdapter.PopularHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HotelItemViewBinding hotelItemViewBinding = HotelItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PopularHotelViewHolder(hotelItemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularHotelAdapter.PopularHotelViewHolder holder, int position) {
        holder.setHotelData(hotelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    class PopularHotelViewHolder extends RecyclerView.ViewHolder{
        HotelItemViewBinding binding;
        public PopularHotelViewHolder(HotelItemViewBinding hotelListItemBinding) {
            super(hotelListItemBinding.getRoot());
            binding = hotelListItemBinding;
        }
        @SuppressLint("SetTextI18n")
        void setHotelData(Hotel hotel){
            try{
                binding.hotelImage.setImageBitmap(getBitmapFromEncodedString(hotel.hotelImage));
            }catch (Exception e) {
                Log.d("Afridi", e.getMessage());
            }
            binding.hotelName.setText(hotel.hotelName);
            binding.hotelLocationName.setText(hotel.hotelLocation);

            binding.getRoot().setOnClickListener(view -> hotelListener.onUserClicked(hotel));
        }

        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
