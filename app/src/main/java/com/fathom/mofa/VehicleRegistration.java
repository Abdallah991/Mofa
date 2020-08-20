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

import com.fathom.mofa.DataModels.CarPhotosDataModel;

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

    public static CarPhotosDataModel carPhotos = new CarPhotosDataModel();
    private NavController mNavController;
    private AutoCompleteTextView registrationType;
    private AutoCompleteTextView registrationStart;
    private AutoCompleteTextView registrationEnd;
    private ImageView vehicleLeftSide;
    private ImageView vehicleRightSide;
    private ImageView vehicleFrontSide;
    private ImageView vehicleBackSide;
    private Button next;
    private Button back;
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

        registrationType = view.findViewById(R.id.registrationType);
        registrationStart = view.findViewById(R.id.registrationStart);
        registrationEnd = view.findViewById(R.id.registrationEnd);
        vehicleLeftSide = view.findViewById(R.id.vehicleLeftSide);
        vehicleRightSide = view.findViewById(R.id.vehicleRightSide);
        vehicleFrontSide = view.findViewById(R.id.vehicleFrontSide);
        vehicleBackSide = view.findViewById(R.id.vehicleBackSide);
        next = view.findViewById(R.id.nextVehicleRegistration);
        back = view.findViewById(R.id.backVehicleRegistration);
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        final DatePickerDialog[] picker = new DatePickerDialog[1];



        registrationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(),
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
                picker[0] = new DatePickerDialog(getContext(),
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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCarInfo()) {

                    mNavController.navigate(actionNavigateToDamageFromRegistration);
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
                                vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
                                carPhotos.setPhotoRightSide(selectedImage);
                            break;
                            case "vehicleLeftSide":
                                vehicleLeftSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
                                carPhotos.setPhotoLeftSide(selectedImage);
                                break;
                            case "vehicleFrontSide":
                                vehicleFrontSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
                                carPhotos.setPhotoFrontSide(selectedImage);
                                break;
                            case "vehicleBackSide":
                                vehicleBackSide.setImageBitmap(selectedImage);
                                vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");
                                carPhotos.setPhotoBackSide(selectedImage);
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
                                        vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
                                        carPhotos.setPhotoRightSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleLeftSide":
                                        vehicleLeftSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
                                        carPhotos.setPhotoLeftSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleFrontSide":
                                        vehicleFrontSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
                                        carPhotos.setPhotoFrontSide(BitmapFactory.decodeFile(picturePath));
                                        break;
                                    case "vehicleBackSide":
                                        vehicleBackSide.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");
                                        carPhotos.setPhotoBackSide(BitmapFactory.decodeFile(picturePath));
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

    private boolean getCarInfo() {

        String registration = registrationType.getText().toString();
        String startR = registrationStart.getText().toString();
        String endR = registrationEnd.getText().toString();
        String leftSide = vehicle.getPhotoLeftSide();
        String rightSide = vehicle.getPhotoRightSide();
        String frontSide = vehicle.getPhotoFrontSide();
        String backSide = vehicle.getPhotoBackSide();

        if ((!registration.isEmpty())&& (!startR.isEmpty())&&
                (!endR.isEmpty())&& (leftSide != null)&&
                (rightSide != null)&& (frontSide != null) &&
                (backSide != null))  {

            vehicle.setRegistrationType(registration);
            vehicle.setRegistrationStart(start);
            vehicle.setRegistrationEnd(end);
            vehicle.setPhotoLeftSide(leftSide);
            vehicle.setPhotoRightSide(rightSide);
            vehicle.setPhotoFrontSide(frontSide);
            vehicle.setPhotoBackSide(backSide);
            return true;
        }
        else {
            Toast.makeText(getContext(), "Please fill the missing fields" , Toast.LENGTH_SHORT).show();
            return false;

        }

    }



}
