package com.fathom.mofa.Adapters;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;


import java.util.ArrayList;

public class DriverRecordsAdaptor extends RecyclerView.Adapter<DriverRecordsAdaptor.ViewHolder> {
    // Declare variables
    private ArrayList<DriverDataModel> mDriverRecords;
    private Context context;
    private NavController mNavController;
    private int actionId;
    private DriverViewModel mModel;
    private int actionNavigateToDriverEdit = R.id.action_fragment_driver_lists_to_editDriver2;



    public DriverRecordsAdaptor(ArrayList<DriverDataModel> mDriverRecords, Context context, NavController navController, int action,
                                DriverViewModel model) {
        this.mDriverRecords = mDriverRecords;
        this.context = context;
        mNavController = navController;
        actionId = action;
        mModel = model;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, final int position) {
        //        setting the value of the item
        DriverDataModel DriverRecords = mDriverRecords.get(position);
         holder.driverID.setText(DriverRecords.getDriverID());
        holder.driverName.setText(DriverRecords.getDriverName());
        holder.driverPhoneNumber.setText(DriverRecords.getPhoneNumber());
        holder.driverNationality.setText(DriverRecords.getNationality());
//        card click implementation

        holder.vehicleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModel.selectDriver(position);
                mNavController.navigate(actionNavigateToDriverEdit);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDriverRecords.size();
    }
    public void filterDrivers(ArrayList<DriverDataModel> driverRecords) {

        mDriverRecords = driverRecords;
        notifyDataSetChanged();
    }

    //    tying the UI elements in the item

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView driverName;
        public TextView driverID;
        public TextView driverPhoneNumber;
        public TextView driverNationality;
        public CardView vehicleCard;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            driverName = itemView.findViewById(R.id.nameDriver);
            driverID = itemView.findViewById(R.id.idDriver);
            driverPhoneNumber = itemView.findViewById(R.id.driverPhone);
            driverNationality = itemView.findViewById(R.id.driverNationality);
            vehicleCard = itemView.findViewById(R.id.vehicleCard);
        }
    }
}
