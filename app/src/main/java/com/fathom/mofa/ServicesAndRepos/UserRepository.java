package com.fathom.mofa.ServicesAndRepos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.mofa.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    public static String USER_TAG = "USER REPO";
    private ArrayList<UserDataModel> mUsers = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static UserRepository getInstance() {
        if (instance == null) {

            Log.d(USER_TAG, " getting static instance of the user repo.");
            instance = new UserRepository();
        }

        Log.d("MVVM", " returning the existing static instance of the user repo.");

        return instance;
    }

    public MutableLiveData<List<UserDataModel>> getUsers() {

        // calling the webservice task of function
        setUserItems();
        MutableLiveData<List<UserDataModel>> data = new MutableLiveData<>();
        data.setValue(mUsers);

        return data;
    }

    private void setUserItems() {

        Log.d(USER_TAG, " Loading the data is going to start");


        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(USER_TAG, document.getId() + " => " + document.getData());
                                if (mUsers.size() <= task.getResult().size()) {
                                    UserDataModel user = document.toObject(UserDataModel.class);
                                    mUsers.add(user);

                                }
                            }
                        } else {
                            Log.d(USER_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(USER_TAG, " Loading the data is DONE");
        Log.d(USER_TAG, mUsers.size() + "");
    }

}
