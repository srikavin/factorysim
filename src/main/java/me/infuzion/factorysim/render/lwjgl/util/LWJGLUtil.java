package me.infuzion.fractorio.render.lwjgl.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.memUTF8;

public class LWJGLUtil {

    /**
     * Create a shader object from the given classpath resource.
     *
     * @param resource the class path
     * @param type     the shader type
     * @return the shader object id
     * @throws IOException
     */
    public static int createShader(String resource, int type) {
        try {
            return createShader(resource, type, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    /**
     * Reads the specified resource and returns the raw data as a ByteBuffer.
     *
     * @param resource   the resource to read
     * @param bufferSize the initial buffer size
     * @return the resource data
     * @throws IOException if an IO error occurs
     */
    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
        try {
            ByteBuffer buffer;
            URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
            File file = new File(url.getFile());
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                FileChannel fc = fis.getChannel();
                buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                fc.close();
                fis.close();
            } else {
                buffer = BufferUtils.createByteBuffer(bufferSize);
                InputStream source = url.openStream();
                if (source == null) {
                    throw new FileNotFoundException(resource);
                }
                try {
                    ReadableByteChannel rbc = Channels.newChannel(source);
                    try {
                        while (true) {
                            int bytes = rbc.read(buffer);
                            if (bytes == -1) {
                                break;
                            }
                            if (buffer.remaining() == 0) {
                                buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                            }
                        }
                        buffer.flip();
                    } finally {
                        rbc.close();
                    }
                } finally {
                    source.close();
                }
            }
            return buffer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a shader object from the given classpath resource.
     *
     * @param resource the class path
     * @param type     the shader type
     * @param version  the GLSL version to prepend to the shader source, or null
     * @return the shader object id
     * @throws IOException
     */
    public static int createShader(String resource, int type, String version) throws IOException {
        int shader = glCreateShader(type);

        ByteBuffer source = ioResourceToByteBuffer(resource, 8192);

        if (version == null) {
            PointerBuffer strings = BufferUtils.createPointerBuffer(1);
            IntBuffer lengths = BufferUtils.createIntBuffer(1);

            strings.put(0, source);
            lengths.put(0, source.remaining());

            glShaderSource(shader, strings, lengths);
        } else {
            PointerBuffer strings = BufferUtils.createPointerBuffer(2);
            IntBuffer lengths = BufferUtils.createIntBuffer(2);

            ByteBuffer preamble = memUTF8("#version " + version + "\n", false);

            strings.put(0, preamble);
            lengths.put(0, preamble.remaining());

            strings.put(1, source);
            lengths.put(1, source.remaining());

            glShaderSource(shader, strings, lengths);
        }

        glCompileShader(shader);
        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        String shaderLog = glGetShaderInfoLog(shader);
        if (shaderLog != null && shaderLog.trim().length() > 0) {
            System.err.println(shaderLog);
        }
        if (compiled == 0) {
            throw new AssertionError("Could not compile shader");
        }
        return shader;
    }
}
