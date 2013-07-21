package com.gmail.mooman219.frame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.file.FileHelper;

public class WorldHelper {
    public static boolean doesWorldExist(String worldName) {
        for(World world : Bukkit.getWorlds()) {
            if(world.getName().equalsIgnoreCase(worldName)) {
                return true;
            }
        }
        return FileHelper.doesExist(worldName + "/level.dat");
    }

    public static World getWorld(String worldName) {
        for(World world : Bukkit.getWorlds()) {
            if(world.getName().equalsIgnoreCase(worldName)) {
                return world;
            }
        }
        World ret = Bukkit.getServer().createWorld(new WorldCreator(worldName));
        return ret;
    }

    public static List<World> getWorlds() {
        return Bukkit.getWorlds();
    }

    public static void teleportToWorld(String worldName) {
        teleportToWorld(getWorld(worldName));
    }

    public static void teleportToWorld(String worldName, Player player) {
        teleportToWorld(getWorld(worldName), player);
    }

    public static void teleportToWorld(World world) {
        Location worldSpawn =  world.getSpawnLocation();
        for(Player player : Bukkit.getOnlinePlayers()){
            player.teleport(worldSpawn);
        }
    }

    public static void teleportToWorld(World world, Player player) {
        Location worldSpawn =  world.getSpawnLocation();
        player.teleport(worldSpawn);
    }

    public static void unloadAll(boolean save) {
        for(World world : Bukkit.getWorlds()) {
            Bukkit.unloadWorld(world, save);
        }
    }

    public static void unloadAllExcept(String worldName, boolean save) {
        for(World world : Bukkit.getWorlds()) {
            if(!world.getName().equalsIgnoreCase(worldName)) {
                Bukkit.unloadWorld(world, save);
            }
        }
    }

    public static void playEffect(Location location, Effect effect, int data, int radius) {
        location.getWorld().playEffect(location, effect, data, radius);
    }

    public static void playEffect(Location location, Effect effect, int data) {
        location.getWorld().playEffect(location, effect, data);
    }

    public static void playEffect(Location location, Effect effect) {
        location.getWorld().playEffect(location, effect, 0);
    }

    public static void playSound(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    public static void playSound(Location location, Sound sound) {
        location.getWorld().playSound(location, sound, 0f, 0f);
    }
}
