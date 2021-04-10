package temple.mentalhealthwellness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import temple.mentalhealthwellness.models.Habit;
import temple.mentalhealthwellness.adapters.HabitRecyclerViewAdapter;

public class HabitTrackingFragment extends Fragment {
    private RecyclerView recyclerView;
    private HabitRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Habit> dataSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize with some fake data for demonstration purposes
        // actual data will come from storage later on in development
        initDataSet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_tracking, container, false);
        recyclerView = root.findViewById(R.id.habit_tracking);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HabitRecyclerViewAdapter(dataSet);
        recyclerView.setAdapter(adapter);
        return root;
    }

    /**
     * Generate data for demonstrations purposes
     */
    private void initDataSet() {
        dataSet = new ArrayList<>();
        Habit one = new Habit("Gym", 3);
        one.getDays()[0] = true;
        one.getDays()[2] = true;
        one.getDays()[5] = true;
        dataSet.add(one);

        /*
        Habit two = new Habit("Sleep 8 hr", 4);
        two.getDays()[1] = true;
        two.getDays()[5] = true;
        dataSet.add(two);

        dataSet.add(new Habit("Study", 4));
        dataSet.add(new Habit("Read", 3));
        */

    }
}