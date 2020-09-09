package com.fathom.mofa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private Button logOut;
    private Button changeLanguage;
    private static ImageView signUp;
    private static ImageView notification;
    private LinearLayout dashboardMenuItem;
    private LinearLayout vehicleRecordMenuItem;
    private LinearLayout driverSetupMenuItem;
    private LinearLayout vehicleSetupMenuItem;
    private int actionNavigateToNotification = R.id.action_home_to_notifications;
    private int actionNavigateToSignUp = R.id.action_home_to_signUpUser;
    private int actionNavigateToHomeFromSignUp = R.id.action_signUpUser_to_home;
    private int actionNavigateToNotificationFromSignUp = R.id.action_signUpUser_to_notifications;
    private int actionNavigateToHomeFromNotification = R.id.action_notifications_to_home;
    private int actionNavigateToSignUpFromVehicleSetUp = R.id.action_vehicleSetUp_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleSetUp = R.id.action_vehicleSetUp_to_notifications;
    private int actionNavigateToNotificationFromVehicleSetUpRegistration = R.id.action_vehicleRegistration_to_notifications;
    private int actionNavigateToSignUpFromVehicleSetUpRegistration = R.id.action_vehicleRegistration_to_signUpUser;
    private int actionNavigateToSignUpFromVehicleSetUpDamage = R.id.action_vehicleSetUpDamageReport_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleSetUpDamage = R.id.action_vehicleSetUpDamageReport_to_notifications;
    private int actionNavigateToNotificationFromVehicleRentalInfo = R.id.action_vehicleSetUpRentalInfo_to_notifications;
    private int actionNavigateToSignUpFromVehicleSetUpRentalInfo = R.id.action_vehicleSetUpRentalInfo_to_signUpUser;
    private int actionNavigateToSignUpFromVehicleSetUpConfirmation = R.id.action_vehicleSetUpConfirmation_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleConfirmation = R.id.action_vehicleSetUpConfirmation_to_notifications;
    private int actionNavigateToNotificationFromDriverSetup = R.id.action_driverSetUp_to_notifications;
    private int actionNavigateToSignUpFromDriverSetUp = R.id.action_driverSetUp_to_signUpUser;
    private int actionNavigateToSignUpFromVehicleRecord = R.id.action_vehicleRecord_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleRecord = R.id.action_vehicleRecord_to_notifications;
    private int actionNavigateToNotificationFromVehicleUtilities = R.id.action_vehicleUtilities_to_notifications;
    private int actionNavigateToSignUpFromVehicleUtilities = R.id.action_vehicleUtilities_to_signUpUser;
    private int actionNavigateToSignUpFromAccidentReport = R.id.action_vehicleAccidentReport_to_signUpUser;
    private int actionNavigateToNotificationFromAccidentReport = R.id.action_vehicleAccidentReport_to_notifications;
    private int actionNavigateToNotificationFromRecordConfirmation = R.id.action_handoverConfirmation_to_notifications;
    private int actionNavigateToSignUpFromRecordConfirmation = R.id.action_handoverConfirmation_to_signUpUser;
    private int actionNavigateToSignUpFromVehicleSetUpSignature = R.id.action_vehicleSetUpSignature_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleSetUpSignature = R.id.action_vehicleSetUpSignature_to_notifications;
    private int actionNavigateToSignUpFromVehicleRecordSignature = R.id.action_vehicleRecordSignature_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleRecordSignature = R.id.action_vehicleRecordSignature_to_notifications;
    private int actionNavigateToNotificationFromVehicleDashboard = R.id.action_vehicleDashboard_to_notifications;
    private int actionNavigateToSignUpFromVehicleDashboard = R.id.action_vehicleDashboard_to_signUpUser;
    private int actionNavigateToNotificationFromVehicleDetails = R.id.action_vehicleDetails_to_notifications;
    private int actionNavigateToSignUpFromVehicleDetails = R.id.action_vehicleDetails_to_signUpUser;
    private int actionNavigateToNotificationFromDashboard = R.id.action_dashboard_to_notifications;
    private int actionNavigateToSignUpFromDashboard = R.id.action_dashboard_to_signUpUser;
    private int actionNavigateToNotificationFromRecordDetails = R.id.action_vehicleRecordDetails_to_notifications;
    private int actionNavigateToSignUpFromRecordDetails= R.id.action_vehicleRecordDetails_to_signUpUser;







    public static String FRAGMENT = "home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.app_bar);
//        View view = findViewById(R.id.app_bar);
        signUp = toolbar.findViewById(R.id.signUpUserIcon);
        notification = toolbar.findViewById(R.id.notificationIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        logOut = findViewById(R.id.logOut);
        changeLanguage = findViewById(R.id.changeLanguage);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        vehicleRecordMenuItem = findViewById(R.id.vehicleRecordMenu);
        dashboardMenuItem = findViewById(R.id.dashboardMenu);
        driverSetupMenuItem = findViewById(R.id.driverSetUpMenu);
        vehicleSetupMenuItem = findViewById(R.id.vehicleSetUpMenu);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        LayoutInflater inflater = getLayoutInflater();
//        View slider = inflater.from(getApplicationContext()).inflate(R.layout.drawer_header, null);
        ViewGroup viewRoot = (ViewGroup) inflater.inflate(R.layout.drawer_header,null);


        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(0.75f);
                toolbar.getChildAt(i).setScaleY(0.75f);
            }
        }

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (FRAGMENT) {
                    case "home":
                        navController.navigate(actionNavigateToNotification);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "signUpUser":
                        navController.navigate(actionNavigateToHomeFromNotification);
                        notification.setImageResource(R.drawable.notification_icon);
                        signUp.setVisibility(View.VISIBLE);
                        break;

                    case "vehicleSetUp":
                        navController.navigate(actionNavigateToNotificationFromVehicleSetUp);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpRegistration":
                        navController.navigate(actionNavigateToNotificationFromVehicleSetUpRegistration);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpDamageReport":
                        navController.navigate(actionNavigateToNotificationFromVehicleSetUpDamage);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpRentalInfo":
                        navController.navigate(actionNavigateToNotificationFromVehicleRentalInfo);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpConfirmation":
                        navController.navigate(actionNavigateToNotificationFromVehicleConfirmation);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "driverSetUp":
                        navController.navigate(actionNavigateToNotificationFromDriverSetup);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleRecord":
                        navController.navigate(actionNavigateToNotificationFromVehicleRecord);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;

                    case "vehicleUtilities":
                        navController.navigate(actionNavigateToNotificationFromVehicleUtilities);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "vehicleAccidentReport":
                        navController.navigate(actionNavigateToNotificationFromAccidentReport);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "RecordConfirmation":
                        navController.navigate(actionNavigateToNotificationFromRecordConfirmation);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "vehicleSetUpSignature":
                        navController.navigate(actionNavigateToNotificationFromVehicleSetUpSignature);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "vehicleRecordSignature":
                        navController.navigate(actionNavigateToNotificationFromVehicleRecordSignature);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "vehicleDashboard":
                        navController.navigate(actionNavigateToNotificationFromVehicleDashboard);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "vehicleDetails":
                        navController.navigate(actionNavigateToNotificationFromVehicleDetails);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "dashboard":
                        navController.navigate(actionNavigateToNotificationFromDashboard);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                    case "RecordDetails":
                        navController.navigate(actionNavigateToNotificationFromRecordDetails);
                        notification.setImageResource(R.drawable.home_icon);
                        signUp.setVisibility(View.GONE);
                        break;
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (FRAGMENT) {
                    case "home":
                        navController.navigate(actionNavigateToSignUp);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "signUpUser":
                        navController.navigate(actionNavigateToHomeFromSignUp);
                        signUp.setImageResource(R.drawable.admin_icon);
                        notification.setVisibility(View.VISIBLE);
                        break;

                    case "vehicleSetUp":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUp);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpRegistration":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUpRegistration);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpDamageReport":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUpDamage);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpRentalInfo":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUpRentalInfo);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleSetUpConfirmation":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUpConfirmation);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "driverSetUp":
                        navController.navigate(actionNavigateToSignUpFromDriverSetUp);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleRecord":
                        navController.navigate(actionNavigateToSignUpFromVehicleRecord);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;

                    case "vehicleUtilities":
                        navController.navigate(actionNavigateToSignUpFromVehicleUtilities);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "vehicleAccidentReport":
                        navController.navigate(actionNavigateToSignUpFromAccidentReport);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "RecordConfirmation":
                        navController.navigate(actionNavigateToSignUpFromRecordConfirmation);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "vehicleSetUpSignature":
                        navController.navigate(actionNavigateToSignUpFromVehicleSetUpSignature);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "vehicleRecordSignature":
                        navController.navigate(actionNavigateToSignUpFromVehicleRecordSignature);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "vehicleDashboard":
                        navController.navigate(actionNavigateToSignUpFromVehicleDashboard);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "vehicleDetails":
                        navController.navigate(actionNavigateToSignUpFromVehicleDetails);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "dashboard":
                        navController.navigate(actionNavigateToSignUpFromDashboard);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                    case "RecordDetails":
                        navController.navigate(actionNavigateToSignUpFromRecordDetails);
                        signUp.setImageResource(R.drawable.home_icon);
                        notification.setVisibility(View.GONE);
                        break;
                }

            }
        });


        setSupportActionBar(toolbar);


        dashboardMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.dashboard);
                drawerLayout.closeDrawers();

            }
        });

        vehicleRecordMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.vehicleDashboard);
                drawerLayout.closeDrawers();

            }
        });

        driverSetupMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.driverSetUp);
                drawerLayout.closeDrawers();

            }
        });

        vehicleSetupMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.vehicleSetUp);
                drawerLayout.closeDrawers();

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        setupNavigation();

    }


    // Setting Up One Time Navigation
    private void setupNavigation() {

        // Setting up the drawer navigation
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        // Setting the title in the start to empty
        getSupportActionBar().setTitle("");




    }


    @Override
    public boolean onSupportNavigateUp() {
        // reference the navigation controller when you want to navigate up
        navController.popBackStack(R.id.home, false);
        signUp.setVisibility(View.VISIBLE);
        notification.setVisibility(View.VISIBLE);
        signUp.setImageResource(R.drawable.admin_icon);
        notification.setImageResource(R.drawable.notification_icon);

        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {

        // When the drawer is open and user press back, the drawer well close
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            signUp.setVisibility(View.VISIBLE);
            notification.setVisibility(View.VISIBLE);
            signUp.setImageResource(R.drawable.admin_icon);
            notification.setImageResource(R.drawable.notification_icon);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // when an menu item is selected from menu, close the drawer
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public void hideIcon() {
        signUp.setVisibility(View.GONE);
        notification.setVisibility(View.GONE);

    }

    public static void showIcon() {
        signUp.setVisibility(View.VISIBLE);
        notification.setVisibility(View.VISIBLE);
    }




}
