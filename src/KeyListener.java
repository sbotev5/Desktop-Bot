import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {


    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {
            Menu.actions.put("KeyBoard" + Menu.kpCounter, e.getKeyCode());
            Menu.kpCounter++;
        }

    }

    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }


}
