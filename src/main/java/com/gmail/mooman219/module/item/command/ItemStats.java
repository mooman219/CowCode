package com.gmail.mooman219.module.item.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.item.AspectWeapon;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.CCItem;

public class ItemStats extends CCommand {
    public ItemStats() {
        super("itemstats", Rank.REGULAR, "/ItemStats");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        if(sender.getItemInHand() != null) {            
            AspectWeapon weaponAspect = AspectWeapon.getAspectWeapon(sender.getItemInHand());
            sender.sendMessage(
                    Chat.GREEN + "Listing Aspect Elements" + Chat.DARK_GREEN + ": \n" +
                            Chat.GRAY + "AspectType" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.getAspectType().getName() + "\n" +
                            Chat.GRAY + "Rarity" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.getRarity().getName() + "\n" +
                            Chat.GRAY + "Is Soulbound" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.isSoulbound() + "\n" +
                            Chat.RED + "Min Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMinDamage() + "\n" +
                            Chat.RED + "Max Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMaxDamage() + "\n" +
                            Chat.RED + "AttackSpeed" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getAttackSpeed() + "\n" +
                            Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.GREEN + weaponAspect.getPrice()
                    );
        } else {
            CCItem.MSG.STATFAIL.send(sender);
        }
    }
}
