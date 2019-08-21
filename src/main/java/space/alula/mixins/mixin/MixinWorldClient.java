package space.alula.mixins.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import space.alula.mixins.imp.IMixinWorldClient;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient implements IMixinWorldClient {

}
