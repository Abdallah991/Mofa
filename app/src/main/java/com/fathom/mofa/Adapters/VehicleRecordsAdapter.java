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
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VehicleRecordsAdapter extends RecyclerView.Adapter<VehicleRecordsAdapter.VehicleRecordHolder> {
    private static final String TAG = "Vehicles Adapter";
    public static VehicleRecordDataModel vehicleRecordDashboard;
    // Declare variables
    private ArrayList<VehicleRecordDataModel> mVehicleRecords;
    private Context mContext;
    private NavController mNavController;
    // The navigation action Id constant
    private int actionId;
    // Injecting the View Model
    private VehicleRecordViewModel mModel;
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");


    public VehicleRecordsAdapter(ArrayList<VehicleRecordDataModel> records,
                           Context context,
                           NavController navController,
                           int action,
                                 VehicleRecordViewModel model) {
        mVehicleRecords = records;
        mContext = context;
        mNavController = navController;
        actionId = action;
        mModel = model;

    }

    @NonNull
    @Override
    public VehicleRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item,parent, false);

        return new VehicleRecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleRecordHolder holder, final int position) {
        holder.driverName.setText(mVehicleRecords.get(position).getDriverName());
        holder.model.setText(mVehicleRecords.get(position).getModel());
        holder.make.setText(mVehicleRecords.get(position).getMake());
        holder.provider.setText(mVehicleRecords.get(position).getRentalInfo());
        Date date = mVehicleRecords.get(position).getDate();
        String dateValue = formatter.format(date);
        holder.date.setText(dateValue);
        holder.status.setText(mVehicleRecords.get(position).getStatus());
        switch (mVehicleRecords.get(position).getStatus()) {
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
                vehicleRecordDashboard = new VehicleRecordDataModel();
                vehicleRecordDashboard = mVehicleRecords.get(position);
                mNavController.navigate(actionId);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVehicleRecords.size();
    }

    public void filterDashboard(ArrayList<VehicleRecordDataModel> vehicleRecords) {

        mVehicleRecords = vehicleRecords;
        notifyDataSetChanged();
    }


    public class VehicleRecordHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView driverName;
        TextView model;
        TextView make;
        TextView provider;
        TextView date;
        TextView status;
        ImageView statusColor;

        public VehicleRecordHolder(View itemView) {
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.dashboardCard);
            driverName = itemView.findViewById(R.id.driverNameDashboard);
            model = itemView.findViewById(R.id.vehicleModelDashboard);
            make = itemView.findViewById(R.id.vehicleMakeDashboard);
            provider = itemView.findViewById(R.id.rentalInfoDashboard);
            date = itemView.findViewById(R.id.vehicleRecordDate);
            status = itemView.findViewById(R.id.vehicleStatusDashboard);
            statusColor = itemView.findViewById(R.id.statusDashboard);
        }
    }
}
