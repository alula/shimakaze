package space.alula.mod.gui;

import net.minecraft.client.gui.GuiButton;

public class SButton extends GuiButton {
    private Runnable onClick;

    public SButton(int buttonId, int x, int y, String buttonText, Runnable onClick) {
        super(buttonId, x, y, buttonText);
        this.onClick = onClick;
    }

    public SButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, Runnable onClick) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.onClick = onClick;
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        if (onClick != null) {
            onClick.run();
        }
    }
}
