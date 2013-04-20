package com.gmail.mooman219.module.region;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyInformation;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewInformation;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;

public class CCRegion implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Region] ";

    public ListenerPlayer listenerPlayer;

    public CCRegion(Loader plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable(){
        listenerPlayer = new ListenerPlayer();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayer, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.getCommand("modifycombat").setExecutor(new ModifyCombat());
        plugin.getCommand("modifyinformation").setExecutor(new ModifyInformation());
        plugin.getCommand("modifyname").setExecutor(new ModifyName());
        plugin.getCommand("newinformation").setExecutor(new NewInformation());
        plugin.getCommand("setregion").setExecutor(new SetRegion());
    }
}
