import java.io.Serializable;

class Movement implements Serializable {

    private String type;  //MouseMove, MouseClick, KeyPress etc.
    private Object movement; //Robot class needs a Point for mouse movements; JNativeHook works with key codes (ints) for key presses

    Movement(String type, Object movement) {
        this.type = type;
        this.movement = movement;
    }

    String getType() {
        return type;
    }

    Object getMovement() {
        return movement;
    }
}
