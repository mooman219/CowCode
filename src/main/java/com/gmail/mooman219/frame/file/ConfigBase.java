package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.mooman219.core.Loader;

public abstract class ConfigBase {
    public File hardFile;
    public YamlConfiguration file = new YamlConfiguration();
    public String directory = "";
    public String fileName = "";

    public ConfigBase(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        if(fileName.length() < 4 || !fileName.substring(fileName.length()-4, fileName.length()).equalsIgnoreCase(".yml")) {
            fileName = fileName+".yml";
        }
        if(directory.charAt(directory.length()-1) != '/') {
            directory = directory+"/";
        }
        Loader.info("Loading file: " + directory + fileName);
    }

    public void init() {
        if(FileHelper.doesExist(directory+fileName)) {
            hardFile = FileHelper.getFile(directory, fileName);
            load();
            save();
        } else {
            hardFile = FileHelper.getFile(directory, fileName);
            save();
        }
    }

    public <T> T loadVar(String key, T value) {
        if(file.contains(key)) {
            return (T) file.get(key, value);
        } else {
            file.addDefault(key, value);
            file.set(key, value);
            return value;
        }
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
