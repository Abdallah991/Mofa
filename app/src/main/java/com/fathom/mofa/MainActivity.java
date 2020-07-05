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

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private Button logOut;
    private ImageView signUp;
    private ImageView notification;
    private int actionNavigateToNotification = R.id.action_home_to_notifications;
    private int actionNavigateToSignUp = R.id.action_home_to_signUpUser;
    private int actionNavigateToHomeFromSignUp = R.id.action_signUpUser_to_home;
    private int actionNavigateToNotificationFromSignUp = R.id.action_signUpUser_to_notifications;

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
        navigationView = findViewById(R.id.nav_view);
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
                        break;

                    case "signUpUser":
                        navController.navigate(actionNavigateToNotificationFromSignUp);
                        notification.setImageResource(R.drawable.notification_icon);
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
                        break;

                    case "signUpUser":
                        navController.navigate(actionNavigateToHomeFromSignUp);
                        break;

                }

            }
        });


        setSupportActionBar(toolbar);




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



        return NavigationUI.navigateUp(navController, drawerLayout);
    }

    @Override
    public void onBackPressed() {

        // When the drawer is open and user press back, the drawer well close
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // when an menu item is selected from menu, close the drawer
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.dashboard:
                navController.navigate(R.id.dashboard);
                break;

            case R.id.vehicleRecord:
                navController.navigate(R.id.vehicleRecord);
                break;

            case R.id.driverSetUp:
                navController.navigate(R.id.driverSetUp);
                break;

            case R.id.vehicleSetUp:
                navController.navigate(R.id.vehicleSetUp);
                break;


        }


        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


}
