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
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.fathom.mofa.DataModels.DamageReportDataModel;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleSetUp.vehicle;


public class VehicleSetUpDamageReport extends Fragment {

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
    private ImageView frontRightTire;
    private ImageView frontLeftTire;
    private ImageView backRightTire;
    private ImageView backLeftTire;
    public static DamageReportDataModel damageReport;
    private NavController navController;
    private int actionNavigateToRentalInformation = R.id.action_vehicleSetUpDamageReport_to_vehicleSetUpRentalInfo;
    // MultiLanguage
    public static String SALOON;
    public static String JEEP;
    public static String FAMILY;
    public static String VAN;

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

        Button next = view.findViewById(R.id.nextVehicleDamage);
        Button back = view.findViewById(R.id.backVehicleDamage);
        ViewFlipper mViewFlipper = view.findViewById(R.id.vehicleSetupViewFlipper);
        switch (vehicle.getCarType()) {
            case "Saloon":
            case "صالون":
                mViewFlipper.setDisplayedChild(0);
                front = view.findViewById(R.id.frontConfirmation);
                frontLeft = view.findViewById(R.id.frontLeftConfirmation);
                frontRight = view.findViewById(R.id.frontRightConfirmation);
                frontLeftDoor = view.findViewById(R.id.frontLeftDoorConfirmation);
                frontRightDoor = view.findViewById(R.id.frontRightDoorConfirmation);
                windShield = view.findViewById(R.id.windShieldConfirmation);
                frontCeiling = view.findViewById(R.id.ceilingConfirmation);
                backCeiling = view.findViewById(R.id.backCeilingConfirmation);
                backRightDoor = view.findViewById(R.id.backRightDoorConfirmation);
                backLeftDoor = view.findViewById(R.id.backLeftDoorConfirmation);
                backWindShield = view.findViewById(R.id.windShieldBackConfirmation);
                backLeft = view.findViewById(R.id.backLeftConfirmation);
                backRight = view.findViewById(R.id.backRightConfirmation);
                backCar = view.findViewById(R.id.backConfirmation);
                backRightTire = view.findViewById(R.id.backRightTire);
                backLeftTire = view.findViewById(R.id.backLeftTire);
                frontRightTire = view.findViewById(R.id.frontRightTire);
                frontLeftTire = view.findViewById(R.id.frontLeftTire);
                break;
            case "Jeep":
            case "جيب":
                mViewFlipper.setDisplayedChild(1);
                front = view.findViewById(R.id.jeepFront);
                frontLeft = view.findViewById(R.id.jeepFrontLeft);
                frontRight = view.findViewById(R.id.jeepFrontRight);
                frontLeftDoor = view.findViewById(R.id.jeepFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.jeepFrontRightDoor);
                windShield = view.findViewById(R.id.jeepWindShield);
                frontCeiling = view.findViewById(R.id.jeepCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.jeepBackRightDoor);
                backLeftDoor = view.findViewById(R.id.jeepBackLeftDoor);
                backWindShield = view.findViewById(R.id.jeepBackWindShield);
                backLeft = view.findViewById(R.id.jeepBackLeft);
                backRight = view.findViewById(R.id.jeepBackRight);
                backCar = view.findViewById(R.id.jeepBack);
                backRightTire = view.findViewById(R.id.jeepBackRightTire);
                backLeftTire = view.findViewById(R.id.jeepBackLeftTire);
                frontRightTire = view.findViewById(R.id.jeepFrontRightTire);
                frontLeftTire = view.findViewById(R.id.jeepFrontLeftTire);
                break;
            case "Family":
            case "مركبة عائلية":
                mViewFlipper.setDisplayedChild(2);
                front = view.findViewById(R.id.familyFront);
                frontLeft = view.findViewById(R.id.familyFrontLeft);
                frontRight = view.findViewById(R.id.familyFrontRight);
                frontLeftDoor = view.findViewById(R.id.familyFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.familyFrontRightDoor);
                windShield = view.findViewById(R.id.familyWindShield);
                frontCeiling = view.findViewById(R.id.familyCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.familyBackRightDoor);
                backLeftDoor = view.findViewById(R.id.familyBackLeftDoor);
                backWindShield = view.findViewById(R.id.jeepBackWindShield);
                backLeft = view.findViewById(R.id.familyBackLeft);
                backRight = view.findViewById(R.id.familyBackRight);
                backCar = view.findViewById(R.id.familyBack);
                backRightTire = view.findViewById(R.id.familyBackRightTire);
                backLeftTire = view.findViewById(R.id.familyBackLeftTire);
                frontRightTire = view.findViewById(R.id.familyFrontRightTire);
                frontLeftTire = view.findViewById(R.id.familyFrontLeftTire);
                break;
            case "Van":
            case "شاحنة صغيرة":
                mViewFlipper.setDisplayedChild(3);
                front = view.findViewById(R.id.vanFront);
                frontLeft = view.findViewById(R.id.vanFrontLeft);
                frontRight = view.findViewById(R.id.vanFrontRight);
                frontLeftDoor = view.findViewById(R.id.vanFrontLeftDoor);
                frontRightDoor = view.findViewById(R.id.vanFrontRightDoor);
                windShield = view.findViewById(R.id.vanWindShield);
                frontCeiling = view.findViewById(R.id.vanCeiling);
//                backCeiling = view.findViewById(R.id.jeepBackCeiling);
                backRightDoor = view.findViewById(R.id.vanBackRightDoor);
                backLeftDoor = view.findViewById(R.id.vanBackLeftDoor);
//                backWindShield = view.findViewById(R.id.vanBackWindShield);
                backLeft = view.findViewById(R.id.vanBackLeft);
                backRight = view.findViewById(R.id.vanBackRight);
                backCar = view.findViewById(R.id.vanBack);
                backRightTire = view.findViewById(R.id.vanBackRightTire);
                backLeftTire = view.findViewById(R.id.vanBackLeftTire);
                frontRightTire = view.findViewById(R.id.vanFrontRightTire);
                frontLeftTire = view.findViewById(R.id.vanFrontLeftTire);
                break;
        }

        // Language handling


        mViewFlipper.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        String[] carTypes = getResources().getStringArray(R.array.types);
        SALOON = carTypes[0];
        JEEP = carTypes[1];
        FAMILY = carTypes[2];
        VAN = carTypes[3];


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
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            front.setImageResource(R.drawable.front_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            front.setImageResource(R.drawable.jeep_front_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            front.setImageResource(R.drawable.family_front_red);
                            break;
                    }
                    damageReport.setFront(true);

                } else if (damageReport.isFront()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            front.setImageResource(R.drawable.front);
                            break;
                        case "Jeep":
                        case "جيب":
                            front.setImageResource(R.drawable.jeep_front);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            front.setImageResource(R.drawable.family_front);
                            break;
                    }
                    damageReport.setFront(false);
                }
            }
        });

        frontRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontRight()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                        frontRight.setImageResource(R.drawable.front_right_red);
                        break;
                        case "Jeep":
                        case "جيب":
                            frontRight.setImageResource(R.drawable.jeep_front_right_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontRight.setImageResource(R.drawable.family_front_right_red);
                            break;
                    }
                    damageReport.setFrontRight(true);
                } else if (damageReport.isFrontRight()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontRight.setImageResource(R.drawable.front_right);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontRight.setImageResource(R.drawable.jeep_front_right);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontRight.setImageResource(R.drawable.family_front_right);
                            break;
                    }
                    damageReport.setFrontRight(false);
                }
            }
        });

        frontLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontLeft()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontLeft.setImageResource(R.drawable.front_left_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontLeft.setImageResource(R.drawable.jeep_front_left_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontLeft.setImageResource(R.drawable.family_front_left_red);
                            break;
                    }
                    damageReport.setFrontLeft(true);
                } else if (damageReport.isFrontLeft()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontLeft.setImageResource(R.drawable.front_left);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontLeft.setImageResource(R.drawable.jeep_front_left);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontLeft.setImageResource(R.drawable.family_front_left);
                            break;
                    }
                    damageReport.setFrontLeft(false);
                }
            }
        });

        frontRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isPassengerDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontRightDoor.setImageResource(R.drawable.jeep_front_right_door_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontRightDoor.setImageResource(R.drawable.family_front_right_door_red);
                            break;
                    }
                    damageReport.setPassengerDoor(true);
                } else if (damageReport.isPassengerDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontRightDoor.setImageResource(R.drawable.front_right_door);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontRightDoor.setImageResource(R.drawable.jeep_front_right_door);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontRightDoor.setImageResource(R.drawable.family_front_right_door);
                            break;
                    }
                    damageReport.setPassengerDoor(false);
                }
            }
        });

        frontLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isDriverDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontLeftDoor.setImageResource(R.drawable.jeep_front_left_door_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontLeftDoor.setImageResource(R.drawable.family_front_left_door_red);
                            break;
                    }
                    damageReport.setDriverDoor(true);

                } else if (damageReport.isDriverDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontLeftDoor.setImageResource(R.drawable.front_left_door);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontLeftDoor.setImageResource(R.drawable.jeep_front_left_door);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontLeftDoor.setImageResource(R.drawable.family_front_left_door);
                            break;
                    }
                    damageReport.setDriverDoor(false);
                }
            }
        });

        backRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackRightDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backRightDoor.setImageResource(R.drawable.back_door_right_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            backRightDoor.setImageResource(R.drawable.jeep_back_right_door_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backRightDoor.setImageResource(R.drawable.family_back_right_door_red);
                            break;
                    }
                    damageReport.setBackRightDoor(true);
                } else if (damageReport.isBackRightDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backRightDoor.setImageResource(R.drawable.back_door_right);
                            break;
                        case "Jeep":
                        case "جيب":
                            backRightDoor.setImageResource(R.drawable.jeep_back_right_door);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backRightDoor.setImageResource(R.drawable.family_back_right_door);
                            break;
                    }
                    damageReport.setBackRightDoor(false);
                }
            }
        });

        backLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackLeftDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            backLeftDoor.setImageResource(R.drawable.jeep_back_left_door_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backLeftDoor.setImageResource(R.drawable.family_back_left_door_red);
                            break;
                    }
                    damageReport.setBackLeftDoor(true);
                } else if (damageReport.isBackLeftDoor()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backLeftDoor.setImageResource(R.drawable.back_door_left);
                            break;
                        case "Jeep":
                        case "جيب":
                            backLeftDoor.setImageResource(R.drawable.jeep_back_left_door);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backLeftDoor.setImageResource(R.drawable.family_back_left_door);
                            break;
                    }
                    damageReport.setBackLeftDoor(false);
                }
            }
        });

        windShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontWindShield()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            windShield.setImageResource(R.drawable.wind_sheild_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            windShield.setImageResource(R.drawable.jeep_wind_sheild_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            windShield.setImageResource(R.drawable.family_wind_sheild_red);
                            break;
                    }
                    damageReport.setFrontWindShield(true);
                } else if (damageReport.isFrontWindShield()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            windShield.setImageResource(R.drawable.wind_sheild);
                            break;
                        case "Jeep":
                        case "جيب":
                            windShield.setImageResource(R.drawable.jeep_wind_sheild);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            windShield.setImageResource(R.drawable.family_wind_sheild);
                            break;
                    }
                    damageReport.setFrontWindShield(false);
                }
            }
        });

        frontCeiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isCeiling()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontCeiling.setImageResource(R.drawable.ceiling_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontCeiling.setImageResource(R.drawable.jeep_ceiling_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontCeiling.setImageResource(R.drawable.family_ceiling_red);
                            break;
                    }
                    damageReport.setCeiling(true);
                } else if (damageReport.isCeiling()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            frontCeiling.setImageResource(R.drawable.ceiling);
                            break;
                        case "Jeep":
                        case "جيب":
                            frontCeiling.setImageResource(R.drawable.jeep_ceiling);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            frontCeiling.setImageResource(R.drawable.family_ceiling);
                            break;
                    }
                    damageReport.setCeiling(false);
                }
            }
        });


        if (vehicle.getCarType().equals(SALOON)) {

            backCeiling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!damageReport.isBackCeiling()) {
                        backCeiling.setImageResource(R.drawable.back_red);
                        damageReport.setBackCeiling(true);
                    } else if (damageReport.isBackCeiling()) {
                        backCeiling.setImageResource(R.drawable.back);
                        damageReport.setBackCeiling(false);
                    }
                }
            });

        }

        if (vehicle.getCarType().equals(SALOON) ||
                vehicle.getCarType().equals(JEEP) ||
                vehicle.getCarType().equals(FAMILY)) {
            backWindShield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!damageReport.isBackWindShield()) {
                        switch (vehicle.getCarType()) {
                            case "Saloon":
                            case "صالون":
                                backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                                break;
                            case "Jeep":
                            case "جيب":
                                backWindShield.setImageResource(R.drawable.jeep_back_red);
                                break;
                            case "Family":
                            case "مركبة عائلية":
                                backWindShield.setImageResource(R.drawable.family_back_red);
                                break;
                        }
                        damageReport.setBackWindShield(true);
                    } else if (damageReport.isBackWindShield()) {
                        switch (vehicle.getCarType()) {
                            case "Saloon":
                            case "صالون":
                                backWindShield.setImageResource(R.drawable.back_wind_shield);
                                break;
                            case "Jeep":
                            case "جيب":
                                backWindShield.setImageResource(R.drawable.jeep_back);
                                break;
                            case "Family":
                            case "مركبة عائلية":
                                backWindShield.setImageResource(R.drawable.family_back);
                                break;
                        }
                        damageReport.setBackWindShield(false);
                    }
                }
            });
        }

        backRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackRight()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backRight.setImageResource(R.drawable.back_right_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            backRight.setImageResource(R.drawable.jeep_back_right_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backRight.setImageResource(R.drawable.family_back_right_red);
                            break;
                    }
                    damageReport.setBackRight(true);
                } else if (damageReport.isBackRight()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backRight.setImageResource(R.drawable.back_right);
                            break;
                        case "Jeep":
                        case "جيب":
                            backRight.setImageResource(R.drawable.jeep_back_right);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backRight.setImageResource(R.drawable.family_back_right);
                            break;
                    }
                    damageReport.setBackRight(false);
                }
            }
        });

        backLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackLeft()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backLeft.setImageResource(R.drawable.back_left_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            backLeft.setImageResource(R.drawable.jeep_back_left_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backLeft.setImageResource(R.drawable.family_back_left_red);
                            break;
                    }
                    damageReport.setBackLeft(true);
                } else if (damageReport.isBackLeft()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backLeft.setImageResource(R.drawable.back_left);
                            break;
                        case "Jeep":
                        case "جيب":
                            backLeft.setImageResource(R.drawable.jeep_back_left);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backLeft.setImageResource(R.drawable.family_back_left);
                            break;
                    }
                    damageReport.setBackLeft(false);
                }
            }
        });

        backCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBack()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backCar.setImageResource(R.drawable.back_red);
                            break;
                        case "Jeep":
                        case "جيب":
                            backCar.setImageResource(R.drawable.jeep_back_red);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backCar.setImageResource(R.drawable.family_back_red);
                            break;
                    }
                    damageReport.setBack(true);
                } else if (damageReport.isBack()) {
                    switch (vehicle.getCarType()) {
                        case "Saloon":
                        case "صالون":
                            backCar.setImageResource(R.drawable.back);
                            break;
                        case "Jeep":
                        case "جيب":
                            backCar.setImageResource(R.drawable.jeep_back);
                            break;
                        case "Family":
                        case "Van":
                        case "مركبة عائلية":
                        case "شاحنة صغيرة":
                            backCar.setImageResource(R.drawable.family_back);
                            break;
                    }
                    damageReport.setBack(false);
                }
            }
        });

        frontRightTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontRightTire()) {
                    frontRightTire.setImageResource(R.drawable.tire_red);
                    damageReport.setFrontRightTire(true);

                } else if (damageReport.isFrontRightTire()) {
                    frontRightTire.setImageResource(R.drawable.tire);
                    damageReport.setFrontRightTire(false);
                }
            }
        });

        frontLeftTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isFrontLeftTire()) {
                    frontLeftTire.setImageResource(R.drawable.tire_red);
                    damageReport.setFrontLeftTire(true);

                } else if (damageReport.isFrontLeftTire()) {
                    frontLeftTire.setImageResource(R.drawable.tire);
                    damageReport.setFrontLeftTire(false);
                }
            }
        });

        backLeftTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackLeftTire()) {
                    backLeftTire.setImageResource(R.drawable.tire_red);
                    damageReport.setBackLeftTire(true);

                } else if (damageReport.isBackLeftTire()) {
                    backLeftTire.setImageResource(R.drawable.tire);
                    damageReport.setBackLeftTire(false);
                }
            }
        });
        backRightTire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReport.isBackRightTire()) {
                    backRightTire.setImageResource(R.drawable.tire_red);
                    damageReport.setBackRightTire(true);

                } else if (damageReport.isBackRightTire()) {
                    backRightTire.setImageResource(R.drawable.tire);
                    damageReport.setBackRightTire(false);
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

    @Override
    public void onStop() {
        super.onStop();
        frontRight = null;
        frontLeft = null;
        front = null;
        frontRightDoor = null;
        frontLeftDoor = null;
        windShield = null;
        frontCeiling = null;
        backCeiling = null;
        backRightDoor = null;
        backLeftDoor = null;
        backWindShield = null;
        backRight = null;
        backLeft = null;
        backCar = null;
        frontRightTire = null;
        frontLeftTire = null;
        backRightTire = null;
        backLeftTire = null;

    }
}
