package com.gmail.mooman219.module.item.api.aspect;

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

    public AspectWeapon setMinDamage(int min) {
        damage.setMin(min);
        return this;
    }

    public AspectWeapon setMaxDamage(int max) {
        damage.setMax(max);
        return this;
    }

    public AspectWeapon setAttackSpeed(float attackSpeed) {
        this.attackSpeed.setValue(attackSpeed);
        return this;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public ArrayList<AspectKey> getKeys() {
        ArrayList<AspectKey> keyList = super.getKeys();
        keyList.add(1, attackSpeed);
        keyList.add(1, damage);
        return keyList;
    }

    public static AspectWeapon get(ItemStack item) {
        AspectWeapon aspect = new AspectWeapon();
        aspect.read(item);
        return aspect;
    }
}
