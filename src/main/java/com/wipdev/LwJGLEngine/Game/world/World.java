package com.wipdev.LwJGLEngine.Game.world;

import com.wipdev.LwJGLEngine.Engine.Camera;
import com.wipdev.LwJGLEngine.Engine.IO.Input;
import com.wipdev.LwJGLEngine.Engine.IO.Window;
import com.wipdev.LwJGLEngine.Engine.Renderer.TileRenderer;
import com.wipdev.LwJGLEngine.Engine.Shader.Shader;
import com.wipdev.LwJGLEngine.Game.Entities.Player;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {

    private byte[][] tiles;
    private int width = 1000;
    private int height = 1000;
    private int scale = 64;

    private Matrix4f world;

    private Player player;

    public World() {

        player = new Player();

        tiles = new byte[width][height];

        for(int x=0;x<width;x++ ) {
            tiles[x] = new byte[height];
            for (int y = 0; y < height; y++) {
                if(x % 2 == 1){
                    tiles[x][y] = (byte)1;
                }else{
                    tiles[x][y] = (byte)0;
                }
            }
        }

        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }


    public void update(double delta, Window window, Camera camera, Input input){
        player.update(delta,window,camera,this,input);
    }

    public void render(TileRenderer render, Shader shader, Camera camera,Window window){
        int view = window.getWidth()/scale;
        int posX = ((int) camera.getPosition().x + (window.getWidth()/2)) / (scale);
        int posY = ((int) camera.getPosition().y - (window.getHeight()/2)) / (scale);

        for(int x=0;x<=view+2;x++ ){
            for(int y=0;y<=view;y++ ){
                Tile tile = getTile(x-posX,y+posY);
                if(tile != null) render.renderTile(tile,x-posX,-y-posY,shader,world,camera);
            }
        }
        player.render(shader,camera);
    }

    public Tile getTile(int x,int y){
        try{
            return Tile.tiles[tiles[x][y]];
        }catch (Exception e){
            return null;
        }
    }

    public int getScale() {
        return scale;
    }
}
