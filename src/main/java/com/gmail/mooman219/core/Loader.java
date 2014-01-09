package com.gmail.mooman219.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.mooman219.frame.command.CCommand;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.module.chat.CCChat;
import com.gmail.mooman219.module.damage.CCDamage;
import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.item.CCItem;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.mineral.CCMineral;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.stat.CCStat;
import com.gmail.mooman219.module.vanilla.CCVanilla;
import com.gmail.mooman219.module.world.CCWorld;

public class Loader extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private ArrayList<CowModule> moduleList = new ArrayList<CowModule>();
    private ArrayList<CowHandler> handlerList = new ArrayList<CowHandler>();

    @Override
    public void onLoad() {
        // Order IS important
        handlerList.add(new CHTask(this));
        handlerList.add(new CHDatabase());
        // ~
        moduleList.add(new CCService());
        moduleList.add(new CCLogin());
        moduleList.add(new CCGraveyard());
        moduleList.add(new CCMineral());
        moduleList.add(new CCChat());
        moduleList.add(new CCRegion());
        moduleList.add(new CCWorld());
        moduleList.add(new CCVanilla());
        moduleList.add(new CCDamage());
        moduleList.add(new CCItem());
        moduleList.add(new CCStat());
    }

    public void processHandlers(boolean enable) {
        Loader.info((enable ? "Loading" : "Unloading") + " " + handlerList.size() + " handlers");
        for(CowHandler handler : handlerList) {
            Loader.info((enable ? "Enabling" : "Disabling") + " " + handler.getType().getName());
            try {
                if(enable) {
                    handler.onEnable();
                } else {
                    handler.onDisable();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processModules(boolean enable) {
        Loader.info((enable ? "Loading" : "Unloading") + " " + moduleList.size() + " modules");
        for(CowModule module : moduleList) {
            Loader.info((enable ? "Enabling" : "Disabling") + " " + module.getType().getName());
            try {
                if(enable) {
                    module.onEnable(this);
                    module.loadCommands(this);
                } else {
                    module.onDisable(this);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        // Start handlers
        processHandlers(true);
        // Start components
        processModules(true);

        PluginDescriptionFile pdfFile = getDescription();
        Loader.info("Version: " + pdfFile.getVersion() + " Enabled.");
        Loader.info("Created by: " + pdfFile.getAuthors());
    }

    @Override
    public void onDisable() {
        // Shutdown components
        Collections.reverse(moduleList);
        processModules(false);
        // Shutdown handlers
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
