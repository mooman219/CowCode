package com.gmail.mooman219.layout;

import com.gmail.mooman219.core.Loader;

public abstract class CowHandler {
    private final Loader plugin;

    public CowHandler(Loader plugin) {
        this.plugin = plugin;
    }

    public abstract HandlerType getType();

    public Loader getPlugin() {
        return plugin;
    }

    public void onEnable() {}

    public void onDisable() {}
}
