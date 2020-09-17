package com.fathom.mofa.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.fathom.mofa.DataModels.NotificationDataModel;
import com.fathom.mofa.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.fathom.mofa.LoginActivity.USER;
import static com.fathom.mofa.SplashActivity.Language;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationRecordHolder> {
    private String TAG = "Notification Adapter";
    // Declare variables
    private ArrayList<NotificationDataModel> notification;
    private Context mContext;
    // The navigation action Id constant
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

    public NotificationAdapter(ArrayList<NotificationDataModel> notificationDataModels,
                                 Context context) {
        notification = notificationDataModels;
        mContext = context;
    }

    @NonNull
    @Override
    public NotificationRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreateViewHolder: Called.");
        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item,parent, false);

        return new NotificationRecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecordHolder holder, int position) {

        holder.notificationContent.setText(notification.get(position).getNotificationContent());
        Date date = notification.get(position).getNotificationDate();
        String dateValue = formatter.format(date);
        holder.notificationTime.setText(dateValue);
        switch (notification.get(position).getNotificationType()) {
            case "Handover":
            case "Release":
                holder.notificationIcon.setImageResource(R.drawable.handover_notification);
                break;
            case "Damage":
                holder.notificationIcon.setImageResource(R.drawable.damage_notification);
                break;
            case "Retrieval":
            case "Set Up":
                holder.notificationIcon.setImageResource(R.drawable.return_notification);
                break;
            case "Lease":
                holder.notificationIcon.setImageResource(R.drawable.record_notification);
                break;

        }

        if(Language.equals("English")) {
            holder.notificationTime.setGravity(Gravity.START);
            holder.notificationContent.setGravity(Gravity.END);
        } else {
            holder.notificationTime.setGravity(Gravity.END);
            holder.notificationContent.setGravity(Gravity.START);
        }



    }

    @Override
    public int getItemCount() {
        return notification.size();
    }

    public void sortByDate(ArrayList<NotificationDataModel> notifications) {

        notification = notifications;
        notifyDataSetChanged();
    }

    public class NotificationRecordHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView notificationContent;
        TextView notificationTime;
        ImageView notificationIcon;

        public NotificationRecordHolder(View itemView) {
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.notificationCard);
            notificationContent = itemView.findViewById(R.id.notificationContent);
            notificationTime = itemView.findViewById(R.id.notificationTiming);
            notificationIcon = itemView.findViewById(R.id.notificationImage);
        }
    }
}
