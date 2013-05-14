package com.gmail.mooman219.frame.tab;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.handler.task.CHTask;

public class Tab {
    public final static int maxTabWidth = 4;
    public final static int maxTabHeight = 20;
    public final static String[] tabSafeColors = {"§a", "§b", "§c", "§d", "§e", "§f", "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§r", "  "};
    private final Player player;
    private final TabValue[][] tab;
    private int index = 0;

    public Tab(Player player) {
        this.player = player;
        this.tab = new TabValue[maxTabWidth][maxTabHeight];

        for(int y = 0; y < maxTabHeight; y++) {
            for(int x = 0; x < maxTabWidth; x++) {
                tab[x][y] = new TabValue(nextUnique());
            }
        }
    }

    public String nextUnique() {
        index = index > 324 ? 0 : index + 1;
        String name = tabSafeColors[MathHelper.floor((index / 18D) % 18)];
        name += tabSafeColors[index % 18];
        return name;
    }

    public void setTab(int x, int y, String name) {
        if(name.length() > 16) {
            name = name.substring(0, 16);
        } else if(name.length() >= 13) {
            name += nextUnique().substring(2, 4);
        } else if(name.length() <= 12) {
            name += nextUnique();
        }
        this.tab[x][y].setName(name);
    }

    public void update() {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                for(int y = 0; y < maxTabHeight; y++) {
                    for(int x = 0; x < maxTabWidth; x++) {
                        CHPacket.manager.sendPlayerInfo(player, tab[x][y].getClientName(), false, true);
                    }
                }
                for(int y = 0; y < maxTabHeight; y++) {
                    for(int x = 0; x < maxTabWidth; x++) {
                        CHPacket.manager.sendPlayerInfo(player, tab[x][y].getName(), true, false);
                        tab[x][y].setClientName(tab[x][y].getName());
                    }
                }
            }
        };
        CHTask.manager.runPlugin(task, true);
    }
}
