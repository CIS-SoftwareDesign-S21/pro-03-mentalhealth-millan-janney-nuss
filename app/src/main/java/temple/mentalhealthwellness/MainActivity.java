package temple.mentalhealthwellness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        /* only create our habit tracking fragment once */
        if (fm.findFragmentById(R.id.habit_container) == null) {
            fm.beginTransaction()
                    .add(R.id.habit_container, new HabitTrackingFragment())
                    .commit();
        }
    }
}