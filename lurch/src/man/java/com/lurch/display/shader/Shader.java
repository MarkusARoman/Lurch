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
     * Path to the shader files.
     * This is used to locate shader source files.
     */
    private final String SHADER_FILE = "lurch/src/main/resource/shader/";


    /**
     * Creates a shader of the specified type and sets its source code.
     *
     * @param type   the type of shader (e.g., GL_VERTEX_SHADER, GL_FRAGMENT_SHADER)
     * @param source the source code of the shader
     */
    public Shader(int type, CharSequence source)
    {
        handle = glCreateShader(type);
        setSource(source);
        compile();
    }


    /**
     * Creates a shader of the specified type and reads its source code from a file.
     *
     * @param type the type of shader (e.g., GL_VERTEX_SHADER, GL_FRAGMENT_SHADER)
     * @param path the path to the shader source file
     */
    public Shader(int type, String name)
    {
        handle = glCreateShader(type);


        /* Construct the full path to the shader source file */
        String path = SHADER_FILE + name;


        /* Check if the shader source file exists */
        if ( !Files.exists(Paths.get(path)) )
        {
            throw new RuntimeException("Shader source file not found: " + path);
        }


        /* Read the shader source code from the file */
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


    public Shader(String name)
    {
        /* Infer the shader type from file extension */
        String extension = getExtension(name);
        ShaderType type = ShaderType.fromExtension(extension);

        this.handle = glCreateShader(type.getGLType());

        String path = SHADER_FILE + name;

        if (!Files.exists(Paths.get(path))) {
            throw new RuntimeException("Shader source file not found: " + path);
        }

        try {
            CharSequence source = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            setSource(source);
            compile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read shader source from " + path, e);
        }
    }


    /**
     * Gets the file extension from the shader file name.
     *
     * @param name the name of the shader file
     * @return the file extension (e.g., "vert", "frag")
     */
    private String getExtension(String name)
    {
        /* Validate the file name */
        if (name == null || name.isEmpty()) 
        {
            throw new IllegalArgumentException("Shader file name cannot be null or empty");
        }


        try 
        {
            /* Find the last dot in the file name */
            int dotIndex = name.lastIndexOf('.');


            /* Validate the file name */
            if (dotIndex == -1 || dotIndex == name.length() - 1) 
            {
                throw new IllegalArgumentException("Shader file name must have an extension: " + name);
            }


            /* Extract and return the file extension */
            return name.substring(dotIndex + 1);
        } 
        catch (Exception e) 
        {
            throw new IllegalArgumentException("Shader file name must contain a valid extension: " + name, e);
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
