package com.gmail.mooman219.core;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.serialize.CSBasicLocation;
import com.gmail.mooman219.frame.serialize.CSChunkLocation;
import com.gmail.mooman219.frame.serialize.CSLocation;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.chat.CCChat;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.world.CCWorld;

public class Loader extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private static ArrayList<CowComponent> componentList = new ArrayList<CowComponent>();
    private static ArrayList<CowHandler> handlerList = new ArrayList<CowHandler>();
    public final static String cast = "[CC] ";

    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSBasicLocation.class, "CSBasicLocation");
        ConfigurationSerialization.registerClass(CSChunkLocation.class, "CSChunkLocation");
        ConfigurationSerialization.registerClass(CSLocation.class, "CSLocation");
        for(CowComponent p : componentList) {
            try {
                p.registerConfigurationSerialization();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Cheat sheet, most likely out of date due to lazyness.
     * I like to append letters to my files that have common names
     * to clarify I want to use my version of said file.
     * C    | Cow             | Cow related files
     * CD	| Cow Data		  |
     * CH   | Cow Handler     | Handler component
     * CC   | Cow Component   | Plugin components
     * CM   | Cow Message     | Strings for messages
     * CS   | Cow Serialize   | Custom ConfigurationSerializables
     * 
     * 
     * PD   | Player Data
     * PL   | Player Live
     * PT   | Player Tag
     * CL   | Chunk Live
     * CT   | Chunk Tag
     * WL   | World Live
     * WT   | World Tag
     */

    @Override
    public void onLoad() {
        handlerList.add(new CHConfig());
        handlerList.add(new CHDatabase());
        handlerList.add(new CHTask(this));
        handlerList.add(new CHPacket());
        componentList.add(new CCService(this));
        componentList.add(new CCLogin(this));
        componentList.add(new CCWorld(this));
        componentList.add(new CCGraveyard(this));
        componentList.add(new CCMineral(this));
        componentList.add(new CCChat(this));
        componentList.add(new CCRegion(this));
    }

    @Override
    public void onEnable() {
        // Start Loader
        Loader.info(cast + "Registering serializables");
        registerConfigurationSerialization();
        // Start handlers
        Loader.info(cast + "Loading handlers");
        for(CowHandler p : handlerList) {
            try {
                p.onEnable();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        // Start components
        Loader.info(cast + "Loading components");
        for(CowComponent p : componentList) {
            try {
                p.onEnable();
                p.loadCommands();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        //
        PluginDescriptionFile pdfFile = getDescription();
        Loader.info(cast + "Version: " + pdfFile.getVersion() + " Enabled.");
        Loader.info(cast + "Created by: " + pdfFile.getAuthors());
    }

    @Override
    public void onDisable() {
        // Shutdown components
        Loader.info(cast + "Unloading components");
        for(int i = componentList.size() - 1; i >= 0; i--) {
            try {
                componentList.get(i).onDisable();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        // Shutdown handlers
        Loader.info(cast + "Unloading handlers");
        for(int i = handlerList.size() - 1; i >= 0; i--) {
            try {
                handlerList.get(i).onDisable();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        //
        PluginDescriptionFile pdfFile = getDescription();
        Loader.info(cast + "Version: " + pdfFile.getVersion() + " Disabled.");
        Loader.info(cast + "Created by: " + pdfFile.getAuthors());
    }
    
    public void addCommand(CCommand command) {
        this.getCommand(command.command).setExecutor(command);
    }

    public static void info(String message) {
        Loader.log.info("[" + (Bukkit.isPrimaryThread() ? "-" : "+") + "] " + message);
    }

    public static void warning(String message) {
        Loader.log.warning("[" + (Bukkit.isPrimaryThread() ? "-" : "+") + "] " + message);
    }
}
// Mooman219's code. | CowCoding == CC | Cow == C
