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
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.VehicleAccidentReport.carPhotosRecord;
import static com.fathom.mofa.VehicleRecord.damageReportRecord;
import static com.fathom.mofa.VehicleRecord.vehicleInRecord;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleRecordSignature extends Fragment {

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
    private final String TAG = "VEHICLE RECORD";
    private ProgressDialog progressDialog;
    private Date mDate;
    private SimpleDateFormat formatter;

    public VehicleRecordSignature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehicle_record_signature, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rentalSignature = view.findViewById(R.id.rentalSignatureVehicleRecord);
        mofaSignature = view.findViewById(R.id.mofaSignatureVehicleRecord);
        done = view.findViewById(R.id.doneVehicleRecord);
        // Dialog components
        final Dialog dialog = new Dialog(getContext());
        final CaptureSignatureView mSig = new CaptureSignatureView(getContext(), null);
        dialog.setContentView(R.layout.signature);
        dialog.setTitle("Signature");
        saveSignature = dialog.findViewById(R.id.save);
        cancelSignature = dialog.findViewById(R.id.cancel);
        clearSignature = dialog.findViewById(R.id.clear);
        signatureLayout = dialog.findViewById(R.id.signature2);

        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


        // setting up Progress Dialog and Storage
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        // adding the signature functionality to the signatureLayout
        signatureLayout.addView(mSig, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Filling Missing Information


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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateVehicleStatus();
                uploadVehicleRecord();
                uploadDamageReport();
                uploadVehicleLeftSide();
                uploadVehicleRightSide();
                uploadVehicleFrontSide();
                uploadVehicleBackSide();



                mNavController.navigate(R.id.home);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "vehicleRecordSignature";
    }

    private void updateVehicleStatus() {
        mDate = new Date();
        progressDialog.show();
        switch (vehicleRecord.getCarTransaction()) {
            case "MTD":
                vehicleInRecord.setStatus("Busy");
                vehicleRecord.setStatus("Busy");
                break;
            case "DTM":
                vehicleInRecord.setStatus("Free");
                vehicleRecord.setStatus("Free");
                break;
            case "MTR":
                vehicleInRecord.setStatus("Returned");
                vehicleRecord.setStatus("Returned");
                break;

        }
        vehicleInRecord.setDamageReport(formatter.format(mDate)+vehicleInRecord.getPlateNumber());
        db.collection("Vehicles")
                .document(vehicleInRecord.getPlateNumber()).set(vehicleInRecord);

    }

    private void uploadVehicleRecord() {
        vehicleRecord.setDamageReport(formatter.format(mDate)+vehicleInRecord.getPlateNumber());
        db.collection("VehicleRecords")
                .document(formatter.format(mDate)+vehicleInRecord.getPlateNumber()).set(vehicleRecord);

    }

    private void uploadDamageReport() {
        damageReportRecord.setDamageReportName(formatter.format(mDate)+vehicleInRecord.getPlateNumber());
        db.collection("Damage Reports")
                .document().set(damageReportRecord);

    }

    private void uploadVehicleRightSide() {
        if (vehicleRecord.isCarHasDamage()) {
            rightImageRef = storageRef.child(vehicleRecord.getPhotoRightSide());
            Bitmap bitmap = carPhotosRecord.getPhotoRightSide();
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

    }

    private void uploadVehicleLeftSide() {
        if (vehicleRecord.isCarHasDamage()) {
            leftImageRef = storageRef.child(vehicleRecord.getPhotoLeftSide());
            Bitmap bitmap = carPhotosRecord.getPhotoLeftSide();
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


    }

    private void uploadVehicleFrontSide() {
        if (vehicleRecord.isCarHasDamage()) {
            frontImageRef = storageRef.child(vehicleRecord.getPhotoFrontSide());
            Bitmap bitmap = carPhotosRecord.getPhotoFrontSide();
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

    }

    private void uploadVehicleBackSide() {
        if (vehicleRecord.isCarHasDamage()) {
            backImageRef = storageRef.child(vehicleRecord.getPhotoBackSide());
            Bitmap bitmap = carPhotosRecord.getPhotoBackSide();
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
    }
}