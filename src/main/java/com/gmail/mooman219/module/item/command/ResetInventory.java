package com.gmail.mooman219.module.item.command;

import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.item.CCItem;
import com.gmail.mooman219.module.item.api.InventoryHelper;

public class ResetInventory extends CCommand {
    public ResetInventory() {
        super("resetinventory", Rank.REGULAR, "/ResetInventory");
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        InventoryHelper.removeUnmoveables(sender.getInventory());
        InventoryHelper.setupPlayerInventory(sender.getInventory());
        CCItem.MSG.INVENTORY_RESET.send(sender);
    }
}
