package com.wipdev.LwJGLEngine.Game.Entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f pos;
    public Vector3f scale;

    public Transform(Vector3f pos, Vector3f scale) {
        this.pos = pos;
        this.scale = scale;
    }

    public Matrix4f getProjection(Matrix4f target){
        return target.translate(pos).scale(scale);
    }
}
