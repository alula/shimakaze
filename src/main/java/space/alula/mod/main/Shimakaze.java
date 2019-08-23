package space.alula.mod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import space.alula.mod.event.UpdateEvent;
import space.alula.mod.gui.Overlay;
import space.alula.mod.util.EventBus;

public class Shimakaze {
    private static Shimakaze instance;

    public static Shimakaze getInstance() {
        if (instance == null) {
            instance = new Shimakaze();
        }

        return instance;
    }

    private final Minecraft minecraft;
    private final EventBus eventBus;
    private final ConsoleHandler consoleHandler;
    private final Overlay overlay;

    public Shimakaze() {
        this.minecraft = Minecraft.getInstance();
        this.eventBus = new EventBus();
        this.overlay = new Overlay();
        this.consoleHandler = new ConsoleHandler(this);
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public ConsoleHandler getConsoleHandler() {
        return consoleHandler;
    }

    public void sendMessage(String message) {
        minecraft.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(message));
    }

    private void registerEvents() {
        eventBus.registerEventType(UpdateEvent.class);
    }

    public void init() {
        registerEvents();
    }

    public void postInit() {

    }
}
