import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

class ClockLabel extends JLabel implements ActionListener {

    public ClockLabel() {
        super("");
        Timer t = new Timer(1000, this);
        t.start();
        setFont(new Font("ClockFont", Font.BOLD, 30));
    }

    public void actionPerformed(ActionEvent e) {

        LocalTime time = LocalTime.now();

        if (time.getHour() < 12) {
            setText(String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute()) + ":" + String.valueOf(time.getSecond()) + " AM");
        } else
            setText(String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute()) + ":" + String.valueOf(time.getSecond()) + " PM");

    }
}