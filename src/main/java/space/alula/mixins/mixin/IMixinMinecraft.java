package space.alula.mixins.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IMixinMinecraft {
    @Accessor
    static int getDebugFPS() {
        throw new IllegalStateException("getDebugFPS failed to inject?");
    }
}
