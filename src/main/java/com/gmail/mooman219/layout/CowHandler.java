package com.gmail.mooman219.layout;

/**
 * Handlers should never rely on the bukkit API
 * @author Mooman219
 *
 */
public abstract class CowHandler {
    public abstract HandlerType getType();

    public void onEnable() {}

    public void onDisable() {}
}
