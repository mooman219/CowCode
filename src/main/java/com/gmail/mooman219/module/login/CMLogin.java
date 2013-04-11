package com.gmail.mooman219.module.login;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMLogin {
    public static final String M_SHUTDOWN = TextHelper.buildString(Chat.GRAY + "", "The server will be " + Chat.DARK_GREEN + "restarting. Kicking to save data.", Chat.formatPassive);

    public static final String F_LOGINDELAY = Chat.GRAY + "Please wait another " + Chat.RED + "{0}" + Chat.GRAY + " before joining" + Chat.DARK_GRAY + ".";
}
