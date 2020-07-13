package com.fathom.mofa;

import android.app.Dialog;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleSetUpSignature extends Fragment {

    private NavController mNavController;
    private Button done;
    private Button rentalSignature;
    private Button mofaSignature;
    private Button btnSave;
    private Button btnCancel;
    private Button btnClear;
    private LinearLayout signatureLayout;
    private ImageView signature;
    private int signatureSelector;

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

        done = view.findViewById(R.id.doneVehicleSetUp);
        rentalSignature = view.findViewById(R.id.rentalSignature);
        mofaSignature = view.findViewById(R.id.mofaSignature);
        final Dialog dialog = new Dialog(getContext());
        final CaptureSignatureView mSig = new CaptureSignatureView(getContext(), null);
        dialog.setContentView(R.layout.signature);
        dialog.setTitle("Signature");
        btnSave   = dialog.findViewById(R.id.save);
        btnCancel = dialog.findViewById(R.id.cancel);
        btnClear = dialog.findViewById(R.id.clear);
        signatureLayout = dialog.findViewById(R.id.signature2);



        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        signatureLayout.addView(mSig , ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



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
                mNavController.navigate(R.id.home);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSig.ClearCanvas();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signatureSelector == 0) {
                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    rentalSignature.setBackground(d);
                    dialog.dismiss();
                }
                if (signatureSelector == 1) {

                    Bitmap signature = mSig.getBitmap();
                    Drawable d = new BitmapDrawable(getResources(), signature);
                    mofaSignature.setBackground(d);
                    dialog.dismiss();
                }

            }
        });
    }


}
