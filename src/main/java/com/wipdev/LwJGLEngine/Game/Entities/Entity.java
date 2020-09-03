package com.wipdev.LwJGLEngine.Game.Entities;

import com.wipdev.LwJGLEngine.Engine.Camera;
import com.wipdev.LwJGLEngine.Engine.IO.Input;
import com.wipdev.LwJGLEngine.Engine.IO.Window;
import com.wipdev.LwJGLEngine.Engine.Shader.Shader;
import com.wipdev.LwJGLEngine.Game.world.World;

public interface Entity {

    public void update(double delta, Window window, Camera camera, World world, Input input);
    public void render(Shader shader, Camera camera);


    public Transform getTransform();
}
