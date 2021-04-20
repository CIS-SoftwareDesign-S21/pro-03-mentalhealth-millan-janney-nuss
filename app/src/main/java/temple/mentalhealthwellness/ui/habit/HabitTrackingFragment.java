package temple.mentalhealthwellness.ui.habit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.adapters.HabitRecyclerViewAdapter;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitTrackingFragment extends Fragment {
    private RecyclerView recyclerView;
    private HabitRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HabitViewModel habitViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_tracking, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);
        //habitViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HabitViewModel.class);
        fab.setOnClickListener(view -> {
                recyclerView = root.findViewById(R.id.habit_tracking);
                adapter = new HabitRecyclerViewAdapter(new HabitRecyclerViewAdapter.HabitDiff());
                recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                Habit h = new Habit("New habit", 3);
                habitViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HabitViewModel.class);
                habitViewModel.insert(h);
                habitViewModel.getHabits().observe(getViewLifecycleOwner(), habits -> adapter.submitList(habits));
        });

        recyclerView = root.findViewById(R.id.habit_tracking);
        adapter = new HabitRecyclerViewAdapter(new HabitRecyclerViewAdapter.HabitDiff());
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        habitViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HabitViewModel.class);
        habitViewModel.getHabits().observe(getViewLifecycleOwner(), habits -> adapter.submitList(habits));

        return root;
    }
}