package space.alula.mixins.imp;

import net.minecraft.util.text.ITextComponent;

public interface IMixinMinecraft {
    void addChatMessage(ITextComponent component);

    IMixinWorldClient getWorld();
}
