package com.gmail.mooman219.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.frame.serialize.yaml.CSBasicLocation;
import com.gmail.mooman219.frame.serialize.yaml.CSChunkLocation;
import com.gmail.mooman219.frame.serialize.yaml.CSLocation;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.module.chat.CCChat;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.rpg.item.CCItem;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.stat.CCStat;
import com.gmail.mooman219.module.vanilla.CCVanilla;
import com.gmail.mooman219.module.world.CCWorld;

public class Loader extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private ArrayList<CowComponent> componentList = new ArrayList<CowComponent>();
    private ArrayList<CowHandler> handlerList = new ArrayList<CowHandler>();

    public void registerConfigurationSerialization() {
        Loader.info("Registering Configuration Serializables");
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

    public void processHandlers(boolean enable) {
        Loader.info((enable ? "Loading" : "Unloading") + " " + handlerList.size() + " handlers");
        for(CowHandler p : handlerList) {
            Loader.info((enable ? "Enabling" : "Disabling") + " " + p.getName());
            try {
                if(enable) {
                    p.onEnable();
                } else {
                    p.onDisable();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processComponents(boolean enable) {
        Loader.info((enable ? "Loading" : "Unloading") + " " + componentList.size() + " components");
        for(CowComponent p : componentList) {
            Loader.info((enable ? "Enabling" : "Disabling") + " " + p.getName());
            try {
                if(enable) {
                    p.onEnable();
                    p.loadCommands();
                } else {
                    p.onDisable();
                }
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
     * CS   | Cow Serialize   | Custom ConfigurationSerializables
     *
     *
     * PD   | Player Data
     * PL   | Player Live
     */

    @Override
    public void onLoad() {
        // Order IS important
        handlerList.add(new CHConfig());
        handlerList.add(new CHDatabase());
        handlerList.add(new CHTask(this));
        // ~
        componentList.add(new CCService(this));
        componentList.add(new CCLogin(this));
        componentList.add(new CCGraveyard(this));
        componentList.add(new CCMineral(this));
        componentList.add(new CCChat(this));
        componentList.add(new CCRegion(this));
        componentList.add(new CCWorld(this));
        componentList.add(new CCVanilla(this));
        // RPG
        componentList.add(new CCItem(this));
        componentList.add(new CCStat(this));
        // Register early
        registerConfigurationSerialization();
    }

    @Override
    public void onEnable() {
        // Start handlers
        processHandlers(true);
        // Start components
        processComponents(true);

        PluginDescriptionFile pdfFile = getDescription();
        Loader.info("Version: " + pdfFile.getVersion() + " Enabled.");
        Loader.info("Created by: " + pdfFile.getAuthors());
    }

    @Override
    public void onDisable() {
        // Shutdown components
        Collections.reverse(componentList);
        processComponents(false);
        // Shutdown handlers
        Collections.reverse(handlerList);
        processHandlers(false);

        PluginDescriptionFile pdfFile = getDescription();
        Loader.info("Version: " + pdfFile.getVersion() + " Disabled.");
        Loader.info("Created by: " + pdfFile.getAuthors());
    }

    public void addCommand(CCommand command) {
        this.getCommand(command.command).setExecutor(command);
    }

    public <T extends Listener> T addListener(T listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
        return listener;
    }

    /**
     * Fuck you, I'm lazy
     */
    public static void info(Number number) {
        info(number + "");
    }

    public static void info(String message) {
        Loader.log.info("[" + (Bukkit.isPrimaryThread() ? "-" : "+") + "] " + message);
    }

    public static void warning(String message) {
        Loader.log.warning("[X] " + "[" + (Bukkit.isPrimaryThread() ? "-" : "+") + "] " + message);
    }
}
// Mooman219's code. | CowCoding == CC | Cow == C
