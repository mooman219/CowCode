package com.gmail.mooman219.frame.time;

public class TimeString {
    public TimeType type;
    public float time;

    public TimeString(TimeType type, float time) {
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        String ret = time + " " + type.name;
        if(time > 1) {
            ret += "s";
        }
        return ret;
    }
}
