package com.gmail.mooman219.frame.rank;

import com.gmail.mooman219.frame.text.Chat;

public enum Rank {
    /**
     * Using 10's for donor/staff levels just in case
     * more ranks need to be added and you cannot edit
     * the database.
     */
    //
    REGULAR       (0,  0, 0, Chat.GRAY, ""),
    DONOR_BASIC   (1, 10, 0, Chat.GRAY, "" + Chat.GREEN + Chat.BOLD + "S" + Chat.RESET),
    DONOR_ADVANCED(2, 20, 0, Chat.GRAY, "" + Chat.DARK_GREEN + Chat.BOLD + "S+" + Chat.RESET),
    //
    BUILDER       (3, 0, 10, Chat.GRAY, "" + Chat.DARK_AQUA + Chat.BOLD + "BUILDER" + Chat.RESET + " "),
    MODERATOR     (4, 0, 20, Chat.GRAY, "" + Chat.DARK_AQUA + Chat.BOLD + "MOD" + Chat.RESET + " "),
    GAMEMASTER    (5, 0, 30, Chat.GOLD, "" + Chat.AQUA + Chat.BOLD + "GM" + Chat.RESET + " "),
    DEVELOPER     (6, 0, 40, Chat.GOLD, "" + Chat.WHITE + Chat.BOLD + "DEV" + Chat.RESET + " "),
    //
    CONSOLE       (100, 0, 0, "" + Chat.BLUE + Chat.BOLD, "");

    public final String color;
    public final String tag;
    public final int index;
    public final int donorLevel;
    public final int staffLevel;

    Rank(int index, int donorLevel, int staffLevel, Chat color, String tag) {
        this(index, donorLevel, staffLevel, "" + color, tag);
    }

    Rank(int index, int donorLevel, int staffLevel, String color, String tag) {
        this.index = index;
        this.color = color;
        this.donorLevel = donorLevel;
        this.staffLevel = staffLevel;
        this.tag = tag;
    }

    public Rank getRank(int index) {
        if(index >= Rank.values().length){
            return Rank.REGULAR;
        }
        return Rank.values()[index];
    }

    public static Rank getRank(int donorRank, int staffRank){
        if(staffRank != 0) {
            return getRankFromStaff(staffRank);
        }
        return getRankFromDonor(donorRank);
    }

    public static Rank getRankFromDonor(int level) {
        for (Rank r : Rank.values()) {
            if (r.donorLevel == level) {
                return r;
            }
        }
        return Rank.REGULAR;
    }

    public static Rank getRankFromStaff(int level) {
        for (Rank r : Rank.values()) {
            if (r.staffLevel == level) {
                return r;
            }
        }
        return Rank.REGULAR;
    }
}
