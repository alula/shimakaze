package space.alula.mod.event;

public class CommandEvent extends EventType {
    private final String message;

    public CommandEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
