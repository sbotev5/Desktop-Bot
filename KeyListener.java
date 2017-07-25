import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyListener extends NativeKeyAdapter {

    private int lastKey = -1;
    private boolean comboDetected = false;
    private int[] keyCombo = new int[2];

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (lastKey == -1 || e.getKeyCode() == lastKey) {

            lastKey = e.getKeyCode();

        } else {

            keyCombo[0] = lastKey;
            keyCombo[1] = e.getKeyCode();

            comboDetected = true;

        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (comboDetected) {

                if (Menu.shouldRecord) {

                    Menu.userMovements.put("KeyCombo" + Menu.diffKeyPress, keyCombo);
                    Menu.diffKeyPress++;

                    lastKey = -1;

                }

                comboDetected = false;

            } else {

                Menu.userMovements.put("KeyBoard" + Menu.diffKeyPress, e.getKeyCode());
                Menu.diffKeyPress++;
                lastKey = -1;

            }
        }
    }
}