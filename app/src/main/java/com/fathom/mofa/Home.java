package com.fathom.mofa;

import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.DataModels.VehicleDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.fathom.mofa.Adapters.VehicleRecordsAdapter.vehicleRecordDashboard;
import static com.fathom.mofa.LoginActivity.USER;
import static com.fathom.mofa.MainActivity.FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ImageView dashboard;
    private ImageView vehicleSetUp;
    private ImageView driverSetUp;
    private ImageView vehicleRecord;
    private TextView vehicleSetUpText;
    private TextView driverSetUpText;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "HOME";
    private NavController mNavController;





    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboard = view.findViewById(R.id.dashboardButton);
        vehicleSetUp = view.findViewById(R.id.vehicleSetUpButton);
        vehicleRecord = view.findViewById(R.id.vehicleRecordButton);
        driverSetUp = view.findViewById(R.id.driverSetUpButton);
        vehicleSetUpText = view.findViewById(R.id.vehicleSetUpText);
        driverSetUpText = view.findViewById(R.id.driverSetUpText);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        vehicleSetUp.setVisibility(View.GONE);
        driverSetUp.setVisibility(View.GONE);
        driverSetUpText.setVisibility(View.GONE);
        vehicleSetUpText.setVisibility(View.GONE);

        // setting up navigation from home
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_home_to_dashboard);
            }
        });

        vehicleSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.vehicleSetUp);
            }
        });

        vehicleRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_home_to_vehicleDashboard);
            }
        });

        driverSetUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.driverSetUp);
            }
        });

        getUserInfo();



    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "home";
    }

    private void getUserInfo() {

        final SharedPreferences pref = getActivity().getSharedPreferences(USER, 0); // 0 - for private mode
        String email = pref.getString("Email", "");

        db.collection("Users").document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                UserDataModel user = document.toObject(UserDataModel.class);
                                String userName = user.getFirstName()+ " "+ user.getLastName();
                                pref.edit().putString("userName", userName).apply();

                                if (user.getUserType().equals("Admin") || user.getUserType().equals("مشرف")){
//                                    isAdmin = true;
                                    vehicleSetUp.setVisibility(View.VISIBLE);
                                    driverSetUp.setVisibility(View.VISIBLE);
                                    driverSetUpText.setVisibility(View.VISIBLE);
                                    vehicleSetUpText.setVisibility(View.VISIBLE);

                                } else
                                {
                                    vehicleSetUp.setVisibility(View.GONE);
                                    driverSetUp.setVisibility(View.GONE);
                                    driverSetUpText.setVisibility(View.GONE);
                                    vehicleSetUpText.setVisibility(View.GONE);
                                }

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }


                });

        Log.d(TAG, " Loading the data is DONE");

    }


}
