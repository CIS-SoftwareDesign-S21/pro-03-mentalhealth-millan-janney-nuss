package temple.mentalhealthwellness.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import temple.mentalhealthwellness.data.db.AppDatabase;
import temple.mentalhealthwellness.data.db.HabitDao;
import temple.mentalhealthwellness.data.db.entities.Habit;

public class HabitRepository {
    private HabitDao habitDao;
    private LiveData<List<Habit>> habits;

    public HabitRepository(Application app) {
        AppDatabase appDb = AppDatabase.getDatabase(app);
        habitDao = appDb.habitDao();
        habits = habitDao.getHabits();
    }

    public LiveData<List<Habit>> getHabits() {
        return habits;
    }

    public void insert(Habit habit) {
        AppDatabase.databaseWriter.execute(() -> habitDao.insert(habit));
    }
}
