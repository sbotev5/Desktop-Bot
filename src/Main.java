import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static Robot robot;
    static Map<Integer, Integer> keyboard;

    public static void main(String[] args) {

        setUpKeyBoard();
        setUpHook();

        try {
//dr
            robot = new Robot();

        } catch (AWTException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            Menu menu = new Menu(robot);
            menu.initialize();
            menu.setVisible(true);
            menu.setSize(700, 700);
            menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        });

    }

    private static void setUpHook() {

        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {

            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {

            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        MouseListener ml = new MouseListener();

        GlobalScreen.addNativeMouseListener(ml);
        GlobalScreen.addNativeMouseMotionListener(ml);

        GlobalScreen.addNativeKeyListener(new KeyListener());
        GlobalScreen.addNativeMouseWheelListener(new MouseWheelListener());
    }

    private static void setUpKeyBoard() {
        //map that pairs JNativeHook key codes to KeyEvents for the Robot class
        keyboard = new HashMap<>();

        keyboard.put(1, KeyEvent.VK_ESCAPE);
        keyboard.put(2400, KeyEvent.KEY_FIRST);
        keyboard.put(2402, KeyEvent.KEY_LAST);
        keyboard.put(2401, KeyEvent.KEY_PRESSED);
        keyboard.put(0, KeyEvent.KEY_LOCATION_UNKNOWN);

        keyboard.put(58, KeyEvent.VK_CAPS_LOCK);
        keyboard.put(12, KeyEvent.VK_MINUS);
        keyboard.put(13, KeyEvent.VK_EQUALS);
        keyboard.put(14, KeyEvent.VK_BACK_SPACE);
        keyboard.put(15, KeyEvent.VK_TAB);
        keyboard.put(41, KeyEvent.VK_BACK_QUOTE);
        keyboard.put(26, KeyEvent.VK_OPEN_BRACKET);
        keyboard.put(27, KeyEvent.VK_CLOSE_BRACKET);
        keyboard.put(43, KeyEvent.VK_BACK_SLASH);
        keyboard.put(39, KeyEvent.VK_SEMICOLON);
        keyboard.put(40, KeyEvent.VK_QUOTE);
        keyboard.put(28, KeyEvent.VK_ENTER);
        keyboard.put(51, KeyEvent.VK_COMMA);
        keyboard.put(52, KeyEvent.VK_PERIOD);
        keyboard.put(53, KeyEvent.VK_SLASH);
        keyboard.put(56, KeyEvent.VK_ALT);
        keyboard.put(29, KeyEvent.VK_CONTROL);
        keyboard.put(57, KeyEvent.VK_SPACE);
        keyboard.put(3639, KeyEvent.VK_PRINTSCREEN);
        keyboard.put(3653, KeyEvent.VK_PAUSE);
        keyboard.put(3666, KeyEvent.VK_INSERT);
        keyboard.put(3667, KeyEvent.VK_DELETE);
        keyboard.put(3655, KeyEvent.VK_HOME);
        keyboard.put(3663, KeyEvent.VK_END);

        keyboard.put(2, KeyEvent.VK_1);
        keyboard.put(3, KeyEvent.VK_2);
        keyboard.put(4, KeyEvent.VK_3);
        keyboard.put(5, KeyEvent.VK_4);
        keyboard.put(6, KeyEvent.VK_5);
        keyboard.put(7, KeyEvent.VK_6);
        keyboard.put(8, KeyEvent.VK_7);
        keyboard.put(9, KeyEvent.VK_8);
        keyboard.put(10, KeyEvent.VK_9);
        keyboard.put(11, KeyEvent.VK_0);

        keyboard.put(59, KeyEvent.VK_F1);
        keyboard.put(60, KeyEvent.VK_F2);
        keyboard.put(61, KeyEvent.VK_F3);
        keyboard.put(62, KeyEvent.VK_F4);
        keyboard.put(63, KeyEvent.VK_F5);
        keyboard.put(64, KeyEvent.VK_F6);
        keyboard.put(65, KeyEvent.VK_F7);
        keyboard.put(66, KeyEvent.VK_F8);
        keyboard.put(67, KeyEvent.VK_F9);
        keyboard.put(68, KeyEvent.VK_F10);
        keyboard.put(87, KeyEvent.VK_F11);
        keyboard.put(88, KeyEvent.VK_F12);
        keyboard.put(91, KeyEvent.VK_F13);
        keyboard.put(92, KeyEvent.VK_F14);
        keyboard.put(93, KeyEvent.VK_F15);
        keyboard.put(99, KeyEvent.VK_F16);
        keyboard.put(100, KeyEvent.VK_F17);
        keyboard.put(101, KeyEvent.VK_F18);
        keyboard.put(102, KeyEvent.VK_F19);
        keyboard.put(103, KeyEvent.VK_F20);
        keyboard.put(104, KeyEvent.VK_F21);
        keyboard.put(105, KeyEvent.VK_F22);
        keyboard.put(106, KeyEvent.VK_F23);
        keyboard.put(107, KeyEvent.VK_F24);

        keyboard.put(30, KeyEvent.VK_A);
        keyboard.put(48, KeyEvent.VK_B);
        keyboard.put(46, KeyEvent.VK_C);
        keyboard.put(32, KeyEvent.VK_D);
        keyboard.put(18, KeyEvent.VK_E);
        keyboard.put(33, KeyEvent.VK_F);
        keyboard.put(34, KeyEvent.VK_G);
        keyboard.put(35, KeyEvent.VK_H);
        keyboard.put(23, KeyEvent.VK_I);
        keyboard.put(36, KeyEvent.VK_J);
        keyboard.put(37, KeyEvent.VK_K);
        keyboard.put(38, KeyEvent.VK_L);
        keyboard.put(50, KeyEvent.VK_M);
        keyboard.put(49, KeyEvent.VK_N);
        keyboard.put(24, KeyEvent.VK_O);
        keyboard.put(25, KeyEvent.VK_P);
        keyboard.put(16, KeyEvent.VK_Q);
        keyboard.put(19, KeyEvent.VK_R);
        keyboard.put(31, KeyEvent.VK_S);
        keyboard.put(20, KeyEvent.VK_T);
        keyboard.put(22, KeyEvent.VK_U);
        keyboard.put(47, KeyEvent.VK_V);
        keyboard.put(17, KeyEvent.VK_W);
        keyboard.put(45, KeyEvent.VK_X);
        keyboard.put(21, KeyEvent.VK_Y);
        keyboard.put(44, KeyEvent.VK_Z);
    }
}