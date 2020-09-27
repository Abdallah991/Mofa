package com.fathom.mofa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverSetUp extends Fragment {

    private NavController mNavController;
    private TextInputEditText driverName;
    private TextInputEditText driverID;
    private TextInputEditText addressLine1;
    private TextInputEditText addressLine2;
    private TextInputEditText nationality;
    private TextInputEditText phoneNumber;
    private TextInputEditText issueDate;
    private TextInputEditText expiryDate;
    private ImageView frontLicense;
    private ImageView backLicense;
    private Button done;
    private String selector;
    private String driverNameText;
    private String driverIDText;
    private String addressLine1Text;
    private String addressLine2Text;
    private String nationalityText;
    private String phoneNumberText;
    private Date issueDateValue;
    private Date expiryDateValue;
    private String frontLicenseName;
    private String backLicenseName;
    private DriverDataModel driver = new DriverDataModel();
    private DriverViewModel model;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;
    private final String TAG = "DRIVER SET UP";
    private ProgressDialog progressDialog;





    public DriverSetUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_set_up, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        driverName = view.findViewById(R.id.driverName);
        driverID = view.findViewById(R.id.driverID);
        addressLine1 = view.findViewById(R.id.addressLine1);
        addressLine2 = view.findViewById(R.id.addressLine2);
        nationality = view.findViewById(R.id.nationality);
        phoneNumber = view.findViewById(R.id.driverPhoneNumber);
        issueDate = view.findViewById(R.id.issueDate);
        expiryDate = view.findViewById(R.id.expiryDate);
        frontLicense = view.findViewById(R.id.frontLicense);
        backLicense = view.findViewById(R.id.backLicense);
        done = view.findViewById(R.id.doneDriverSetUp);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.setCanceledOnTouchOutside(false);
        final DatePickerDialog[] picker = new DatePickerDialog[1];
        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        model = new ViewModelProvider(requireActivity()).get(DriverViewModel.class);
        model.initDrivers();
        model.getDrivers().observe(getViewLifecycleOwner(), new Observer<List<DriverDataModel>>() {
            @Override
            public void onChanged(List<DriverDataModel> drivers) {

            }
        });

        issueDate.setInputType(0);
        expiryDate.setInputType(0);
        issueDate.setOnClickListener(new View.OnClickListener() {
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
                                issueDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    issueDateValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), issueDateValue.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        expiryDate.setOnClickListener(new View.OnClickListener() {
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
                                expiryDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                                try {
                                    expiryDateValue = input.parse(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                    Toast.makeText(getContext(), expiryDateValue.toString() +" ", Toast.LENGTH_SHORT).show();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, year, month, day);
                picker[0].show();
            }
        });

        frontLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());
                selector= "frontLicense";

            }
        });

        backLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());
                selector= "backLicense";

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
                String nineDigits = getResources().getString(R.string.nine_digits);
                String eightDigits = getResources().getString(R.string.eight_digits);
                String fillMissingFields = getResources().getString(R.string.fill_missing_feilds);
                if(phoneNumberText.length() == 8) {
                    if (driverIDText.length() == 9) {
                        Toast.makeText(getContext(), fillMissingFields +phoneNumberText.length()+ driverIDText.length(), Toast.LENGTH_SHORT).show();
                        if ((!driverNameText.isEmpty()) && (!driverIDText.isEmpty()) &&
//                (!addressLine1Text.isEmpty()) && (!addressLine2Text.isEmpty()) &&
                                (!nationalityText.isEmpty()) && (!phoneNumberText.isEmpty()) &&
                                (!issueDateValue.toString().isEmpty()) && (!expiryDateValue.toString().isEmpty()) &&
                                (frontLicenseName != null) && (backLicenseName != null)
                        ) {
                            progressDialog.show();
                            uploadDriver();
                            uploadFrontDriverLicense();
                            uploadBackDriverLicense();
                            mNavController.navigate(R.id.home);
                        } else {
                            Toast.makeText(getContext(), fillMissingFields, Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getContext(), nineDigits, Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), eightDigits, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "driverSetUp";

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
                            case "frontLicense":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(frontLicense);//
//                                frontLicense.setImageBitmap(selectedImage);
                                frontLicense.setScaleType(ImageView.ScaleType.FIT_XY);
                                frontLicenseName = selectedImage.toString();
                                break;

                            case "backLicense":
                                Glide.with(getContext())
                                        .load(selectedImage)
                                        .centerCrop()
                                        .into(backLicense);//
//                                backLicense.setImageBitmap(selectedImage);
                                backLicense.setScaleType(ImageView.ScaleType.FIT_XY);
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
                                                .into(frontLicense);//
//                                        frontLicense.setImageURI(selectedImage);
                                        frontLicense.setScaleType(ImageView.ScaleType.FIT_XY);
                                        frontLicenseName = selectedImage.toString();

                                        break;
                                    case "backLicense":
                                        Glide.with(getContext())
                                                .load(selectedImage)
                                                .centerCrop()
                                                .into(backLicense);//
//                                        backLicense.setImageURI(selectedImage);
                                        backLicense.setScaleType(ImageView.ScaleType.FIT_XY);
                                        backLicenseName = selectedImage.toString();                                        break;
                                }
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void checkFields () {
        driverNameText = driverName.getText().toString();
        driverIDText = driverID.getText().toString();
        addressLine1Text = Objects.requireNonNull(addressLine1.getText()).toString();
        addressLine2Text = Objects.requireNonNull(addressLine2.getText()).toString();
        nationalityText = nationality.getText().toString();
        phoneNumberText = phoneNumber.getText().toString();
        driverNameText = driverName.getText().toString();

    }

    private void uploadDriver () {
        driver.setDriverName(driverNameText);
        driver.setDriverID(driverIDText);
        driver.setAddressLine1(addressLine1Text);
        driver.setAddressLine2(addressLine2Text);
        driver.setNationality(nationalityText);
        driver.setPhoneNumber(phoneNumberText);
        driver.setIssueDate(issueDateValue);
        driver.setExpiryDate(expiryDateValue);
        driver.setFrontLicense(driverIDText+"front");
        driver.setBackLicense(driverIDText+"back");
        driver.setBusy(false);

        db.collection("Drivers")
                .document(driver.getDriverID()).set(driver);
        addDriverToViewModel();

    }

    private void uploadFrontDriverLicense(){
        frontImageRef = storageRef.child(driverIDText+"front");
        frontLicense.setDrawingCacheEnabled(true);
        frontLicense.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) frontLicense.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
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

    private void uploadBackDriverLicense(){
        backImageRef = storageRef.child(driverIDText+"back");
        backLicense.setDrawingCacheEnabled(true);
        backLicense.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) backLicense.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
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


    private void addDriverToViewModel() {
        model.addDriver(driver);
    }

}
