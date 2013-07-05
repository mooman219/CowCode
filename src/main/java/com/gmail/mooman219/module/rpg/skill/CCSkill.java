package com.gmail.mooman219.module.rpg.skill;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.layout.CowComponent;

public class CCSkill implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[RPG][Skill] ";
    public static Messages MSG;
    public static Formats FRM;

    public CCSkill(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "RPG Skill";
    }

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}

    public class Messages {}

    public class Formats {}
}
