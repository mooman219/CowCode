package com.gmail.mooman219.module.mineral;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.mineral.command.AddMineral;
import com.gmail.mooman219.module.mineral.command.ClearMinerals;
import com.gmail.mooman219.module.mineral.command.ListMinerals;
import com.gmail.mooman219.module.mineral.command.RemoveMineral;
import com.gmail.mooman219.module.mineral.command.RevertMinerals;
import com.gmail.mooman219.module.mineral.command.TotalMinerals;
import com.gmail.mooman219.module.mineral.listener.ListenerBlock;
import com.gmail.mooman219.module.mineral.listener.ListenerTime;
import com.gmail.mooman219.module.mineral.store.StoreMineral;

public class CCMineral extends CowModule {
    private static final ModuleType type = ModuleType.MINERAL;
    public static Messages MSG;
    public static Formats FRM;

    public StoreMineral storeMineral;

    public CCMineral() {
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
    public void onEnable(Loader plugin){
        storeMineral = new StoreMineral();

        plugin.addListener(new ListenerBlock());
        plugin.addListener(new ListenerTime());
    }

    @Override
    public void onDisable(Loader plugin){
        Loader.info(getCast() + "Reverting minerals");
        MineralManager.revert();
        storeMineral.save();
    }

    @Override
    public void loadCommands(Loader plugin) {
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
        public final Bulletin TOTAL = new Bulletin(Chat.msgPassive, "Total minerals [{0}].", Chat.formatPassive);
        public final Bulletin ADD = new Bulletin(Chat.msgInfo, "Added new mineral [{0}] Delay [{1}].", Chat.formatInfo);
        public final Bulletin CLEAR = new Bulletin(Chat.msgWarn, "Cleared [{0}] minerals.", Chat.formatWarn);
        public final Bulletin LIST_TITLE = new Bulletin(Chat.msgPassive, "Listing [{0}] minerals.", Chat.formatPassive);
        public final Bulletin LIST = new Bulletin(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Delay [{4}].", Chat.formatPassive);
        public final Bulletin REMOVE = new Bulletin(Chat.msgWarn, "Removed mineral [{0}].", Chat.formatWarn);
    }
}
