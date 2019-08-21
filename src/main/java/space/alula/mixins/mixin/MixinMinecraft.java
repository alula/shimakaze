package space.alula.mixins.mixin;

import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.resources.VanillaPack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.alula.mod.event.UpdateEvent;
import space.alula.mod.gui.MainMenuScreen;
import space.alula.mod.gui.SDrawable;
import space.alula.mod.main.Shimakaze;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {
    @Shadow
    private WorldClient world;

    @Shadow
    private EntityPlayerSP player;

    @Shadow
    private GuiIngame ingameGUI;

    @Shadow
    private GuiScreen currentScreen;

    @Shadow
    private GameSettings gameSettings;

    @Shadow
    private MouseHelper mouseHelper;

    @Shadow
    private SoundHandler soundHandler;

    @Shadow
    private MainWindow mainWindow;

    @Shadow
    private boolean skipRenderWorld;

    @Shadow
    private IReloadableResourceManager resourceManager;

    private Shimakaze aluMod;

    @Inject(method = "init", at = @At("HEAD"))
    public void preInit(CallbackInfo ci) {
        aluMod = Shimakaze.getInstance();
        aluMod.initialize();
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void postInit(CallbackInfo ci) {
        ((SimpleReloadableResourceManager) resourceManager).addResourcePack(new VanillaPack("shimakaze"));
    }

    @Inject(method = "initMainWindow", at = @At("RETURN"))
    public void postGLInit(CallbackInfo ci) {
        SDrawable.glInit();
    }

    @Overwrite
    public void displayGuiScreen(@Nullable GuiScreen screen) {
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }

        if (screen == null && this.world == null) {
            screen = new MainMenuScreen();
        } else if (screen == null && this.player.getHealth() <= 0.0F) {
            screen = new GuiGameOver(null);
        }

        if (screen instanceof GuiMainMenu || screen instanceof GuiMultiplayer) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages(true);
        }

        if (screen instanceof GuiMainMenu) {
            screen = new MainMenuScreen();
        }

        this.currentScreen = screen;
        if (screen != null) {
            this.mouseHelper.ungrabMouse();
            KeyBinding.unPressAllKeys();
            screen.setWorldAndResolution((Minecraft) (Object) this,
                    this.mainWindow.getScaledWidth(),
                    this.mainWindow.getScaledHeight());
            this.skipRenderWorld = false;
        } else {
            this.soundHandler.resume();
            this.mouseHelper.grabMouse();
        }
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (aluMod != null) {
            aluMod.getEventBus().publishEmpty(UpdateEvent.class);
        }
    }
}
