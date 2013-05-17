package com.gmail.mooman219.module.mineral;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.mineral.command.AddMineral;
import com.gmail.mooman219.module.mineral.command.ClearMinerals;
import com.gmail.mooman219.module.mineral.command.ListMinerals;
import com.gmail.mooman219.module.mineral.command.RemoveMineral;
import com.gmail.mooman219.module.mineral.command.RevertMinerals;
import com.gmail.mooman219.module.mineral.command.TotalMinerals;
import com.gmail.mooman219.module.mineral.listener.ListenerBlock;
import com.gmail.mooman219.module.mineral.listener.ListenerTime;
import com.gmail.mooman219.module.mineral.store.CSMineral;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class CCMineral implements CowComponent {
    public final Loader plugin;
    public StoreMineral storeMineral;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Mineral] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerBlock listenerBlock;
    public ListenerTime listenerTime;

    public CCMineral(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public void onEnable(){
        storeMineral = new StoreMineral();
        Loader.info(cast + "Loaded " + storeMineral.fileName);
        Loader.info(cast + "Starting MineralManager");
        MineralManager.start();

        listenerBlock = new ListenerBlock();
        listenerTime = new ListenerTime();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerBlock, plugin);
        pm.registerEvents(listenerTime, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Stopping MineralManager");
        MineralManager.stop();
        Loader.info(cast + "Saving " + storeMineral.fileName);
        storeMineral.save();
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSMineral.class, "CSMineral");
    }

    @Override
    public void loadCommands() {
        plugin.addCommand(new AddMineral(this));
        plugin.addCommand(new ClearMinerals(this));
        plugin.addCommand(new ListMinerals());
        plugin.addCommand(new RemoveMineral(this));
        plugin.addCommand(new RevertMinerals());
        plugin.addCommand(new TotalMinerals());
    }
    
    public class Messages {
        public final Bulletin REVERT = new Bulletin(Chat.msgInfo, "All minerals reverted.", Chat.formatInfo);
        public final Bulletin LOCATE_FAILED = new Bulletin(Chat.msgError, "Unable to find a block.", Chat.formatError);
    }
    
    public class Formats {
        public final Bulletin EDIT = new Bulletin(Chat.msgInfo, "Edited mineral [{0}] Delay [{1}].", Chat.formatInfo);
        public final Bulletin TOTAL = new Bulletin(Chat.msgPassive, "Total minerals [{0}].", Chat.formatPassive);
        public final Bulletin ADD = new Bulletin(Chat.msgInfo, "Added new mineral [{0}] Delay [{1}].", Chat.formatInfo);
        public final Bulletin CLEAR = new Bulletin(Chat.msgWarn, "Cleared [{0}] minerals.", Chat.formatWarn);
        public final Bulletin LIST_TITLE = new Bulletin(Chat.msgPassive, "Listing [{0}] minerals.", Chat.formatPassive);
        public final Bulletin LIST = new Bulletin(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Delay [{4}].", Chat.formatPassive);
        public final Bulletin REMOVE = new Bulletin(Chat.msgWarn, "Removed mineral [{0}].", Chat.formatWarn);
    }
}
