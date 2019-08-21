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

    private final EventBus eventBus;
    private final Minecraft minecraft;
    private final Overlay overlay;

    public Shimakaze() {
        this.minecraft = Minecraft.getInstance();
        this.eventBus = new EventBus();
        this.overlay = new Overlay();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Overlay getOverlay() {
        return overlay;
    }

    public void sendMessage(String message) {
        minecraft.ingameGUI.addChatMessage(ChatType.CHAT, new TextComponentString(message));
    }

    private void registerEvents() {
        eventBus.registerEventType(UpdateEvent.class);
        eventBus.addListener(UpdateEvent.class, (t) -> {
            if (minecraft.world != null && (minecraft.world.getGameTime() % 200) == 0) {
                sendMessage("Your mom gay! \u00a7d" + minecraft.world.getGameTime());
            }
            return true;
        });
    }

    public void initialize() {
        registerEvents();
        System.out.println("AluMod initialized " + getClass().getClassLoader());
    }
}
