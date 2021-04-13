package temple.mentalhealthwellness.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "habit_table")
public class Habit {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Description")
    public String description;

    @ColumnInfo(name = "Total")
    public int total;

    @ColumnInfo(name = "Mon")
    public boolean mon;

    @ColumnInfo(name = "Tue")
    public boolean tue;

    @ColumnInfo(name = "Wed")
    public boolean wed;

    @ColumnInfo(name = "Thu")
    public boolean thu;

    @ColumnInfo(name = "Fri")
    public boolean fri;

    @ColumnInfo(name = "Sat")
    public boolean sat;

    @ColumnInfo(name = "Sun")
    public boolean sun;

    public Habit(int id, String description, int total) {
        this.id = id;
        this.description = description;
        this.total = total;
    }

    @Ignore
    public Habit(String description, int total) {
        this.description = description;
        this.total = total;
    }

    @Override
    public String toString() {
        return this.description + " " + this.total + "x";
    }
}
