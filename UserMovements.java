import java.util.ArrayList;
import java.util.UUID;

class UserMovements {
    private int hour;
    private int minute;
    private UUID id;
    private String name;
    private ArrayList<Movement> movements;

    UserMovements(String name, UUID id, int hour, int minute, ArrayList<Movement> movements) {
        this.hour = hour;
        this.minute = minute;
        this.id = id;
        this.name = name;
        this.movements = movements;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Movement> getMovements() {
        return movements;
    }
}
