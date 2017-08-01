import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class KeyListener extends NativeKeyAdapter {

    private static boolean comboDetected = false;
    private static int previousKey = -1;
    private static ArrayList<Integer> keyCombo = new ArrayList<>();
    private static ArrayList<Integer> var = new ArrayList<>();

    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key pressed     " + e.getKeyCode());
        if (Menu.shouldRecord) {

            if (previousKey == -1) {

                previousKey = e.getKeyCode();

            } else if (previousKey == e.getKeyCode()) {

            } else {

                if (keyCombo.isEmpty()) {

                    keyCombo.add(previousKey);
                    keyCombo.add(e.getKeyCode());
                    comboDetected = true;

                } else {

                    if (keyCombo.contains(e.getKeyCode())) {

                    } else keyCombo.add(e.getKeyCode());

                }
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key released     " + e.getKeyCode());
        if (Menu.shouldRecord) {

            if (comboDetected) {

                if (!keyCombo.isEmpty()) {

                    var.add(keyCombo.get(keyCombo.size() - 1));
                    keyCombo.remove(keyCombo.size() - 1);

                } else {

                    Menu.currentRecording.add(new Movement("KeyCombo", var));

                    System.out.println("Size: " + var.size());
                    System.out.println();
                    for (int i = 0; i < var.size(); i++) {
                        System.out.println(var.get(i));
                    }

                    comboDetected = false;
                    var = new ArrayList<>();
                }

            } else {
                Menu.currentRecording.add(new Movement("KeyBoard", e.getKeyCode()));
                previousKey = -1;
            }
        }
    }
}