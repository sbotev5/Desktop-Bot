import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {


    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            Menu.userMovements.put("KeyBoard" + Menu.keyPressCounter, e.getKeyCode());
            Menu.keyPressCounter++;

        }

    }

    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }


}