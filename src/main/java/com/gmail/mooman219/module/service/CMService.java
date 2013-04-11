package com.gmail.mooman219.module.service;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.TextHelper;

public class CMService {
    public static final String M_DATALOAD = TextHelper.buildString(Chat.msgPassive, "Your data has been loaded successfully.", Chat.formatPassive);
    public static final String M_LOGINERROR = TextHelper.buildString("", Chat.RED + "This server is unable to handle your login request currently, please try a different one.", Chat.formatError);
    
    public static final String F_WHOIS_NOEXIST = TextHelper.buildString(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
}
