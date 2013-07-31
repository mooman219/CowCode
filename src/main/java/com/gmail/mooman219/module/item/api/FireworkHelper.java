package com.gmail.mooman219.module.item.api;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkHelper {
    private static Color[] colors = {Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON,
        Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW};

    public static Color getRandomColor() {
        return colors[(int) (colors.length * Math.random())];
    }

    public static Type getRandomType() {
        return Type.values()[(int) (Type.values().length * Math.random())];
    }

    public static FireworkEffect getRandomFireworkEffect() {
        return FireworkEffect.builder().withColor(getRandomColor()).with(getRandomType()).withFade(getRandomColor()).flicker(true).trail(true).build();
    }

    public static Firework randomize(Firework firework) {
        firework.setFireworkMeta(randomize(firework.getFireworkMeta()));
        return firework;
    }

    public static FireworkMeta randomize(FireworkMeta firework) {
        firework.addEffect(getRandomFireworkEffect());
        firework.setPower((int) (3 * Math.random()));
        return firework;
    }

    public static void spawnRandomFirework(Location loc) {
        randomize((Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK));
    }
}