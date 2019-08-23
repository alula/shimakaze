package space.alula.mod.util;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class Util {
    private static int cycle;

    public static FadeType fadeType = FadeType.GAY;
    private static int[] gradientTrans = {0x55cdfc, 0xf7a8b8, 0xffffff, 0xf7a8b8};
    private static int[] gradientPolice = {0xff0000, 0xffffff, 0x0000ff, 0xffffff};

    public static void updateCycle(float delta) {
        cycle += (int) (delta * 100);
    }

    public static int cycleColor() {
        switch (fadeType) {
            case GAY:
                return cycleColor(cycle);
            case TRANS:
                return gradientXFade(gradientTrans, cycle, 10000f);
            case POLICE:
                return gradientXFade(gradientPolice, cycle, 2500f);
            case WHITE:
            default:
                return 0xffffff;
        }
    }

    private static int gradientXFade(int[] colors, int cycle, float speed) {
        if (colors.length == 0) return 0xffffff;

        float t = (cycle % (int) speed) / speed;

        int idx = (int) Math.floor(t * colors.length) + 1;
        float fadeProg = (t * colors.length) % 1f;

        int col1 = colors[idx - 1];
        int col2 = colors[idx == colors.length ? 0 : idx];

        return ((int) (((col1 >> 16) & 0xff) + (((col2 >> 16) & 0xff) - ((col1 >> 16) & 0xff)) * fadeProg) << 16)
                | ((int) (((col1 >> 8) & 0xff) + (((col2 >> 8) & 0xff) - ((col1 >> 8) & 0xff)) * fadeProg) << 8)
                | (int) ((col1 & 0xff) + ((col2 & 0xff) - (col1 & 0xff)) * fadeProg);
    }

    private static int cycleColor(int cycle) {
        double pi3 = 2 * Math.PI / 3;
        double freq = 0.001;
        return (((int) (Math.sin(freq * cycle) * 127 + 128) & 0xff) << 16)
                | (((int) (Math.sin(freq * cycle + pi3) * 127 + 128) & 0xff) << 8)
                | ((int) (Math.sin(freq * cycle + pi3 + pi3) * 127 + 128) & 0xff);
    }

    public enum FadeType {
        WHITE, GAY, TRANS, POLICE
    }

    // skidded from LWJGL demos
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = Util.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }
}
