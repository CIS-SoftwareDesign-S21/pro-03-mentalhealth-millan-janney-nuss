package temple.mentalhealthwellness.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import temple.mentalhealthwellness.data.db.entities.Habit;

@Dao
public interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("SELECT * FROM habit_table")
    LiveData<List<Habit>> getHabits();

    @Query("DELETE FROM habit_table")
    void deleteAll();
}
