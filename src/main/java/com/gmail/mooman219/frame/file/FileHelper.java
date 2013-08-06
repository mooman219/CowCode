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
        // "type" -> ".type"
        if(!type.startsWith(".")) {
            type = "." + type;
        }
        // "/filename" -> "filename"
        if(fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        // "filename" -> "filename.type"
        if(!fileName.endsWith(type)) {
            fileName = fileName + type;
        }
        // "directory" -> "directory/"
        if(!directory.endsWith("/")) {
            directory = directory + "/";
        }
        // "directory/filename.type"
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

    public static String getURL(String directory, String fileName, String type) {
        // "type" -> ".type"
        if(!type.startsWith(".")) {
            type = "." + type;
        }
        // "/filename" -> "filename"
        if(fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        // "filename" -> "filename.type"
        if(!fileName.endsWith(type)) {
            fileName = fileName + type;
        }
        // "directory" -> "directory/"
        if(!directory.endsWith("/")) {
            directory = directory + "/";
        }
        // "directory/filename.type"
        return directory + fileName;
    }

    public static boolean doesExist(String directory, String fileName, String type){
        return new File(getURL(directory, fileName, type)).exists();
    }
}
