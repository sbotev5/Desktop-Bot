import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyListener extends NativeKeyAdapter {

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            Menu.currentRecording.add(new Movement("KeyPress", e.getKeyCode()));

        }
    }

}