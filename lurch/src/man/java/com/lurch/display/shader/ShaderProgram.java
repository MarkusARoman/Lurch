package com.lurch.display.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.function.IntConsumer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;


public class ShaderProgram 
{
    /**
     * Handle for the shader program object.
     */
    private final int handle;


    /**
     * Creates a shader program with the specified handle.
     *
     * @param handle the handle of the shader program
     */
    public ShaderProgram(Shader... shaders) 
    {
        /* Validate input */
        if (shaders == null || shaders.length == 0) 
        {
            throw new IllegalArgumentException("At least one shader must be provided");
        }


        /* Create the shader program */
        handle = glCreateProgram();

        if (handle == 0) 
        {
            throw new RuntimeException("Failed to create shader program");
        }


        /* Attach shaders to the program */
        for (Shader shader : shaders) 
        {
            glAttachShader(handle, shader.getHandle());
        }


        /* Link the program */
        glLinkProgram(handle);


        /* Check for linking error */
        if (glGetProgrami(handle, GL_LINK_STATUS) == GL_FALSE) 
        {
            String infoLog = glGetProgramInfoLog(handle);
            throw new RuntimeException("Shader program linking failed:\n" + infoLog);
        }


        /* Validate the program */
        glValidateProgram(handle);


        /* Check for validation error */
        if (glGetProgrami(handle, GL_VALIDATE_STATUS) == GL_FALSE) 
        {
            String infoLog = glGetProgramInfoLog(handle);
            System.err.println("Shader program validation warning:\n" + infoLog);
        }


        /* Delete shaders after linking */
        for (Shader shader : shaders) 
        {
            shader.delete();
        }
    }


    /**
     * Installs the shader program for use in rendering.
     */
    public void install()
    {
        glUseProgram(handle);
    }


    /**
     * Uninstalls the shader program, reverting to the default shader program.
     */
    public void uninstall()
    {
        glUseProgram(0);
    }


    /**
     * Executes the provided action if the uniform variable is found in the shader program.
     * 
     * @param name   The name of the uniform variable.
     * @param action The action to perform with the uniform location.
     */
    public void withUniform(String name, IntConsumer action) 
    {
        int location = getUniformLocation(name);


        /* Only execute the action if the uniform location is valid */
        if (location != -1) 
        {
            action.accept(location);
        }
    }

    /**
     * Returns the location of a uniform variable for this program.
     *
     * @param name the name of the uniform variable
     * @return the location of the uniform variable, or -1 if not found
     */
    public int getUniformLocation(String name) 
    {
        return glGetUniformLocation(handle, name);
    }


    // --- Scalar uniforms --- //

    public void setUniform(String name, int value) {
        withUniform(name, loc -> glUniform1i(loc, value));
    }

    public void setUniform(String name, float value) {
        withUniform(name, loc -> glUniform1f(loc, value));
    }

    public void setUniform(String name, boolean value) {
        withUniform(name, loc -> glUniform1i(loc, value ? 1 : 0));
    }


    // --- Vector uniforms --- //

    public void setUniform(String name, Vector2f vec) {
        withUniform(name, loc -> glUniform2f(loc, vec.x, vec.y));
    }

    public void setUniform(String name, Vector3f vec) {
        withUniform(name, loc -> glUniform3f(loc, vec.x, vec.y, vec.z));
    }

    public void setUniform(String name, Vector4f vec) {
        withUniform(name, loc -> glUniform4f(loc, vec.x, vec.y, vec.z, vec.w));
    }


    // --- Matrix uniforms --- //

    public void setUniform(String name, Matrix3f mat) {
        withUniform(name, loc -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(9);
                mat.get(buffer);
                glUniformMatrix3fv(loc, false, buffer);
            }
        });
    }

    public void setUniform(String name, Matrix4f mat) {
        withUniform(name, loc -> {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(16);
                mat.get(buffer);
                glUniformMatrix4fv(loc, false, buffer);
            }
        });
    }


    /**
     * Deletes the shader program.
     */
    public void delete()
    {
        if (handle != 0) 
        {
            glDeleteProgram(handle);
        }
    }


    /**
     * Returns the handle of the shader program.
     *
     * @return the handle of the shader program
     */
    public int getHandle()
    {
        return handle;
    }
}
