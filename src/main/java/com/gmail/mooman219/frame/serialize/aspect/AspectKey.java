package com.gmail.mooman219.frame.serialize.aspect;

import java.util.concurrent.Callable;

public abstract class AspectKey<T> {
    private final String identifier;
    private final String name;
    private final T defaultValue;
    private Callable<Boolean> writeCheck;
    private T value;

    public AspectKey(String identifier, String name, T defaultValue) {
        this.identifier = identifier;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getName() {
        return identifier + name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public T getValue() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setWriteCheck(Callable<Boolean> writeCheck) {
        this.writeCheck = writeCheck;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean match(String line) {
        return line.startsWith(getName());
    }

    public boolean canWrite() {
        if(writeCheck != null) {
            try {
                return writeCheck.call();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public String write() {
        return getName() + getValue();
    }

    /**
     * Returns ture is the line matched and was read.
     */
    public abstract boolean read(String line);
}
