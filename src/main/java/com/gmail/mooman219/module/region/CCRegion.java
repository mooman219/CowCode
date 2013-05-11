package com.gmail.mooman219.module.region;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyInformation;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewInformation;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class CCRegion implements CowComponent {
    public final Loader plugin;
    public StoreRegionInformation storeRegionInformation;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Region] ";

    public ListenerPlayer listenerPlayer;

    public CCRegion(Loader plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable(){
        storeRegionInformation = new StoreRegionInformation();
        Loader.info(cast + "Loaded " + storeRegionInformation.fileName);
        
        listenerPlayer = new ListenerPlayer();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayer, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeRegionInformation.fileName);
        storeRegionInformation.save();
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSRegionInformation.class, "CSRegionInformation");
    }

    @Override
    public void loadCommands() {
        plugin.addCommand(new ModifyCombat());
        plugin.addCommand(new ModifyInformation());
        plugin.addCommand(new ModifyName());
        plugin.addCommand(new NewInformation());
        plugin.addCommand(new SetRegion());
    }
}
