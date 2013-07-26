package com.gmail.mooman219.module.region;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.region.command.ModifyCombat;
import com.gmail.mooman219.module.region.command.ModifyID;
import com.gmail.mooman219.module.region.command.ModifyDescription;
import com.gmail.mooman219.module.region.command.ModifyLock;
import com.gmail.mooman219.module.region.command.ModifyName;
import com.gmail.mooman219.module.region.command.NewRegion;
import com.gmail.mooman219.module.region.command.Region;
import com.gmail.mooman219.module.region.command.SetRegion;
import com.gmail.mooman219.module.region.listener.ListenerPlayer;
import com.gmail.mooman219.module.region.store.StoreChunk;
import com.gmail.mooman219.module.region.store.StoreRegion;

public class CCRegion implements CowComponent {
    public final Loader plugin;
    public StoreRegion storeRegion;
    public StoreChunk storeChunk;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Region] ";
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
        storeRegion = new StoreRegion(cast, directory);
        storeChunk = new StoreChunk(cast, directory);

        listenerPlayer = plugin.addListener(new ListenerPlayer());
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeRegion.getFile().getName());
        storeRegion.save();
        Loader.info(cast + "Saving " + storeChunk.getFile().getName());
        storeChunk.save();
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new ModifyID());
        plugin.addCommand(new ModifyCombat());
        plugin.addCommand(new ModifyDescription());
        plugin.addCommand(new ModifyLock());
        plugin.addCommand(new ModifyName());
        plugin.addCommand(new NewRegion());
        plugin.addCommand(new Region());
        plugin.addCommand(new SetRegion());
    }

    public class Messages {
        public final Bulletin LOCKED = new Bulletin(Chat.msgError, "That region is currently locked.", Chat.formatError);
        public final Bulletin LOCKEDFAIL = new Bulletin(Chat.msgError, "You have broken the region lock?", Chat.formatError);
        public final Bulletin EXISTS = new Bulletin(Chat.msgError, "Region already exists.", Chat.formatError);
        public final Bulletin NONEXISTS = new Bulletin(Chat.msgError, "The region does not exist.", Chat.formatError);
        public final Bulletin ADDED = new Bulletin(Chat.msgInfo, "Region added!", Chat.formatInfo);
    }

    public class Formats {
        public final Bulletin MODIFIED = new Bulletin(Chat.msgInfo, "Modified region [" + Chat.GRAY + "{0}]!", Chat.formatInfo);
    }
}
