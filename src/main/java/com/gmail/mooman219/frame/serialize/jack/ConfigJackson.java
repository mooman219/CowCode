package com.gmail.mooman219.frame.serialize.jack;

import java.io.File;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.FileHelper;

public abstract class ConfigJackson {
    private transient File file;

    public ConfigJackson(String cast, String directory, String fileName, String type) {
        boolean exists = FileHelper.doesExist(directory, fileName, type);
        this.file = FileHelper.getFile(directory, fileName, type);
        Loader.info(cast + (exists ? "[FOUND]" : "[MISSING]") + " Loading " + file.getName());
        if(!exists) {
            save();
        } else {
            load();
            save();
        }
    }

    public final void load() {
        onLoad(file);
        validateData();
    }

    public abstract void onLoad(File file);

    public final void save() {
        validateData();
        onSave(file);
    }

    public abstract void onSave(File file);

    public abstract void validateData();
}
