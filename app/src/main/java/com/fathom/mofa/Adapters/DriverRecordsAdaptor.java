package com.fathom.mofa.Adapters;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.DataModels.VehicleRecordDataModel;
import com.fathom.mofa.R;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.fathom.mofa.ViewModels.VehicleRecordViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DriverRecordsAdaptor extends RecyclerView.Adapter<DriverRecordsAdaptor.ViewHolder> {
    private ArrayList<DriverDataModel> mDriverRecords;
    private Context context;
    private NavController mNavController;
    private int actionId;
    private DriverViewModel mModel;

    public DriverRecordsAdaptor(ArrayList<DriverDataModel> mDriverRecords, Context context, NavController navController, int action,
                                DriverViewModel model) {
        this.mDriverRecords = mDriverRecords;
        this.context = context;
        mNavController = navController;
        actionId = action;
        mModel = model;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
         DriverDataModel DriverRecords = mDriverRecords.get(position);
         holder.driverID.setText(DriverRecords.getDriverID());
        holder.driverName.setText(DriverRecords.getDriverName());
        holder.driverPhoneNumber.setText(DriverRecords.getPhoneNumber());
        holder.driverNationality.setText(DriverRecords.getNationality());
    }


    @Override
    public int getItemCount() {
        return mDriverRecords.size();
    }
    public void filterDrivers(ArrayList<DriverDataModel> driverRecords) {

        mDriverRecords = driverRecords;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
public TextView driverName;
        public TextView driverID;
        public TextView driverPhoneNumber;
        public TextView driverNationality;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            driverName = itemView.findViewById(R.id.nameDriver);
            driverID = itemView.findViewById(R.id.idDriver);
            driverPhoneNumber = itemView.findViewById(R.id.driverPhone);
            driverNationality = itemView.findViewById(R.id.driverNationality);
        }
    }
}
