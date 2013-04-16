package com.gmail.mooman219.module.mineral.command;

import java.util.HashSet;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.CMMineral;
import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.store.StoreMineral;
import com.gmail.mooman219.module.service.DTPlayer;

public class AddMineral extends CCommand {
    public HashSet<Byte> skippedBlocks;
    public CCMineral module;

    public AddMineral(CCMineral module) {
        super("number", Rank.GAMEMASTER, "/AddMineral (Delay)");
        this.module = module;
        this.skippedBlocks = new HashSet<Byte>();
        this.skippedBlocks.add((byte) 6); this.skippedBlocks.add((byte) 8); this.skippedBlocks.add((byte) 9);
        this.skippedBlocks.add((byte) 10); this.skippedBlocks.add((byte) 11); this.skippedBlocks.add((byte) 30);
        this.skippedBlocks.add((byte) 31); this.skippedBlocks.add((byte) 32); this.skippedBlocks.add((byte) 37);
        this.skippedBlocks.add((byte) 38); this.skippedBlocks.add((byte) 39); this.skippedBlocks.add((byte) 40);
        this.skippedBlocks.add((byte) 50); this.skippedBlocks.add((byte) 51); this.skippedBlocks.add((byte) 55);
        this.skippedBlocks.add((byte) 59); this.skippedBlocks.add((byte) 69); this.skippedBlocks.add((byte) 70);
        this.skippedBlocks.add((byte) 72); this.skippedBlocks.add((byte) 75); this.skippedBlocks.add((byte) 76);
        this.skippedBlocks.add((byte) 77); this.skippedBlocks.add((byte) 78); this.skippedBlocks.add((byte) 83);
        this.skippedBlocks.add((byte) 104); this.skippedBlocks.add((byte) 105); this.skippedBlocks.add((byte) 106);
        this.skippedBlocks.add((byte) 65); this.skippedBlocks.add((byte) 66); this.skippedBlocks.add((byte) 0);
    }

    @Override
    public void processPlayer(Player sender, DTPlayer playerData, String[] args) {
        for(Block block : sender.getLineOfSight(skippedBlocks, 6)){
            if(block.getType() != Material.AIR) {
                int delay = Integer.parseInt(args[0]);
                if(MineralManager.addMineral(block.getLocation(), block.getType(), delay)) {
                    TextHelper.message(sender, CMMineral.F_EDIT, StoreMineral.minerals.size(), delay);
                } else {
                    TextHelper.message(sender, CMMineral.F_ADD, StoreMineral.minerals.size(), delay);
                }
                WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
                WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
                module.storeMineral.save();
                return;
            }
        }
        TextHelper.message(sender, CMMineral.M_LOCATE_FAILED);
    }
}