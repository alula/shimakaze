package space.alula.mod.gui;

public class Overlay extends SDrawable {
    @Override
    public void render() {
        setColor(1, 1, 1, 1);
        drawRoundedRectMat(2, 2, 100, 50, 1);
    }
}
