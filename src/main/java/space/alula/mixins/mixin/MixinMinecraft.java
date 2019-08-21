package space.alula.mixins.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.alula.mixins.imp.IMixinMinecraft;
import space.alula.mod.event.UpdateEvent;
import space.alula.mod.gui.SDrawable;
import space.alula.mod.gui.MainMenuScreen;
import space.alula.mod.main.Shimakaze;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {
    @Shadow
    public WorldClient world;

    @Shadow
    public EntityPlayerSP player;

    @Shadow
    public GuiIngame ingameGUI;

    public Shimakaze aluMod;

    @Inject(method = "init", at = @At("HEAD"))
    public void preInit(CallbackInfo ci) {
        aluMod = Shimakaze.getInstance();
        aluMod.initialize();
    }

    @Inject(method = "initMainWindow", at = @At("RETURN"))
    @Shadow
    public void postGLInit(CallbackInfo ci) {
        SDrawable.glInit();
    }

    @Override
    public void addChatMessage(ITextComponent component) {
        ingameGUI.addChatMessage(ChatType.CHAT, component);
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    public void displayGuiScreen(@Nullable GuiScreen screen, CallbackInfo ci) {
        if (screen == null && this.world == null) {
            screen = new MainMenuScreen();
        } else if (screen == null && this.player.getHealth() <= 0.0F) {
            screen = new GuiGameOver(null);
        }

        if (screen instanceof GuiMainMenu) {
            screen = new MainMenuScreen();
        }
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (aluMod != null) {
            aluMod.getEventBus().publishEmpty(UpdateEvent.class);
        }
    }
}
