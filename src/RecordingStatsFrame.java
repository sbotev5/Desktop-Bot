import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecordingStatsFrame extends JFrame {

    private JSpinner hour;
    private JSpinner minute;
    private JTextField name;
    private JLabel enterHour;
    private JLabel enterMinute;
    private JLabel enterName;
    private JButton setTime;
    private Menu menu;

    RecordingStatsFrame(Menu menu) {

        super("ENTER RECORDING DETAILS");
        this.menu = menu;

    }

    void initialize() {

        menu.getRecord().setEnabled(false);
        menu.getStopRecord().setEnabled(false);
        menu.getLoadRecording().setEnabled(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        setTime = new JButton("SET TIME");
        hour = new JSpinner();
        minute = new JSpinner();
        name = new JTextField();

        int[] stats = getRecordingStats(Menu.singleRecording);

        // Formatting JSpinners to not accepting anything apart from numbers

        JFormattedTextField txt1 = ((JSpinner.NumberEditor) hour.getEditor()).getTextField();
        ((NumberFormatter) txt1.getFormatter()).setAllowsInvalid(false);

        JFormattedTextField txt2 = ((JSpinner.NumberEditor) minute.getEditor()).getTextField();
        ((NumberFormatter) txt2.getFormatter()).setAllowsInvalid(false);

        Font font = new Font("Panel", Font.BOLD, 20);

        Font font2 = new Font("Spinner", Font.BOLD, 50);

        name.setFont(font2);
        hour.setFont(font2);
        minute.setFont(font2);

        enterHour = new JLabel("HOUR FOR AUTOMATIC EXECUTION", SwingConstants.CENTER);
        enterHour.setFont(font);

        enterName = new JLabel("NAME OF RECORDING", SwingConstants.CENTER);
        enterName.setFont(font);

        enterMinute = new JLabel("MINUTE FOR AUTOMATIC EXECUTION", SwingConstants.CENTER);
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

            boolean timeClash = false;
            String clashesWith = null;

            long recordingDuration = TimeUnit.NANOSECONDS.toSeconds(Menu.stopTime); //storing the duration

            for (Recording um : Menu.allUserRecordings) {  // checking for a time clash with already existing recordings

                if (um.getHour() == (int) hour.getValue() && um.getMinute() == (int) minute.getValue()) {
                    timeClash = true;
                    clashesWith = um.getName();
                }

            }

            if (timeClash) {

                //informing the user which recording is in conflict with the current, if any

                JOptionPane.showMessageDialog(this, "A recording named \"" + clashesWith + "\" has the same execution time!");

            } else if (name.getText().equals("")) {

                JOptionPane.showMessageDialog(this, "Please enter a name."); // informing the user

            } else {

                JPanel singleRecording = new JPanel();
                singleRecording.setLayout(new GridLayout(8, 1));
                singleRecording.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

                Recording singleUser = new Recording(name.getText(), (int) hour.getValue(), (int) minute.getValue(), recordingDuration, Menu.singleRecording, singleRecording);

                JLabel name = new JLabel("Recording Name : " + singleUser.getName(), SwingConstants.CENTER);
                JLabel timeForExecution = new JLabel("Time for execution : " + singleUser.getHour() + ":" + singleUser.getMinute(), SwingConstants.CENTER);
                JLabel durationLabel = new JLabel("Duration : " + singleUser.getDuration() + " seconds", SwingConstants.CENTER);

                Font font1 = new Font("Panel", Font.BOLD, 15);

                name.setFont(font1);
                timeForExecution.setFont(font1);
                durationLabel.setFont(font1);

                JLabel numKeyPress = new JLabel("Number Of Key Presses : " + stats[2], SwingConstants.CENTER);
                numKeyPress.setFont(font1);

                JLabel numPointerMoves = new JLabel("Number Of Mouse Pointer Movements : " + stats[0], SwingConstants.CENTER);
                numPointerMoves.setFont(font1);

                JLabel numMouseClicks = new JLabel("Number Of Mouse Clicks : " + stats[1], SwingConstants.CENTER);
                numMouseClicks.setFont(font1);

                JButton remove = new JButton("Remove Recording");

                remove.addActionListener(e1 -> {

                    Menu.allUserRecordings.remove(singleUser);

                    menu.getRecordings().remove(singleRecording); //updating GUI
                    menu.getRecordings().revalidate();

                });

                JButton save = new JButton("Save Recording");

                //dedicated method for code clarity
                save.addActionListener(e1 -> saveRecording(singleUser.getName(), singleUser.getMovements()));

                singleRecording.add(name);
                singleRecording.add(timeForExecution);
                singleRecording.add(durationLabel);
                singleRecording.add(numKeyPress);
                singleRecording.add(numMouseClicks);
                singleRecording.add(numPointerMoves);
                singleRecording.add(save);
                singleRecording.add(remove);

                menu.getRecordings().add(singleRecording);

                Menu.allUserRecordings.add(singleUser);

                Menu.singleRecording = null;

                dispose();

                menu.getRecord().setEnabled(true);
                menu.getStopRecord().setEnabled(true);
                menu.getLoadRecording().setEnabled(true);

            }
        });

        setTime.setFont(new Font("BUTTON", Font.BOLD, 40));

        panel.add(enterName);
        panel.add(name);
        panel.add(enterHour);
        panel.add(hour);
        panel.add(enterMinute);
        panel.add(minute);
        panel.add(setTime);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {  //if user does not want to keep recording upon entering stats
                super.windowClosing(e);

                dispose();

                Menu.singleRecording = null;

                menu.getRecord().setEnabled(true);
                menu.getStopRecord().setEnabled(true);
                menu.getLoadRecording().setEnabled(true);
            }
        });

        setSize(500, 500);
        setVisible(true);
    }

    private int[] getRecordingStats(List<Movement> var) {

        // used to generate some stats about the recording as a feature

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

                case "KeyPress":

                    howManyKeyPresses++;

                    break;

                case "KeyCombo":

                    LinkedList<Integer> comboModifiers = (LinkedList<Integer>) movement.getMovement();
                    howManyKeyPresses += comboModifiers.size();

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

    private void saveRecording(String name, List<Movement> recording) {

        //used in order to have the option to save a recording as a serialized object, using its name to differentiate and avoid overwriting

        try {

            FileOutputStream fileOut = new FileOutputStream(name + ".desktopbotFile");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(recording);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}