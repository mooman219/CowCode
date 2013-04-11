package com.gmail.mooman219.frame;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class PlayerHelper {
    public static void setTabListName(Player player, String name) {
        player.setPlayerListName(TextHelper.shrink(name));
    }

    public static void setDisplayName(Player player, String name) {
        player.setDisplayName(name + Chat.RESET);
    }
}
