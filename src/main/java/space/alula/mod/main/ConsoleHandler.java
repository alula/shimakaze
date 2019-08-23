package space.alula.mod.main;

import org.apache.commons.lang3.tuple.Pair;
import space.alula.mod.event.CommandEvent;

import java.util.LinkedList;
import java.util.List;

public class ConsoleHandler {
    private final List<Pair<MessageType, String>> lines;

    public ConsoleHandler(Shimakaze shimakaze) {
        this.lines = new LinkedList<>();

        shimakaze.getEventBus().addHandler(CommandEvent.class, this::handleCommand);
    }

    public List<Pair<MessageType, String>> getLines() {
        return lines;
    }

    public void send(MessageType type, String message) {
        if (message.length() > 80) {
            String[] splits = message.split("(?<=\\G.{80})");

            for (String split : splits) {
                doSend(type, split);
            }
        } else {
            doSend(type, message);
        }
    }

    private void doSend(MessageType type, String message) {
        lines.add(0, Pair.of(type, message));
        if (lines.size() > 40) {
            lines.remove(lines.size() - 1);
        }
    }

    private boolean handleCommand(CommandEvent event) {
        send(MessageType.INPUT, event.getMessage());
        send(MessageType.SUCCESS, "You are very gay btw and typed " + event.getMessage() + " xD");
        return true;
    }

    public enum MessageType {
        INPUT, NORMAL, SUCCESS, ERROR
    }
}
