package me.infuzion.engine.render.lwjgl.util;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private final Map<String, Integer> uniforms;
    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create Shader");
        }
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(programId,
                uniformName);
        if (uniformLocation < 0) {
            throw new RuntimeException("Could not find uniform:" +
                    uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public int getProgramId() {
        return programId;
    }

    public void createVertexShader(String shaderCode) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    protected int createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new RuntimeException("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}