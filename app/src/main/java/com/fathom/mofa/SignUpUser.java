package com.fathom.mofa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fathom.mofa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpUser extends Fragment {

    public SignUpUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_user, container, false);
    }
}
