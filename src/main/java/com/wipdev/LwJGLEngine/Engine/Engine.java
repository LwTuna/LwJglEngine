package com.wipdev.LwJGLEngine.Engine;

import com.wipdev.LwJGLEngine.Engine.IO.Input;
import com.wipdev.LwJGLEngine.Engine.Renderer.TileRenderer;
import com.wipdev.LwJGLEngine.Engine.Shader.Shader;
import com.wipdev.LwJGLEngine.Game.world.World;
import com.wipdev.LwJGLEngine.Settings;
import com.wipdev.LwJGLEngine.Engine.IO.Window;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Engine implements IEngine,Runnable {

    private static final int tps = 40;

    private Window window = new Window(this,Settings.getInt("width"),Settings.getInt("height"),Settings.getBoolean("fullscreen"));

    private TileRenderer tileRenderer;
    private Shader shader;
    private Camera camera;
    private Input input;

    private World world;




    @Override
    public void init() {
        input = new Input(window.getWindow());
        shader = new Shader("shader");
        camera = new Camera(window.getWidth(),window.getHeight());
        tileRenderer = new TileRenderer();
        world = new World();
    }

    @Override
    public void update(double frameTime) {
        input.update();

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();



        world.update(frameTime,window,camera,input);

        if ( input.isKeyReleased(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindow(), true);
        }
    }

    @Override
    public void render() {
        world.render(tileRenderer,shader,camera,window);

    }

    @Override
    public void run() {
        window.init();
        init();
        window.loop(tps);
        window.cleanUp();
    }

    public static int getTps() {
        return tps;
    }

    public Window getWindow() {
        return window;
    }

    public Camera getCamera() {
        return camera;
    }
}
