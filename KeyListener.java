import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyListener extends NativeKeyAdapter {

    private int lastKey = -1;
    private boolean comboAdded = false;

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (lastKey == -1 || e.getKeyCode() == lastKey) {

            lastKey = e.getKeyCode();

        } else {

            int[] keyCombo = new int[2];
            keyCombo[0] = lastKey;
            keyCombo[1] = e.getKeyCode();

            if (Menu.shouldRecord) {

                System.out.println(keyCombo[0]);
                System.out.println(keyCombo[1]);

                Menu.userMovements.put("KeyCombo" + Menu.diffKeyPress, keyCombo);
                Menu.diffKeyPress++;
                comboAdded = true;
                lastKey = -1;

            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (comboAdded) {

                comboAdded = false;

            } else {

                Menu.userMovements.put("KeyBoard" + Menu.diffKeyPress, e.getKeyCode());
                Menu.diffKeyPress++;
                lastKey = -1;

            }
        }
    }

}