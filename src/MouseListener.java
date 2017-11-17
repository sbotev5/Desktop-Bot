import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputAdapter;

import java.awt.*;

public class MouseListener extends NativeMouseInputAdapter {

    public void nativeMouseReleased(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement("MouseButton", e.getButton()));

        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement("MouseMove", new Point(e.getX(), e.getY())));

        }

    }

    public void nativeMouseDragged(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement("MouseDrag", new Point(e.getX(), e.getY())));

        }
    }
}