package com.gmail.mooman219.frame.text;

import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

public class TextHelper {
    public static final Pattern chatPattern = Pattern.compile("&([A-FK-OR0-9])", Pattern.CASE_INSENSITIVE);
    public static final Pattern chatPlanetMinecraft = Pattern.compile("pl.n.t.?m.n.cr.ft", Pattern.CASE_INSENSITIVE);
    public static final Pattern punctuationPattern = Pattern.compile("[\\])\"'(\\:\\.\\[|,><]");
    public static final Pattern splitPattern = Pattern.compile("\\:");
    public static final Pattern spacePattern = Pattern.compile(" ");

    private static final Chat[] rainbowOrder = {Chat.DARK_RED, Chat.RED, Chat.GOLD,
        Chat.YELLOW, Chat.GREEN, Chat.DARK_GREEN, Chat.AQUA, Chat.DARK_AQUA
        , Chat.BLUE, Chat.DARK_BLUE, Chat.PURPLE, Chat.PINK};
    private static final String[] planetMinecraftBank = {"Serbia", "downtown Brooklyn", "the Sims", "Jupiter",
        "Neptune", "Cornwall", "Arkansas", "St. Helen's", "the Jersey Shore", "the past", "Black Rock Shooter",
        "Japan, the land of kawaii", "the Horde", "the Alliance", "the internet", "the Island of Misfit Toys",
        "this server", "China", "the Astral Plane", "Narnia", "the Planet of the Apes", "Fooly Cooly",
        "the movie Inception", "the Twilight series", "the Church of Scientology",
        "Texas", "Lucky Star", "my mother's basement", "the land of \"/rules? what /rules?\"",
        "Croydon", "the Consortium of Allied Griefers", "the Desert Well Building Committee",
        "the Village Green Preservation Society", "a land down under", "Narnia",
        "my dirt house near spawn", "your bathroom", "that special place in your heart",
        "a shoebox by the side of the road", "the only gay bar in the whole town",
        "the discarded carcass of a narwhal", "the mental hospital", "Nigeria",
        "the IRS", "the Starship Enterprise", "the Malaysian Embassy", "France",
        "the dark side of the moon", "the dump", "the middle of a riddle", "marklar",
        "Battersea Dog's Home, please adopt me :(", "Tipperary", "next door",
        "Canada", "the local soup kitchen", "just around the block", "the local homeless shelter",
        "the future", "the Vatican", "your worst nightmares", "a galaxy far, far away",
        "a rolled-up carpet in a skip", "Hogwarts", "an obscure 80's band", "Ikea",
        "Gandalf's army", "Dumbledore's Army", "the FBI", "the sewers", "CubedHost",
        "the Skylands world", "a Happy Meal box in the middle of the motorway", "a farm",
        "Mexico", "Taco Bell", "the local Chess club", "Sweden", "the Land of Cows",
        "the restaurant at the end of the universe", "the hack forums"};

    public static String editPlanetMinecraft(String string) {
        return chatPlanetMinecraft.matcher(string).replaceAll(planetMinecraftBank[(int)(Math.random()*planetMinecraftBank.length)]);
    }

    public static String editColor(String string) {
        return chatPattern.matcher(string).replaceAll(Chat.COLOR_CHAR + "$1");
    }

    public static String editRainbow(String string) {
        String newstring = "";
        for(int i = 0; i < string.length(); i++) {
            newstring += rainbowOrder[i % rainbowOrder.length] + "" + Chat.BOLD + string.charAt(i);
        }
        return newstring;
    }

    public static String buildString(String prefix, String string, String format) {
        return prefix + punctuationPattern.matcher(string).replaceAll(format);
    }

    public static <T> String buildQuery(String tag, String name, T value) {
        String ret = '"' + tag + "." + name + "\":";
        if(value instanceof String) {
            ret += '"' + (String)value + '"';
        } else {
            ret += value;
        }
        return ret;
    }

    public static String shrink(String string) {
        if(string.length() > 16) {
            string = string.substring(0, 16);
        } else {
            if(string.length() < 15){
                string = string + Chat.RESET;
            }
        }
        return string;
    }

    public static void message(CommandSender target, String pattern, Object... arguments) {
        target.sendMessage(parse(pattern, arguments));
    }

    public static void message(CommandSender target, String message) {
        target.sendMessage(message);
    }

    public static String parse(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public static String merge(String[] arguments) {
        String ret = "";
        for(String arg : arguments) {
            ret += arg + " ";
        }
        return ret.trim();
    }

    public static String merge(String[] arguments, int startIndex) {
        String ret = "";
        for (int i = startIndex; i < arguments.length; i++) {
            ret += arguments[i] + " ";
        }
        return ret.trim();
    }
}
