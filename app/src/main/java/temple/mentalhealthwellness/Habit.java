package temple.mentalhealthwellness;

/**
 * User defined habit
 */
public class Habit {
    /**
     * Description of the habit
     */
    private String description;

    /**
     * Total amount of times a task is to be completed in a week
     */
    private int total;

    /**
     * Days this was completed or not
     */
    private boolean[] days;

    public Habit(String description, int total) {
        this.description = description;
        this.total = total;
        days = new boolean[7];
    }

    public String getDescription() {
        return description;
    }

    public boolean[] getDays() {
        return days;
    }

    @Override
    public String toString() {
        return description + " " + total + "x";
    }
}
