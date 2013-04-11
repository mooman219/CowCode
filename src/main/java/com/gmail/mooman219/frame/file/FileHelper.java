package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    public static File getFile(String dir, String name){
        new File(dir).mkdirs();
        File file = new File(dir+name);
        try {
            file.createNewFile();
        } catch (IOException e) {e.printStackTrace();}
        return file;
    }

    public static boolean doesExist(String url){
        return new File(url).exists();
    }
}
