package com.gmail.mooman219.module.item.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.mooman219.frame.serialize.aspect.AspectKey;
import com.gmail.mooman219.frame.serialize.aspect.KeyBoolean;
import com.gmail.mooman219.frame.serialize.aspect.KeyInteger;
import com.gmail.mooman219.frame.text.Chat;

public class Aspect {
    private KeyBoolean soulbound = new KeyBoolean(Chat.GRAY + "Soulbound", false);
    private KeyBoolean unmoveable = new KeyBoolean(Chat.GRAY + "Unmoveable", false);
    private KeyInteger price = new KeyInteger(Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.WHITE, -1);

    public Aspect() {
        price.setWriteCheck(new PriceWriteCheck(price));
    }

    public int getPrice() {
        return price.getValue();
    }

    public boolean isSoulbound() {
        return soulbound.getValue();
    }

    public boolean isUnmoveable() {
        return unmoveable.getValue();
    }

    public Aspect setPrice(int price) {
        this.price.setValue(price);
        return this;
    }

    public Aspect setSoulbound(boolean soulbound) {
        this.soulbound.setValue(soulbound);
        return this;
    }

    public Aspect setUnmoveable(boolean unmoveable) {
        this.unmoveable.setValue(unmoveable);
        return this;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<AspectKey> getKeys() {
        ArrayList<AspectKey> keyList = new ArrayList<AspectKey>();
        keyList.add(soulbound);
        keyList.add(unmoveable);
        keyList.add(price);
        return keyList;
    }

    @SuppressWarnings("rawtypes")
    public final ItemStack write(ItemStack item) {
        ItemMeta meta = ItemHelper.getItemMeta(item);
        ArrayList<String> lore = new ArrayList<String>();
        onWrite(item);
        for(AspectKey key : getKeys()) {
            if(key.canWrite()) {
                lore.add(key.write());
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @SuppressWarnings("rawtypes")
    public final void read(ItemStack item) {
        ItemMeta meta = ItemHelper.getItemMeta(item);
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<String>();
        ArrayList<AspectKey> keyList = getKeys();
        for(String line : lore) {
            Iterator<AspectKey> keyIterator = keyList.iterator();
            while(keyIterator.hasNext()) {
                if(keyIterator.next().read(line)) {
                    keyIterator.remove();
                    break;
                }
            }
        }
        onRead(item);
    }

    /**
     * Called after the Aspect reads the lore.
     */
    public void onRead(ItemStack item) {}

    /**
     * Called before the aspect writes the lore.
     */
    public void onWrite(ItemStack item) {}

    public static Aspect get(ItemStack item) {
        Aspect aspect = new Aspect();
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
