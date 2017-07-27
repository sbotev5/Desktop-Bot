import java.io.Serializable;

public class Movement implements Serializable {
    private String type;
    private Object movement;

    Movement(String type, Object movement) {
        this.type = type;
        this.movement = movement;
    }

    public String getType() {
        return type;
    }

    public Object getMovement() {
        return movement;
    }
}
