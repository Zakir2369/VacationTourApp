package com.rajendra.sophorapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.sophorapplication.databinding.RecentsRowItemBinding;
import com.rajendra.sophorapplication.listeners.TourListeners;
import com.rajendra.sophorapplication.model.Tour;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    private final List<Tour> tourList;
    private final TourListeners tourListeners;

    public RecentAdapter(List<Tour> tourList, TourListeners tourListeners) {
        this.tourList = tourList;
        this.tourListeners = tourListeners;
    }

    @NonNull
    @Override
    public RecentAdapter.RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecentsRowItemBinding recentsRowItemBinding = RecentsRowItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new RecentViewHolder(recentsRowItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentAdapter.RecentViewHolder holder, int position) {
        holder.setTourData(tourList.get(position));
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    static class RecentViewHolder extends RecyclerView.ViewHolder {
        RecentsRowItemBinding binding;
        public RecentViewHolder(RecentsRowItemBinding recentsRowItemBinding) {
            super(recentsRowItemBinding.getRoot());
            binding = recentsRowItemBinding;
        }

        void setTourData(Tour tour) {
            binding.placeImage.setImageBitmap(getBitmapFromEncodedString(tour.mainImage));
            binding.placeName.setText(tour.name);
            binding.countryName.setText(tour.location);
            binding.price.setText("BDT "+tour.price);
            binding.eventDate.setText(tour.date);
        }
        private Bitmap getBitmapFromEncodedString(String encodedImage) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
