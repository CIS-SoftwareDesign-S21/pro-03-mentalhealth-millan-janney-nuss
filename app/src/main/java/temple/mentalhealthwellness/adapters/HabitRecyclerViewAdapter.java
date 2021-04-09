package temple.mentalhealthwellness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.models.Habit;

public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitRecyclerViewAdapter.HabitViewHolder> {
    private ArrayList<Habit> dataSet;

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private final TextView descText;
        private final ToggleButton monButton;
        private final ToggleButton tueButton;
        private final ToggleButton wedButton;
        private final ToggleButton thuButton;
        private final ToggleButton friButton;
        private final ToggleButton satButton;
        private final ToggleButton sunButton;

        public HabitViewHolder(@NonNull View v) {
            super(v);
            descText = v.findViewById(R.id.description_text);
            monButton = v.findViewById(R.id.toggle_mon);
            tueButton = v.findViewById(R.id.toggle_tue);
            wedButton = v.findViewById(R.id.toggle_wed);
            thuButton = v.findViewById(R.id.toggle_thu);
            friButton = v.findViewById(R.id.toggle_fri);
            satButton = v.findViewById(R.id.toggle_sat);
            sunButton = v.findViewById(R.id.toggle_sun);
        }

        public void setDesc(String desc) {
            descText.setText(desc);
        }

        public void setChecked(int day, boolean checked) {
            ToggleButton dayButton = getDayButton(day);
            if (dayButton != null) {
                dayButton.setChecked(checked);
            }
        }

        private ToggleButton getDayButton(int day) {
            switch (day) {
                case 0:
                    return monButton;
                case 1:
                    return tueButton;
                case 2:
                    return wedButton;
                case 3:
                    return thuButton;
                case 4:
                    return friButton;
                case 5:
                    return satButton;
                case 6:
                    return sunButton;
                default:
                    return null;
            }
        }
    }

    public HabitRecyclerViewAdapter(ArrayList<Habit> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_row_item, parent, false);
        return new HabitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = dataSet.get(position);
        boolean[] days = habit.getDays();
        for (int i = 0; i < days.length; i++) {
            holder.setChecked(i, days[i]);
        }
        holder.setDesc(habit.toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
