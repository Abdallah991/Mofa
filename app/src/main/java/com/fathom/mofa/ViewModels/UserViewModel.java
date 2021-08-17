package com.fathom.mofa.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fathom.mofa.DataModels.UserDataModel;
import com.fathom.mofa.ServicesAndRepos.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.mofa.ServicesAndRepos.UserRepository.USER_TAG;

public class UserViewModel extends ViewModel {

    private MutableLiveData<List<UserDataModel>> mUsers;
    private UserRepository mRepository;
    private int positionOfItems;

    public void selectUser(List Items, int position) {

        mUsers.setValue(Items);
        positionOfItems = position;
    }

    public int getPositionOfUser() {
        return positionOfItems;
    }

    public void initUsers( ) {

        Log.d(USER_TAG, " init in ViewModel called.");
        if (mUsers != null) {
            Log.d(USER_TAG, "Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d(USER_TAG, " Mutable data is empty and going to be loaded");

        mRepository = UserRepository.getInstance();
        mUsers = mRepository.getUsers();
    }

    public LiveData<List<UserDataModel>> getUsers() {
        return mUsers;
    }

    public void addUser(UserDataModel  user) {
        ArrayList<UserDataModel> USERS = new ArrayList<>();

        USERS.addAll(mUsers.getValue());
        USERS.add(user);

        mUsers.setValue(USERS);

    }
}
