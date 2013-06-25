package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
    public static File getFile(String directory, String fileName){
        new File(directory).mkdirs();
        File file = new File(directory + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File getFile(String directory, String fileName, String type) {
        if(!type.startsWith(".")) {
            type = "." + type;
        }
        if(fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        if(fileName.length() < 4 || !fileName.endsWith(type)) {
            fileName = fileName + type;
        }
        if(!directory.endsWith("/")) {
            directory = directory + "/";
        }
        return getFile(directory, fileName);
    }

    public static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileWriter getFileWriter(File file) {
        try {
            return new FileWriter(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean doesExist(String url){
        return new File(url).exists();
    }

    /**
    public static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch(FileNotFoundException e) {
            file = FileHelper.getFile(directory, fileName);
            try {
                return new FileReader(file);
            } catch(FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static FileWriter getFileWriter(File file) {
        try {
            return new FileWriter(file);
        } catch(FileNotFoundException e) {
            file = FileHelper.getFile(directory, fileName);
            try {
                return new FileWriter(file);
            } catch(Exception e1) {
                e1.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     */
}
