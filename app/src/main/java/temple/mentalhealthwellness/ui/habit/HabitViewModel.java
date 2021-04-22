package temple.mentalhealthwellness.ui.habit;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import temple.mentalhealthwellness.data.repositories.HabitRepository;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitViewModel extends AndroidViewModel {
    private final HabitRepository repository;
    private final LiveData<List<Habit>> habits;

    public HabitViewModel(Application app) {
        super(app);
        repository = new HabitRepository(app);
        habits = repository.getHabits();
    }

    public LiveData<List<Habit>> getHabits() {
        return habits;
    }

    public void insert(Habit habit) {
        repository.insert(habit);
    }

    public void delete(Habit habit) {
        repository.delete(habit);
    }

    public void update(Habit habit) {
        repository.update(habit);
    }
}
