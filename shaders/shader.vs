#version 120

attribute vec3 vertices;
attribute vec2 textures;

varying vec2 tex_Coords;

uniform mat4 projection;

void main(){
    tex_Coords = textures;
    gl_Position = projection * vec4(vertices,1);
}