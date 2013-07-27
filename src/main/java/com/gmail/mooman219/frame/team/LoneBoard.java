package com.gmail.mooman219.frame.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import net.minecraft.server.v1_6_R2.Packet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.PacketHelper;

public class LoneBoard {
    private final HashSet<CDPlayer> entries;

    public LoneBoard() {
        entries = new HashSet<CDPlayer>();
    }

    public void add(CDPlayer player) {
        if(!entries.contains(player)) {
            entries.add(player);
            Packet packet = PacketHelper.getSetScoreboardTeam(TeamModifyType.CREATED, false, false, player.getUsername(), player.getUsername(), player.getOverheadPrefix(), player.getOverheadSuffix(), toCollection(player));
            for(Player bukkitOther : Bukkit.getOnlinePlayers()) {
                if(bukkitOther.getName().equals(player.getUsername())) {
                    continue;
                }
                CDPlayer other = CDPlayer.get(bukkitOther);
                player.sendPacket(PacketHelper.getSetScoreboardTeam(TeamModifyType.CREATED, false, false, other.getUsername(), other.getUsername(), other.getOverheadPrefix(), other.getOverheadSuffix(), toCollection(other)));
                other.sendPacket(packet);
            }
        } else {
            Loader.warning("add(): Player already in entries");
        }
    }

    public void update(CDPlayer player) {
        if(entries.contains(player)) {
            PacketHelper.sendGlobalExcept(player, PacketHelper.getSetScoreboardTeam(TeamModifyType.UPDATED, false, false, player.getUsername(), player.getUsername(), player.getOverheadPrefix(), player.getOverheadSuffix(), toCollection(player)));
        } else {
            Loader.warning("update(): Player not in entries");
        }
    }

    public void remove(CDPlayer player) {
        if(entries.contains(player)) {
            entries.remove(player);
            PacketHelper.sendGlobalExcept(player, PacketHelper.getSetScoreboardTeam(TeamModifyType.REMOVED, false, false, player.getUsername(), player.getUsername(), player.getOverheadPrefix(), player.getOverheadSuffix(), toCollection(player)));
        } else {
            Loader.warning("remove(): Player not in entries");
        }
    }

    private Collection<String> toCollection(CDPlayer player) {
        ArrayList<String> players = new ArrayList<String>();
        players.add(player.getUsername());
        return players;
    }
}
