package com.gmail.mooman219.frame.text;

import java.text.MessageFormat;

import org.bukkit.command.CommandSender;

public class Bulletin {
    public final String message;
    
    public Bulletin(String message, String format) {
        this("", message, format);
    }
    
    public Bulletin(Chat prefix, String message, String format) {
        this(prefix.toString(), message, format);
    }
    
    public Bulletin(String prefix, String message, String format) {
        this(prefix + TextHelper.punctuationPattern.matcher(message).replaceAll(format));
    }
    
    public Bulletin(String message) {
        this.message = message;
    }
    
    public String parse(Object... args) {
        return MessageFormat.format(message, args);
    }
    
    public void send(CommandSender target) {
        target.sendMessage(message);
    }
    
    public void send(CommandSender target, Object... args) {
        target.sendMessage(parse(args));
    }
    
    public String toString() {
        return message;
    }
}
