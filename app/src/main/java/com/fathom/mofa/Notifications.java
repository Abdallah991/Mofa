package com.fathom.mofa;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fathom.mofa.Adapters.NotificationAdapter;
import com.fathom.mofa.DataModels.NotificationDataModel;
import com.fathom.mofa.ViewModels.NotificationViewModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fathom.mofa.MainActivity.FRAGMENT;



public class Notifications extends Fragment {
    private NavController mNavController;
    private ArrayList<NotificationDataModel> mNotifications = new ArrayList<>();
    private RecyclerView mNotificationsRecycler;
    private NotificationAdapter mNotificationsAdapter;
    private NotificationViewModel mNotificationsViewModel;
    private ProgressDialog progressDialog;

    public Notifications() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNotificationsRecycler = view.findViewById(R.id.notificationRecycler);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Downloading...");

        mNotificationsViewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        mNotificationsViewModel.initNotifications();
        mNotificationsViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<NotificationDataModel>>() {
            @Override
            public void onChanged(List<NotificationDataModel> notificationDataModels) {
                Log.d("TAG", notificationDataModels.size()+" ");
                mNotificationsAdapter.notifyDataSetChanged();
                initRecycler();
            }
        });

        initRecycler();

    }

    @Override
    public void onResume() {
        super.onResume();
        FRAGMENT = "signUpUser";
    }

    private void initRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 2500;
        myHandler = new Handler();
        mNotifications = (ArrayList<NotificationDataModel>) mNotificationsViewModel.getNotifications().getValue();
        mNotificationsAdapter = new NotificationAdapter(mNotifications, getContext());
        progressDialog.show();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNotificationsRecycler.setAdapter(mNotificationsAdapter);
                mNotificationsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                sortNotification();
                progressDialog.dismiss();
            }


        },SPLASH_TIME_OUT);

    }

    private void sortNotification() {
        NotificationSorter sorter = new NotificationSorter(mNotifications);
        mNotifications = new ArrayList<>();
        mNotifications = sorter.getSortedNotificationByDate();
        Collections.reverse(mNotifications);
        mNotificationsAdapter.sortByDate(mNotifications);


    }
}
