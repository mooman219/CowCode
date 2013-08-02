package com.gmail.mooman219.frame.serialize.aspect;

import java.util.concurrent.Callable;

public class KeyBoolean extends AspectKey<Boolean> {
    public KeyBoolean(String name, Boolean defaultValue) {
        super("§α", name, defaultValue);
        this.setWriteCheck(new BooleanWriteCheck());
    }

    @Override
    public String write() {
        if(getValue()) {
            return getName();
        }
        throw new IllegalStateException("You shouldn't write a false KeyBoolean idiot.");
    }

    @Override
    public boolean read(String line) {
        boolean ret = match(line);
        setValue(ret);
        return ret;
    }

    private class BooleanWriteCheck implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            return getValue();
        }
    }
}
