package com.fathom.mofa;

import com.fathom.mofa.DataModels.NotificationDataModel;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationSorter {

    ArrayList<NotificationDataModel> NotificationSorted = new ArrayList<>();
    public NotificationSorter(ArrayList<NotificationDataModel> jobCandidate) {
        this.NotificationSorted = jobCandidate;
    }

    public ArrayList<NotificationDataModel> getSortedNotificationByDate() {
        Collections.sort(NotificationSorted, NotificationDataModel.dateComparator);
        return NotificationSorted;
    }
}
