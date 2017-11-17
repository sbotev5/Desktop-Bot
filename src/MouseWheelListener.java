import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class MouseWheelListener implements NativeMouseWheelListener {

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {

        if (Menu.shouldRecord) {

            Menu.singleRecording.add(new Movement("WheelMove", e.getWheelRotation()));

        }
    }
}
