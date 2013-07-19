package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.serialize.aspect.KeyAspectType;
import com.gmail.mooman219.frame.serialize.aspect.KeyBoolean;
import com.gmail.mooman219.frame.serialize.aspect.KeyInteger;
import com.gmail.mooman219.frame.text.Chat;

public class Aspect {
    private KeyAspectType aspectType = new KeyAspectType(Chat.GRAY + "Item " + Chat.DARK_GRAY + "-> ", AspectType.SWORD);
    private KeyBoolean soulbound = new KeyBoolean(Chat.GRAY + "Soulbound", false);
    private KeyInteger price = new KeyInteger(Chat.GREEN + "Price" + Chat.DARK_GREEN + ": " + Chat.WHITE, -1);
}
