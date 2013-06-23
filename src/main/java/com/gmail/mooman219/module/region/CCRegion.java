package com.gmail.mooman219.module.region;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyID;
import com.gmail.mooman219.module.region.command.ModifyInformation;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewRegion;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;
import com.gmail.mooman219.module.region.store.CSRegionInformation;
import com.gmail.mooman219.module.region.store.StoreRegionInformation;

public class CCRegion implements CowComponent {
    public final Loader plugin;
    public StoreRegionInformation storeRegionInformation;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][Region] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerPlayer listenerPlayer;

    public CCRegion(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Region";
    }

    @Override
    public void onEnable(){
        storeRegionInformation = new StoreRegionInformation();
        Loader.info(cast + "Loaded " + storeRegionInformation.fileName);

        listenerPlayer = new ListenerPlayer();

        plugin.addListener(listenerPlayer);
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeRegionInformation.fileName);
        storeRegionInformation.save();
    }

    @Override
    public void registerConfigurationSerialization() {
        ConfigurationSerialization.registerClass(CSRegionInformation.class, "CSRegionInformation");
    }

    @Override
    public void loadCommands() {
        plugin.addCommand(new ModifyID());
        plugin.addCommand(new ModifyCombat());
        plugin.addCommand(new ModifyInformation());
        plugin.addCommand(new ModifyName());
        plugin.addCommand(new NewRegion());
        plugin.addCommand(new SetRegion());
    }

    public class Messages {
        public final Bulletin EXISTS = new Bulletin(Chat.msgError, "Region already exists.", Chat.formatError);
        public final Bulletin NONEXISTS = new Bulletin(Chat.msgError, "The region does not exist.", Chat.formatError);
        public final Bulletin ADDED = new Bulletin(Chat.msgInfo, "Region added!", Chat.formatInfo);
        public final Bulletin MODIFIED = new Bulletin(Chat.msgInfo, "Region modified!", Chat.formatInfo);
    }

    public class Formats {}
}
