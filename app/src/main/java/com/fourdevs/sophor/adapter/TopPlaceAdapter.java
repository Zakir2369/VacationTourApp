package com.fourdevs.sophor.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fourdevs.sophor.databinding.TopPlacesRowItemBinding;
import com.fourdevs.sophor.listeners.TourListeners;
import com.fourdevs.sophor.model.Tour;

import java.util.List;

public class TopPlaceAdapter extends RecyclerView.Adapter<TopPlaceAdapter.TopPlaceViewHolder> {

    private final List<Tour> tourList;
    private final TourListeners tourListeners;

    public TopPlaceAdapter(List<Tour> tourList, TourListeners tourListeners) {
        this.tourList = tourList;
        this.tourListeners = tourListeners;
    }

    @NonNull
    public TopPlaceAdapter.TopPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopPlacesRowItemBinding topPlacesRowItemBinding = TopPlacesRowItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new TopPlaceViewHolder(topPlacesRowItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull TopPlaceAdapter.TopPlaceViewHolder holder, int position) {
        holder.setTourData(tourList.get(position));
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    class TopPlaceViewHolder extends RecyclerView.ViewHolder {

        TopPlacesRowItemBinding binding;
        public TopPlaceViewHolder(TopPlacesRowItemBinding topPlacesRowItemBinding) {
            super(topPlacesRowItemBinding.getRoot());
            binding=topPlacesRowItemBinding;
        }
        @SuppressLint("SetTextI18n")
        void setTourData(Tour tour) {

            binding.placeImage.setImageBitmap(getBitmapFromEncodedString(tour.mainImage));
            binding.placeName.setText(tour.name);
            binding.countryName.setText(tour.location);
            binding.price.setText("BDT "+tour.price);
            binding.eventDate.setText("Date: "+tour.date);
            binding.getRoot().setOnClickListener(view -> tourListeners.onUserClicked(tour));
        }
        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
