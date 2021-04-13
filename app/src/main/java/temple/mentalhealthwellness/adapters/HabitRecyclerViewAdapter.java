package temple.mentalhealthwellness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitRecyclerViewAdapter extends ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder> {

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

    public HabitRecyclerViewAdapter(@NonNull DiffUtil.ItemCallback<Habit> diffCallback) {
        super(diffCallback);
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_row_item, parent, false);
        return new HabitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit current = getItem(position);
        holder.setChecked(0, current.mon);
        holder.setChecked(1, current.tue);
        holder.setChecked(2, current.wed);
        holder.setChecked(3, current.thu);
        holder.setChecked(4, current.fri);
        holder.setChecked(5, current.sat);
        holder.setChecked(6, current.sun);
        holder.setDesc(current.toString());
    }

    public static class HabitDiff extends DiffUtil.ItemCallback<Habit> {
        @Override
        public boolean areItemsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Habit oldItem, @NonNull Habit newItem) {
            return oldItem.description.equals(newItem.description);
        }
    }
}
