package com.gmail.mooman219.frame.command;

public enum CCArg {
    OPTIONAL("optional"),
    MESSAGE("message"),
    STRING("string"),
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    LONG("long"),
    BOOLEAN("boolean");
    
    public final String name;
    
    CCArg(String name) {
        this.name = name.toLowerCase();
    }
    
    public static CCArg get(String name) {
        for(CCArg param : CCArg.values()) {
            if(name.toLowerCase().equals(param.name)) {
                return param;
            }
        }
        return CCArg.STRING;
    }
}
