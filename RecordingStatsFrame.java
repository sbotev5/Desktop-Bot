import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.UUID;

public class RecordingStatsFrame extends JFrame {

    private JSpinner hour;
    private JSpinner minute;
    private JButton setTime;
    private JTextField nameOfRec;
    private JLabel enterHour;
    private JLabel enterMinute;
    private JLabel enterName;
    private Menu menu;


    RecordingStatsFrame(Menu menu) {
        super("RECORDING DETAILS");
        this.menu = menu;
    }

    void initialize() {

        menu.getRecord().setEnabled(false);
        menu.getStopRecord().setEnabled(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        setTime = new JButton("SET TIME");
        hour = new JSpinner();
        minute = new JSpinner();
        nameOfRec = new JTextField();

        int[] stats = getRecordingStats(Menu.currentRecording);

        JFormattedTextField txt1 = ((JSpinner.NumberEditor) hour.getEditor()).getTextField();
        ((NumberFormatter) txt1.getFormatter()).setAllowsInvalid(false);

        JFormattedTextField txt2 = ((JSpinner.NumberEditor) minute.getEditor()).getTextField();
        ((NumberFormatter) txt2.getFormatter()).setAllowsInvalid(false);

        Font font = new Font("Panel", Font.BOLD, 20);

        Font font2 = new Font("Spinner", Font.BOLD, 50);

        nameOfRec.setFont(font2);

        hour.setFont(font2);
        minute.setFont(font2);

        enterHour = new JLabel("ENTER HOUR FOR AUTOMATIC EXECUTION", SwingConstants.CENTER);
        enterHour.setFont(font);

        enterName = new JLabel("ENTER NAME OF RECORDING", SwingConstants.CENTER);
        enterName.setFont(font);

        enterMinute = new JLabel("ENTER MINUTE FOR AUTOMATIC EXECUTION", SwingConstants.CENTER);
        enterMinute.setFont(font);

        hour.setValue(0);
        hour.addChangeListener(e -> {
            if ((int) hour.getValue() < 0 || (int) hour.getValue() > 23) {
                JOptionPane.showMessageDialog(this, "INVALID HOUR");
                hour.setValue(0);
            }
        });

        minute.setValue(0);

        minute.addChangeListener(e -> {

            if ((int) minute.getValue() < 0 || (int) minute.getValue() > 59) {
                JOptionPane.showMessageDialog(this, "INVALID MINUTE");
                minute.setValue(0);
            }
        });

        setTime.addActionListener(e -> {

            if (nameOfRec.getText().equals("")) {

                JOptionPane.showMessageDialog(this, "PLEASE ENTER A NAME!");

            } else {

                UUID id = UUID.randomUUID();

                UserMovements singleUser = new UserMovements(nameOfRec.getText(), id, (int) hour.getValue(), (int) minute.getValue(), Menu.currentRecording);

                dispose();

                JPanel singleRecording = new JPanel();
                singleRecording.setLayout(new GridLayout(9, 1));
                singleRecording.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

                JLabel name = new JLabel("Recording Name : " + singleUser.getName(), SwingConstants.CENTER);
                JLabel timeForExecution = new JLabel("Time for execution : " + Integer.toString(singleUser.getHour()) + ":" + Integer.toString(singleUser.getMinute()), SwingConstants.CENTER);

                Font font1 = new Font("Panel", Font.BOLD, 15);

                name.setFont(font1);

                timeForExecution.setFont(font1);

                JLabel numKeyPress = new JLabel("Number Of Key Presses : " + String.valueOf(stats[2]), SwingConstants.CENTER);
                numKeyPress.setFont(font1);

                JLabel numPointerMoves = new JLabel("Number Of Mouse Pointer Movements : " + String.valueOf(stats[0]), SwingConstants.CENTER);
                numPointerMoves.setFont(font1);

                JLabel numMouseClicks = new JLabel("Number Of Mouse Clicks : " + String.valueOf(stats[1]), SwingConstants.CENTER);
                numMouseClicks.setFont(font1);

                JLabel numTotalMoves = new JLabel("Number Of Total Movements : " + String.valueOf(singleUser.getMovements().size()), SwingConstants.CENTER);
                numTotalMoves.setFont(font1);

                JLabel idNumber = new JLabel("ID : " + id, SwingConstants.CENTER);
                idNumber.setFont(font1);

                JButton remove = new JButton("Remove Recording");

                remove.addActionListener(e1 -> {

                    Menu.saveUserMovements.removeIf(forCheck -> forCheck.getId().equals(id));

                    menu.getRecordings().remove(singleRecording);
                    menu.getRecordings().revalidate();
                });

                singleRecording.add(name);
                singleRecording.add(timeForExecution);
                singleRecording.add(numKeyPress);
                singleRecording.add(numPointerMoves);
                singleRecording.add(numMouseClicks);
                singleRecording.add(numTotalMoves);
                singleRecording.add(idNumber);
                singleRecording.add(remove);

                Menu.updateGUI.put(id, singleRecording);

                menu.getRecordings().add(singleRecording);

                Menu.saveUserMovements.add(singleUser);

                Menu.currentRecording = null;

                menu.getRecord().setEnabled(true);
                menu.getStopRecord().setEnabled(true);

            }
        });

        setTime.setFont(new Font("BUTTON", Font.BOLD, 40));

        panel.add(enterName);
        panel.add(nameOfRec);
        panel.add(enterHour);
        panel.add(hour);
        panel.add(enterMinute);
        panel.add(minute);
        panel.add(setTime);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                dispose();

                Menu.currentRecording = null;

                menu.getRecord().setEnabled(true);
                menu.getStopRecord().setEnabled(true);

            }
        });

        setSize(500, 500);
        setVisible(true);
    }

    private int[] getRecordingStats(ArrayList<Movement> var) {

        int[] stats = new int[3];

        int howManyMovements = 0;
        int howManyMouseClicks = 0;
        int howManyKeyPresses = 0;

        for (Movement movement : var) {

            switch (movement.getType()) {
                case "MouseMove":
                case "MouseDrag":
                case "WheelMove":

                    howManyMovements++;

                    break;
                case "KeyBoard":

                    howManyKeyPresses++;

                    break;
                case "KeyCombo":

                    howManyKeyPresses += 2;

                    break;
                case "MouseButton":

                    howManyMouseClicks++;

                    break;
            }
        }

        stats[0] = howManyMovements;
        stats[1] = howManyMouseClicks;
        stats[2] = howManyKeyPresses;

        return stats;
    }
}