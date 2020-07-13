package com.fathom.mofa;


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
import android.widget.EditText;
import android.widget.Toast;
import static com.fathom.mofa.MainActivity.FRAGMENT;
import com.fathom.mofa.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpUser extends Fragment {

    private NavController navController;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText phoneNumber;
    private EditText userType;
    private Button register;
    private UserDataModel user = new UserDataModel();
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // You can store data in shared Preferences her

                if (isEmailValid(email.getText().toString())
                        && isPasswordValid(password.getText().toString())) {
                    SignUp();
                    uploadUser();
                } else
                {
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
            user.setPassword(password.getText().toString());
            user.setUserType(userType.getText().toString());
            user.setPhoneNumber(phoneNumber.getText().toString());
        }

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        if (password.length() >6) {
            Log.d(TAG, "password is valid.");
            user.setPassword(password);
        }
        return password.length() > 6;
    }
    private void SignUp() {

        String username = email.getText().toString();
        String userPassword = password.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();

        mAuth.createUserWithEmailAndPassword(username, userPassword)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
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
                                                Toast.makeText(getContext(), "You can Login now",
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
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

    }

}
