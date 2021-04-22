package temple.mentalhealthwellness.ui.habit;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.primitives.Ints;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.adapters.HabitRecyclerViewAdapter;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitTrackingFragment extends Fragment {
    private RecyclerView recyclerView;
    private HabitRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private HabitViewModel habitViewModel;
    private FloatingActionButton newHabitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_tracking, container, false);

        recyclerView = root.findViewById(R.id.habit_tracking);
        adapter = new HabitRecyclerViewAdapter(new HabitRecyclerViewAdapter.HabitDiff());
        layoutManager = new LinearLayoutManager(getActivity());
        habitViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(HabitViewModel.class);
        newHabitButton = root.findViewById(R.id.fab);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        habitViewModel.getHabits().observe(getViewLifecycleOwner(), habits ->
                adapter.submitList(habits));

        adapter.setOnItemDeletedListener(habit ->
                habitViewModel.delete(habit));

        newHabitButton.setOnClickListener(v ->
                showHabitDialog());

        return root;
    }


    @Override
    // Postpone any database transactions until the habit is destroyed
    public void onStop() {
        super.onStop();
        for (Habit h : adapter.getCurrentList()) {
            habitViewModel.update(h);
        }
    }

    /**
     * Display a dialog for creating a new habit
     */
    private void showHabitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_habit_dialog, (ViewGroup) getView(), false);
        builder.setView(view)
                .setTitle(getString(R.string.add_habit_title))
                .setPositiveButton(R.string.button_submit, null)
                .setNegativeButton(R.string.button_cancel, (dialog, which) ->
                        dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Set this after the fact because the default behavior will dismiss the dialog
        // and disallow us from validating input and displaying any necessary errors
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            TextInputEditText descEditText = view.findViewById(R.id.desc_edit_text);
            Editable descText = descEditText.getText();

            TextInputEditText freqEditText = view.findViewById(R.id.freq_edit_text);
            Editable freqText = freqEditText.getText();

            if (!isEmpty(descText)) {
                if (!isEmpty(freqText)) {
                    Integer freq = Ints.tryParse(freqText.toString());
                    if (!isValidFreq(freq)) {
                        freqEditText.setError(getString(R.string.freq_error));
                    } else {
                        descEditText.setError(null);
                        freqEditText.setError(null);
                        Habit habit = new Habit(descText.toString(), freq);
                        habitViewModel.insert(habit);
                        dialog.dismiss();
                    }
                } else {
                    freqEditText.setError(getString(R.string.empty_error));
                }
            } else {
                descEditText.setError(getString(R.string.empty_error));
            }
        });
    }

    private boolean isEmpty(Editable text) {
        return text != null && text.toString().isEmpty();
    }

    private boolean isValidFreq(Integer freq) {
        if (freq == null) {
            return false;
        }
        return freq >= 1 && freq <= 7;
    }
}