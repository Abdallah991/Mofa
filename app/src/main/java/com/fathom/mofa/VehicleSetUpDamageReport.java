package com.fathom.mofa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fathom.mofa.DataModels.DamageReportDataModel;
import com.google.android.material.navigation.NavigationView;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleSetUp.vehicle;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpDamageReport extends Fragment {

    private Button next;
    private Button back;
    private ImageView frontRight;
    private ImageView frontLeft;
    private ImageView front;
    public static DamageReportDataModel damageReport;
    private NavController navController;
    private int actionNavigateToRentalInformation = R.id.action_vehicleSetUpDamageReport_to_vehicleSetUpRentalInfo;

    public VehicleSetUpDamageReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up_damage_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        next = view.findViewById(R.id.nextVehicleDamage);
        back = view.findViewById(R.id.backVehicleDamage);
        front = view.findViewById(R.id.front);
        frontLeft = view.findViewById(R.id.frontLeft);
        frontRight = view.findViewById(R.id.frontRight);


        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        damageReport = new DamageReportDataModel();

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFront()) {
                    front.setImageResource(R.drawable.front_shadow);
                    damageReport.setFront(true);
                } else if (damageReport.isFront()) {
                    front.setImageResource(R.drawable.front);
                    damageReport.setFront(false);
                }
            }
        });

        frontRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontRight()) {
                    frontRight.setImageResource(R.drawable.front_right_light_shadow);
                    damageReport.setFrontRight(true);
                } else if (damageReport.isFrontRight()) {
                    frontRight.setImageResource(R.drawable.front_right_light);
                    damageReport.setFrontRight(false);
                }
            }
        });

        frontLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left_light_shadow);
                    damageReport.setFrontLeft(true);
                } else if (damageReport.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left_light);
                    damageReport.setFrontLeft(false);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(actionNavigateToRentalInformation);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpDamageReport";
        Toast.makeText(getContext(), vehicle.getPhotoLeftSide() + " ", Toast.LENGTH_SHORT).show();
    }


}
