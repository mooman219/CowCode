package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.BlockHelper;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.serialize.json.BasicLocation;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class RemoveMineral extends CCommand {
    public CCMineral module;

    public RemoveMineral(CCMineral module) {
        super("removemineral", Rank.GAMEMASTER, "/RemoveMineral");
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        Block block = BlockHelper.getLineOfSightSolid(sender, 6);
        if(block.getType() != Material.AIR) {
            if(MineralManager.remove(new BasicLocation(block.getLocation()))) {
                CCMineral.FRM.REMOVE.send(sender, StoreMineral.getMinerals().size());
                WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
                return;
            }
        }
        CCMineral.MSG.LOCATE_FAILED.send(sender);
    }
}