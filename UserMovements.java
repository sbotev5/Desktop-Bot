import javax.swing.*;
import java.util.ArrayList;

class UserMovements {
    private int hour;
    private int minute;
    private String name;
    private ArrayList<Movement> movements;
    private long duration;
    private JPanel panelGUI;

    // class used to hold the stats and set of movements of a single recording
    // List<UserMovements> saveUserMovements (in Menu class) is better performance-wise than a Map
    // A Map would allow for recognizing different movement types (mouse move, click , key press etc) but does not allow duplicate keys
    // Additionally, having it as a class allows for better structure and better pairing of irrelevant data to the actual movements themselves
    // (name, duration and time are not connected to the actual set of movements but to the recording itself)
    // The JPanel that is used to show the recording on the GUI is stored here in order to remove the correct one

    UserMovements(String name, int hour, int minute, long duration, ArrayList<Movement> movements, JPanel panelGUI) {
        this.hour = hour;
        this.minute = minute;
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
