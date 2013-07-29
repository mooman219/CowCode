package com.gmail.mooman219.layout;

public enum ModuleType {
    BUFF(0, "Buff", "plugins/CowCraft/"),
    CHAT(1, "Chat", "plugins/CowCraft/"),
    DAMAGE(0, "Damage", "plugins/CowCraft/"),
    DISCIPLINE(0, "Discipline", "plugins/CowCraft/"),
    GRAVEYARD(0, "Graveyard", "plugins/CowCraft/"),
    ITEM(0, "Item", "plugins/CowCraft/"),
    LOGIN(0, "Login", "plugins/CowCraft/"),
    MINERAL(0, "Mineral", "plugins/CowCraft/"),
    MOUNT(0, "Mount", "plugins/CowCraft/"),
    PET(0, "Pet", "plugins/CowCraft/"),
    REGION(0, "Region", "plugins/CowCraft/"),
    SERVICE(0, "Service", "plugins/CowCraft/"),
    SHOP(0, "Shop", "plugins/CowCraft/"),
    STAT(0, "Stat", "plugins/CowCraft/"),
    VANILLA(0, "Vanilla", "plugins/CowCraft/"),
    WORLD(0, "World", "plugins/CowCraft/");

    private final int id;
    private final String name;
    private final String directory;

    ModuleType(int id, String name, String directory) {
        this.id = id;
        this.name = name;
        this.directory = directory;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirectory() {
        return directory;
    }

    public String getCast() {
        return "[" + name + "] ";
    }
}
