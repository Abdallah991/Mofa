package com.fathom.mofa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fathom.mofa.DataModels.CarPhotosDataModel;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleSetUp.vehicle;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRegistration extends Fragment {

//    variable declaration
    public static CarPhotosDataModel carPhotos = new CarPhotosDataModel();
    private NavController mNavController;
    private AutoCompleteTextView registrationType;
    private AutoCompleteTextView engineSize;
    private AutoCompleteTextView registrationStart;
    private AutoCompleteTextView registrationEnd;
    private ImageView vehicleLeftSide;
    private ImageView vehicleRightSide;
    private ImageView vehicleFrontSide;
    private ImageView vehicleBackSide;
    private ImageView vehicleFrontInterior;
    private ImageView vehicleBackInterior;
    private ImageView vehicleTrunk;
    private Button interior;
    private Button exterior;
    private String selector;
    private Date start;
    private Date end;
    private int actionNavigateToDamageFromRegistration = R.id.action_vehicleRegistration_to_vehicleSetUpDamageReport;

    public VehicleRegistration() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        link variables to the UI
        registrationType = view.findViewById(R.id.registrationType);
        engineSize = view.findViewById(R.id.motorSize);
        registrationStart = view.findViewById(R.id.registrationStart);
        registrationEnd = view.findViewById(R.id.registrationEnd);
        vehicleLeftSide = view.findViewById(R.id.vehicleLeftSide);
        vehicleRightSide = view.findViewById(R.id.vehicleRightSide);
        vehicleFrontSide = view.findViewById(R.id.vehicleFrontSide);
        vehicleBackSide = view.findViewById(R.id.vehicleBackSide);
        vehicleFrontInterior = view.findViewById(R.id.vehicleFrontInterior);
        vehicleBackInterior = view.findViewById(R.id.vehicleBackInterior);
        vehicleTrunk = view.findViewById(R.id.vehicleTrunk);
        Button next = view.findViewById(R.id.nextVehicleRegistration);
        Button back = view.findViewById(R.id.backVehicleRegistration);
        interior = view.findViewById(R.id.interiorVehicleRegistration);
        exterior = view.findViewById(R.id.exteriorVehicleRegistration);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        final DatePickerDialog[] picker = new DatePickerDialog[1];


        registrationStart.setInputType(0);
        registrationEnd.setInputType(0);

//       date click listeners
        registrationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(), R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                registrationStart.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    start = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), start.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        registrationEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(), R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                registrationEnd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    end = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), start.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, year, month, day);
                picker[0].show();
            }
        });


        vehicleFrontInterior.setVisibility(View.GONE);
        vehicleBackInterior.setVisibility(View.GONE);
        vehicleTrunk.setVisibility(View.GONE);


//        images click listeners
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
        vehicleFrontInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleFrontInterior";


            }
        });
        vehicleBackInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleBackInterior";


            }
        });
        vehicleTrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage(getContext());
                selector= "vehicleTrunk";


            }
        });


        interior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    vehicleRightSide.setVisibility(View.GONE);
                    vehicleLeftSide.setVisibility(View.GONE);
                    vehicleFrontSide.setVisibility(View.GONE);
                    vehicleBackSide.setVisibility(View.GONE);
                    vehicleFrontInterior.setVisibility(View.VISIBLE);
                    vehicleBackInterior.setVisibility(View.VISIBLE);
                    vehicleTrunk.setVisibility(View.VISIBLE);
                    interior.setBackground(getResources().getDrawable(R.drawable.button_shadow));
                    interior.setTextColor(getResources().getColor(R.color.colorBackground));
                    exterior.setBackground(getResources().getDrawable(R.drawable.button_shadow_white));
                    exterior.setTextColor(getResources().getColor(R.color.colorPrimary));




            }
        });

        exterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    vehicleRightSide.setVisibility(View.VISIBLE);
                    vehicleLeftSide.setVisibility(View.VISIBLE);
                    vehicleFrontSide.setVisibility(View.VISIBLE);
                    vehicleBackSide.setVisibility(View.VISIBLE);
                    vehicleFrontInterior.setVisibility(View.GONE);
                    vehicleBackInterior.setVisibility(View.GONE);
                    vehicleTrunk.setVisibility(View.GONE);
                    exterior.setBackground(getResources().getDrawable(R.drawable.button_shadow));
                    exterior.setTextColor(getResources().getColor(R.color.colorBackground));
                    interior.setBackground(getResources().getDrawable(R.drawable.button_shadow_white));
                    interior.setTextColor(getResources().getColor(R.color.colorPrimary));



            }
        });

//        next click listener
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCarInfo()) {

                    mNavController.navigate(actionNavigateToDamageFromRegistration);
                    Glide.with(getContext()).clear(vehicleRightSide);
                    Glide.with(getContext()).clear(vehicleLeftSide);
                    Glide.with(getContext()).clear(vehicleFrontSide);
                    Glide.with(getContext()).clear(vehicleBackSide);
                    Glide.with(getContext()).clear(vehicleFrontInterior);
                    Glide.with(getContext()).clear(vehicleBackInterior);
                    Glide.with(getContext()).clear(vehicleTrunk);
                    vehicleRightSide = null;
                    vehicleLeftSide = null;
                    vehicleFrontSide = null;
                    vehicleBackSide = null;
                    vehicleFrontInterior = null;
                    vehicleBackInterior = null;
                    vehicleTrunk = null;
                    registrationType = null;
                    engineSize = null;
                    registrationStart = null;
                    registrationEnd = null;
                    interior = null;
                    exterior = null;
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.popBackStack();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpRegistration";
//        Toast.makeText(getContext(), vehicle.getModel(), Toast.LENGTH_SHORT).show();
    }

//    select image implementation
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
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto,1);

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
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleRightSide);//
//                                vehicleRightSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
                                carPhotos.setPhotoRightSide(selectedImage);
                            break;
                            case "vehicleLeftSide":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleLeftSide);//
//                                vehicleLeftSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
                                carPhotos.setPhotoLeftSide(selectedImage);
                                break;
                            case "vehicleFrontSide":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleFrontSide);//
//                                vehicleFrontSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
                                carPhotos.setPhotoFrontSide(selectedImage);
                                break;
                            case "vehicleBackSide":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleBackSide);//
//                                vehicleBackSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");
                                carPhotos.setPhotoBackSide(selectedImage);
                                break;
                            case "vehicleFrontInterior":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleFrontInterior);//
//                                vehicleFrontInterior.setImageBitmap(selectedImage);
                                vehicle.setVehicleFrontInterior(vehicle.getPlateNumber()+"frontInterior");
                                carPhotos.setVehicleFrontInterior(selectedImage);
                                break;
                            case "vehicleBackInterior":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleBackInterior);//
//                                vehicleBackInterior.setImageBitmap(selectedImage);
                                vehicle.setVehicleBackInterior(vehicle.getPlateNumber()+"backInterior");
                                carPhotos.setVehicleBackInterior(selectedImage);
                                break;
                            case "vehicleTrunk":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(vehicleTrunk);//
//                                vehicleTrunk.setImageBitmap(selectedImage);
                                vehicle.setVehicleTrunk(vehicle.getPlateNumber()+"trunk");
                                carPhotos.setVehicleTrunk(selectedImage);
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

//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                Log.d("GET IMAGE", "the picture path is: "+ picturePath);
                                Bitmap bitmap = null;
                                try {
                                  bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                switch (selector) {
                                    case "vehicleRightSide":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleRightSide);//                                        vehicleRightSide.setImageURI(selectedImage);
                                        vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
                                        carPhotos.setPhotoRightSide(bitmap);
                                        break;
                                    case "vehicleLeftSide":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleLeftSide);//
                                        vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
                                        carPhotos.setPhotoLeftSide(bitmap);
                                        break;
                                    case "vehicleFrontSide":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleFrontSide);//
//                                        vehicleFrontSide.setImageURI(selectedImage);
                                        vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
                                        carPhotos.setPhotoFrontSide(bitmap);
                                        break;
                                    case "vehicleBackSide":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleBackSide);//
//                                        vehicleBackSide.setImageURI(selectedImage);
                                        vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");
                                        carPhotos.setPhotoBackSide(bitmap);
                                        break;
                                    case "vehicleFrontInterior":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleFrontInterior);//
//                                        vehicleFrontInterior.setImageURI(selectedImage);
                                        vehicle.setVehicleFrontInterior(vehicle.getPlateNumber()+"frontInterior");
                                        carPhotos.setVehicleFrontInterior(bitmap);
                                        break;
                                    case "vehicleBackInterior":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleBackInterior);//
//                                        vehicleBackInterior.setImageURI(selectedImage);
                                        vehicle.setVehicleBackInterior(vehicle.getPlateNumber()+"backInterior");
                                        carPhotos.setVehicleBackInterior(bitmap);
                                        break;
                                    case "vehicleTrunk":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(vehicleTrunk);//
//                                        vehicleTrunk.setImageURI(selectedImage);
                                        vehicle.setVehicleTrunk(vehicle.getPlateNumber()+"trunk");
                                        carPhotos.setVehicleTrunk(bitmap);
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

    @Override
    public void onStop() {
        super.onStop();

    }

//    get car information
    private boolean getCarInfo() {

        String registration = registrationType.getText().toString();
        String motorSize = engineSize.getText().toString();
        String startR = registrationStart.getText().toString();
        String endR = registrationEnd.getText().toString();
        String leftSide = vehicle.getPhotoLeftSide();
        String rightSide = vehicle.getPhotoRightSide();
        String frontSide = vehicle.getPhotoFrontSide();
        String backSide = vehicle.getPhotoBackSide();
        String frontInterior = vehicle.getVehicleFrontInterior();
        String backInterior  = vehicle.getVehicleBackInterior();
        String trunk = vehicle.getVehicleTrunk();

        if ((!registration.isEmpty())&& (!startR.isEmpty())&&
                (!endR.isEmpty())&& (leftSide != null)&&
                (rightSide != null)&& (frontSide != null) &&
                (backSide != null) && (frontInterior != null)
                && (backInterior != null)
                && (trunk != null))  {

            vehicle.setRegistrationType(registration);
            vehicle.setMotorSize(motorSize);
            vehicle.setRegistrationStart(start);
            vehicle.setRegistrationEnd(end);
            vehicle.setPhotoLeftSide(leftSide);
            vehicle.setPhotoRightSide(rightSide);
            vehicle.setPhotoFrontSide(frontSide);
            vehicle.setPhotoBackSide(backSide);
            vehicle.setVehicleFrontInterior(frontInterior);
            vehicle.setVehicleBackInterior(backInterior);
            vehicle.setVehicleTrunk(trunk);


            return true;
        }
        else {
            Toast.makeText(getContext(), "Please fill the missing fields" , Toast.LENGTH_SHORT).show();
            return false;
        }

    }



}
