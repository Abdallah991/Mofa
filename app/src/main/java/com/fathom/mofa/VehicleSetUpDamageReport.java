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
    private ImageView frontRightDoor;
    private ImageView frontLeftDoor;
    private ImageView windShield;
    private ImageView frontCeiling;
    private ImageView backCeiling;
    private ImageView backRightDoor;
    private ImageView backLeftDoor;
    private ImageView backWindShield;
    private ImageView backRight;
    private ImageView backLeft;
    private ImageView backCar;
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
        frontLeftDoor = view.findViewById(R.id.frontLeftDoor);
        frontRightDoor = view.findViewById(R.id.frontRightDoor);
        windShield = view.findViewById(R.id.windShield);
        frontCeiling = view.findViewById(R.id.ceiling);
        backCeiling = view.findViewById(R.id.backCeiling);
        backRightDoor = view.findViewById(R.id.backRightDoor);
        backLeftDoor = view.findViewById(R.id.backLeftDoor);
        backWindShield = view.findViewById(R.id.windShieldBack);
        backLeft = view.findViewById(R.id.backLeft);
        backRight = view.findViewById(R.id.backRight);
        backCar = view.findViewById(R.id.back);



        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        damageReport = new DamageReportDataModel();

        damageReport.setCarId(vehicle.getPlateNumber());
        damageReport.setCarTransaction("RTM");
        // To describe the transition of the car which is four Types:
        // 1- Getting the car from the rental company. "RTM"
        // 2- Returning the car to the rental company. "MTR"
        // 3- Hanover to the driver."MTD"
        // 4- Retrieval from the driver. "DTM"
        // D: Driver
        // M: Mofa
        // R: Rental Company
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFront()) {
                    front.setImageResource(R.drawable.front_red);
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
                    frontRight.setImageResource(R.drawable.front_right_red);
                    damageReport.setFrontRight(true);
                } else if (damageReport.isFrontRight()) {
                    frontRight.setImageResource(R.drawable.front_right);
                    damageReport.setFrontRight(false);
                }
            }
        });

        frontLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left_red);
                    damageReport.setFrontLeft(true);
                } else if (damageReport.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left);
                    damageReport.setFrontLeft(false);
                }
            }
        });

        frontRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isPassengerDoor()) {
                    frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                    damageReport.setPassengerDoor(true);
                } else if (damageReport.isPassengerDoor()) {
                    frontRightDoor.setImageResource(R.drawable.front_right_door);
                    damageReport.setPassengerDoor(false);
                }
            }
        });

        frontLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isDriverDoor()) {
                    frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                    damageReport.setDriverDoor(true);
                } else if (damageReport.isDriverDoor()) {
                    frontLeftDoor.setImageResource(R.drawable.front_left_door);
                    damageReport.setDriverDoor(false);
                }
            }
        });

        backRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackRightDoor()) {
                    backRightDoor.setImageResource(R.drawable.back_door_right_red);
                    damageReport.setBackRightDoor(true);
                } else if (damageReport.isBackRightDoor()) {
                    backRightDoor.setImageResource(R.drawable.back_door_right);
                    damageReport.setBackRightDoor(false);
                }
            }
        });

        backLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackLeftDoor()) {
                    backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                    damageReport.setBackLeftDoor(true);
                } else if (damageReport.isBackLeftDoor()) {
                    backLeftDoor.setImageResource(R.drawable.back_door_left);
                    damageReport.setBackLeftDoor(false);
                }
            }
        });

        windShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontWindShield()) {
                    windShield.setImageResource(R.drawable.wind_sheild_red);
                    damageReport.setFrontWindShield(true);
                } else if (damageReport.isFrontWindShield()) {
                    windShield.setImageResource(R.drawable.wind_sheild);
                    damageReport.setFrontWindShield(false);
                }
            }
        });

        frontCeiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isCeiling()) {
                    frontCeiling.setImageResource(R.drawable.ceiling_red);
                    damageReport.setCeiling(true);
                } else if (damageReport.isCeiling()) {
                    frontCeiling.setImageResource(R.drawable.ceiling);
                    damageReport.setCeiling(false);
                }
            }
        });

        backCeiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackCeiling()) {
                    backCeiling.setImageResource(R.drawable.ceiling_back_red);
                    damageReport.setBackCeiling(true);
                } else if (damageReport.isBackCeiling()) {
                    backCeiling.setImageResource(R.drawable.ceiling_back);
                    damageReport.setBackCeiling(false);
                }
            }
        });

        backWindShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackWindShield()) {
                    backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                    damageReport.setBackWindShield(true);
                } else if (damageReport.isBackWindShield()) {
                    backWindShield.setImageResource(R.drawable.back_wind_shield);
                    damageReport.setBackWindShield(false);
                }
            }
        });

        backRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackRight()) {
                    backRight.setImageResource(R.drawable.back_right_red);
                    damageReport.setBackRight(true);
                } else if (damageReport.isBackRight()) {
                    backRight.setImageResource(R.drawable.back_right);
                    damageReport.setBackRight(false);
                }
            }
        });

        backLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackLeft()) {
                    backLeft.setImageResource(R.drawable.back_left_red);
                    damageReport.setBackLeft(true);
                } else if (damageReport.isBackLeft()) {
                    backLeft.setImageResource(R.drawable.back_left);
                    damageReport.setBackLeft(false);
                }
            }
        });

        backCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBack()) {
                    backCar.setImageResource(R.drawable.back_red);
                    damageReport.setBack(true);
                } else if (damageReport.isBack()) {
                    backCar.setImageResource(R.drawable.back);
                    damageReport.setBack(false);
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
//        Toast.makeText(getContext(), vehicle.getPhotoLeftSide() + " ", Toast.LENGTH_SHORT).show();
    }


}
