package com.gmail.mooman219.frame.item;

import java.util.ArrayList;

import com.gmail.mooman219.frame.serialize.aspect.AspectKey;
import com.gmail.mooman219.frame.serialize.aspect.KeyFloat;
import com.gmail.mooman219.frame.serialize.aspect.KeyIntegerRange;

public class AspectWeapon extends AspectItem {
    private KeyIntegerRange damage = new KeyIntegerRange("Damage: ", " - ", 1, 1);
    private KeyFloat attackSpeed = new KeyFloat("Attack Speed: ", 1f);

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
        keyList.add(1, attackSpeed);
        keyList.add(1, damage);
        return keyList;
    }
}
