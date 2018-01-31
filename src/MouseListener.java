import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputAdapter;

public class MouseListener extends NativeMouseInputAdapter {

    public void nativeMouseReleased(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement<>("MouseButton", e.getButton()));

        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement<>("MouseMove", e.getPoint()));

        }

    }

    public void nativeMouseDragged(NativeMouseEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement<>("MouseDrag", e.getPoint()));

        }
    }
}