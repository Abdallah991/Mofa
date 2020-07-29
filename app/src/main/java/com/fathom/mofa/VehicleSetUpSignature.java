package com.fathom.mofa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fathom.mofa.ServicesAndRepos.VehicleRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.Date;

import java.io.ByteArrayOutputStream;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleRegistration.carPhotos;
import static com.fathom.mofa.VehicleSetUp.vehicle;
import static com.fathom.mofa.VehicleSetUpDamageReport.damageReport;
import static com.fathom.mofa.VehicleSetUpRentalInfo.rentalInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpSignature extends Fragment {

    private NavController mNavController;
    private Button rentalSignature;
    private Button mofaSignature;
    private Button saveSignature;
    private Button cancelSignature;
    private Button clearSignature;
    private LinearLayout signatureLayout;
    private int signatureSelector;
    private Button done;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference frontImageRef;
    private StorageReference backImageRef;
    private StorageReference leftImageRef;
    private StorageReference rightImageRef;
    private final String TAG = "VEHICLE SET UP";
    private ProgressDialog progressDialog;
    private VehicleRepository mVehicleRepository;
    public static boolean doneUploading = false;

    public VehicleSetUpSignature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_set_up_signature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rentalSignature = view.findViewById(R.id.rentalSignature);
        mofaSignature = view.findViewById(R.id.mofaSignature);
        done = view.findViewById(R.id.doneVehicleSetUp);
        // Dialog components
        final Dialog dialog = new Dialog(getContext());
        final CaptureSignatureView mSig = new CaptureSignatureView(getContext(), null);
        dialog.setContentView(R.layout.signature);
        dialog.setTitle("Signature");
        saveSignature = dialog.findViewById(R.id.save);
        cancelSignature = dialog.findViewById(R.id.cancel);
        clearSignature = dialog.findViewById(R.id.clear);
        signatureLayout = dialog.findViewById(R.id.signature2);

        // setting up Progress Dialog and Storage
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        // adding the signature functionality to the signatureLayout
        signatureLayout.addView(mSig, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        // filling up some data for Vehicle Object
        vehicle.setRentalInfo(vehicle.getPlateNumber());
        vehicle.setCarName(vehicle.getManufacturer()+ " "+vehicle.getModel()+ " "+vehicle.getPlateNumber());
        vehicle.setPhotoLeftSide(vehicle.getPlateNumber()+"left");
        vehicle.setPhotoRightSide(vehicle.getPlateNumber()+"right");
        vehicle.setPhotoFrontSide(vehicle.getPlateNumber()+"front");
        vehicle.setPhotoBackSide(vehicle.getPlateNumber()+"back");

        rentalSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureSelector = 0;
                dialog.show();

            }
        });

        mofaSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureSelector = 1;
                dialog.show();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadVehicleInfo();
                uploadDamageReportOfVehicle();
                uploadRentalInfoOfVehicle();
                uploadVehicleLeftSide();
                uploadVehicleRightSide();
                uploadVehicleFrontSide();
                uploadVehicleBackSide();



                mNavController.navigate(R.id.home);

            }
        });

        cancelSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        clearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSig.ClearCanvas();
            }
        });

        saveSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signatureSelector == 0) {
                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    rentalSignature.setBackground(d);
                    mSig.ClearCanvas();
                    dialog.dismiss();
                }
                if (signatureSelector == 1) {

                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    mofaSignature.setBackground(d);
                    mSig.ClearCanvas();
                    dialog.dismiss();
                }

            }
        });
    }

    private void uploadVehicleInfo() {
        progressDialog.show();
        db.collection("Vehicles")
                .document(vehicle.getPlateNumber()).set(vehicle);
//        mVehicleRepository.uploadVehicle(vehicle);
    }

    private void uploadRentalInfoOfVehicle() {
        db.collection("Rental Information")
                .document(rentalInfo.getCarId()).set(rentalInfo);

    }

    private void uploadDamageReportOfVehicle() {
        db.collection("Damage Reports")
                .document().set(damageReport);

    }

    private void uploadVehicleRightSide() {
//        mVehicleRepository.uploadVehicleRightSide(vehicle.getPhotoRightSide(), carPhotos.getPhotoRightSide());
        rightImageRef = storageRef.child(vehicle.getPhotoRightSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoRightSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = rightImageRef.putBytes(data);
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User right image failed to upload.");
                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User right image uploaded.");
                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleLeftSide() {
        leftImageRef = storageRef.child(vehicle.getPhotoLeftSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoLeftSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = leftImageRef.putBytes(data);
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User left image failed to upload.");
                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User left image uploaded.");
                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleFrontSide() {
        frontImageRef = storageRef.child(vehicle.getPhotoFrontSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoFrontSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = frontImageRef.putBytes(data);
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User front image failed to upload.");
                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User front image uploaded.");
                progressDialog.dismiss();
            }
        });

    }

    private void uploadVehicleBackSide() {
        backImageRef = storageRef.child(vehicle.getPhotoBackSide());
//        frontLicense.setDrawingCacheEnabled(true);
//        frontLicense.buildDrawingCache();
        Bitmap bitmap = carPhotos.getPhotoBackSide();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = backImageRef.putBytes(data);
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User back image failed to upload.");
                progressDialog.dismiss();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User back image uploaded.");
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleSetUpSignature";
    }


}
