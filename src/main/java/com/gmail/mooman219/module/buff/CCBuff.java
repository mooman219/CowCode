package com.gmail.mooman219.module.buff;

import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;

public class CCBuff extends CowModule {
    private static final ModuleType type = ModuleType.BUFF;
    public static Messages MSG;
    public static Formats FRM;

    public CCBuff(){
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
