package com.gmail.mooman219.module.chat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.frame.MetaHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.chat.CMChat;
import com.gmail.mooman219.module.service.player.PlayerData;

public class ListenerChat implements Listener{
    @EventHandler()
    public void onChat(AsyncPlayerChatEvent event) {
        PlayerData playerData = MetaHelper.getPlayerData(event.getPlayer());
        // Muted players
        if(playerData.chatData.mutedUntil - System.currentTimeMillis() > 0) {
            event.setCancelled(true);
            TextHelper.message(playerData.player, CMChat.F_MUTED, TimeHelper.getLargestType(playerData.chatData.mutedUntil - System.currentTimeMillis(), TimeType.MILLISECOND));
            // Private chat
        } else if(event.getMessage().charAt(0) == '@') {
            event.setCancelled(true);
            String[] message = TextHelper.spacePattern.split(event.getMessage(), 2);
            if(message.length <= 1) {
                TextHelper.message(playerData.player, CMChat.M_MESSAGE_EMPTY);
            } else if(message[0].length() == 1) {
                message(playerData, playerData.chat.getLastMessaged(), message[1]);
            } else {
                Player foundPlayer = Bukkit.getPlayer(message[0].substring(1));
                if(foundPlayer == null) {
                    TextHelper.message(playerData.player, CMChat.F_MESSAGE_EXIST, message[0].substring(1));
                } else {
                    PlayerData otherPlayerData = MetaHelper.getPlayerData(foundPlayer);
                    if(otherPlayerData.username.equals(playerData.username)) {
                        TextHelper.message(playerData.player, CMChat.M_MESSAGE_SELF);
                    } else {
                        message(playerData, otherPlayerData, message[1]);
                    }
                }
            }
            // Global chat
        } else if(event.getMessage().charAt(0) == '!') {
            event.setCancelled(true);
            if(event.getMessage().length() <= 1) {
                TextHelper.message(playerData.player, CMChat.M_MESSAGE_EMPTY);
            } else if(System.currentTimeMillis() - playerData.chat.lastGlobalChat <= ConfigGlobal.chatGlobalDelay) {
                TextHelper.message(playerData.player, CMChat.F_GLOBAL_DELAY, TimeHelper.getLargestType(ConfigGlobal.chatGlobalDelay - (System.currentTimeMillis() - playerData.chat.lastGlobalChat), TimeType.MILLISECOND));
            } else {
                event.setFormat(Chat.msgGlobal + playerData.serviceData.rank.tag + "%s" + Chat.DARK_GRAY + ":" + Chat.WHITE + " %s");
                event.setMessage(event.getMessage().substring(1));
                event.setCancelled(false);
                // Moderators and above are not effected by the delay
                if(playerData.serviceData.rank.index < Rank.MODERATOR.index) {
                    playerData.chat.lastGlobalChat = System.currentTimeMillis();
                }
            }
            // Normal chat
        } else {
            event.setFormat(playerData.serviceData.rank.tag + "%s" + Chat.DARK_GRAY + ":" + Chat.WHITE + " %s");
            for(PlayerData otherPlayerData : MetaHelper.getAllPlayerData()) {
                if(!playerData.username.equals(otherPlayerData.username)) {
                    double distance = LocationHelper.get2DistanceSquared(playerData.player.getLocation(), otherPlayerData.player.getLocation());
                    if(distance > Math.pow(ConfigGlobal.chatRadius, 2)) {
                        event.getRecipients().remove(otherPlayerData.player);
                    }
                }
            }
            if(event.getRecipients().size() <= 1) {
                TextHelper.message(playerData.player, CMChat.M_NOHEAR);
            }
        }
    }

    public void message(PlayerData sender, PlayerData receiver, String message) {
        if(receiver == null || !receiver.player.isOnline()) {
            sender.chat.setLastMessaged(null);
            TextHelper.message(sender.player, CMChat.M_MESSAGE_LOST);
        } else {
            sender.player.sendMessage(Chat.DARK_GRAY + "" + Chat.BOLD + "TO " + Chat.RESET + receiver.serviceData.rank.tag + receiver.player.getDisplayName() + Chat.DARK_GRAY + ":" + Chat.WHITE + " " + message);
            receiver.player.sendMessage(Chat.DARK_GRAY + "" + Chat.BOLD + "FROM " + Chat.RESET + sender.serviceData.rank.tag + sender.player.getDisplayName() + Chat.DARK_GRAY + ":" + Chat.WHITE + " " + message);
            sender.chat.setLastMessaged(receiver);
            receiver.chat.setLastMessaged(sender);
        }
    }
}