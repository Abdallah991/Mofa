package com.fathom.mofa;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class DriverSetUp extends Fragment {

    private NavController mNavController;


    public DriverSetUp() {
        // Required empty public constructor
    }

    // Code to Override back button
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
//
//        // This callback will only be called when MyFragment is at least Started.
//        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
//            @Override
//            public void handleOnBackPressed() {
//                Toast.makeText(getContext(), "pressed", Toast.LENGTH_SHORT).show();
//                mNavController.navigate(R.id.home);
//
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_set_up, container, false);


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "driverSetUp";
    }



}
