package com.wipdev.LwJGLEngine.Engine.IO;

import com.wipdev.LwJGLEngine.Engine.IEngine;
import com.wipdev.LwJGLEngine.Engine.IWindowResizeCallback;
import com.wipdev.LwJGLEngine.Settings;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window{

    private long window;
    private IEngine engine;

    private int width,height;
    private boolean fullscreen = false;

    private List<IWindowResizeCallback> resizeCallbackList = new ArrayList();

    public Window(IEngine engine,int width,int height,boolean fullscreen) {
        this.engine = engine;
        this.width = width;
        this.height = height;
        this.fullscreen = fullscreen;
    }

    public void init(){
        System.out.println("Phase 1 Initlaizing");

        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window

        long primaryScreen = fullscreen ? glfwGetPrimaryMonitor() : NULL;
        window = glfwCreateWindow(width, height, "Testing Game Engine",primaryScreen , NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");



        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        if(Settings.getBoolean("vSync")){
            // Enable v-sync
            glfwSwapInterval(1);
        }

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        //Enable Texture Rendering
        glEnable(GL_TEXTURE_2D);
        // Make the window visible
        glfwShowWindow(window);
    }
    /**
        @param tps the ticks per second, how often update is called
     */
    public void loop(double tps){
        System.out.println("Phase 2 Loop");


        // Set the clear color
        glClearColor(0.0f, .0f, .0f, 0.0f);

        //The Amount of Time per Tick
        double timePerTick =  1/tps;
        //The last Time
        double lastTime = Timer.getTime();
        //The time carrying over to the next loop
        double unprocessedTimeUpdate = 0;

        //To show fps
        double frameTime = 0;
        int frames =0;

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            //The real currentTime;
            double time2 = Timer.getTime();
            //The Time passed since last call
            double passed = time2-lastTime;

            unprocessedTimeUpdate +=passed;

            frameTime +=passed;

            lastTime = time2;

            while (unprocessedTimeUpdate >= timePerTick){
                engine.update(unprocessedTimeUpdate);
                unprocessedTimeUpdate -= timePerTick;



                if(frameTime >=1.0){
                    frameTime -= 1;
                    System.out.println("FPS = "+frames);
                    frames = 0;
                }
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            engine.render();
            glfwSwapBuffers(window); // swap the color buffers
            frames++;

        }
    }



    public void cleanUp() {
        System.out.println("Phase 3 Cleanup");
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }



    public void addResizeCallback(IWindowResizeCallback callback){
        resizeCallbackList.add(callback);
    }

    public void removeResizeCallback(IWindowResizeCallback callback){
        resizeCallbackList.add(callback);
    }

    public void onResize(){
        int width = 0;
        int height = 0;
        for (IWindowResizeCallback callback:resizeCallbackList) {
            callback.onResize(width,height);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }
}
