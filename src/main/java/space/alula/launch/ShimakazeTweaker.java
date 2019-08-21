package space.alula.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShimakazeTweaker implements ITweaker {
    private final Logger logger = LogManager.getLogger();
    protected ArrayList<String> list = new ArrayList<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        list.addAll(args);
        if (!args.contains("--version") && profile != null) {
            list.add("--version");
            list.add(profile);
        }
        if (!args.contains("--assetsDir") && assetsDir != null) {
            list.add("--assetsDir");
            list.add(assetsDir.getAbsolutePath());
        }
        if (!args.contains("--gameDir") && gameDir != null) {
            list.add("--gameDir");
            list.add(gameDir.getAbsolutePath());
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        logger.info("Initializing mixins...");
        logger.info("JVM: {} {}", System.getProperty("java.vendor"), System.getProperty("java.version"));
        MixinBootstrap.init();
        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
        //Mixins.addConfiguration("mixins.emc.json");
        Mixins.addConfiguration("mixins.alumod.json");
        environment.setSide(MixinEnvironment.Side.CLIENT);
        environment.setObfuscationContext(ObfuscationServiceMCP.NOTCH);
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return list.toArray(new String[list.size()]);
    }
}
