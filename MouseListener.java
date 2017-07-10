import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;

public class MouseListener implements NativeMouseInputListener {


    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeMousePressed(NativeMouseEvent e) {


    }

    public void nativeMouseReleased(NativeMouseEvent e) {

        if (Menu.shouldRecord) {
            Menu.actions.put("MouseButton" + Menu.mpCounter, e.getButton());
            Menu.mpCounter++;

        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {

        if (Menu.shouldRecord) {
            Menu.actions.put("MouseMove" + Menu.mmCounter, new Point(e.getX(), e.getY()));
            Menu.mmCounter++;

        }

    }

    public void nativeMouseDragged(NativeMouseEvent e) {

    }

}