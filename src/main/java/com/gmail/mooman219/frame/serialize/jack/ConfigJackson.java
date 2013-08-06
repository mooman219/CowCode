package com.gmail.mooman219.frame.serialize.jack;

import java.io.File;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.file.FileHelper;

public abstract class ConfigJackson {
    private transient File file;

    public ConfigJackson(String cast, String directory, String fileName, String type) {
        this.file = FileHelper.getFile(directory, fileName, type);
        Loader.info(cast + "Loading " + file.getName());
    }

    public final void load() {
        onLoad(file);
    }

    public abstract void onLoad(File file);

    public final void save() {
        onLoad(file);
    }

    public abstract void onSave(File file);
}
