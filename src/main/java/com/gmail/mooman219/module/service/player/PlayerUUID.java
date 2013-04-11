package com.gmail.mooman219.module.service.player;

import java.net.InetAddress;

import org.bukkit.entity.Player;

public class PlayerUUID {
    public final String username;
    public final InetAddress connection;

    public PlayerUUID(Player player) {
        this.connection = player.getAddress().getAddress();
        this.username = player.getName();
    }

    public PlayerUUID(String username, InetAddress connection) {
        this.username = username;
        this.connection = connection;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = PlayerUUID.class.hashCode();
        result = prime * result + ((connection == null) ? 0 : connection.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerUUID other = (PlayerUUID) obj;
        if (connection == null) {
            if (other.connection != null)
                return false;
        } else if (!connection.equals(other.connection))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }
}
