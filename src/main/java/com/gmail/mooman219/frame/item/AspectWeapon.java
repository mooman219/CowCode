package com.gmail.mooman219.frame.item;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.frame.serialize.aspect.AspectKey;
import com.gmail.mooman219.frame.serialize.aspect.KeyFloat;
import com.gmail.mooman219.frame.serialize.aspect.KeyIntegerRange;
import com.gmail.mooman219.frame.text.Chat;

public class AspectWeapon extends AspectItem {
    private KeyIntegerRange damage = new KeyIntegerRange(Chat.RED + "Damage" + Chat.DARK_RED + ": " + Chat.RED, Chat.DARK_RED + " - " + Chat.RED, 1, 1);
    private KeyFloat attackSpeed = new KeyFloat(Chat.RED + "Attack Speed" + Chat.DARK_RED + ": " + Chat.RED, 1f);

    public int getMinDamage() {
        return damage.getMin();
    }

    public int getMaxDamage() {
        return damage.getMax();
    }

    public float getAttackSpeed() {
        return attackSpeed.getValue();
    }

    public void setMinDamage(int min) {
        damage.setMin(min);
    }

    public void setMaxDamage(int max) {
        damage.setMax(max);
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed.setValue(attackSpeed);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ArrayList<AspectKey> getKeys() {
        ArrayList<AspectKey> keyList = super.getKeys();
        keyList.add(2, attackSpeed);
        keyList.add(2, damage);
        return keyList;
    }

    public static AspectWeapon getAspectWeapon(ItemStack item) {
        AspectWeapon aspect = new AspectWeapon();
        aspect.read(item);
        return aspect;
    }
}
