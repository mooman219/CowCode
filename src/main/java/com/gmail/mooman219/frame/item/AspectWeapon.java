package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.serialize.aspect.KeyFloat;
import com.gmail.mooman219.frame.serialize.aspect.KeyIntegerRange;

public class AspectWeapon extends Aspect {
    private KeyIntegerRange damage = new KeyIntegerRange("Damage: ", " - ", 1, 1);
    private KeyFloat attackSpeed = new KeyFloat("Attack Speed: ", 1f);
}
