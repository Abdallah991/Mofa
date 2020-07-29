package com.fathom.mofa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.fathom.mofa.DataModels.DamageReportDataModel;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRecord.damageReportRecord;
import static com.fathom.mofa.VehicleRecord.vehicleInRecord;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;
import static com.fathom.mofa.VehicleSetUp.vehicle;


public class VehicleAccidentReport extends Fragment {

    private NavController mNavController;
    private ImageView vehicleLeftSide;
    private ImageView vehicleRightSide;
    private ImageView vehicleFrontSide;
    private ImageView vehicleBackSide;
    private ImageView carHasDamage;
    private ImageView carIsUseable;
    private ImageView front;
    private ImageView frontRight;
    private ImageView frontLeft;
    private ImageView windshield;
    private ImageView frontLeftDoor;
    private ImageView frontRightDoor;
    private ImageView backRightDoor;
    private ImageView backLeftDoor;
    private ImageView ceilingFront;
    private ImageView ceilingBack;
    private ImageView backWindShield;
    private ImageView backRight;
    private ImageView backLeft;
    private ImageView back;
    private Button next;
    private Button backButton;
    // Damage Report
    // Photos
    public static CarPhotosDataModel carPhotosRecord = new CarPhotosDataModel();
    private String selector;
    // Vehicle state
    private boolean carDamageStatus =false;
    private boolean carUseStatus =false;

    private int actionToRecordConfirmation = R.id.action_vehicleAccidentReport_to_handoverConfirmation;


    public VehicleAccidentReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_accident_report, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vehicleRightSide = view.findViewById(R.id.vehicleRightSideAR);
        vehicleLeftSide = view.findViewById(R.id.vehicleLeftSideAR);
        vehicleFrontSide = view.findViewById(R.id.vehicleFrontSideAR);
        vehicleBackSide = view.findViewById(R.id.vehicleBackSideAR);
        carHasDamage = view.findViewById(R.id.carHasDamageImage);
        carIsUseable = view.findViewById(R.id.carIsUseableImage);
        back = view.findViewById(R.id.backConfirmation);
        backRight = view.findViewById(R.id.backRightConfirmation);
        backLeft = view.findViewById(R.id.backLeftConfirmation);
        backWindShield = view.findViewById(R.id.windShieldBackConfirmation);
        backRightDoor = view.findViewById(R.id.backRightDoorConfirmation);
        backLeftDoor = view.findViewById(R.id.backLeftDoorConfirmation);
        ceilingBack = view.findViewById(R.id.backCeilingConfirmation);
        ceilingFront = view.findViewById(R.id.ceilingConfirmation);
        frontLeftDoor = view.findViewById(R.id.frontLeftDoorConfirmation);
        frontRightDoor = view.findViewById(R.id.frontRightDoorConfirmation);
        windshield = view.findViewById(R.id.windShieldConfirmation);
        frontRight = view.findViewById(R.id.frontRightConfirmation);
        frontLeft = view.findViewById(R.id.frontLeftConfirmation);
        front = view.findViewById(R.id.frontConfirmation);
        backButton = view.findViewById(R.id.backAccidentReport);
        next = view.findViewById(R.id.nextAccidentReport);


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        carIsUseable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carUseStatus) {
                    carIsUseable.setImageResource(R.drawable.empty_check_box);
                    carUseStatus = false;
                } else {
                    carUseStatus = true;
                    carIsUseable.setImageResource(R.drawable.checked_check_box);
                }
                vehicleRecord.setCarIsUseable(carUseStatus);
            }
        });

        carHasDamage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carDamageStatus) {
                    carHasDamage.setImageResource(R.drawable.empty_check_box);
                    carDamageStatus = false;
                } else {
                    carDamageStatus = true;
                    carHasDamage.setImageResource(R.drawable.checked_check_box);
                }
                vehicleRecord.setCarHasDamage(carDamageStatus);

            }
        });

        damageReportRecord.setCarId(vehicle.getPlateNumber());
        damageReportRecord.setCarTransaction(vehicleRecord.getCarTransaction());
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
                if (!damageReportRecord.isFront()) {
                    front.setImageResource(R.drawable.front_red);
                    damageReportRecord.setFront(true);
                } else if (damageReportRecord.isFront()) {
                    front.setImageResource(R.drawable.front);
                    damageReportRecord.setFront(false);
                }
            }
        });

        frontRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isFrontRight()) {
                    frontRight.setImageResource(R.drawable.front_right_red);
                    damageReportRecord.setFrontRight(true);
                } else if (damageReportRecord.isFrontRight()) {
                    frontRight.setImageResource(R.drawable.front_right);
                    damageReportRecord.setFrontRight(false);
                }
            }
        });

        frontLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left_red);
                    damageReportRecord.setFrontLeft(true);
                } else if (damageReportRecord.isFrontLeft()) {
                    frontLeft.setImageResource(R.drawable.front_left);
                    damageReportRecord.setFrontLeft(false);
                }
            }
        });

        frontRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isPassengerDoor()) {
                    frontRightDoor.setImageResource(R.drawable.front_right_door_red);
                    damageReportRecord.setPassengerDoor(true);
                } else if (damageReportRecord.isPassengerDoor()) {
                    frontRightDoor.setImageResource(R.drawable.front_right_door);
                    damageReportRecord.setPassengerDoor(false);
                }
            }
        });

        frontLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isDriverDoor()) {
                    frontLeftDoor.setImageResource(R.drawable.front_left_door_red);
                    damageReportRecord.setDriverDoor(true);
                } else if (damageReportRecord.isDriverDoor()) {
                    frontLeftDoor.setImageResource(R.drawable.front_left_door);
                    damageReportRecord.setDriverDoor(false);
                }
            }
        });

        backRightDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackRightDoor()) {
                    backRightDoor.setImageResource(R.drawable.back_door_right_red);
                    damageReportRecord.setBackRightDoor(true);
                } else if (damageReportRecord.isBackRightDoor()) {
                    backRightDoor.setImageResource(R.drawable.back_door_right);
                    damageReportRecord.setBackRightDoor(false);
                }
            }
        });

        backLeftDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackLeftDoor()) {
                    backLeftDoor.setImageResource(R.drawable.back_door_left_red);
                    damageReportRecord.setBackLeftDoor(true);
                } else if (damageReportRecord.isBackLeftDoor()) {
                    backLeftDoor.setImageResource(R.drawable.back_door_left);
                    damageReportRecord.setBackLeftDoor(false);
                }
            }
        });

        windshield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isFrontWindShield()) {
                    windshield.setImageResource(R.drawable.wind_sheild_red);
                    damageReportRecord.setFrontWindShield(true);
                } else if (damageReportRecord.isFrontWindShield()) {
                    windshield.setImageResource(R.drawable.wind_sheild);
                    damageReportRecord.setFrontWindShield(false);
                }
            }
        });

        ceilingFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isCeiling()) {
                    ceilingFront.setImageResource(R.drawable.ceiling_red);
                    damageReportRecord.setCeiling(true);
                } else if (damageReportRecord.isCeiling()) {
                    ceilingFront.setImageResource(R.drawable.ceiling);
                    damageReportRecord.setCeiling(false);
                }
            }
        });

        ceilingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackCeiling()) {
                    ceilingBack.setImageResource(R.drawable.ceiling_back_red);
                    damageReportRecord.setBackCeiling(true);
                } else if (damageReportRecord.isBackCeiling()) {
                    ceilingBack.setImageResource(R.drawable.ceiling_back);
                    damageReportRecord.setBackCeiling(false);
                }
            }
        });

        backWindShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackWindShield()) {
                    backWindShield.setImageResource(R.drawable.back_wind_shield_red);
                    damageReportRecord.setBackWindShield(true);
                } else if (damageReportRecord.isBackWindShield()) {
                    backWindShield.setImageResource(R.drawable.back_wind_shield);
                    damageReportRecord.setBackWindShield(false);
                }
            }
        });

        backRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackRight()) {
                    backRight.setImageResource(R.drawable.back_right_red);
                    damageReportRecord.setBackRight(true);
                } else if (damageReportRecord.isBackRight()) {
                    backRight.setImageResource(R.drawable.back_right);
                    damageReportRecord.setBackRight(false);
                }
            }
        });

        backLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBackLeft()) {
                    backLeft.setImageResource(R.drawable.back_left_red);
                    damageReportRecord.setBackLeft(true);
                } else if (damageReportRecord.isBackLeft()) {
                    backLeft.setImageResource(R.drawable.back_left);
                    damageReportRecord.setBackLeft(false);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!damageReportRecord.isBack()) {
                    back.setImageResource(R.drawable.back_red);
                    damageReportRecord.setBack(true);
                } else if (damageReportRecord.isBack()) {
                    back.setImageResource(R.drawable.back);
                    damageReportRecord.setBack(false);
                }
            }
        });

        vehicleLeftSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleLeftSide";


            }
        });

        vehicleRightSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleRightSide";


            }
        });

        vehicleFrontSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleFrontSide";


            }
        });

        vehicleBackSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleBackSide";


            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getVehicleRecordInfo()) {
                    mNavController.navigate(actionToRecordConfirmation);
                }
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleAccidentReport";

    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        switch (selector) {
                            case "vehicleRightSide":
                                vehicleRightSide.setImageBitmap(selectedImage);
                                vehicleRecord.setPhotoRightSide(selectedImage.toString());
                                carPhotosRecord.setPhotoRightSide(selectedImage);
                                break;
                            case "vehicleLeftSide":
                                vehicleLeftSide.setImageBitmap(selectedImage);
                                vehicleRecord.setPhotoLeftSide(selectedImage.toString());
                                carPhotosRecord.setPhotoLeftSide(selectedImage);
                                break;
                            case "vehicleFrontSide":
                                vehicleFrontSide.setImageBitmap(selectedImage);
                                vehicleRecord.setPhotoFrontSide(selectedImage.toString());
                                carPhotosRecord.setPhotoFrontSide(selectedImage);
                                break;
                            case "vehicleBackSide":
                                vehicleBackSide.setImageBitmap(selectedImage);
                                vehicleRecord.setPhotoBackSide(selectedImage.toString());
                                carPhotosRecord.setPhotoBackSide(selectedImage);
                                break;

                        }
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        Log.d("GET IMAGE", "the Uri is: " +selectedImage.toString());
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Log.d("GET IMAGE", "the path is:"+filePathColumn[0]);

                        if (selectedImage != null) {
                            Context applicationContext = getActivity().getApplicationContext();
                            Cursor cursor = applicationContext.getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            Log.d("GET IMAGE", "the cursor is:"+ cursor);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Log.d("GET IMAGE", "the picture path is: "+ picturePath);

                                switch (selector) {
                                    case "vehicleRightSide":
                                        vehicleRightSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicleRecord.setPhotoRightSide(BitmapFactory.decodeFile(picturePath).toString());
                                        carPhotosRecord.setPhotoRightSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleLeftSide":
                                        vehicleLeftSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicleRecord.setPhotoLeftSide(BitmapFactory.decodeFile(picturePath).toString());
                                        carPhotosRecord.setPhotoLeftSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleFrontSide":
                                        vehicleFrontSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicleRecord.setPhotoFrontSide(BitmapFactory.decodeFile(picturePath).toString());
                                        carPhotosRecord.setPhotoFrontSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleBackSide":
                                        vehicleBackSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicleRecord.setPhotoBackSide(BitmapFactory.decodeFile(picturePath).toString());
                                        carPhotosRecord.setPhotoBackSide(BitmapFactory.decodeFile(picturePath));
                                        break;

                                }
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private boolean getVehicleRecordInfo() {

        if (carUseStatus) {
            vehicleRecord.setDamageReport(vehicleInRecord.getPlateNumber());
            damageReportRecord.setCarId(vehicleInRecord.getPlateNumber());


            return true;
        }
        else {
            Toast.makeText(getContext(), "Please select if the car is Useable", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
