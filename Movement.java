import java.io.Serializable;

class Movement implements Serializable {
    private String type;
    private Object movement;

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
