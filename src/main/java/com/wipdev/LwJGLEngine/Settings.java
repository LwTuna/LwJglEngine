package com.wipdev.LwJGLEngine;

import com.wipdev.LwJGLEngine.Exceptions.SettingNotFoundException;
import com.wipdev.LwJGLEngine.Exceptions.SettingsNotLoadedException;
import org.json.JSONObject;

import java.io.*;

public class Settings {

    public static final String DEFAULT_FILE_PATH = "res/settings.json";

    private static JSONObject data;

    public static void load(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
            fillDefaultData();
            save(filePath);
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String data = "";
        String line;

        while ((line=bufferedReader.readLine())!=null){
            data += line;
        }
        bufferedReader.close();

        Settings.data = new JSONObject(data);
    }

    private static void fillDefaultData() {
        data = new JSONObject();
        data.put("vSync", false);
        data.put("width", 1280);
        data.put("height",720);
        data.put("fullscreen",false);
    }

    public static void save(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(data.toString());
        bufferedWriter.close();
    }

    public static String getString(String key){
        if(data == null){ throw new SettingsNotLoadedException();}
        String ret = data.getString(key);
        if(ret == null) throw new SettingNotFoundException();
        return ret;
    }

    public static Integer getInt(String key){
        if(data == null){ throw new SettingsNotLoadedException();}
        Integer ret = data.getInt(key);
        if(ret == null) throw new SettingNotFoundException();
        return ret;
    }

    public static boolean getBoolean(String key){
        if(data == null){ throw new SettingsNotLoadedException();}
        Boolean ret = data.getBoolean(key);
        if(ret == null) throw new SettingNotFoundException();
        return ret;
    }


    public static void setString(String key,String value){
        if(data == null){ throw new SettingsNotLoadedException();}
        data.put(key, value);
    }

    public static void setBoolean(String key,Boolean value){
        if(data == null){ throw new SettingsNotLoadedException();}
        data.put(key, value);
    }

    public static String asString() {
        return data.toString();
    }
}
