package com.kunalkirimkar.tomatodiseasedetectionapp;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.kunalkirimkar.tomatodiseasedetectionapp.fragments.DiseaseDetectionFragment;
import com.kunalkirimkar.tomatodiseasedetectionapp.fragments.MarketIntelligenceFragment;
import com.kunalkirimkar.tomatodiseasedetectionapp.fragments.PreservationFragment;
import com.kunalkirimkar.tomatodiseasedetectionapp.fragments.ProductionFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.production);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return changeFragment(item.getItemId());
            }
        });

        changeFragment(R.id.production);
    }

    private boolean changeFragment(int itemId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (itemId == R.id.production){
            fragmentTransaction.replace(R.id.container, new ProductionFragment());
        } else if (itemId == R.id.diseaseDetection) {
            fragmentTransaction.replace(R.id.container, new DiseaseDetectionFragment());
        } else if (itemId == R.id.marketIntelligence) {
            fragmentTransaction.replace(R.id.container, new MarketIntelligenceFragment());
        } else {
            fragmentTransaction.replace(R.id.container, new PreservationFragment());
        }

        fragmentTransaction.addToBackStack(null).commit();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}