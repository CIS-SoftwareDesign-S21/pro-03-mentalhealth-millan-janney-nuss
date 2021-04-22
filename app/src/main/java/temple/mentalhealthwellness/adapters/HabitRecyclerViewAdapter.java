package temple.mentalhealthwellness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitRecyclerViewAdapter extends ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder> {
    private OnItemDeletedListener deleteListener;

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private final TextView descText;
        private final ToggleButton monButton;
        private final ToggleButton tueButton;
        private final ToggleButton wedButton;
        private final ToggleButton thuButton;
        private final ToggleButton friButton;
        private final ToggleButton satButton;
        private final ToggleButton sunButton;
        private final ImageButton deleteButton;

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
            deleteButton = v.findViewById(R.id.delete);
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

    @NonNull
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

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.delete(current);
            }
        });

        holder.monButton.setOnClickListener(v ->
                current.mon = !current.mon);

        holder.tueButton.setOnClickListener(v ->
                current.tue = !current.tue);

        holder.wedButton.setOnClickListener(v ->
                current.wed = !current.wed);

        holder.thuButton.setOnClickListener(v ->
                current.thu = !current.thu);

        holder.friButton.setOnClickListener(v ->
                current.fri = !current.fri);

        holder.satButton.setOnClickListener(v ->
                current.sat = !current.sat);

        holder.sunButton.setOnClickListener(v ->
                current.sun = !current.sun);
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

    public void setOnItemDeletedListener(OnItemDeletedListener listener) {
        deleteListener = listener;
    }

    /**
     * Interface to communicate to the fragment
     * that an item has been selected to be deleted
     */
    public interface OnItemDeletedListener {
        void delete(Habit habit);
    }
}
