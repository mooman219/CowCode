package com.gmail.mooman219.frame.tab;

import org.bukkit.entity.Player;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.handler.packet.CHPacket;
import com.gmail.mooman219.module.CDPlayer;

public class Tab {
    public final static int maxTabWidth = 5;
    public final static int maxTabHeight = 20;
    /** 160 **/public final static String[] bigList = {"§ΰ","§α","§β","§γ","§δ","§ε","§ζ","§η","§θ","§ι","§κ","§λ","§μ","§ν","§ξ","§ο","§π","§ρ","§ς","§σ","§τ","§υ","§φ","§χ","§ψ","§ω","§ϊ","§ϋ","§ό","§ύ","§ώ","§Ϗ","§ϐ","§ϑ","§ϒ","§ϓ","§ϔ","§ϕ","§ϖ","§ϗ","§Ϙ","§ϙ","§Ϛ","§ϛ","§Ϝ","§ϝ","§Ϟ","§ϟ","§Ϡ","§ϡ","§Ϣ","§ϣ","§Ϥ","§ϥ","§Ϧ","§ϧ","§Ϩ","§ϩ","§Ϫ","§ϫ","§Ϭ","§ϭ","§Ϯ","§ϯ","§ϰ","§ϱ","§ϲ","§ϳ","§ϴ","§ϵ","§϶","§Ϸ","§ϸ","§Ϲ","§Ϻ","§ϻ","§ϼ","§Ͻ","§Ͼ","§Ͽ","§Ѐ","§Ё","§Ђ","§Ѓ","§Є","§Ѕ","§І","§Ї","§Ј","§Љ","§Њ","§Ћ","§Ќ","§Ѝ","§Ў","§Џ","§А","§Б","§В","§Г","§Д","§Е","§Ж","§З","§И","§Й","§К","§Л","§М","§Н","§О","§П","§Р","§С","§Т","§У","§Ф","§Х","§Ц","§Ч","§Ш","§Щ","§Ъ","§Ы","§Ь","§Э","§Ю","§Я","§а","§б","§в","§г","§д","§е","§ж","§з","§и","§й","§к","§л","§м","§н","§о","§п","§р","§с","§т","§у","§ф","§х","§ц","§ч","§ш","§щ","§ъ","§ы","§ь","§э","§ю","§я"};
    private final Player player;
    private final TabValue[][] tab;
    private int index = 0;

    public Tab(Player player) {
        this.player = player;
        this.tab = new TabValue[maxTabWidth][maxTabHeight];

        for(int y = 0; y < maxTabHeight; y++) {
            for(int x = 0; x < maxTabWidth; x++) {
                tab[x][y] = new TabValue(nextUnique());
            }
        }
    }

    public String nextUnique() {
        index = index > 25600 ? 0 : index + 1;
        return bigList[MathHelper.floor((index / 160D) % bigList.length)] + bigList[index % bigList.length];
    }

    public void setTab(int x, int y, String name) {
        if(name.length() > 16) {
            name = name.substring(0, 16);
        } else if(name.length() >= 13) {
            name += nextUnique().substring(2, 4);
        } else if(name.length() <= 12) {
            name += nextUnique();
        }
        this.tab[x][y].setName(name);
    }

    public void update() {
        CDPlayer.get(player).runTask(new Runnable() {
            @Override
            public void run() {
                for(int y = 0; y < maxTabHeight; y++) {
                    for(int x = 0; x < maxTabWidth; x++) {
                        CHPacket.manager.sendPlayerInfo(player, tab[x][y].getClientName(), false, true);
                    }
                }
                for(int y = 0; y < maxTabHeight; y++) {
                    for(int x = 0; x < maxTabWidth; x++) {
                        CHPacket.manager.sendPlayerInfo(player, tab[x][y].getName(), true, false);
                        tab[x][y].setClientName(tab[x][y].getName());
                    }
                }
            }
        });
    }
}
