package com.fathom.mofa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fathom.mofa.DataModels.DriverDataModel;
import com.fathom.mofa.ViewModels.DriverViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class EditDriver extends Fragment {

//    Text Inputs
    private TextInputEditText driverNameEdit;
    private TextInputEditText driverIDEdit;
    private TextInputEditText area;
    private TextInputEditText addressLineEdit;
    private TextInputEditText nationalityEdit;
    private TextInputEditText phoneNumberEdit;
    private TextInputEditText issueDateEdit;
    private TextInputEditText expiryDateEdit;
//    date values
    private Date issueDateValue = new Date();
    private Date expiryDateValue = new Date();
//    Text Input Values
    private String driverNameText;
    private String driverIDText;
    private String areaText;
    private String addressLineText;
    private String nationalityText;
    private String phoneNumberText;
//    Image values
    private ImageView frontLicenseEdit;
    private ImageView backLicenseEdit;
    private String selector;
    private String frontLicenseName;
    private String backLicenseName;
//      action buttons
    private Button doneEdit;
    private Button editDriver;
    private Button backEdit;
    private NavController mNavController;
//    ViewModel and dataModel
    private DriverViewModel mDriverRecordViewModel;
    private DriverDataModel driver;
    private DriverDataModel driverOld;
//    controllers
    private Boolean editMode = false;
    private ProgressDialog progressDialog;
//    backend initialization
private FirebaseFirestore db = FirebaseFirestore.getInstance();
private StorageReference storageRef;
//Tags
private final String TAG = "DRIVER EDIT";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_driver, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        input fields
        driverNameEdit = view.findViewById(R.id.driverNameِEdit);
        driverIDEdit = view.findViewById(R.id.driverIDEdit);
        area = view.findViewById(R.id.area);
        addressLineEdit = view.findViewById(R.id.addressLineEdit);
        nationalityEdit = view.findViewById(R.id.nationalityEdit);
        phoneNumberEdit = view.findViewById(R.id.driverPhoneNumberEdit);
        issueDateEdit = view.findViewById(R.id.issueDatEdit);
        expiryDateEdit = view.findViewById(R.id.expiryDateEdit);
//        images
        frontLicenseEdit = view.findViewById(R.id.frontLicenseEdit);
        backLicenseEdit = view.findViewById(R.id.backLicenseEdit);
//        action buttons
        doneEdit = view.findViewById(R.id.doneEdit);
        editDriver = view.findViewById(R.id.editDriver);
        backEdit = view.findViewById(R.id.backEditDriver);

//          setting up navigation
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

//        Storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


//        progress dialog set up
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);


//          getting the selected driver from the model
        mDriverRecordViewModel = new ViewModelProvider(requireActivity()).get(DriverViewModel.class);
        driver = mDriverRecordViewModel.driver;
        driverOld = mDriverRecordViewModel.driver;


//        Setting up the values
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String driverName = driver.getDriverName();
        String driverId = driver.getDriverID();
        String areaValue = driver.getAddressLine1();
        String addressLine = driver.getAddressLine2();
        String nationality = driver.getNationality();
        String phoneNumber = driver.getPhoneNumber();
        String issueDate = formatter.format(driver.getIssueDate());
        String expiryDate = formatter.format(driver.getExpiryDate());
        Bitmap frontLicense = driver.getLicenseFront();
        Bitmap backLicense = driver.getLicenseBack();

        driverNameEdit.setText(driverName);
        driverIDEdit.setText(driverId);
        area.setText(areaValue);
        addressLineEdit.setText(addressLine);
        nationalityEdit.setText(nationality);
        phoneNumberEdit.setText(phoneNumber);
        issueDateEdit.setText(issueDate);
        expiryDateEdit.setText(expiryDate);
//        setting the images
        frontLicenseEdit.setImageBitmap(frontLicense);
        backLicenseEdit.setImageBitmap(backLicense);


//        Locking the edit
       disableEditing();


//       date click listeners
        final DatePickerDialog[] picker = new DatePickerDialog[1];

        issueDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(),R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                issueDateEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    issueDateValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });


        expiryDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker[0] = new DatePickerDialog(getContext(),R.style.DatePickerDialog,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                expiryDateEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    expiryDateValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

//        Images click listeners
        frontLicenseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector= "frontLicense";
                selectImage(getContext());
            }
        });

        backLicenseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector= "backLicense";
                selectImage(getContext());
            }
        });


//          action button click listeners
        backEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigateUp();
            }
        });

        doneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Save was Clicked", Toast.LENGTH_SHORT).show();
                updateDriver();
//                mNavController.navigateUp();


            }
        });

        editDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toggle editing mode3
                if(editMode) {
                    disableEditing();
                    editDriver.setText(R.string.edit);
                    editMode = false;
                    deleteDriver();

                } else {
                    enableEditing();
                    editDriver.setText(R.string.delete);
                    editMode = true;

                }


            }
        });
    }


//    disable editing
    private void disableEditing() {
        driverNameEdit.setFocusable(false);
        driverIDEdit.setFocusable(false);
        area.setFocusable(false);
        addressLineEdit.setFocusable(false);
        nationalityEdit.setFocusable(false);
        phoneNumberEdit.setFocusable(false);
        issueDateEdit.setFocusable(false);
        expiryDateEdit.setFocusable(false);
        editDriver.setBackground(getContext().getResources().getDrawable(R.drawable.button_shadow));

    }
//    enable editing
    private void enableEditing() {
        driverNameEdit.setFocusableInTouchMode(true);
        driverIDEdit.setFocusableInTouchMode(true);
        area.setFocusableInTouchMode(true);
        addressLineEdit.setFocusableInTouchMode(true);
        nationalityEdit.setFocusableInTouchMode(true);
        phoneNumberEdit.setFocusableInTouchMode(true);
        issueDateEdit.setFocusableInTouchMode(true);
        expiryDateEdit.setFocusableInTouchMode(true);
        editDriver.setBackground(getContext().getResources().getDrawable(R.drawable.button_shadow_grey));
    }


//    Editing the driver Images Implementation

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
                            case "frontLicense":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(frontLicenseEdit);//
//                                frontLicense.setImageBitmap(selectedImage);
                                frontLicenseEdit.setScaleType(ImageView.ScaleType.FIT_XY);
                                frontLicenseName = selectedImage.toString();
                                break;

                            case "backLicense":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(backLicenseEdit);//
//                                backLicense.setImageBitmap(selectedImage);
                                backLicenseEdit.setScaleType(ImageView.ScaleType.FIT_XY);
                                backLicenseName = selectedImage.toString();
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
                                    case "frontLicense":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(frontLicenseEdit);//
//                                        frontLicense.setImageURI(selectedImage);
                                        frontLicenseEdit.setScaleType(ImageView.ScaleType.FIT_XY);
                                        frontLicenseName = selectedImage.toString();

                                        break;
                                    case "backLicense":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(backLicenseEdit);//
//                                        backLicense.setImageURI(selectedImage);
                                        backLicenseEdit.setScaleType(ImageView.ScaleType.FIT_XY);
                                        backLicenseName = selectedImage.toString();
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

    private void updateDriver() {
        Toast.makeText(getContext(),"update driver in progress", Toast.LENGTH_SHORT).show();
//       get the values from the fields
        checkFields();
//                  show progress dialog and do upload for the data model and images

        if (editMode) {
            progressDialog.show();
            uploadDriver();
            uploadFrontDriverLicense();
            uploadBackDriverLicense();
//                  navigate home
        }
        mNavController.navigate(R.id.home);


    }

//    check if the field is valid
    private void checkFields() {
        driverNameText = driverNameEdit.getText().toString();
        driverIDText = driverIDEdit.getText().toString();
        areaText = Objects.requireNonNull(area.getText()).toString();
        addressLineText = Objects.requireNonNull(addressLineEdit.getText()).toString();
        nationalityText = nationalityEdit.getText().toString();
        phoneNumberText = phoneNumberEdit.getText().toString();
        driverNameText = driverNameEdit.getText().toString();

    }

//    Backend operation
private void uploadDriver () {

        Date issueDateEdit = issueDateValue.equals(null)? driverOld.getIssueDate(): issueDateValue;
        Date expiryDateEdit = expiryDateValue.equals(null)? driverOld.getIssueDate(): expiryDateValue;
    driver.setDriverName(driverNameText);
    driver.setDriverID(driverIDText);
    driver.setAddressLine1(areaText);
    driver.setAddressLine2(addressLineText);
    driver.setNationality(nationalityText);
    driver.setPhoneNumber(phoneNumberText);
    Log.d("Value is", String.valueOf(issueDateValue));
    Log.d("Value is", String.valueOf(expiryDateValue));
    driver.setIssueDate(issueDateEdit);
    driver.setExpiryDate(expiryDateEdit);
    driver.setFrontLicense(driverIDText+"front");
    driver.setBackLicense(driverIDText+"back");
    driver.setBusy(false);
    driver.setLicenseFront(null);
    driver.setLicenseBack(null);
//    Add The driver to the backend
    db.collection("Drivers")
            .document(driver.getDriverID()).set(driver);
//    Modify the data locally
    modifyDriverToViewModel();

}

//      upload license images
    private void uploadFrontDriverLicense(){
        StorageReference frontImageRef = storageRef.child(driverIDText + "front");
        frontLicenseEdit.setDrawingCacheEnabled(true);
        frontLicenseEdit.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) frontLicenseEdit.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bitmap == null) {
            progressDialog.dismiss();

        }
        else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = frontImageRef.putBytes(data);
            progressDialog.show();
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "User image failed to upload.");
                    progressDialog.dismiss();
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "User image uploaded.");
                    progressDialog.dismiss();
                }
            });
        }

    }

    private void uploadBackDriverLicense(){
        StorageReference backImageRef = storageRef.child(driverIDText + "back");
        backLicenseEdit.setDrawingCacheEnabled(true);
        backLicenseEdit.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) backLicenseEdit.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bitmap == null) {
            progressDialog.dismiss();

        } else {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = backImageRef.putBytes(data);
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User image failed to upload.");

                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User image uploaded.");
                progressDialog.dismiss();
            }
        });

        }
    }

// Modify Driver in View Model & backend
    private void modifyDriverToViewModel() {
        mDriverRecordViewModel.modifyDriver(driverOld, driver);
    }

//    Delete driver from View Model & Backend
    private void deleteDriver() {
        deleteDriverBackend();
        mDriverRecordViewModel.deleteDriver(driverOld);
        mNavController.navigateUp();

    }
//      Delete driver from the backend
    private void deleteDriverBackend() {
        db.collection("Drivers").document(driverOld.getDriverID()).delete();
    }

    @Override
    public void onStop() {
        progressDialog.dismiss();
        super.onStop();
    }
}