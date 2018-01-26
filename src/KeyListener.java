import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.LinkedList;

public class KeyListener extends NativeKeyAdapter {

    public void nativeKeyReleased(NativeKeyEvent e) {

//  DEBUG OUTPUT COMMENTED OUT
//        System.out.println(NativeInputEvent.getModifiersText(e.getModifiers()).equals(""));
//
//        System.out.println("I just pressed" + e.getKeyCode());
//
//        LinkedList<Integer> whichModifiers = resolveModifiers(e.getModifiers());
//
//        whichModifiers.add(e.getKeyCode());
//
//        System.out.println("----- resulting list:");
//        for(int i : whichModifiers){
//            System.out.println(i);
//        }

        if (Menu.shouldRecord) {

            if (NativeInputEvent.getModifiersText(e.getModifiers()).equals("")) {

                Menu.singleRecording.add(new Movement("KeyPress", e.getKeyCode()));

            } else {

                LinkedList<Integer> whichModifiers = resolveModifiers(e.getModifiers());
                whichModifiers.add(e.getKeyCode());

                Menu.singleRecording.add(new Movement("KeyCombo", whichModifiers));
            }
        }
    }

    private LinkedList<Integer> resolveModifiers(int modifiers) {

        LinkedList<Integer> whichModifiers = new LinkedList<>();


        if ((modifiers & 17) != 0) {
            whichModifiers.add(NativeKeyEvent.VC_SHIFT);

        }

        if ((modifiers & 34) != 0) {
            whichModifiers.add(NativeKeyEvent.VC_CONTROL);

        }

        if ((modifiers & 68) != 0) {
            whichModifiers.add(NativeKeyEvent.VC_META);

        }

        if ((modifiers & 136) != 0) {
            whichModifiers.add(NativeKeyEvent.VC_ALT);

        }
        return whichModifiers;
    }
}
