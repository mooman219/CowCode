package com.gmail.mooman219.module.graveyard;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMGraveyard {
    public static final String M_RESPAWN = TextHelper.buildString(Chat.msgPassive, "You have spawned at the nearest graveyard.", Chat.formatPassive);
    public static final String M_TPCLOSE = TextHelper.buildString(Chat.msgInfo, "Teleported to the closest graveyard.", Chat.formatInfo);
    public static final String M_TPCLOSE_FAILED = TextHelper.buildString(Chat.msgError, "Unable to find suitable graveyard.", Chat.formatError);

    public static final String F_ADD = TextHelper.buildString(Chat.msgInfo, "Graveyard added. Total graveyards [{0}]", Chat.formatInfo);
    public static final String F_CLEAR = TextHelper.buildString(Chat.msgWarn, "Cleared [{0}] graveyards.", Chat.formatWarn);
    public static final String F_LIST_TITLE = TextHelper.buildString(Chat.msgPassive, "Listing [{0}] graveyards.", Chat.formatPassive);
    public static final String F_LIST = TextHelper.buildString(Chat.linePassive, "ID: [{0}] | [X:Y:Z] [{1}:{2}:{3}] Level: [{4}]", Chat.formatPassive);
    public static final String F_REMOVE = TextHelper.buildString(Chat.msgWarn, "Removed graveyard at [X,Z] [{0},{1}]", Chat.formatWarn);
    public static final String F_TP = TextHelper.buildString(Chat.msgInfo, "Teleported to graveyard [{0}].", Chat.formatInfo);
    public static final String F_TP_FAILED = TextHelper.buildString(Chat.msgError, "Unable to find graveyard [{0}].", Chat.formatError);
    public static final String F_TOTAL = TextHelper.buildString(Chat.msgPassive, "Total graveyards [{0}]", Chat.formatPassive);
}
