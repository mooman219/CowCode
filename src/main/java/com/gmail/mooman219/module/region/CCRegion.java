package com.gmail.mooman219.module.region;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyInformation;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewInformation;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerData;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;

public class CCRegion implements Module {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Region] ";

    public ListenerPlayer listenerPlayer;
    public ListenerData listenerData;

    public CCRegion(Loader plugin){
        this.plugin = plugin;
    }

    public void onEnable(){
        listenerPlayer = new ListenerPlayer();
        listenerData = new ListenerData();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayer, plugin);
        pm.registerEvents(listenerData, plugin);

        Loader.info(cast + "Enabled");
    }

    public void onDisable(){
        Loader.info(cast + "Disabled");
    }
    
    public void registerConfigurationSerialization() {}

    public void loadCommands() {
        plugin.getCommand("modifycombat").setExecutor(new ModifyCombat());
        plugin.getCommand("modifyinformation").setExecutor(new ModifyInformation());
        plugin.getCommand("modifyname").setExecutor(new ModifyName());
        plugin.getCommand("newinformation").setExecutor(new NewInformation());
        plugin.getCommand("setregion").setExecutor(new SetRegion());
    }
}
