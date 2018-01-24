import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

public class KeyListener extends NativeKeyAdapter {

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (NativeKeyEvent.getModifiersText(e.getModifiers()).contains("+")) {
                //  System.out.println(NativeKeyEvent.getModifiersText(e.getModifiers()));
                // System.out.println(e.getKeyCode() + "sus kombo");
            } else {
                // Menu.singleRecording.add(new Movement("KeyPress", e.getKeyCode()));
                // System.out.println(e.isActionKey());
                // System.out.println(e.getKeyCode() + "bez  kombo");
            }
        }
    }
}