package com.gmail.mooman219.core;

public interface Module {
    public void onEnable();

    public void onDisable();

    public void loadCommands();

    public void registerConfigurationSerialization();
}
