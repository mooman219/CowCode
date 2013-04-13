package com.gmail.mooman219.core;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mooman219.frame.serialize.CSBasicLocation;
import com.gmail.mooman219.frame.serialize.CSChunkLocation;
import com.gmail.mooman219.frame.serialize.CSLocation;
import com.gmail.mooman219.handler.config.CHConfig;
import com.gmail.mooman219.handler.databse.CHDatabase;
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
    public static ArrayList<Module> moduleList = new ArrayList<Module>();
    public static ArrayList<Handler> handlerList = new ArrayList<Handler>();
    public static String cast = "[CC] ";
    public static String version;
    public static String authors;

    public void registerConfigurationSerialization() {
        try {
            ConfigurationSerialization.registerClass(CSBasicLocation.class, "CSBasicLocation");
            ConfigurationSerialization.registerClass(CSChunkLocation.class, "CSChunkLocation");
            ConfigurationSerialization.registerClass(CSLocation.class, "CSLocation");
        } catch(Exception e) {
            e.printStackTrace();
        }
        for(Module p : moduleList) {
            try {
                p.registerConfigurationSerialization();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * C    | Cow             | Cow related files
     * CH   | Cow Handler     | Handler modules
     * CC   | Cow Component   | Plugin modules
     * CM   | Cow Message     | Strings for messages
     * CS   | Cow Serialize   | Custom ConfigurationSerializables
     * 
     * PD   | Player Data     | Offline PlayerData
     * PL   | Player Live     | Online PlayerData
     */

    @Override
    public void onLoad() {
        handlerList.add(new CHConfig());
        handlerList.add(new CHDatabase());
        handlerList.add(new CHTask(this));
        handlerList.add(new CHPacket());
        moduleList.add(new CCService(this));
        moduleList.add(new CCLogin(this));
        moduleList.add(new CCWorld(this));
        moduleList.add(new CCGraveyard(this));
        moduleList.add(new CCMineral(this));
        moduleList.add(new CCChat(this));
        moduleList.add(new CCRegion(this));
        
        //moduleList.add(new CCVanilla(this));
        //moduleList.add(new CCDiscipline(this));
        //moduleList.add(new CCShop(this));
        //moduleList.add(new CCRPGProfession(this));
        //moduleList.add(new CCRPGSkill(this));
    }

    @Override
    public void onEnable() {
        // Start Loader
        Loader.info(cast + "Registering serializables");
        registerConfigurationSerialization();
        // Start handlers
        Loader.info(cast + "Loading handlers");
        for(Handler p : handlerList) {
            try {
                p.onEnable();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        // Start modules
        Loader.info(cast + "Loading modules");
        for(Module p : moduleList) {
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
        // Shutdown modules
        Loader.info(cast + "Unloading modules");
        for(int i = moduleList.size() - 1; i >= 0; i--) {
            try {
                moduleList.get(i).onDisable();
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

    public static void info(String message) {
        Loader.log.info("[" + (Boolean.valueOf(Bukkit.isPrimaryThread()) ? "-" : "+") + "] " + message);
    }

    public static void warning(String message) {
        Loader.log.warning("[" + (Boolean.valueOf(Bukkit.isPrimaryThread()) ? "-" : "+") + "] " + message);
    }
}
// Mooman219's code. | CowCoding == CC | Cow == C
