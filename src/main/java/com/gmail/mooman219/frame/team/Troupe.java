package com.gmail.mooman219.frame.team;

import java.util.Collection;

public interface Troupe {
    public String getName();

    public String getDisplayName();

    public boolean getFriendFire();

    public boolean getSeeInvisible();

    public Collection<String> getPlayers();

    public String getPrefix();

    public String getSuffix();

    public void setFriendlyFire(boolean friendlyFire);

    public void setSesInvisible(boolean seeInvisible);

    public void setPrefix(String prefix);

    public void setSuffix(String suffix);
}
