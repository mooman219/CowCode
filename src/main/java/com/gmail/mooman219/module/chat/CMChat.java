package com.gmail.mooman219.module.chat;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMChat {
    public static final String M_MESSAGE_EMPTY = TextHelper.buildString(Chat.msgError, "Please specify a message.", Chat.formatError);
    public static final String M_MESSAGE_SELF = TextHelper.buildString(Chat.msgError, "You should stop talking to yourself.", Chat.formatError);
    public static final String M_MESSAGE_LOST = TextHelper.buildString(Chat.msgError, "No suitable player to respond to.", Chat.formatError);
    public static final String M_NOHEAR = TextHelper.buildString(Chat.msgWarn, "No one can hear you. Try adding \"!\" before your message.", Chat.formatWarn);

    public static final String F_SETRANGE = TextHelper.buildString(Chat.msgInfo, "Chat range updated from [{0}] to [{1}]!", Chat.formatInfo);
    public static final String F_MUTED = TextHelper.buildString(Chat.msgError, "You're currently muted for {0}.", Chat.formatError);
    public static final String F_MESSAGE_EXIST = TextHelper.buildString(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
    public static final String F_GLOBAL_DELAY = TextHelper.buildString(Chat.msgError, "Wait {0} before next message.", Chat.formatError);
    public static final String F_LOGIN = TextHelper.buildString(Chat.msgPassive, "{0} has logged in.", Chat.formatPassive);
    public static final String F_KICK = TextHelper.buildString(Chat.msgPassive, "{0} has been kicked.", Chat.formatPassive);
    public static final String F_QUIT = TextHelper.buildString(Chat.msgPassive, "{0} has logged out.", Chat.formatPassive);
}
