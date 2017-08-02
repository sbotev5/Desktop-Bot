import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

class UserMovements {
    private int hour;
    private int minute;
    private UUID id;
    private String name;
    private ArrayList<Movement> movements;
    private long duration;
    private JPanel panelGUI;

    UserMovements(String name, UUID id, int hour, int minute, long duration, ArrayList<Movement> movements, JPanel panelGUI) {
        this.hour = hour;
        this.minute = minute;
        this.id = id;
        this.name = name;
        this.movements = movements;
        this.duration = duration;
        this.panelGUI = panelGUI;
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

    JPanel getPanelGUI() {
        return panelGUI;
    }
}
