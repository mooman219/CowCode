package com.gmail.mooman219.core;

public interface CowComponent {
    public String getCast();

    public void onEnable();

    public void onDisable();

    public void loadCommands();

    public void registerConfigurationSerialization();
}
