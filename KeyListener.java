import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {


    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {
            Menu.userMovements.put("KeyBoard" + Menu.keyPressCounter, e.getKeyCode());
            Menu.keyPressCounter++;
        }


        //System.out.println("KEY PRESSED");
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
       // System.out.println("KEY RELEASED");
    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }


}