package com.gmail.mooman219.frame.serialize.jack;

import java.io.File;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.FileHelper;

public abstract class ConfigJackson {
    private transient File file;
    private transient final String cast;

    public ConfigJackson(String cast, String directory, String fileName, String type) {
        this.cast = cast;
        boolean exists = FileHelper.doesExist(directory, fileName, type);
        this.file = FileHelper.getFile(directory, fileName, type);
        Loader.info(cast + (exists ? "[FOUND]" : "[MISSING]") + " Initializing " + file.getName());
        if(!exists) {
            save();
        } else {
            load();
            save();
        }
    }

    public final void load() {
        Loader.info(cast + "Loading " + file.getName());
        onLoad(file);
        validateData();
    }

    public abstract void onLoad(File file);

    public final void save() {
        Loader.info(cast + "Saving " + file.getName());
        validateData();
        onSave(file);
    }

    public abstract void onSave(File file);

    public abstract void validateData();
}
