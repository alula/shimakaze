package space.alula.mod.gui;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.glfw.GLFW;
import space.alula.mod.event.CommandEvent;
import space.alula.mod.main.ConsoleHandler;
import space.alula.mod.main.Shimakaze;

import static org.lwjgl.nanovg.NanoVG.*;

public class CommandScreen extends SDrawable implements GuiInputHandler {
    private final StringBuilder buffer;
    private String lastString;
    private int cursor = 0;

    public CommandScreen() {
        buffer = new StringBuilder();
    }

    @Override
    public void render() {
        nvgSave(vg);
        nvgTranslate(vg, (width - 404) / 2, (height - 204) / 2);
        String cdata = lastString;
        int cpos = cursor;

        setColor(0xd0000000);
        drawRoundedRectMat(0, 0, 404, 204, 1);

        ConsoleHandler handler = Shimakaze.getInstance().getConsoleHandler();
        if (!handler.getLines().isEmpty()) {
            int entries = Math.min(handler.getLines().size(), 19);

            for (int i = 0; i < entries; i++) {
                Pair<ConsoleHandler.MessageType, String> messageTuple = handler.getLines().get(i);
                if (messageTuple.getRight().isEmpty()) continue;

                switch (messageTuple.getLeft()) {
                    case INPUT: // was meant to be yellow but i'm fucking colorblind and i wasn't able distinguish it from success lol
                        fontRenderer.draw(messageTuple.getRight(), 2, 182 - 10 * i, 20, 0f, 1f, 1f, 1, "inconsolata");
                        break;
                    case SUCCESS:
                        fontRenderer.draw(messageTuple.getRight(), 2, 182 - 10 * i, 20, 0f, 1f,  0f, 1, "inconsolata");
                        break;
                    case ERROR:
                        fontRenderer.draw(messageTuple.getRight(), 2, 182 - 10 * i, 20, 1f, .3f, .3f, 1, "inconsolata");
                        break;
                    case NORMAL:
                    default:
                        fontRenderer.draw(messageTuple.getRight(), 2, 182 - 10 * i, 20, .7f, .7f, .7f, 1, "inconsolata");
                        break;
                }
            }
        }

        if (lastString != null && lastString.length() > 0) {
            if (lastString.length() > 79) {
                if (cursor >= 80) {
                    cpos = 79;
                    cdata = lastString.substring(Math.max(cursor - 79, 0), Math.min(cursor + 1, lastString.length()));
                } else {
                    cdata = lastString.substring(0, 80);
                }
            }

            fontRenderer.draw(cdata, 2, 192, 20, .7f, .7f, .7f, 1, "inconsolata");
        }
        setColor(0xffcccccc);
        drawRect(2 + cpos * 5, 200, 4, 1);

        nvgRestore(vg);
    }

    @Override
    public boolean keyPressed(char typed) {
        if (typed == GLFW.GLFW_KEY_LEFT) {
            if (cursor > 0) {
                cursor--;
            }

            return true;
        } else if (typed == GLFW.GLFW_KEY_RIGHT) {
            if (cursor < buffer.length()) {
                cursor++;
            }

            return true;
        } else if (typed == GLFW.GLFW_KEY_HOME) {
            cursor = 0;
            return true;
        } else if (typed == GLFW.GLFW_KEY_END) {
            cursor = buffer.length();
            return true;
        } else if (typed == GLFW.GLFW_KEY_BACKSPACE) {
            if (buffer.length() > 0 && cursor > 0) {
                buffer.deleteCharAt(cursor - 1);
                lastString = buffer.toString();
                cursor--;
            }
            return true;
        } else if (typed == GLFW.GLFW_KEY_DELETE) {
            if (buffer.length() > 0 && cursor < buffer.length()) {
                buffer.deleteCharAt(cursor);
                lastString = buffer.toString();
            }
            return true;
        } else if (typed == GLFW.GLFW_KEY_ENTER) {
            if (buffer.length() == 0) return true;

            Shimakaze.getInstance().getEventBus().publish(new CommandEvent(buffer.toString()));

            buffer.setLength(0);
            lastString = "";
            cursor = 0;

            return true;
        } else if (typed >= ' ' && typed < '~') {
            buffer.insert(cursor, typed);
            lastString = buffer.toString();
            cursor++;
            return true;
        }

        return false;
    }
}
