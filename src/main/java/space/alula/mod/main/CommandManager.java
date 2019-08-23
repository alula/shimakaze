package space.alula.mod.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandManager {
    private Map<String, Consumer<List<String>>> commandMap;

    public CommandManager() {
        this.commandMap = new HashMap<>();
    }

    public void registerCommand(String name, Consumer<List<String>> handler) {
        commandMap.put(name, handler);
    }

    public Map<String, Consumer<List<String>>> getCommandMap() {
        return commandMap;
    }
}
