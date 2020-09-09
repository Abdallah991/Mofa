package com.fathom.mofa;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.fathom.mofa.MainActivity.FRAGMENT;
import static com.fathom.mofa.MainActivity.showIcon;
import static com.fathom.mofa.VehicleRecord.vehicleRecord;

import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.ViewModels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;


public class SignUpUser extends Fragment {

    private NavController navController;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText phoneNumber;
    private Spinner userType;
    private Button register;
    private UserDataModel user = new UserDataModel();
    private UserViewModel model;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private final String TAG = "SIGN UP";
    public static final String USER = "USER";


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

        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        userType = view.findViewById(R.id.userType);
        register = view.findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");

        // setting up user types spinner
        String[] userTypes = getResources().getStringArray(R.array.userType);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, userTypes) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.appGrey));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                }
                return view;
            }
        };
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        userType.setAdapter(typeAdapter);

        model = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        model.initUsers();
        model.getUsers().observe(getViewLifecycleOwner(), new Observer<List<UserDataModel>>() {
            @Override
            public void onChanged(List<UserDataModel> users) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // You can store data in shared Preferences her

                if (isEmailValid(email.getText().toString())
                        && isPasswordValid(password.getText().toString())
                        && isUserTypeValid()) {
                    progressDialog.show();
                    SignUp();
                    uploadUser();
                    showIcon();
                } else {
                    Toast.makeText(getContext(), "Email And/or password are invalid",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "signUpUser";
    }

    private boolean isEmailValid(String email) {

        if (email.contains("@")) {
            Log.d(TAG, "Email is valid.");
            user.setEmail(email);
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setPhoneNumber(phoneNumber.getText().toString());

        }

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        if (password.length() > 6) {
            Log.d(TAG, "password is valid.");
            user.setPassword(password);
        }
        return password.length() > 6;
    }

    private boolean isUserTypeValid() {
        if (!userType.getSelectedItem().toString().equals("User Type")) {
            user.setUserType(userType.getSelectedItem().toString());
            return true;

        } else {
            Toast.makeText(getContext(), "Select User Type", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SignUp() {

        String username = email.getText().toString();
        String userPassword = password.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();

        mAuth.createUserWithEmailAndPassword(username, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName.getText().toString())
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                navController.navigate(R.id.action_signUpUser_to_home);
                                                progressDialog.dismiss();


                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }

    private void uploadUser() {
        // Create a new user with a first and last name
        Log.d(TAG, "uploading user starting.");
        db.collection("Users")
                .document(user.getEmail()).set(user);
        Log.d(TAG, "uploading user done.");
        addUserToViewModel();

    }

    private void addUserToViewModel() {
        model.addUser(user);
    }

}
