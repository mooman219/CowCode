package com.gmail.mooman219.frame.command;

public class CommandUsage {
    public final String[] usage;
    public final String mUsage;

    // Tags: string number message optional
    public CommandUsage(String usage) {
        this.mUsage = usage.toLowerCase();
        this.usage = mUsage.length() == 0 ? new String[0] : mUsage.split(" ");
    }

    public boolean validate(String[] parameters) {
        if(usage.length == 0) {
            return true;
        } else {
            for(int i = 0; i < usage.length; i++) {
                if(usage.length - 1 == i && usage[i].equalsIgnoreCase("optional")) {
                    return true;
                } else if(i >= parameters.length) {
                    return false;
                } else if(usage[i].equalsIgnoreCase("string")) {
                    if(isEmpty(parameters[i])) {
                        return false;
                    }
                } else if(usage[i].equalsIgnoreCase("number")) {
                    if(!isNumber(parameters[i])) {
                        return false;
                    }
                } else if(usage[i].equalsIgnoreCase("message")) {
                    return true;
                }
            }
        }
        return usage.length == parameters.length;
    }

    public boolean isNumber(String parameter) {
        try {
            Integer.valueOf(parameter);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isEmpty(String parameter) {
        return parameter.length() <= 0;
    }
}
