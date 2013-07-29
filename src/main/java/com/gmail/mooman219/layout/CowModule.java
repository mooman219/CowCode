package com.gmail.mooman219.layout;

import com.gmail.mooman219.core.Loader;

public abstract class  CowModule {
    private final Loader plugin;

    public CowModule(Loader plugin) {
        this.plugin = plugin;
    }

    public abstract ModuleType getType();

    public Loader getPlugin() {
        return plugin;
    }

    public void onEnable() {}

    public void onDisable() {}

    public void loadCommands() {}
}
