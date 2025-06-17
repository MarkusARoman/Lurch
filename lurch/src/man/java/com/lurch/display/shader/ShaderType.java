package com.lurch.display.shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;

import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

/**
 * Represents the type of shader in OpenGL.
 * This enum defines the different types of shaders available in OpenGL,
 * each associated with its corresponding OpenGL constant.
 */
public enum ShaderType 
{
    VERTEX              (GL_VERTEX_SHADER),
    FRAGMENT            (GL_FRAGMENT_SHADER),
    GEOMETRY            (GL_GEOMETRY_SHADER),
    TESS_CONTROL        (GL_TESS_CONTROL_SHADER),
    TESS_EVALUATION     (GL_TESS_EVALUATION_SHADER),
    COMPUTE             (GL_COMPUTE_SHADER);

    /**
     * The OpenGL constant representing the shader type.
     */
    public final int glType;

    /**
     * Constructs a ShaderType with the specified OpenGL type.
     *
     * @param glType the OpenGL constant for the shader type
     */
    ShaderType(int glType) 
    {
        this.glType = glType;
    }
}
