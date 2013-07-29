package com.gmail.mooman219.layout;

public enum ModuleType {
    BUFF(0, "Buff", "plugins/CowCraft/"),
    CHAT(1, "Chat", "plugins/CowCraft/"),
    DAMAGE(2, "Damage", "plugins/CowCraft/"),
    DISCIPLINE(3, "Discipline", "plugins/CowCraft/"),
    GRAVEYARD(4, "Graveyard", "plugins/CowCraft/"),
    ITEM(5, "Item", "plugins/CowCraft/"),
    LOGIN(6, "Login", "plugins/CowCraft/"),
    MINERAL(7, "Mineral", "plugins/CowCraft/"),
    MOUNT(8, "Mount", "plugins/CowCraft/"),
    PET(9, "Pet", "plugins/CowCraft/"),
    REGION(10, "Region", "plugins/CowCraft/"),
    SERVICE(11, "Service", "plugins/CowCraft/"),
    SHOP(12, "Shop", "plugins/CowCraft/"),
    STAT(13, "Stat", "plugins/CowCraft/"),
    VANILLA(14, "Vanilla", "plugins/CowCraft/"),
    WORLD(15, "World", "plugins/CowCraft/");

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
