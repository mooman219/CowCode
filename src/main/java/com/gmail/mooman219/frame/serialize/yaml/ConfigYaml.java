package com.gmail.mooman219.frame.serialize.yaml;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.FileHelper;

public abstract class ConfigYaml {
    public final String directory;
    public final String fileName;

    public File hardFile;
    public YamlConfiguration file = new YamlConfiguration();

    public ConfigYaml(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        // Create the file if needed
        hardFile = FileHelper.getFile(directory, fileName, "yml");
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
