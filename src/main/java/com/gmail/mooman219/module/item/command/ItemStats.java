package com.gmail.mooman219.module.item.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.CCItem;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.aspect.Aspect;
import com.gmail.mooman219.module.item.api.aspect.AspectWeapon;

public class ItemStats extends CCommand {
    public ItemStats() {
        super("itemstats", Rank.REGULAR, "/ItemStats");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(!ItemHelper.isNull(sender.getItemInHand())) {
            AspectWeapon weaponAspect = AspectWeapon.get(sender.getItemInHand());
            sender.sendMessage(
                    Chat.msgInfo + Chat.GREEN + "Listing " + Chat.GRAY + "Aspect" + Chat.GREEN + " Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.lineInfo + Chat.WHITE + "Has Aspect" + Chat.DARK_GRAY + ": " + Chat.WHITE + Aspect.hasAspect(sender.getItemInHand()) + "\n" +
                    Chat.linePassive + Chat.GRAY + "Button" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.isButton() + "\n" +
                    Chat.linePassive + Chat.GRAY + "Soulbound" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.isSoulbound() + "\n" +
                    Chat.linePassive + Chat.GRAY + "Unmoveable" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.isUnmoveable() + "\n" +
                    Chat.linePassive + Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.GREEN + weaponAspect.getPrice()
                    );
            sender.sendMessage(
                    Chat.msgInfo + Chat.GREEN + "Listing " + Chat.GOLD + "AspectItem" + Chat.GREEN + " Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.linePassive + Chat.GRAY + "Rarity" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.getRarity().getFullName() + "\n" +
                    Chat.linePassive + Chat.GRAY + "AspectType" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.getAspectType().getFullName()
                    );
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
