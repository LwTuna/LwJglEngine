package com.wipdev.LwJGLEngine.Engine.IO;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Input {

    private long window;

    private boolean keys[];

    public Input(long window) {
        this.window = window;
        this.keys = new boolean[GLFW_KEY_LAST];

        for(int i=0;i<GLFW_KEY_LAST;i++){
            keys[i] = false;
        }
    }

    public boolean isKeyDown(int keyCode) {
        return glfwGetKey(window, keyCode) == GLFW_PRESS;
    }

    public boolean isKeyPressed(int key){
        return (isKeyDown(key) && !keys[key]);
    }

    public boolean isKeyReleased(int key){
        return (!isKeyDown(key) && keys[key]);
    }

    public void update(){
        for(int i=32;i<GLFW_KEY_LAST;i++){
            keys[i] = isKeyDown(i);
        }
    }

    public boolean isMousePressed(int button) {
        return glfwGetMouseButton(window, button) == GLFW_PRESS;
    }

}
