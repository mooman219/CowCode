package com.gmail.mooman219.frame.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.mooman219.frame.serialize.aspect.AspectKey;
import com.gmail.mooman219.frame.serialize.aspect.KeyAspectType;
import com.gmail.mooman219.frame.serialize.aspect.KeyBoolean;
import com.gmail.mooman219.frame.serialize.aspect.KeyInteger;
import com.gmail.mooman219.frame.serialize.aspect.KeyRarity;
import com.gmail.mooman219.frame.text.Chat;

public class AspectItem {
    private KeyAspectType aspectType = new KeyAspectType(Chat.GRAY + "", AspectType.UNKNOWN);
    private KeyRarity rarity = new KeyRarity(Chat.DARK_GRAY + "* ", Rarity.COMMON);
    private KeyBoolean soulbound = new KeyBoolean(Chat.GRAY + "Soulbound", false);
    private KeyInteger price = new KeyInteger(Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.WHITE, -1);

    public AspectItem() {
        price.setWriteCheck(new PriceWriteCheck(price));
    }

    public int getPrice() {
        return price.getValue();
    }

    public boolean isSoulbound() {
        return soulbound.getValue();
    }

    public AspectType getAspectType() {
        return aspectType.getValue();
    }

    public Rarity getRarity() {
        return rarity.getValue();
    }

    public void setPrice(int price) {
        this.price.setValue(price);
    }

    public void setSoulbound(boolean soulbound) {
        this.soulbound.setValue(soulbound);
    }

    public void setAspectType(AspectType aspectType) {
        this.aspectType.setValue(aspectType);
    }

    public void setRarity(Rarity rarity) {
        this.rarity.setValue(rarity);
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<AspectKey> getKeys() {
        ArrayList<AspectKey> keyList = new ArrayList<AspectKey>();
        keyList.add(aspectType);
        keyList.add(rarity);
        keyList.add(soulbound);
        keyList.add(price);
        return keyList;
    }

    @SuppressWarnings("rawtypes")
    public void write(ItemStack item) {
        ItemMeta meta = ItemHelper.getItemMeta(item);
        ArrayList<String> lore = new ArrayList<String>();
        aspectType.setValue(AspectType.fromItem(item));

        for(AspectKey aspect : getKeys()) {
            if(aspect.canWrite()) {
                lore.add(aspect.write());
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @SuppressWarnings("rawtypes")
    public void read(ItemStack item) {
        ItemMeta meta = ItemHelper.getItemMeta(item);
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<String>();
        ArrayList<AspectKey> keyList = getKeys();
        keyList.remove(aspectType); // ASPECT TYPE IS SHPECIAL :o)
        aspectType.setValue(AspectType.fromItem(item));
        for(String line : lore) {
            Iterator<AspectKey> iterator = keyList.iterator();
            while(iterator.hasNext()) {
                if(iterator.next().read(line)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public static AspectItem getAspectItem(ItemStack item) {
        AspectItem aspect = new AspectItem();
        aspect.read(item);
        return aspect;
    }

    private class PriceWriteCheck implements Callable<Boolean> {
        private final KeyInteger price;

        public PriceWriteCheck(KeyInteger price) {
            this.price = price;
        }

        @Override
        public Boolean call() throws Exception {
            return price.getValue() > -1;
        }
    }
}
