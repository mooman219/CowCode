package com.gmail.mooman219.module.graveyard;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.graveyard.command.AddGraveyard;
import com.gmail.mooman219.module.graveyard.command.ClearGraveyards;
import com.gmail.mooman219.module.graveyard.command.ListGraveyards;
import com.gmail.mooman219.module.graveyard.command.RemoveGraveyard;
import com.gmail.mooman219.module.graveyard.command.TeleportClosestGraveyard;
import com.gmail.mooman219.module.graveyard.command.TeleportGraveyard;
import com.gmail.mooman219.module.graveyard.command.TotalGraveyards;
import com.gmail.mooman219.module.graveyard.listener.ListenerPlayer;
import com.gmail.mooman219.module.graveyard.store.StoreGraveyard;

public class CCGraveyard implements CowComponent {
    public final Loader plugin;
    public StoreGraveyard storeGraveyard;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][Graveyard] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerPlayer listenerPlayer;

    public CCGraveyard(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Graveyard";
    }

    @Override
    public void onEnable(){
        storeGraveyard = new StoreGraveyard(directory);
        Loader.info(cast + "Loaded " + storeGraveyard.getFile().getName());

        listenerPlayer = plugin.addListener(new ListenerPlayer());
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Saving " + storeGraveyard.getFile().getName());
        storeGraveyard.save();
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new AddGraveyard(this));
        plugin.addCommand(new ClearGraveyards(this));
        plugin.addCommand(new ListGraveyards());
        plugin.addCommand(new RemoveGraveyard(this));
        plugin.addCommand(new TeleportClosestGraveyard());
        plugin.addCommand(new TeleportGraveyard());
        plugin.addCommand(new TotalGraveyards());
    }

    public class Messages {
        public final Bulletin RESPAWN = new Bulletin(Chat.msgPassive, "You have spawned at the nearest graveyard.", Chat.formatPassive);
        public final Bulletin TPCLOSE = new Bulletin(Chat.msgInfo, "Teleported to the closest graveyard.", Chat.formatInfo);
        public final Bulletin TPCLOSE_FAILED = new Bulletin(Chat.msgError, "Unable to find suitable graveyard.", Chat.formatError);
    }

    public class Formats {
        public final Bulletin ADD = new Bulletin(Chat.msgInfo, "Graveyard added. Total graveyards [{0}]", Chat.formatInfo);
        public final Bulletin CLEAR = new Bulletin(Chat.msgWarn, "Cleared [{0}] graveyards.", Chat.formatWarn);
        public final Bulletin LIST_TITLE = new Bulletin(Chat.msgPassive, "Listing [{0}] graveyards.", Chat.formatPassive);
        public final Bulletin LIST = new Bulletin(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Level: [{4}]", Chat.formatPassive);
        public final Bulletin REMOVE = new Bulletin(Chat.msgWarn, "Removed graveyard at [X,Z] [{0},{1}]", Chat.formatWarn);
        public final Bulletin TP = new Bulletin(Chat.msgInfo, "Teleported to graveyard [{0}].", Chat.formatInfo);
        public final Bulletin TP_FAILED = new Bulletin(Chat.msgError, "Unable to find graveyard [{0}].", Chat.formatError);
        public final Bulletin TOTAL = new Bulletin(Chat.msgPassive, "Total graveyards [{0}]", Chat.formatPassive);
    }
}
