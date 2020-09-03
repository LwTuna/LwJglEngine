package com.wipdev.LwJGLEngine.Engine.Model;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static  org.lwjgl.opengl.GL15.*;
import static  org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Model {

    private int vCount;
    private int vId;
    private int tId;

    private int i_id;

    public Model(float[] vertices,float[] textureCoords, int[] indices){
        vCount = indices.length;

        vId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices),GL_STATIC_DRAW);

        tId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoords), GL_STATIC_DRAW);

        i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vId);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);

        glBindBuffer(GL_ARRAY_BUFFER, tId);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        glDrawElements(GL_TRIANGLES, vCount,GL_UNSIGNED_INT,0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
    }


    private IntBuffer createBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static Model createDefaultSquareModel(){
        float[] vertices = new float[]{
                -0.5f,0.5f,0,
                0.5f,0.5f,0,
                0.5f,-0.5f,0,
                -0.5f,-0.5f,0,
        };

        float[] textureCoords = new float[] {
                0,0,
                1,0,
                1,1,
                0,1,
        };
        int[] indices = new int[]{
                0,1,2,
                2,3,0
        };
        return new Model(vertices,textureCoords,indices);
    }
}
