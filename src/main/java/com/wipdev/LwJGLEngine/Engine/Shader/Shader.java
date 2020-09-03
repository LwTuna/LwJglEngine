package com.wipdev.LwJGLEngine.Engine.Shader;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private static final String shader_path = "./shaders/";

    private int programId;
    private int vertexId;
    private int fragmentId;

    private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

    public Shader(String file){
        programId = glCreateProgram();

        vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexId, readFile(file+".vs"));
        glCompileShader(vertexId);
        if(glGetShaderi(vertexId, GL_COMPILE_STATUS) != 1){
            System.err.println(glGetShaderInfoLog(vertexId));
            System.exit(1);
        }

        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentId, readFile(file+".fs"));
        glCompileShader(fragmentId);
        if(glGetShaderi(fragmentId, GL_COMPILE_STATUS) != 1){
            System.err.println(glGetShaderInfoLog(fragmentId));
            System.exit(1);
        }

        glAttachShader(programId, vertexId);
        glAttachShader(programId, fragmentId);

        glBindAttribLocation(programId, 0, "vertices");
        glBindAttribLocation(programId,1,"textures");

        glLinkProgram(programId);
        if(glGetProgrami(programId, GL_LINK_STATUS) != 1){
            System.err.println(glGetProgramInfoLog(programId));
            System.exit(1);
        }
        glValidateProgram(programId);
        if(glGetProgrami(programId, GL_VALIDATE_STATUS) != 1){
            System.err.println(glGetProgramInfoLog(programId));
            System.exit(1);
        }
    }

    public void setUniform(String name, int value){
        int location = glGetUniformLocation(programId,name);
        if(location != -1){
            glUniform1i(location,value);
        }else{
            System.err.println("Cannot find Uniform var: "+name);
        }
    }

    public void setUniform(String name, Matrix4f value){
        int location = glGetUniformLocation(programId,name);
        FloatBuffer floatBuffer = this.floatBuffer;
        value.get(floatBuffer);
        if(location != -1){
            glUniformMatrix4fv(location,false,floatBuffer);
        }else{
            System.err.println("Cannot find Uniform var: "+name);
        }
    }



    public void bind(){
        glUseProgram(programId);
    }

    private String readFile(String filename)  {
        StringBuilder string = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(shader_path+filename)));
            String line;
            while ((line = br.readLine())!=null){
                string.append(line+"\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string.toString();
    }
}
