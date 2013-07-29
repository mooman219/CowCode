package com.gmail.mooman219.layout;

import com.gmail.mooman219.core.Loader;

/**
 * Modules are for ingame content.
 * @author Mooman219
 *
 */
public abstract class CowModule {
    public abstract ModuleType getType();

    public void onEnable(Loader plugin) {}

    public void onDisable(Loader plugin) {}

    public void loadCommands(Loader plugin) {}
}
