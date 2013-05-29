package com.gmail.mooman219.module.chat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.chat.CCChat;

public class ListenerChat implements Listener{
    @EventHandler()
    public void onChat(AsyncPlayerChatEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
//    ~ Muted players
        if(player.chatData.mutedUntil - System.currentTimeMillis() > 0) {
            event.setCancelled(true);
            CCChat.FRM.MUTED.send(event.getPlayer(), TimeHelper.getLargestType(player.chatData.mutedUntil - System.currentTimeMillis(), TimeType.MILLISECOND));
//    ~ Private chat
        } else if(event.getMessage().charAt(0) == '@') {
            event.setCancelled(true);
            String[] message = TextHelper.spacePattern.split(event.getMessage(), 2);
            if(message.length <= 1) {
                CCChat.MSG.MESSAGE_EMPTY.send(event.getPlayer());
            } else if(message[0].length() == 1) {
                message(player, player.chat.getLastMessaged(), message[1]);
            } else {
                Player foundPlayer = Bukkit.getPlayer(message[0].substring(1));
                if(foundPlayer == null) {
                    CCChat.FRM.MESSAGE_EXIST.send(event.getPlayer(), message[0].substring(1));
                } else {
                    CDPlayer otherPlayer = CDPlayer.get(foundPlayer);
                    if(otherPlayer.username.equals(player.username)) {
                        CCChat.MSG.MESSAGE_SELF.send(event.getPlayer());
                    } else {
                        message(player, otherPlayer, message[1]);
                    }
                }
            }
//    ~ Global chat
        } else if(event.getMessage().charAt(0) == '!') {
            event.setCancelled(true);
            if(event.getMessage().length() <= 1) {
                CCChat.MSG.MESSAGE_EMPTY.send(event.getPlayer());
            } else if(System.currentTimeMillis() - player.chat.lastGlobalChat <= ConfigGlobal.chatGlobalDelay) {
                CCChat.FRM.GLOBAL_DELAY.send(event.getPlayer(), TimeHelper.getLargestType(ConfigGlobal.chatGlobalDelay - (System.currentTimeMillis() - player.chat.lastGlobalChat), TimeType.MILLISECOND));
            } else {
                event.setFormat(Chat.msgGlobal + player.serviceData.rank.tag + "%s" + Chat.DARK_GRAY + ":" + Chat.WHITE + " %s");
                event.setMessage(event.getMessage().substring(1));
                event.setCancelled(false);
                // Moderators and above are not effected by the delay
                if(player.serviceData.rank.index < Rank.MODERATOR.index) {
                    player.chat.lastGlobalChat = System.currentTimeMillis();
                }
            }
//    ~ Normal chat
        } else {
            event.setFormat(player.serviceData.rank.tag + "%s" + Chat.DARK_GRAY + ":" + Chat.WHITE + " %s");
            for(Player recipient : event.getRecipients()) {
                if(!player.username.equals(recipient.getName())) {
                    double distance = LocationHelper.get2DistanceSquared(player.getPlayer().getLocation(), recipient.getLocation());
                    if(distance > Math.pow(ConfigGlobal.chatRadius, 2)) {
                        event.getRecipients().remove(recipient);
                    }
                }
            }
            if(event.getRecipients().size() <= 1) {
                CCChat.MSG.NOHEAR.send(event.getPlayer());
            }
        }
    }

    public void message(CDPlayer sender, CDPlayer receiver, String message) {
        if(receiver == null || !receiver.getPlayer().isOnline()) {
            sender.chat.setLastMessaged(null);
            CCChat.MSG.MESSAGE_LOST.send(sender.getPlayer());
        } else {
            sender.getPlayer().sendMessage(Chat.DARK_GRAY + "" + Chat.BOLD + "TO " + Chat.RESET + receiver.serviceData.rank.tag + receiver.getPlayer().getDisplayName() + Chat.DARK_GRAY + ":" + Chat.WHITE + " " + message);
            receiver.getPlayer().sendMessage(Chat.DARK_GRAY + "" + Chat.BOLD + "FROM " + Chat.RESET + sender.serviceData.rank.tag + sender.getPlayer().getDisplayName() + Chat.DARK_GRAY + ":" + Chat.WHITE + " " + message);
            sender.chat.setLastMessaged(receiver);
            receiver.chat.setLastMessaged(sender);
        }
    }
}