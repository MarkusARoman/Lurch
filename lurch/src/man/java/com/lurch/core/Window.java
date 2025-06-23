package com.lurch.core;


import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represents a GLFW window.
 * This class encapsulates the creation and management of a GLFW window,
 * including handling input events and rendering.
 */
public class Window 
{
    /**
     * Identifier for window handle.
     */
    private final long handle;


    /**
     * Window width in pixels.
     */
    private final int width;


    /**
     * Window height in pixels.
     */
    private final int height;


    /**
     * Window key callback.
     */
    private final GLFWKeyCallback keyCallback;


    /**
     * Creates a GLFW window with the specified width, height, and title.
     * 
     * @param width  window width
     * @param height window height
     * @param title  window title
     */
    public Window(int width, int height, CharSequence title)
    {
        /* Restore default window hints */
        glfwDefaultWindowHints();

        
        /* Set GLFW error callback to print errors to the standard error stream */
        GLFWErrorCallback.createPrint(System.err).set();

        
        /* Initialize GLFW */
        if ( !glfwInit() )
        {
            throw new IllegalStateException("Failed to initialize GLFW");
        }
        

        /* Hints for OpenGL 4.6 */
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);


        /* Non-resizable */
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);


        /* Initially hidden */
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);


        /* Disable window decorations */
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);


        /* Retrieve primary monitor dimensions */
        GLFWVidMode vidmode = glfwGetVideoMode( glfwGetPrimaryMonitor() );
        if (width == 0)  width  = vidmode.width();  
        if (height == 0) height = vidmode.height();


        /* Set window dimension feilds */
        this.width  = width;
        this.height = height;


        /* Create window */
        handle = glfwCreateWindow(width, height, title, 0L, 0L);
        if (handle == 0L)
        {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window!");
        }


        /* Center the window on the primary monitor */
        glfwSetWindowPos(handle,
            (vidmode.width()  - width)  / 2,
            (vidmode.height() - height) / 2
        );


        /* Create OpenGL context */
        glfwMakeContextCurrent(handle);
        GL.createCapabilities();


        /* Enable v-sync */
        glfwSwapInterval(1);


        /* Set key callback */
        keyCallback = new GLFWKeyCallback() 
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                /* Bind pressing Esc to closing the window */
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };
        glfwSetKeyCallback(handle, keyCallback);


        /* Make the window visible */
        glfwShowWindow(handle);
    }


    /**
     * Creates a full-screen GLFW window with the specified title.
     *
     * @param title  window title
     */
    public Window(CharSequence title) 
    {
        this(0, 0, title);
    }


    /**
     * Returns if the window should close.
     * 
     * @return true if window should close, false otherwise.
     */
    public boolean shouldClose()
    {
        return glfwWindowShouldClose(handle);
    }


    /**
     * Mark window as should close.
     */
    public void close()
    {
        glfwSetWindowShouldClose(handle, true);
    }


    /**
     * Swap the window buffers and poll for events.
     */
    public void refresh() 
    {
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }


    /**
     * Destroys the window, terminates GLFW, and frees its callbacks.
     */
    public void delete() 
    {
        glfwDestroyWindow(handle);
        keyCallback.free();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    /**
     * Returns the window handle.
     * 
     * @return the window handle
     */
    public long getHandle()
    {
        return handle;
    }
    

    /**
     * Returns the window width.
     * 
     * @return the window width in pixels
     */
    public int getWidth()
    {
        return width;
    }


    /**
     * Returns the window height.
     * 
     * @return the window height in pixels
     */
    public int getHeight()
    {
        return height;
    }
}
