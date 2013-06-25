package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.mooman219.core.Loader;

public abstract class ConfigBase {
    public final String directory;
    public final String fileName;

    public File hardFile;
    public YamlConfiguration file = new YamlConfiguration();

    public ConfigBase(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        // Parse the data
        if(fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        if(fileName.length() < 4 || !fileName.endsWith(".yml")) {
            fileName = fileName + ".yml";
        }
        if(!directory.endsWith("/")) {
            directory = directory + "/";
        }
        // Create the file if needed
        hardFile = FileHelper.getFile(directory, fileName);
        load();
        save();
        Loader.info("Loading file: " + directory + fileName);
    }

    public <T> T loadVar(String key, T value) {
        try {
            if(file.contains(key)) {
                return (T) file.get(key, value);
            } else {
                file.addDefault(key, value);
                file.set(key, value);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public <T> void saveVar(String key, T value) {
        file.addDefault(key, value);
        file.set(key, value);
    }

    public void save() {
        file = new YamlConfiguration();
        onSave();
        try {
            file.save(hardFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        file = YamlConfiguration.loadConfiguration(hardFile);
        onLoad();
    }

    public abstract void onLoad();

    public abstract void onSave();
}
