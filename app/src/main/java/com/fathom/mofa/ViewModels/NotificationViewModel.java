package com.fathom.mofa.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.mofa.DataModels.NotificationDataModel;
import com.fathom.mofa.ServicesAndRepos.NotificationRepository;

import java.util.List;

import static com.fathom.mofa.ServicesAndRepos.NotificationRepository.NOTIFICATION_TAG;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<List<NotificationDataModel>> mNotifications;
    private NotificationRepository mRepository;

    public void initNotifications() {

        Log.d(NOTIFICATION_TAG, " init in ViewModel called.");
        if (mNotifications != null) {
            Log.d(NOTIFICATION_TAG, "Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d(NOTIFICATION_TAG, " Mutable data is empty and going to be loaded");

        mRepository = NotificationRepository.getInstance();
        mNotifications = mRepository.getNotifications();
    }

    public LiveData<List<NotificationDataModel>> getNotifications() {
        return mNotifications;
    }
}
