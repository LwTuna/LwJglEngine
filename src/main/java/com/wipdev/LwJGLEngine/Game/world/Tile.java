package com.wipdev.LwJGLEngine.Game.world;

public class Tile {

    public static final Tile pathTile = new Tile("path");
    public static final Tile grassTile = new Tile("grass");
    public static  Tile tiles[] = new Tile[]{pathTile,grassTile};


    private static byte nextId=0;
    private byte id;
    private String texture;

    public Tile(String texture){
        this.id = nextId;
        nextId++;
        this.texture = texture;
    }

    public byte getId() {
        return id;
    }

    public String getTexture() {
        return texture;
    }
}
