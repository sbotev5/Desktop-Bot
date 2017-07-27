import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.time.LocalTime;
import java.util.*;

public class Menu extends JFrame {

    private JButton record;
    private JButton stopRecord;
    private JPanel buttonPanel;
    private JPanel clockPanel;
    private JPanel recordings;
    private JPanel mainPanel;

    static boolean shouldRecord = false;
    static ArrayList<UserMovements> saveUserMovements;
    static ArrayList<Movement> currentRecording;
    static HashMap<UUID, JPanel> updateGUI;

    private Robot robot;

    Menu(Robot robot) {

        super("AUTOMATION TOOL");
        this.robot = robot;

    }

    void initialize() {

        new Thread(new MyThread()).start();
        saveUserMovements = new ArrayList<>();
        updateGUI = new HashMap<>();

        mainPanel = new JPanel();
        setContentPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Font buttonFont = new Font("BUTTON", Font.BOLD, 25);

        record = new JButton("RECORD");
        record.setFont(buttonFont);
        stopRecord = new JButton("STOP RECORDING");
        stopRecord.setFont(buttonFont);

        buttonPanel = new JPanel();
        buttonPanel.add(record);
        buttonPanel.add(stopRecord);

        recordings = new JPanel();
        recordings.setLayout(new GridLayout(1, 5));
        recordings.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3)), "RECORDINGS",
                TitledBorder.CENTER, TitledBorder.TOP));

        clockPanel = new JPanel();
        ClockLabel clock = new ClockLabel();
        clock.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        clockPanel.add(clock);

        record.addActionListener(e -> {

            currentRecording = new ArrayList<>();
            shouldRecord = true;

            setExtendedState(JFrame.ICONIFIED);
            record.setEnabled(false);
        });

        stopRecord.addActionListener(e -> {

            if (currentRecording != null) {

                currentRecording.remove(currentRecording.size() - 1);

                shouldRecord = false;
                record.setEnabled(true);

                RecordingStatsFrame statsFrame = new RecordingStatsFrame(this);
                statsFrame.initialize();

            } else JOptionPane.showMessageDialog(this, "YOU HAVEN'T STARTED A RECORDING!");

        });

        mainPanel.add(buttonPanel);
        mainPanel.add(clockPanel);
        mainPanel.add(recordings);

    }

    private void executeMovements(UserMovements var) {

        boolean holdButton = false;

        for (Movement movement : var.getMovements()) {

            switch (movement.getType()) {
                case "MouseMove": {

                    Point point = (Point) movement.getMovement();
                    robot.mouseMove((int) point.getX(), (int) point.getY());

                    holdButton = false;

                    robot.delay(3);

                    break;
                }
                case "WheelMove":

                    robot.mouseWheel((int) movement.getMovement());

                    holdButton = false;

                    robot.delay(500);

                    break;
                case "MouseButton":

                    try {

                        if ((int) movement.getMovement() == 1) {

                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                            holdButton = false;

                            robot.delay(500);

                        } else {

                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

                            holdButton = false;

                            robot.delay(500);
                        }

                    } catch (Exception exc) {
                        System.err.println("Mouse button not recognised!");
                    }

                    break;
                case "MouseDrag": {

                    if (!holdButton) {
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        holdButton = true;
                    }

                    Point point = (Point) movement.getMovement();

                    robot.mouseMove((int) point.getX(), (int) point.getY());

                    robot.delay(3);

                    break;
                }
                case "KeyBoard":

                    try {

                        robot.keyPress(Main.keyboard.get(movement.getMovement()));
                        robot.keyRelease(Main.keyboard.get(movement.getMovement()));

                        holdButton = false;

                        robot.delay(500);

                    } catch (Exception exc) {
                        System.err.println("Keyboard button not recognised!");
                    }

                    break;
                case "KeyCombo":

                    try {

                        int[] keyCombo = (int[]) movement.getMovement();

                        robot.keyPress(keyCombo[0]);
                        robot.keyPress(keyCombo[1]);

                        robot.keyRelease(keyCombo[1]);
                        robot.keyPress(keyCombo[0]);

                        holdButton = false;

                        robot.delay(500);

                    } catch (Exception e) {
                        System.err.println("Keyboard combo not recognized!");
                    }
                    break;
            }
        }
    }

    private class MyThread implements Runnable {

        @Override
        public void run() {

            while (true) {

                LocalTime time = LocalTime.now();

                if (!saveUserMovements.isEmpty()) {

                    for (Iterator<UserMovements> listIT = saveUserMovements.iterator(); listIT.hasNext(); ) {

                        UserMovements forCheck = listIT.next();

                        if (forCheck.getHour() == time.getHour() && forCheck.getMinute()== time.getMinute()) {

                            setExtendedState(JFrame.ICONIFIED);

                            record.setEnabled(false);
                            stopRecord.setEnabled(false);

                            executeMovements(forCheck);

                            record.setEnabled(true);
                            stopRecord.setEnabled(true);

                            listIT.remove();

                            recordings.remove(updateGUI.get(forCheck.getId()));
                            recordings.revalidate();
                        }
                    }
                }
            }
        }
    }

    public JButton getRecord() {
        return record;
    }

    public JButton getStopRecord() {
        return stopRecord;
    }

    public JPanel getRecordings() {
        return recordings;
    }
}