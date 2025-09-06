package com.shivamsingh.verinews;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shivamsingh.EntertainmentFragment;
import com.shivamsingh.HealthFragment;
import com.shivamsingh.HomeFragment;
import com.shivamsingh.ScienceFragment;
import com.shivamsingh.SportsFragment;

public class MainActivity extends AppCompatActivity {

    private  Fragment homeFragment = new HomeFragment();
    private  Fragment scienceFragment = new ScienceFragment();
    private  Fragment sportsFragment = new SportsFragment();
    private  Fragment healthFragment = new HealthFragment();
    private  Fragment entertainmentFragment = new EntertainmentFragment();

    private Fragment activeFragment;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);

        if (savedInstanceState == null) {
            // This block runs ONLY the very first time the Activity is created
            // savedInstanceState is null means the app is starting fresh (not recreating after rotation or config change)

            // Add all fragments only on first creation
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.content, entertainmentFragment, "entertainment").hide(entertainmentFragment);
            ft.add(R.id.content, healthFragment, "health").hide(healthFragment);
            ft.add(R.id.content, sportsFragment, "sports").hide(sportsFragment);
            ft.add(R.id.content, scienceFragment, "science").hide(scienceFragment);
            ft.add(R.id.content, homeFragment, "home");
            ft.commit();

            activeFragment = homeFragment;
        } else {
            // Retrieve existing fragment references
           /* This block runs if the Activity is being recreated, e.g.,
            after screen rotation
            Screen rotation (portrait ↔ landscape)
            Changing system theme (dark mode ↔ light mode)
            Language change
            Low memory killing and restarting your app

          */

            FragmentManager fm = getSupportFragmentManager();
            activeFragment = fm.findFragmentByTag("home"); // Default
            homeFragment = fm.findFragmentByTag("home");
            scienceFragment = fm.findFragmentByTag("science");
            sportsFragment = fm.findFragmentByTag("sports");
            healthFragment = fm.findFragmentByTag("health");
            entertainmentFragment = fm.findFragmentByTag("entertainment");
        }

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    switchFragment(homeFragment);
                } else if (id == R.id.nav_science) {
                    switchFragment(scienceFragment);
                } else if (id == R.id.nav_sports) {
                    switchFragment(sportsFragment);
                } else if (id == R.id.nav_health) {
                    switchFragment(healthFragment);
                } else if (id == R.id.nav_entertainment) {
                    switchFragment(entertainmentFragment);
                } else {
                    return false; // Unknown item → don't mark it as handled
                }

                return true; // Known item → handled
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (activeFragment != homeFragment) {
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                } else {
                    finish();
                }
            }
        });
    }
    private void switchFragment(Fragment targetFragment) {
        if (activeFragment != targetFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(activeFragment);
            transaction.show(targetFragment);
            transaction.commit();
            activeFragment = targetFragment;
        }
    }


}

/*
package com.shivamsingh.verinews;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shivamsingh.EntertainmentFragment;
import com.shivamsingh.HealthFragment;
import com.shivamsingh.HomeFragment;
import com.shivamsingh.ScienceFragment;
import com.shivamsingh.SportsFragment;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private final Fragment homeFragment = new HomeFragment();
    private final Fragment scienceFragment = new ScienceFragment();
    private final Fragment sportsFragment = new SportsFragment();
    private final Fragment healthFragment = new HealthFragment();
    private final Fragment entertainmentFragment = new EntertainmentFragment();
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       bottomNavigationView=findViewById(R.id.bottomNav);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               int id= menuItem.getItemId();
               if(id==R.id.nav_home){
                   loadFragment(homeFragment);
               } else if (id==R.id.nav_science) {
                   loadFragment(scienceFragment);
               } else if (id==R.id.nav_sports) {
                   loadFragment(sportsFragment);
               } else if (id==R.id.nav_health) {
                   loadFragment(healthFragment);
               }else if (id == R.id.nav_entertainment) {
                   loadFragment(entertainmentFragment);
               } else {
                   return false; // Unknown item → don't mark it as handled
               }

               return true; // Known item → handled
           }

       });
        bottomNavigationView.setSelectedItemId(R.id.nav_home);


        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int selectedItemId = bottomNavigationView.getSelectedItemId();
                if(selectedItemId!=R.id.nav_home)
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                else
                   finish();

            }
        });
    }

    public void loadFragment(Fragment fragment){

        if(currentFragment!=fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.content, fragment);

            currentFragment=fragment;
            fragmentTransaction.commit();
        }

    }


}*/
