package com.fathom.mofa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;


public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private final String TAG = "SIGN IN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String USER = "USER";

    private TextView loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading...");

        // if the user logged in, sign them up
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            SharedPreferences pref = getSharedPreferences(USER, 0); // 0 - for private mode
            String lang = pref.getString("Lang", "");
            switch (lang){
                case "Arabic":
                    setApplicationLocale("ar");
                    Intent intent = new Intent(getApplicationContext(),
                            SplashActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case "English":
                    setApplicationLocale("en");
                    break;
            }
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog.show();

               signIn();

           }
       });
    }

    private void signIn() {

        String username = userName.getText().toString();
        String userPassword = password.getText().toString();

        SharedPreferences userPrefs = getSharedPreferences(USER, 0);
        userPrefs.edit().putString("Email", username).apply();


        if (username.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "One or more fields are empty.",
                    Toast.LENGTH_SHORT).show();

            return;
        }
        mAuth.signInWithEmailAndPassword(username, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name = user.getDisplayName();
//                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    }
                });

    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
}
