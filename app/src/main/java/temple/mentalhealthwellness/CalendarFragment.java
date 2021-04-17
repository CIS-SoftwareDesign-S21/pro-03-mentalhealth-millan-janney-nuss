package temple.mentalhealthwellness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;


import temple.mentalhealthwellness.R;

public class CalendarFragment extends Fragment {
    CalendarView calender;
    TextView date_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        // Inflate the layout for this fragment
        calender = (CalendarView)
                root.findViewById(R.id.calender);
        date_view = (TextView)
                root.findViewById(R.id.date_view);

        // Add Listener in calendar
        // Listener for calendarview
        calender
                .setOnDateChangeListener(
                        (view, year, month, dayOfMonth) -> {

                            // Display the date at top of calendar
                            String Date
                                    = (month + 1) + "-"
                                    + dayOfMonth + "-" + year;

                            // Set the date
                            date_view.setText(Date);
                        });
        return root;
    }
}