import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class Menu extends JFrame {

    private JButton record;
    private JButton stopRecord;
    private JButton loadRecording;
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
        loadRecording = new JButton("LOAD RECORDING");
        loadRecording.setFont(buttonFont);

        buttonPanel = new JPanel();
        buttonPanel.add(record);
        buttonPanel.add(stopRecord);
        buttonPanel.add(loadRecording);

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
            loadRecording.setEnabled(false);
        });

        stopRecord.addActionListener(e -> {

            if (currentRecording != null) {

                currentRecording.remove(currentRecording.size() - 1);

                shouldRecord = false;
                record.setEnabled(true);
                loadRecording.setEnabled(true);

                new RecordingStatsFrame(this).initialize();

            } else JOptionPane.showMessageDialog(this, "YOU HAVEN'T STARTED A RECORDING!");

        });

        loadRecording.addActionListener(e -> {

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a recording file to load");

            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setMultiSelectionEnabled(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Recording files", "ATrecording");
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File selectedFile = jfc.getSelectedFile();

                try {

                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(selectedFile));
                    currentRecording = (ArrayList<Movement>) in.readObject();
                    in.close();
                    new RecordingStatsFrame(this).initialize();

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
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

                        if (forCheck.getHour() == time.getHour() && forCheck.getMinute() == time.getMinute()) {

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

    public JButton getLoadRecording() {
        return loadRecording;
    }

    public JPanel getRecordings() {
        return recordings;
    }
}