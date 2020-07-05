package com.fathom.mofa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;
    private final String TAG = "SIGN IN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               signIn();

           }
       });
    }

    private void signIn() {

        String username = userName.getText().toString();
        String userPassword = password.getText().toString();

//        SharedPreferences userPrefs = getSharedPreferences(USER, 0);
//        userPrefs.edit().putString("Email", username).apply();


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
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
}
