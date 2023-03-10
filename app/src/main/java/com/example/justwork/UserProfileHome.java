package com.example.justwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserProfileHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    BalanceFragment balanceFragment = new BalanceFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    MapsFragment mapsFragment = new MapsFragment();

    String Email;
    String HOME_POSTCODE;
    String FULL_NAME;
    String UNI;
    String PHONE_NUMBER;
    String STUDENT_NUMBER;
    String PASSWORD;
    String CARBON_RANK;
    String CARBON_BALANCE;
    String P_RANK;
    Bundle results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_home);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            String Email = extras.getString("Email");
            String HOME_POSTCODE = extras.getString("HOME_POSTCODE");
            String FULL_NAME = extras.getString("FULL_NAME");
            String UNI = extras.getString("UNI");
            String PHONE_NUMBER = extras.getString("PHONE_NUMBER");
            String STUDENT_NUMBER = extras.getString("STUDENT_NUMBER");
            String PASSWORD = extras.getString("PASSWORD");
            String CARBON_RANK = extras.getString("CARBON_RANK");
            String CARBON_BALANCE = extras.getString("CARBON_BALANCE");
            String P_RANK = extras.getString("P_RANK");

            System.out.println(value);
            System.out.println(Email);
            System.out.println(HOME_POSTCODE);
            System.out.println(FULL_NAME);
            System.out.println(UNI);
            System.out.println(PHONE_NUMBER);
            System.out.println(STUDENT_NUMBER);
            System.out.println(PASSWORD);
            System.out.println(CARBON_RANK);
            System.out.println(CARBON_BALANCE);
            System.out.println(P_RANK);

            Bundle bundle = new Bundle();
            bundle.putString("CARBON_BALANCE", CARBON_BALANCE);
            bundle.putString("Email", Email);
            bundle.putString("FULL_NAME", FULL_NAME);
            balanceFragment.setArguments(bundle);
            homeFragment.setArguments(bundle);


        }
        else{
            Toast.makeText(UserProfileHome.this, "data not passed", Toast.LENGTH_SHORT).show();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                    case R.id.balance:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, balanceFragment).commit();
                        return true;
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }


}




