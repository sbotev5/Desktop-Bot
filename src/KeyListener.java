import org.jnativehook.keyboard.NativeKeyAdapter;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class KeyListener extends NativeKeyAdapter {

    /* A bit complicated approach to saving key combos but the keys bounce differently upon the invoking
       of nativeKeyPressed and nativeKeyReleased - some keys bounce continuously while others don't.
       I have worked around this and it works now with different key combinations.
     */

    private static boolean comboDetected = false; // to know when it is a combo and not a single key press
    private static int previousKey = -1; //used to store last key
    private static ArrayList<Integer> keyCombo; //passed in order to be stored
    private static int numKeysForRelease = 0;

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (Menu.shouldRecord) {

            if (previousKey == -1) {

                previousKey = e.getKeyCode();

            } else if (previousKey == e.getKeyCode()) {
                //if the key is already pressed and is bouncing while being held down ---> do nothing
            } else {

                if (numKeysForRelease == 0) {  //if it is the second key in the key combo

                    keyCombo = new ArrayList<>();

                    keyCombo.add(previousKey);  // add the first
                    keyCombo.add(e.getKeyCode());  // and the second

                    numKeysForRelease = 2;
                    comboDetected = true;

                } else {

                    if (keyCombo.contains(e.getKeyCode())) {

                        // because of bouncing (while being held done) some keys will constantly invoke the method
                        // if the key is already there and it is not the first time, nothing needs to be done

                    } else {

                        keyCombo.add(e.getKeyCode());
                        numKeysForRelease++;

                    }
                }
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        /*
            When a key is pressed nativeKeyReleased does not get called until it is released.
            If we have a key combo then the method will be called >= 2 times after each key release.
            We need to know when the keys making the key combo are released in order to store the key combo for the recording.
         */
        if (Menu.shouldRecord) {

            if (comboDetected) {

                // This is added in order to "absorb" when all the keys that are involved in an combination are released.

                if (numKeysForRelease > 0) {

                    numKeysForRelease--;

                } else {

                    Menu.currentRecording.add(new Movement("KeyCombo", keyCombo));

                    comboDetected = false;

                    keyCombo = new ArrayList<>();
                    numKeysForRelease = 0;
                    previousKey = -1;
                }

            } else {
                //just store single key
                Menu.currentRecording.add(new Movement("KeyBoard", e.getKeyCode()));
                previousKey = -1;

            }
        }
    }
}
