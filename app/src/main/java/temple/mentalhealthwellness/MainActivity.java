package temple.mentalhealthwellness;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import temple.mentalhealthwellness.ui.calendar.CalendarFragment;
import temple.mentalhealthwellness.ui.habit.HabitTrackingFragment;
import temple.mentalhealthwellness.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment calendarFragment;
    Fragment habitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        habitFragment = new HabitTrackingFragment();
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