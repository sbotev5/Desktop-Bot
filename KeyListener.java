import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class KeyListener extends NativeKeyAdapter {

    private static boolean comboDetected = false;
    private static int previousKey = -1;
    private static ArrayList<Integer> var;
    private static ArrayList<Integer> keyCombo = new ArrayList<>();

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (previousKey == -1) {

                previousKey = e.getKeyCode();

            } else if (previousKey == e.getKeyCode()) {

            } else {

                if (keyCombo.isEmpty()) {

                    keyCombo.add(previousKey);
                    keyCombo.add(e.getKeyCode());

                    comboDetected = true;
                    var = new ArrayList<>();

                } else {

                    if (keyCombo.contains(e.getKeyCode())) {

                    } else keyCombo.add(e.getKeyCode());

                }
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (comboDetected) {

                if (!keyCombo.isEmpty()) {

                    var.add(keyCombo.get(keyCombo.size() - 1));
                    keyCombo.remove(keyCombo.size() - 1);

                } else {

                    Menu.currentRecording.add(new Movement("KeyCombo", var));

                    comboDetected = false;
                    var = new ArrayList<>();
                    previousKey = -1;
                }

            } else {

                Menu.currentRecording.add(new Movement("KeyBoard", e.getKeyCode()));
                previousKey = -1;

            }
        }
    }
}