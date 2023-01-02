package com.rajendra.sophorapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.sophorapplication.databinding.TopPlacesRowItemBinding;
import com.rajendra.sophorapplication.listeners.TourListeners;
import com.rajendra.sophorapplication.model.Tour;

import java.util.List;

public class TopPlaceAdapter extends RecyclerView.Adapter<TopPlaceAdapter.TopPlaceViewHolder> {

    private final List<Tour> tourList;
    private final TourListeners tourListeners;

    public TopPlaceAdapter(List<Tour> tourList, TourListeners tourListeners) {
        this.tourList = tourList;
        this.tourListeners = tourListeners;
    }

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

    static class TopPlaceViewHolder extends RecyclerView.ViewHolder {

        TopPlacesRowItemBinding binding;
        public TopPlaceViewHolder(TopPlacesRowItemBinding topPlacesRowItemBinding) {
            super(topPlacesRowItemBinding.getRoot());
            binding=topPlacesRowItemBinding;
        }
        void setTourData(Tour tour) {

            binding.placeImage.setImageBitmap(getBitmapFromEncodedString(tour.mainImage));
            binding.placeName.setText(tour.name);
            binding.countryName.setText(tour.location);
            binding.price.setText("Package Price: BDT "+tour.price);
            binding.eventDate.setText("Even Date: "+tour.date);
        }
        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
