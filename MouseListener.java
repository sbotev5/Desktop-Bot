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

            Menu.userMovements.put("MouseButton" + Menu.mousePressCounter, e.getButton());
            Menu.mousePressCounter++;

        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.userMovements.put("MouseMove" + Menu.mouseMoveCounter, new Point(e.getX(), e.getY()));
            Menu.mouseMoveCounter++;

        }

    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        if (Menu.shouldRecord) {

            Menu.userMovements.put("MouseDrag" + Menu.mouseDragCounter, new Point(e.getX(), e.getY()));
            Menu.mouseDragCounter++;

        }
    }

}