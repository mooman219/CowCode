package com.gmail.mooman219.module.mineral.command;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.BlockHelper;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.store.FastMineral;
import com.gmail.mooman219.module.mineral.store.DataMineral;

public class AddMineral extends CCommand {
    public CCMineral module;

    public AddMineral(CCMineral module) {
        super("addmineral", Rank.GAMEMASTER, "/AddMineral (Delay)", Carg.INT);
        this.module = module;
    }

    @Override
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        Block block = BlockHelper.getLineOfSightSolid(sender, 6);
        if(block != null) {
            int delay = Integer.parseInt(args[0]);
            FastMineral mineral = new FastMineral(block, delay * 1000);
            if(MineralManager.add(mineral)) {
                CCMineral.FRM.EDIT.send(sender, DataMineral.getMinerals().size(), delay);
            } else {
                CCMineral.FRM.ADD.send(sender, DataMineral.getMinerals().size(), delay);
            }
            WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
            return;
        }
        CCMineral.MSG.LOCATE_FAILED.send(sender);
    }
}