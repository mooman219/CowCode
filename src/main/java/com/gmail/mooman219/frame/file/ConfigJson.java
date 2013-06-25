package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.file.FileHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.Gson;

public abstract class ConfigJson {
    private transient File file;

    public ConfigJson(String directory, String fileName, String type) {
        this.file = FileHelper.getFile(directory, fileName, type);
        load();
        save();
        Loader.info("Loading file: " + file.getName());
    }

    public File getFile() {
        return file;
    }

    public void save() {
        FileWriter writer = FileHelper.getFileWriter(file);
        onSave(writer);
        try {
            writer.flush();
            writer.close();
        } catch(IOException e) {}
    }

    public abstract void onSave(FileWriter writer);

    public void load() {
        FileReader reader = FileHelper.getFileReader(file);
        onLoad(reader);
        try {
            reader.close();
        } catch(IOException e) {}
    }

    public abstract void onLoad(FileReader reader);

    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .setPrettyPrinting()
        .create();
    }
}