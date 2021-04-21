package temple.mentalhealthwellness.ui.habit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.adapters.HabitRecyclerViewAdapter;

public class HabitTrackingFragment extends Fragment {
    private RecyclerView recyclerView;
    private HabitRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HabitViewModel habitViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_tracking, container, false);

        recyclerView = root.findViewById(R.id.habit_tracking);
        adapter = new HabitRecyclerViewAdapter(new HabitRecyclerViewAdapter.HabitDiff());
        layoutManager = new LinearLayoutManager(getActivity());
        habitViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HabitViewModel.class);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        habitViewModel.getHabits().observe(getViewLifecycleOwner(), habits -> adapter.submitList(habits));
        adapter.setOnItemDeletedListener(habit -> {
            habitViewModel.delete(habit);
        });

        return root;
    }
}