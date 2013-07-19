package com.gmail.mooman219.frame.item;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.frame.text.Chat;

public enum AspectType {
    UNKNOWN(-1, Chat.GRAY, "Unknown"),
    // Weapon
    SWORD(0, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Sword"),
    SHIELD(1, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Shield"),
    BOW(2, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Bow"),
    STAFF(3, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Staff"),
    DAGGER(4, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Dagger"),
    HAMMER(5, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Hammer"),
    AXE(6, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Axe"),
    SPELLBOOK(7, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Spellbook"),
    // Armor
    CHESTPLATE(8, Chat.GRAY + "Armor " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Chestplate"),
    HELMET(9, Chat.GRAY + "Armor " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Helmet"),
    LEGGINGS(10, Chat.GRAY + "Armor" + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Leggings"),
    FOOTWEAR(11, Chat.GRAY + "Armor " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Foorwear"),
    RING(12, Chat.GRAY + "Armor " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Ring"),
    AMULET(13, Chat.GRAY + "Armor " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Amulet"),
    // Tool
    PICKAXE(14, Chat.GRAY + "Tool " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Pick Axe"),
    FISHING_ROD(15, Chat.GRAY + "Tool " + Chat.DARK_GRAY + "-> " + Chat.GRAY, "Fishing Rod");

    private final int id;
    private final String prefix;
    private final String name;

    AspectType(int id, Chat prefix, String name) {
        this(id, prefix + "", name);
    }

    AspectType(int id, String prefix, String name) {
        this.id = id;
        this.prefix = prefix;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return prefix + name;
    }

    public static AspectType fromString(String string) {
        for(AspectType aspect : AspectType.values()) {
            if(string.toLowerCase().contains(aspect.getName().toLowerCase())) {
                return aspect;
            }
        }
        throw new IllegalArgumentException("Invalid name of " + string);
    }

    public static AspectType fromItem(ItemStack item) {
        switch(item.getType()) {
        // Tool
        case GOLD_PICKAXE:
        case DIAMOND_PICKAXE:
        case IRON_PICKAXE:
        case STONE_PICKAXE:
        case WOOD_PICKAXE:
            return PICKAXE;
        case FISHING_ROD:
            return FISHING_ROD;
        // Armor
        case GOLD_HELMET:
        case DIAMOND_HELMET:
        case IRON_HELMET:
        case CHAINMAIL_HELMET:
        case LEATHER_HELMET:
            return HELMET;
        case GOLD_CHESTPLATE:
        case DIAMOND_CHESTPLATE:
        case IRON_CHESTPLATE:
        case CHAINMAIL_CHESTPLATE:
        case LEATHER_CHESTPLATE:
            return CHESTPLATE;
        case GOLD_LEGGINGS:
        case DIAMOND_LEGGINGS:
        case IRON_LEGGINGS:
        case CHAINMAIL_LEGGINGS:
        case LEATHER_LEGGINGS:
            return LEGGINGS;
        case GOLD_BOOTS:
        case DIAMOND_BOOTS:
        case IRON_BOOTS:
        case CHAINMAIL_BOOTS:
        case LEATHER_BOOTS:
            return FOOTWEAR;
        // Weapon
        case GOLD_SPADE:
        case DIAMOND_SPADE:
        case IRON_SPADE:
        case STONE_SPADE:
        case WOOD_SPADE:
            return HAMMER;
        case GOLD_HOE:
        case DIAMOND_HOE:
        case IRON_HOE:
        case STONE_HOE:
        case WOOD_HOE:
            return STAFF;
        case BOW:
            return BOW;
        case GOLD_AXE:
        case DIAMOND_AXE:
        case IRON_AXE:
        case STONE_AXE:
        case WOOD_AXE:
            return AXE;
        case GOLD_SWORD:
        case DIAMOND_SWORD:
        case IRON_SWORD:
        case STONE_SWORD:
        case WOOD_SWORD:
            return SHIELD;
        // Other
        default:
            return UNKNOWN;
        }
    }
}