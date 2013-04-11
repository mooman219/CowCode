package com.gmail.mooman219.module.region;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMRegion {
    public final static String M_EXISTS = TextHelper.buildString(Chat.msgError, "Region already exists.", Chat.formatError);
    public final static String M_NONEXISTS = TextHelper.buildString(Chat.msgError, "The region does not exist.", Chat.formatError);
    public final static String M_ADDED = TextHelper.buildString(Chat.msgInfo, "Region added!", Chat.formatInfo);
    public final static String M_MODIFIED = TextHelper.buildString(Chat.msgInfo, "Region modified!", Chat.formatInfo);
}
