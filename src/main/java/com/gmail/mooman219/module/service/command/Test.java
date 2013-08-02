package com.gmail.mooman219.module.service.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.api.AspectWeapon;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.Rarity;

public class Test extends CCommand {
    public Test() {
        super("test", Rank.REGULAR, "/Test (Flag - Create/Read)", Carg.INT);
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        int flag = NumberHelper.toInt(args[0]);
        if(flag == 0) {
            ItemStack item = ItemHelper.setName(Material.GOLD_AXE.getId(), Chat.GOLD + "Great Axe of Azazel");
            AspectWeapon weaponAspect = new AspectWeapon();
            weaponAspect.setMinDamage(6);
            weaponAspect.setMaxDamage(14);
            weaponAspect.setSoulbound(true);
            weaponAspect.setAttackSpeed(0.8f);
            weaponAspect.setRarity(Rarity.EPIC);
            sender.getInventory().addItem(weaponAspect.write(item));
        }
    }
}
