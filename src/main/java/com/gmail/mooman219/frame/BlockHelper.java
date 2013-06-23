package com.gmail.mooman219.frame;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockHelper {
    /**
     * Returns the first solid block in the player's line of sight
     * over distance. Returns null if there is no solid block with-
     * -in the given distance.
     */
    public static Block getLineOfSightSolid(Player sender, int distance) {
        for(Block block : sender.getLineOfSight(null, distance)) {
            if(block != null && block.getType() != Material.AIR && block.getType().isSolid()) {
                return block;
            }
        }
        return null;
    }
}
