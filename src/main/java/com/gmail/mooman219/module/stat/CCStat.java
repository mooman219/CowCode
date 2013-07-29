package com.gmail.mooman219.module.stat;

import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;

public class CCStat extends CowModule {
    private static final ModuleType type = ModuleType.STAT;
    public static Messages MSG;
    public static Formats FRM;

    public CCStat(){
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public ModuleType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
    }

    public class Messages {}

    public class Formats {}
}
