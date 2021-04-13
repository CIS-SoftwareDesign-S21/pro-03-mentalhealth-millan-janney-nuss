package temple.mentalhealthwellness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        final Fragment homeFragment = new HomeFragment();
        final Fragment calendarFragment = new CalendarFragment();
        final Fragment habitFragment = new HabitTrackingFragment();
        final Fragment screenTimeFragment = new ScreenTimeFragment();
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
                        setCurrentFragment(screenTimeFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }
}