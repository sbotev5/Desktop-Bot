import java.io.Serializable;

class Movement<T> implements Serializable {

    private String type;  //MouseMove, MouseClick, KeyPress etc.
    private T movement; //Robot class needs a Point for mouse movements; JNativeHook works with key codes (ints) for key presses

    Movement(String type, T movement) {

        this.type = type;
        this.movement = movement;

    }

    String getType() {
        return type;
    }

    T getMovement() {
        return movement;
    }
}
