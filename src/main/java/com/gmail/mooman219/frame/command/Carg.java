package com.gmail.mooman219.frame.command;

public enum Carg {
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
    
    Carg(String name) {
        this.name = name.toLowerCase();
    }
    
    public static Carg get(String name) {
        for(Carg param : Carg.values()) {
            if(name.toLowerCase().equals(param.name)) {
                return param;
            }
        }
        return Carg.STRING;
    }
}
