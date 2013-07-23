package com.gmail.mooman219.frame;

public enum Particle {
    /**
     * The biggest explosion particle effect
     */
    HUGE_EXPLOSION("hugeexplosion"),
    /**
     * A larger version of the explode particle
     */
    LARGE_EXPLODE("largeexplode"),
    /**
     * The spark that comes off a fireworks
     */
    FIREWORKS_SPARK("fireworksSpark"),
    /**
     * Currently shows nothing
     */
    BUBBLE("bubble"),
    /**
     * Currently shows nothing
     */
    SUSPENDED("suspended"),
    /**
     * Small gray particles
     */
    DEPTH_SUSPEND("depthsuspend"),
    /**
     * Small gray particles
     */
    TOWNAURA("townaura"),
    /**
     * Critical hit particles
     */
    CRIT("crit"),
    /**
     * Blue critical hit particles
     */
    MAGIC_CRIT("magicCrit"),
    /**
     * Smoke particles
     */
    SMOKE("smoke"),
    /**
     * Multicolored potion effect particles
     */
    MOB_SPELL("mobSpell"),
    /**
     * Multicolored potion effect particles that are slightly transparent
     */
    MOB_SPELL_AMBIENT("mobSpellAmbient"),
    /**
     * A puff of white particles
     */
    SPELL("spell"),
    /**
     * A puff of white starts
     */
    INSTANT_SPELL("instantSpell"),
    /**
     * A puff of purple particles
     */
    WITCH_MAGIC("witchMagic"),
    /**
     * The note that appears above note blocks
     */
    NOTE("note"),
    /**
     * The particles shown at nether portals
     */
    PORTAL("portal"),
    /**
     * The symbols that fly towards the enchantment table
     */
    ENCHANTMENT_TABLE("enchantmenttable"),
    /**
     * Explosion particles
     */
    EXPLODE("explode"),
    /**
     * Fire particles
     */
    FLAME("flame"),
    /**
     * The particles that pop out of lava
     */
    LAVA("lava"),
    /**
     * A small gray square
     */
    FOOTSTEP("footstep"),
    /**
     * Water particles
     */
    SPLASH("splash"),
    /**
     * Currently shows nothing
     */
    LARGE_SMOKE("largesmoke"),
    /**
     * A puff of smoke
     */
    CLOUD("cloud"),
    /**
     * Multicolored dust particles
     */
    REDDUST("reddust"),
    /**
     * Snowball breaking
     */
    SNOWBALL_POOF("snowballpoof"),
    /**
     * The water drip particle that appears on blocks under water
     */
    DRIP_WATER("dripWater"),
    /**
     * The lava drip particle that appears on blocks under lava
     */
    DRIP_LAVA("dripLava"),
    /**
     * White particles
     */
    SNOW_SHOVEL("snowshovel"),
    /**
     * The particle shown when a slime jumps
     */
    SLIME("slime"),
    /**
     * The particle that appears when breading animals
     */
    HEART("heart"),
    /**
     * The particle that appears when hitting a villager
     */
    ANGRY_VILLAGER("angryVillager"),
    /**
     * The particle that appears when trading with a villager
     */
    HAPPY_VILLAGER("happyVillager"),
    /**
     * The item's icon breaking. This needs a material
     */
    ICON_CRACK("iconcrack_*", true),
    /**
     * The block breaking particles. This needs a material and a material data value.
     */
    TILE_CRACK("tilecrack_*_*", true, true);

    private final String name;
    private final boolean hasMaterial;
    private final boolean hasMaterialData;

    Particle(String name) {
        this.name = name;
        hasMaterial = false;
        hasMaterialData = false;
    }

    Particle(String name, boolean hasMaterial) {
        this.name = name;
        this.hasMaterial = hasMaterial;
        hasMaterialData = false;
    }

    Particle(String name, boolean hasMaterial, boolean hasMaterialData) {
        this.name = name;
        this.hasMaterial = hasMaterial;
        this.hasMaterialData = hasMaterialData;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns whether the particle needs the extra ID information
     *
     * @return Whether the particle needs extra ID information
     */
    public boolean hasMaterial() {
        return hasMaterial;
    }

    /**
     * Returns whether the particle needs the extra data information
     *
     * @return Whether the particle needs extra data information
     */
    public boolean hasMaterialData() {
        return hasMaterialData;
    }
}