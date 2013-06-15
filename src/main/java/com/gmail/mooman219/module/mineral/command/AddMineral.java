package com.gmail.mooman219.module.mineral.command;

import java.util.HashSet;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.gmail.mooman219.bullbukkit.CDChunk;
import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.command.Carg;
import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.mineral.store.Mineral;

public class AddMineral extends CCommand {
    public HashSet<Byte> skippedBlocks;
    public CCMineral module;

    public AddMineral(CCMineral module) {
        super("addmineral", Rank.GAMEMASTER, "/AddMineral (Delay)", Carg.INT);
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
    public void processPlayer(Player sender, CDPlayer playerData, String[] args) {
        for(Block block : sender.getLineOfSight(skippedBlocks, 6)){
            if(block.getType() != Material.AIR) {
                CDChunk chunk = CDChunk.get(sender);
                int delay = Integer.parseInt(args[0]);
                Mineral mineral = chunk.getMineral(block.getLocation());
                if(mineral != null) {
                    mineral.type = block.getType();
                    mineral.respawnDelay = delay;
                    mineral.respawnTime = -1L;
                    CCMineral.FRM.EDIT.send(sender, chunk.minerals.size(), delay);
                } else {
                    chunk.minerals.add(new Mineral(block, delay));
                    CCMineral.FRM.ADD.send(sender, chunk.minerals.size(), delay);
                }
                WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
                return;
            }
        }
        CCMineral.MSG.LOCATE_FAILED.send(sender);
    }
}