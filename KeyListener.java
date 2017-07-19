import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
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

                Menu.userMovements.put("KeyCombo" + Menu.keyPressCounter, keyCombo);
                Menu.keyPressCounter++;
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

                Menu.userMovements.put("KeyBoard" + Menu.keyPressCounter, lastKey);
                Menu.keyPressCounter++;
                lastKey = -1;

            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }


}