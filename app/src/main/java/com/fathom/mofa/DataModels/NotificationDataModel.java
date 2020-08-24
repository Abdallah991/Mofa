package com.fathom.mofa.DataModels;

import java.util.Comparator;
import java.util.Date;

public class NotificationDataModel {

    private String notificationContent;
    private Date notificationDate;
    private String notificationType;

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    // per year of make field
    public static Comparator<NotificationDataModel> dateComparator = new Comparator<NotificationDataModel>() {
        @Override
        public int compare(NotificationDataModel jc1, NotificationDataModel jc2) {
            return (int) (jc1.getNotificationDate().compareTo(jc2.getNotificationDate()));
        }
    };
}
