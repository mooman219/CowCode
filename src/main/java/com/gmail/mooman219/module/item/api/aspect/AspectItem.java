package com.gmail.mooman219.module.item.api.aspect;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.frame.serialize.aspect.AspectKey;
import com.gmail.mooman219.frame.serialize.aspect.KeyRarityAspectType;
import com.gmail.mooman219.frame.text.Chat;

public class AspectItem extends Aspect {
    private KeyRarityAspectType rarityAspectPair = new KeyRarityAspectType(Chat.DARK_GRAY + "* ", " ", Rarity.COMMON, ItemType.UNKNOWN);

    public ItemType getAspectType() {
        return rarityAspectPair.getAspectType();
    }

    public Rarity getRarity() {
        return rarityAspectPair.getRarity();
    }

    public AspectItem setAspectType(ItemType aspectType) {
        this.rarityAspectPair.setAspectType(aspectType);
        return this;
    }

    public AspectItem setRarity(Rarity rarity) {
        this.rarityAspectPair.setRarity(rarity);
        return this;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ArrayList<AspectKey> getKeys() {
        ArrayList<AspectKey> keyList = super.getKeys();
        keyList.add(0, rarityAspectPair);
        return keyList;
    }

    @Override
    public void read(ItemStack item) {
        super.read(item);
        setAspectType(ItemType.fromItem(item));
    }

    @Override
    public ItemStack write(ItemStack item) {
        super.write(item);
        setAspectType(ItemType.fromItem(item));
        return item;
    }

    public static AspectItem get(ItemStack item) {
        AspectItem aspect = new AspectItem();
        aspect.read(item);
        return aspect;
    }
}
