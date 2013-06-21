package com.gmail.mooman219.module.mineral;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.mineral.command.AddMineral;
import com.gmail.mooman219.module.mineral.command.ClearMinerals;
import com.gmail.mooman219.module.mineral.command.ListMinerals;
import com.gmail.mooman219.module.mineral.command.RemoveMineral;
import com.gmail.mooman219.module.mineral.command.RevertMinerals;
import com.gmail.mooman219.module.mineral.command.TotalMinerals;
import com.gmail.mooman219.module.mineral.listener.ListenerBlock;

public class CCMineral implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][Mineral] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerBlock listenerBlock;

    public CCMineral(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Mineral";
    }

    @Override
    public void onEnable(){
        listenerBlock = new ListenerBlock();

        plugin.addListener(listenerBlock);
    }

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

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
        public final Bulletin LOCATE_FAILED = new Bulletin(Chat.msgError, "Unable to find a block.", Chat.formatError);
    }

    public class Formats {
        public final Bulletin REVERT = new Bulletin(Chat.msgInfo, "Reverted [{0}] minerals.", Chat.formatInfo);
        public final Bulletin EDIT = new Bulletin(Chat.msgInfo, "Edited mineral [{0}] Delay [{1}].", Chat.formatInfo);
        public final Bulletin TOTAL = new Bulletin(Chat.msgPassive, "Total minerals [{0}] for current chunk.", Chat.formatPassive);
        public final Bulletin ADD = new Bulletin(Chat.msgInfo, "Added new mineral [{0}] Delay [{1}].", Chat.formatInfo);
        public final Bulletin CLEAR = new Bulletin(Chat.msgWarn, "Cleared [{0}] minerals.", Chat.formatWarn);
        public final Bulletin LIST_TITLE = new Bulletin(Chat.msgPassive, "Listing [{0}] minerals from chunk.", Chat.formatPassive);
        public final Bulletin LIST = new Bulletin(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Delay [{4}].", Chat.formatPassive);
        public final Bulletin REMOVE = new Bulletin(Chat.msgWarn, "Removed mineral [{0}].", Chat.formatWarn);
    }
}
