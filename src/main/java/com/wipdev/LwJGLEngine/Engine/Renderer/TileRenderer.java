package com.wipdev.LwJGLEngine.Engine.Renderer;

import com.wipdev.LwJGLEngine.Engine.Camera;
import com.wipdev.LwJGLEngine.Engine.Model.Model;
import com.wipdev.LwJGLEngine.Engine.Model.Texture;
import com.wipdev.LwJGLEngine.Engine.Shader.Shader;
import com.wipdev.LwJGLEngine.Game.world.Tile;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;

public class TileRenderer {
    public HashMap<String, Texture> tileTextures;
    public Model model;

    private static final String tilePath = "tiles/";

    public TileRenderer() {
        tileTextures = new HashMap();
        model = Model.createDefaultSquareModel();

        for(int i=0;i< Tile.tiles.length;i++){
            if(Tile.tiles[i] !=null) {
                if (!tileTextures.containsKey(Tile.tiles[i].getTexture())) {
                    String key = Tile.tiles[i].getTexture();
                    tileTextures.put(key, new Texture(tilePath+key));
                }
            }else{
                throw new IllegalArgumentException("Tile.tiles[i] == null");
            }
        }
    }

    public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera camera){
        shader.bind();

        Matrix4f tilePosition = new Matrix4f().translate(new Vector3f(x,y,0));
        Matrix4f target = new Matrix4f();
        camera.getProjection().mul(world,target);
        target.mul(tilePosition);

        shader.setUniform("sampler",0);
        shader.setUniform("projection", target);

        if(tileTextures.containsKey(tile.getTexture())){
            tileTextures.get(tile.getTexture()).bind(0);
        }
        model.render();
    }
}
