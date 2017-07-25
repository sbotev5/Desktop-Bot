import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class MouseWheelListener implements NativeMouseWheelListener {

    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {


        if (Menu.shouldRecord) {

            Menu.userMovements.put("WheelMove"+ Menu.diffMouseMove, e.getWheelRotation());
            Menu.diffMouseMove++;

        }


    }
}
