package com.gmail.mooman219.module.region;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
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

public class CCRegion extends CowModule {
    private static final ModuleType type = ModuleType.REGION;
    public static Messages MSG;
    public static Formats FRM;

    public StoreRegion storeRegion;
    public StoreChunk storeChunk;
    public ListenerPlayer listenerPlayer;

    public CCRegion(Loader plugin) {
        super(plugin);
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public ModuleType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
    }

    @Override
    public void onEnable(){
        storeRegion = new StoreRegion(getCast(), getDirectory());
        storeChunk = new StoreChunk(getCast(), getDirectory());

        listenerPlayer = getPlugin().addListener(new ListenerPlayer());
    }

    @Override
    public void onDisable(){
        Loader.info(getCast() + "Saving " + storeRegion.getFile().getName());
        storeRegion.save();
        Loader.info(getCast() + "Saving " + storeChunk.getFile().getName());
        storeChunk.save();
    }

    @Override
    public void loadCommands() {
        getPlugin().addCommand(new ModifyID());
        getPlugin().addCommand(new ModifyCombat());
        getPlugin().addCommand(new ModifyDescription());
        getPlugin().addCommand(new ModifyLock());
        getPlugin().addCommand(new ModifyName());
        getPlugin().addCommand(new NewRegion());
        getPlugin().addCommand(new Region());
        getPlugin().addCommand(new SetRegion());
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
