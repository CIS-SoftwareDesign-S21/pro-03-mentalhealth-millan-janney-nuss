package temple.mentalhealthwellness.adapters;

import android.content.Context;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import temple.mentalhealthwellness.R;
import temple.mentalhealthwellness.models.Habit;

public class HabitRecyclerViewAdapter extends RecyclerView.Adapter<HabitRecyclerViewAdapter.HabitViewHolder> {
    private ArrayList<Habit> dataSet;
    int habitAmt;
    private String description;
    ImageButton deleteButton;

    TextView descText;

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private final TextView descText;
        private final ToggleButton monButton;
        private final ToggleButton tueButton;
        private final ToggleButton wedButton;
        private final ToggleButton thuButton;
        private final ToggleButton friButton;
        private final ToggleButton satButton;
        private final ToggleButton sunButton;
        //private final ImageButton deleteButton;

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
            //deleteButton = v.findViewById(R.id.delete);
        }

        public void setDesc(String desc) {
            descText.setText(desc);
            //description = desc;
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
        // Delete / edit habit button
        deleteButton = v.findViewById(R.id.delete);
        descText = v.findViewById(R.id.description_text);

        /*
        descText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) parent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(descText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        */
        return new HabitViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        habitAmt = dataSet.size();
        Habit habit = dataSet.get(position);
        this.description = dataSet.get(position).getDescription();
        boolean[] days = habit.getDays();
        for (int i = 0; i < days.length; i++) {
            holder.setChecked(i, days[i]);
        }
        holder.setDesc(habit.toString());
        // Delete button listener
        deleteButton.setOnClickListener(v -> removeAt(position));


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // Remove an item from the view
    private void removeAt(int position) {
        if(dataSet.size()>1) {
            dataSet.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataSet.size());
        }
    }


}
