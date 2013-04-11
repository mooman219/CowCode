package com.gmail.mooman219.frame.scoreboard;

import com.gmail.mooman219.frame.text.TextHelper;

public class ScoreboardValue {
    private String clientName;
    private String name;
    private int value;

    public ScoreboardValue(String name, int value) {
        this.clientName = name;
        this.name = name;
        this.value = value;
    }

    protected String getClientName() {
        return clientName;
    }

    protected void setClientName(String clientName) {
        this.clientName = TextHelper.shrink(clientName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = TextHelper.shrink(name);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
