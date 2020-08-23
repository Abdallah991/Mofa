package com.fathom.mofa.ServicesAndRepos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.fathom.mofa.DataModels.NotificationDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {

    private static NotificationRepository instance;
    public static String NOTIFICATION_TAG = "NOTIFICATION REPO";
    private ArrayList<NotificationDataModel> mNotifications = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static NotificationRepository getInstance() {
        if (instance == null) {

            Log.d(NOTIFICATION_TAG ," getting static instance of the driver repo.");
            instance = new NotificationRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the driver repo.");

        return instance;
    }


    public MutableLiveData<List<NotificationDataModel>> getNotifications() {

        // calling the webservice task of function
        setNotificationItems();
        MutableLiveData<List<NotificationDataModel>> data = new MutableLiveData<>();
        data.setValue(mNotifications);

        return data;
    }

    private  void setNotificationItems () {


        Log.d(NOTIFICATION_TAG," Loading the data is going to start");


        db.collection("Notifications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(NOTIFICATION_TAG, document.getId() + " => " + document.getData());
                                if (mNotifications.size() <= task.getResult().size()) {
                                    NotificationDataModel noti = document.toObject(NotificationDataModel.class);
                                    mNotifications.add(noti);

                                }
                            }
                        } else {
                            Log.d(NOTIFICATION_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(NOTIFICATION_TAG," Loading the data is DONE");
        Log.d(NOTIFICATION_TAG, mNotifications.size()+"");
    }
}
