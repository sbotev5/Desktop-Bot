import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

class ClockLabel extends JLabel implements ActionListener {

    ClockLabel() {
        super("");

        Timer t = new Timer(1000, this); //refresh every second
        t.start();

        setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        setFont(new Font("ClockFont", Font.BOLD, 50));
    }

    public void actionPerformed(ActionEvent e) {  //set new time each second

        LocalTime time = LocalTime.now(); // getting current time

        if (time.getHour() < 10) {
            setText("0" + String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute()) + ":" + String.valueOf(time.getSecond()));
        } else
            setText(String.valueOf(time.getHour()) + ":" + String.valueOf(time.getMinute()) + ":" + String.valueOf(time.getSecond()));
    }
}
