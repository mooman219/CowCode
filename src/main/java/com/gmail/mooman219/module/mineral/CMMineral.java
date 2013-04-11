package com.gmail.mooman219.module.mineral;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMMineral {
    public final static String M_REVERT = TextHelper.buildString(Chat.msgInfo, "All minerals reverted.", Chat.formatInfo);
    public final static String M_LOCATE_FAILED = TextHelper.buildString(Chat.msgError, "Unable to find a block.", Chat.formatError);

    public final static String F_EDIT = TextHelper.buildString(Chat.msgInfo, "Edited mineral [{0}] Delay [{1}].", Chat.formatInfo);
    public final static String F_TOTAL = TextHelper.buildString(Chat.msgPassive, "Total minerals [{0}].", Chat.formatPassive);
    public final static String F_ADD = TextHelper.buildString(Chat.msgInfo, "Added new mineral [{0}] Delay [{1}].", Chat.formatInfo);
    public final static String F_CLEAR = TextHelper.buildString(Chat.msgWarn, "Cleared [{0}] minerals.", Chat.formatWarn);
    public final static String F_LIST_TITLE = TextHelper.buildString(Chat.msgPassive, "Listing [{0}] minerals.", Chat.formatPassive);
    public final static String F_LIST = TextHelper.buildString(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Delay [{4}].", Chat.formatPassive);
    public final static String F_REMOVE = TextHelper.buildString(Chat.msgWarn, "Removed mineral [{0}].", Chat.formatWarn);
}
