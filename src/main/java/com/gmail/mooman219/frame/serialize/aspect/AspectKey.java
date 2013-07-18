package com.gmail.mooman219.frame.serialize.aspect;

public abstract class AspectKey<T> {
    private final String name;
    private final T defaultValue;
    private T value;

    public AspectKey(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean match(String line) {
        return line.startsWith(name);
    }

    public abstract String write();

    public abstract void read(String line);
}
