package com.gmail.mooman219.module.item.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.item.Aspect;
import com.gmail.mooman219.frame.item.AspectItem;
import com.gmail.mooman219.frame.item.AspectWeapon;
import com.gmail.mooman219.frame.item.ItemHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.CCItem;

public class ItemStats extends CCommand {
    public ItemStats() {
        super("itemstats", Rank.REGULAR, "/ItemStats");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(!ItemHelper.isNull(sender.getItemInHand())) {
            Aspect aspect = Aspect.get(sender.getItemInHand());
            sender.sendMessage(
                    Chat.msgInfo + Chat.GREEN + "Listing " + Chat.GRAY + "Aspect" + Chat.GREEN + " Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.linePassive + Chat.GRAY + "Is Soulbound" + Chat.DARK_GRAY + ": " + Chat.GRAY + aspect.isSoulbound() + "\n" +
                    Chat.linePassive + Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.GREEN + aspect.getPrice()
                    );
            AspectItem itemAspect = AspectItem.get(sender.getItemInHand());
            sender.sendMessage(
                    Chat.msgInfo + Chat.GREEN + "Listing " + Chat.GOLD + "AspectItem" + Chat.GREEN + " Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.linePassive + Chat.GRAY + "Rarity" + Chat.DARK_GRAY + ": " + Chat.GRAY + itemAspect.getRarity().getFullName() + "\n" +
                    Chat.linePassive + Chat.GRAY + "AspectType" + Chat.DARK_GRAY + ": " + Chat.GRAY + itemAspect.getAspectType().getFullName()
                    );
            AspectWeapon weaponAspect = AspectWeapon.get(sender.getItemInHand());
            sender.sendMessage(
                    Chat.msgInfo + Chat.GREEN + "Listing " + Chat.RED + "AspectWeapon" + Chat.GREEN + " Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.linePassive + Chat.RED + "Min Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMinDamage() + "\n" +
                    Chat.linePassive + Chat.RED + "Max Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMaxDamage() + "\n" +
                    Chat.linePassive + Chat.RED + "AttackSpeed" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getAttackSpeed()
                    );
        } else {
            CCItem.MSG.STATFAIL.send(sender);
        }
    }
}
