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

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fathom.mofa.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Locale;

import static com.fathom.mofa.LoginActivity.USER;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    menu, navigation controller and constants.
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private Button changeLanguage;
    private ImageView signUp;
    private ImageView notification;
    public static boolean RegisterClicked = false;
    private LinearLayout driverSetupMenuItem;
    private LinearLayout vehicleSetupMenuItem;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean isAdmin;
    private String TAG = "HOME";
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
        Toolbar toolbar = findViewById(R.id.app_bar);
//        View view = findViewById(R.id.app_bar);
        signUp = toolbar.findViewById(R.id.signUpUserIcon);
        notification = toolbar.findViewById(R.id.notificationIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Button logOut = findViewById(R.id.logOut);
        changeLanguage = findViewById(R.id.changeLanguage);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        LinearLayout vehicleRecordMenuItem = findViewById(R.id.vehicleRecordMenu);
        LinearLayout dashboardMenuItem = findViewById(R.id.dashboardMenu);
        driverSetupMenuItem = findViewById(R.id.driverSetUpMenu);
        vehicleSetupMenuItem = findViewById(R.id.vehicleSetUpMenu);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);

        LayoutInflater inflater = getLayoutInflater();
//        View slider = inflater.from(getApplicationContext()).inflate(R.layout.drawer_header, null);
        ViewGroup viewRoot = (ViewGroup) inflater.inflate(R.layout.drawer_header,null);


        signUp.setVisibility(View.INVISIBLE);
        driverSetupMenuItem.setVisibility(View.INVISIBLE);
        vehicleSetupMenuItem.setVisibility(View.INVISIBLE);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            if(toolbar.getChildAt(i) instanceof ImageButton){
                toolbar.getChildAt(i).setScaleX(0.75f);
                toolbar.getChildAt(i).setScaleY(0.75f);
            }
        }



//        notification click listener
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
                        if(isAdmin){
                            signUp.setVisibility(View.VISIBLE);
                        }
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

//        signup a user click listener
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

//      menu item click listener
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
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        change language click listener
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeLanguage.getText().toString().equals("Arabic")) {
                    setApplicationLocale("ar");
                    SharedPreferences userPrefs = getSharedPreferences(USER, 0);
                    userPrefs.edit().putString("Lang", "Arabic").apply();
                    Intent intent = new Intent(getApplicationContext(),
                            SplashActivity.class);
                    startActivity(intent);
                    finish();


                }

                if(changeLanguage.getText().toString().equals("English")) {
                    setApplicationLocale("en");
                    SharedPreferences userPrefs = getSharedPreferences(USER, 0);
                    userPrefs.edit().putString("Lang", "English").apply();
                    Intent intent = new Intent(getApplicationContext(),
                            SplashActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });


        setupNavigation();

        getUserInfo();


    }


    // Setting Up One Time Navigation
    private void setupNavigation() {

        // Setting up the drawer navigation
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        // Setting the title in the start to empty
        getSupportActionBar().setTitle("");




    }


//    physical back click implementation
    @Override
    public boolean onSupportNavigateUp() {

        drawerLayout.openDrawer(GravityCompat.START);
        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {

        // When the drawer is open and user press back, the drawer well close
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            if(isAdmin) {
                signUp.setVisibility(View.VISIBLE);
                signUp.setImageResource(R.drawable.admin_icon);
            }
            notification.setVisibility(View.VISIBLE);
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


//    get user data

    private void getUserInfo() {

        final SharedPreferences pref = getSharedPreferences(USER, 0); // 0 - for private mode
        String email = pref.getString("Email", "");

        db.collection("Users").document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                UserDataModel user = document.toObject(UserDataModel.class);
                                String userName = user.getFirstName()+ " "+ user.getLastName();
                                String userStatus = user.getUserType();
                                pref.edit().putString("userName", userName).apply();
                                pref.edit().putString("userStatus", userStatus).apply();

                                if (user.getUserType().equals("Admin")|| user.getUserType().equals("مشرف")){
                                    isAdmin = true;
                                    signUp.setVisibility(View.VISIBLE);
                                    driverSetupMenuItem.setVisibility(View.VISIBLE);
                                    vehicleSetupMenuItem.setVisibility(View.VISIBLE);

                                } else
                                {
                                    signUp.setVisibility(View.INVISIBLE);
                                    driverSetupMenuItem.setVisibility(View.INVISIBLE);
                                    vehicleSetupMenuItem.setVisibility(View.INVISIBLE);
                                    isAdmin = false;
                                }

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }


                });

        Log.d(TAG, " Loading the data is DONE");

    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
}





