package temple.mentalhealthwellness;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import temple.mentalhealthwellness.ui.calendar.CalendarFragment;
import temple.mentalhealthwellness.ui.habit.HabitTrackingFragment;
import temple.mentalhealthwellness.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements ScreenTimeFragment.TimeInterface {
    HomeFragment homeFragment;
    Fragment calendarFragment;
    Fragment habitFragment;
    ScreenTimeFragment screenTimeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        habitFragment = new HabitTrackingFragment();
        screenTimeFragment = new ScreenTimeFragment();
        setCurrentFragment(homeFragment);




        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        setCurrentFragment(homeFragment);
                        break;
                    case R.id.calendar:
                        setCurrentFragment(calendarFragment);
                        break;
                    case R.id.habit:
                        setCurrentFragment(habitFragment);
                        break;

                    case R.id.screentime:
                        requestPermissions();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0 && requestCode == 1111) {
            setCurrentFragment(screenTimeFragment);

        }

    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }


    private void requestPermissions() {
        if (!checkPermissions()) {

            new AlertDialog.Builder(this)
                    .setMessage("Do you want to allow usage permissions")
                    .setTitle("Allow Permissions")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1111);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();

        } else {
            setCurrentFragment(screenTimeFragment);
        }
    }


    private boolean checkPermissions() {
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager) this
                .getSystemService(this.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), this.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (this.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        return granted;
    }


    @Override
    public void setTime(String total) {
        homeFragment.setTotal(total);
    }
}

