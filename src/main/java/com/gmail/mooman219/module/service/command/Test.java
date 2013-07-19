package com.gmail.mooman219.module.service.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.item.AspectWeapon;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;

public class Test extends CCommand {
    public Test() {
        super("test", Rank.REGULAR, "/Test (Flag - Create/Read)", Carg.BOOLEAN);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        boolean flag = NumberHelper.toBoolean(args[0]);
        if(flag) {
            ItemStack testStack = new ItemStack(Material.GOLD_AXE);
            AspectWeapon weaponAspect = new AspectWeapon();
            weaponAspect.setMinDamage(2);
            weaponAspect.setMaxDamage(10);
            weaponAspect.write(testStack);
            sender.getInventory().addItem(testStack);
        } else {
            AspectWeapon weaponAspect = AspectWeapon.getAspectWeapon(sender.getItemInHand());
            sender.sendMessage(
                    Chat.GREEN + "Listing Aspect Elements" + Chat.DARK_GREEN + ": \n" +
                    Chat.GRAY + "AspectType" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.getAspectType().getName() + "\n" +
                    Chat.GRAY + "Is Soulbound" + Chat.DARK_GRAY + ": " + Chat.GRAY + weaponAspect.isSoulbound() + "\n" +
                    Chat.RED + "Min Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMinDamage() + "\n" +
                    Chat.RED + "Max Damage" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getMaxDamage() + "\n" +
                    Chat.RED + "AttackSpeed" + Chat.DARK_RED + ": " + Chat.RED + weaponAspect.getAttackSpeed());
        }
    }
}
