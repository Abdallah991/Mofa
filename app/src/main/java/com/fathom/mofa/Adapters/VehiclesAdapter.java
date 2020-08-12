package com.fathom.mofa.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.mofa.DataModels.VehicleDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ViewModels.VehicleViewModel;

import java.util.ArrayList;

public class VehiclesAdapter extends RecyclerView.Adapter<VehiclesAdapter.VehicleHolder> {

    private static final String TAG = "Vehicles Adapter";
    public static VehicleDataModel vehicleDashboard;
    // Declare variables
    private ArrayList<VehicleDataModel> mVehicles;
    private Context mContext;
    private NavController mNavController;
    // The navigation action Id constant
    private int actionId;
    // Injecting the View Model
    private VehicleViewModel mModel;

    public VehiclesAdapter(ArrayList<VehicleDataModel> vehicles,
                           Context context,
                           NavController navController,
                           int action,
                           VehicleViewModel model) {
        mVehicles = vehicles;
        mContext = context;
        mNavController = navController;
        actionId = action;
        mModel = model;

    }

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_list_item,parent, false);

        return new VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, final int position) {
        holder.vehicleId.setText(mVehicles.get(position).getPlateNumber());
        holder.make.setText(mVehicles.get(position).getMake());
        holder.model.setText(mVehicles.get(position).getModel());
        holder.provider.setText(mVehicles.get(position).getRentalInfoContent());
        holder.color.setText(mVehicles.get(position).getColorOfCar());
        holder.status.setText(mVehicles.get(position).getStatus());
        switch (mVehicles.get(position).getStatus()) {
            case "Returned":
                holder.statusColor.setImageResource(R.drawable.green_status);
                break;
            case "Busy":
                holder.statusColor.setImageResource(R.drawable.orange_status);
                break;
            case "Released":
                holder.statusColor.setImageResource(R.drawable.red_status);
                break;
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehicleDashboard = new VehicleDataModel();
                vehicleDashboard = mVehicles.get(position);
                mNavController.navigate(actionId);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mVehicles.size();
    }


    public void filterRecycler(ArrayList<VehicleDataModel> vehicles) {

        mVehicles = vehicles;
        notifyDataSetChanged();
    }


    public class VehicleHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView vehicleId;
        TextView model;
        TextView make;
        TextView provider;
        TextView color;
        TextView status;
        ImageView statusColor;

        public VehicleHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.vehicleCard);
            vehicleId = itemView.findViewById(R.id.IdVehicle);
            model = itemView.findViewById(R.id.vehicleModel);
            make = itemView.findViewById(R.id.vehicleMake);
            provider = itemView.findViewById(R.id.rentalInfoVehicle);
            color = itemView.findViewById(R.id.vehicleDate);
            status = itemView.findViewById(R.id.vehicleStatus);
            statusColor = itemView.findViewById(R.id.status);
        }



    }


}
