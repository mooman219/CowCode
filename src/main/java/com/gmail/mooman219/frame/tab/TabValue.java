package com.gmail.mooman219.frame.tab;

import com.gmail.mooman219.frame.text.TextHelper;

public class TabValue {
    private String clientName;
    private String name;
    private boolean ping;

    public TabValue(String name, boolean ping) {
        setClientName(name);
        setName(name);
        setPing(ping);
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

    public boolean getPint() {
        return ping;
    }

    public void setPing(boolean ping) {
        this.ping = ping;
    }
}
