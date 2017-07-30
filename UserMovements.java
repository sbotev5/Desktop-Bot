import java.util.ArrayList;
import java.util.UUID;

class UserMovements {
    private int hour;
    private int minute;
    private UUID id;
    private String name;
    private ArrayList<Movement> movements;
    private long duration;

    UserMovements(String name, UUID id, int hour, int minute, long duration, ArrayList<Movement> movements) {
        this.hour = hour;
        this.minute = minute;
        this.id = id;
        this.name = name;
        this.movements = movements;
        this.duration = duration;
    }

    int getHour() {
        return hour;
    }

    int getMinute() {
        return minute;
    }

    UUID getId() {
        return id;
    }

    String getName() {
        return name;
    }

    ArrayList<Movement> getMovements() {
        return movements;
    }

    long getDuration() {
        return duration;
    }
}
