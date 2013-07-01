package com.gmail.mooman219.module.region;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyID;
import com.gmail.mooman219.module.region.command.ModifyInfo;
import com.gmail.mooman219.module.region.command.ModifyLock;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewRegion;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;
import com.gmail.mooman219.module.region.store.StoreRegion;

public class CCRegion implements CowComponent {
    public final Loader plugin;
    public StoreRegion storeRegionInformation;

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
        storeRegionInformation = new StoreRegion(directory);
        Loader.info(cast + "Loaded " + storeRegionInformation.getFile().getName());

        listenerPlayer = new ListenerPlayer();

        plugin.addListener(listenerPlayer);
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeRegionInformation.getFile().getName());
        storeRegionInformation.save();
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new ModifyID());
        plugin.addCommand(new ModifyCombat());
        plugin.addCommand(new ModifyInfo());
        plugin.addCommand(new ModifyLock());
        plugin.addCommand(new ModifyName());
        plugin.addCommand(new NewRegion());
        plugin.addCommand(new SetRegion());
    }

    public class Messages {
        public final Bulletin LOCKED = new Bulletin(Chat.msgError, "That region is currently locked.", Chat.formatError);
        public final Bulletin EXISTS = new Bulletin(Chat.msgError, "Region already exists.", Chat.formatError);
        public final Bulletin NONEXISTS = new Bulletin(Chat.msgError, "The region does not exist.", Chat.formatError);
        public final Bulletin ADDED = new Bulletin(Chat.msgInfo, "Region added!", Chat.formatInfo);
    }

    public class Formats {
        public final Bulletin MODIFIED = new Bulletin(Chat.msgInfo, "Modified region {0}!", Chat.formatInfo);
    }
}
