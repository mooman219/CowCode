package com.gmail.mooman219.frame.tab;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.handler.packet.CHPacket;

public class Tab {
    public final static int maxTabWidth = 4;
    public final static int maxTabHeight = 20;
    public final static String[] tabSafeColors = {"a", "b", "c", "d", "e", "f", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private ArrayList<Player> watchers;
    private TabValue[][] tab;
    private int index = 0;

    public Tab() {
        this.watchers = new ArrayList<Player>();
        this.tab = new TabValue[maxTabWidth][maxTabHeight];
        for(int x = 0; x < maxTabWidth; x++) {
            for(int y = 0; y < maxTabHeight; y++) {
                tab[x][y] = new TabValue(nextUnique(), true);
            }   
        }
    }

    public void addWatcher(Player player) {
        if(!watchers.contains(player)) {
            watchers.add(player);
        }
        updatePlayer(player, false);
        updatePlayer(player, true);
    }

    public void removeWatcher(Player player) {
        if(watchers.contains(player)) {
            updatePlayer(player, false);
        }
    }
    
    public void updatePlayer(Player player, boolean login) {
        for(int x = 0; x < maxTabWidth; x++) {
            for(int y = 0; y < maxTabHeight; y++) {
                CHPacket.helper.sendPlayerInfo(player, tab[x][y].getClientName(), login, true);
            }   
        }
    }

    public void setTab(int x, int y, String name, boolean ping) {
        this.tab[x][y].setName(name);
        this.tab[x][y].setPing(ping);
    }

    public String nextUnique() {
        if(index > 256) {
            index = 0;
        }
        String name = Chat.COLOR_CHAR + tabSafeColors[NumberHelper.floor((index / 16d) % 16)];
        name += Chat.COLOR_CHAR + tabSafeColors[index % 16];
        return name;
    }
}
