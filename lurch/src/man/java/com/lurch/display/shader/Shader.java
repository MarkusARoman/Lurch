package com.lurch.display.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Represents a shader in OpenGL.
 * This class encapsulates the creation, compilation, and management of OpenGL shaders.
 */
public class Shader 
{
    /**
     * Handle for the shader object.
     */
    private final int handle;


    /**
     * Creates a shader of the specified type.
     *
     * @param type the type of shader (e.g., GL_VERTEX_SHADER, GL_FRAGMENT_SHADER)
     */
    public Shader(int type)
    {
        handle = glCreateShader(type);
    }


    /**
     * Creates a shader of the specified type and sets its source code.
     *
     * @param type   the type of shader (e.g., GL_VERTEX_SHADER, GL_FRAGMENT_SHADER)
     * @param source the source code of the shader
     */
    public Shader(int type, CharSequence source)
    {
        this(type);
        setSource(source);
        compile();
    }


    /**
     * Creates a shader of the specified type and reads its source code from a file.
     *
     * @param type the type of shader (e.g., GL_VERTEX_SHADER, GL_FRAGMENT_SHADER)
     * @param path the path to the shader source file
     */
    public Shader(int type, String path)
    {
        this(type);
        try 
        {
            CharSequence source = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            setSource(source);
            compile();
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Failed to read shader source from " + path, e);
        }
    }


    /**
     * Compiles the shader.
     *
     * @throws RuntimeException if the shader compilation fails
     */
    public void compile()
    {
        glCompileShader(handle);

        /* Check for compilation error */
        if ( glGetShaderi(handle, GL_COMPILE_STATUS) == GL_FALSE )
        {
            throw new RuntimeException( glGetShaderInfoLog(handle) );
        }
    }


    /**
     * Sets the source code for the shader.
     *
     * @param source the source code of the shader
     */
    public void setSource(CharSequence source)
    {
        glShaderSource(handle, source);
    }


    /**
     * Gets the source code of the shader.
     *
     * @return the source code of the shader
     */
    public String getSource()
    {
        return glGetShaderSource(handle);
    }
    

    /**
     * Gets the handle of the shader.
     *
     * @return the handle of the shader
     */
    public int getHandle()
    {
        return handle;
    }


    /**
     * Checks if the shader is compiled.
     *
     * @return true if the shader is compiled, false otherwise
     */
    public void delete()
    {
        if (handle != 0)
        {
            glDeleteShader(handle);
        }
    }
}
