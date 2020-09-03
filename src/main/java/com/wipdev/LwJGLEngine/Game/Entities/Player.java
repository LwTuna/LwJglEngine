package com.wipdev.LwJGLEngine.Game.Entities;

import com.wipdev.LwJGLEngine.Engine.Camera;
import com.wipdev.LwJGLEngine.Engine.IO.Input;
import com.wipdev.LwJGLEngine.Engine.IO.Window;
import com.wipdev.LwJGLEngine.Engine.Model.Model;
import com.wipdev.LwJGLEngine.Engine.Model.Texture;
import com.wipdev.LwJGLEngine.Engine.Shader.Shader;
import com.wipdev.LwJGLEngine.Game.world.World;
import javafx.scene.transform.Scale;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;

public class Player implements Entity {

    private Model model;
    private Texture texture;
    private Transform transform;

    public Player() {
        model = Model.createDefaultSquareModel();
        texture = new Texture("tiles/test");
        transform = new Transform(new Vector3f(0),new Vector3f(64));
    }

    @Override
    public void update(double delta, Window window, Camera camera, World world, Input input) {
        if ( input.isKeyDown(GLFW_KEY_W)) {
            transform.pos.y +=256 * delta;
        }
        if ( input.isKeyDown(GLFW_KEY_S)) {
            transform.pos.y -=256* delta;
        }
        if ( input.isKeyDown(GLFW_KEY_D)) {
            transform.pos.x +=256 * delta;
        }
        if ( input.isKeyDown(GLFW_KEY_A)) {
            transform.pos.x -=256 * delta;
        }

        camera.setPosition(transform.pos.mul(-1,new Vector3f()));
    }

    @Override
    public void render(Shader shader, Camera camera) {
        shader.bind();
        shader.setUniform("sampler",0);
        shader.setUniform("projection",transform.getProjection(camera.getProjection()));
        texture.bind(0);
        model.render();
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
