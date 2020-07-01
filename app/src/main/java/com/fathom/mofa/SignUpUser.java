package com.fathom.mofa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import static com.fathom.mofa.MainActivity.FRAGMENT;

import com.fathom.mofa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpUser extends Fragment {

    private NavController navController;
    private ImageView home;
    private ImageView notification;


    public SignUpUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(actionNavigateToHome);
//
//            }
//        });
//
//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(actionNavigateToNotification);
//
//            }
//        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "signUpUser";
    }
}
