package com.gmail.mooman219.module.stat.store;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDStat extends PlayerData {
    public PDStat(CDPlayer player) {
        super(player, "stat");
    }

    /**
     * Offline
     */

    public int level = 1;
    public int exp = 0;

    public double healthCur = 10;
    public double healthMax = 10;
    public double manaCur = 10;
    public double manaMax = 10;

    // -+ Strength is a measure of muscle, endurance and stamina combined. Strength affects
    // the ability of characters to lift and carry weights, melee attack rolls, damage rolls
    // (for both melee and ranged weapons,) the Jump, Climb, and Swim skills, several combat
    // actions, and general checks involving moving or breaking stubborn objects.
    //
    // Attack Power
    // Block
    // Parry
    // Physical Crit
    public int strength = 3;
    // -+ Dexterity encompasses a number of physical attributes including eye–hand coordination,
    // agility, reflexes, fine motor skills, balance and speed of movement; a high dexterity
    // score indicates superiority in all these attributes. Dexterity affects characters with
    // regard to initiative in combat, ranged attack rolls, Armor Class, Reflex saves, and
    // the Balance, Escape Artist, Hide, Move Silently, Open Lock, Ride, Sleight of Hand,
    // Tumble, and Use Rope skills. It also affects the number of additional attacks of
    // opportunity granted by the Combat Reflexes feat. Dexterity is the ability most
    // influenced by outside influences (such as armor).
    //
    // Physical Crit
    // Dodge
    // Parry
    public int dexterity = 3;
    // -+ Constitution is a term which encompasses the character's physique, toughness, health
    // and resistance to disease and poison. The higher a character's Constitution, the more
    // hit points that character will have. Constitution also is important for Fortitude saves,
    // the Concentration skill, and fatigue-based general checks. Constitution also determines
    // the duration of a barbarian's rage. Unlike the other ability scores, which render the
    // character unconscious or immobile when they hit 0, having 0 Constitution is fatal.
    //
    // Max Health
    public int constitution = 3;
    // Intelligence is similar to IQ, but also includes mnemonic ability, reasoning and learning
    // ability outside those measured by the written word. Intelligence dictates the number of
    // languages a character can learn, and it influences the number of spells a preparation-based
    // arcane spellcaster (like a Wizard) may cast per day, and the effectiveness of said spells.
    // It also affects how many skill points a character gains per level, the Appraise, Craft,
    // Decipher Script, Disable Device, Forgery, Knowledge, Search, and Spellcraft skills, and
    // bardic knowledge checks.
    //
    // Spell Power
    // Spell Crit
    // Max Mana
    public int intelligence = 3;
    // -+ Wisdom is a composite term for the character's enlightenment, judgment, wile, willpower
    // and intuitiveness. Wisdom influences the number of spells a divine spellcaster (like clerics,
    // druids, paladins, and rangers) can cast per day, and the effectiveness of said spells. It
    // also affects Will saving throws, the Heal, Listen, Profession, Sense Motive, Spot, and Survival
    // skills, the effectiveness of the Stunning Fist feat, and a monk's quivering palm attack.
    //
    // Spell Power
    // Mana Regen
    public int wisdom = 3;
    // -+ Charisma is the measure of the character's combined physical attractiveness, persuasiveness,
    // and personal magnetism. A generally non-beautiful character can have a very high charisma due
    // to strong measures of the other two aspects of charisma. Charisma influences how many spells
    // spontaneous arcane spellcasters (like sorcerers and bards) can cast per day, and the effectiveness
    // of said spells. It also affects Bluff, Diplomacy, Disguise, Gather Information, Handle Animal,
    // Intimidate, Perform, and Use Magic Device checks, how often and how effectively clerics and
    // paladins can turn undead, the wild empathy of druids and rangers, and a paladin's lay on hands ability.
    public int charisma = 3;

    public int unspentPoints = 3;

    @Override
    public void sync(DownloadReason reason, DBObject stat) {
        switch(reason) {
        case LOGIN:
        case QUERY:
        default:
            this.level = MongoHelper.getValue(stat, "level", level);
            this.exp = MongoHelper.getValue(stat, "exp", exp);

            this.healthCur = MongoHelper.getValue(stat, "healthcurrent", healthCur);
            this.healthMax = MongoHelper.getValue(stat, "healthmax", healthMax);
            this.manaCur = MongoHelper.getValue(stat, "manacurrent", manaCur);
            this.manaMax = MongoHelper.getValue(stat, "manamax", manaMax);

            this.strength = MongoHelper.getValue(stat, "strength", strength);
            this.dexterity = MongoHelper.getValue(stat, "dexterity", dexterity);
            this.constitution = MongoHelper.getValue(stat, "constitution", constitution);
            this.intelligence = MongoHelper.getValue(stat, "intelligence", intelligence);
            this.wisdom = MongoHelper.getValue(stat, "wisdom", wisdom);
            this.charisma = MongoHelper.getValue(stat, "charisma", charisma);

            this.unspentPoints = MongoHelper.getValue(stat, "unspentpoints", unspentPoints);
            break;
        }
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".level", level)
            .append(getTag() + ".exp", exp)

            .append(getTag() + ".healthcurrent", healthCur)
            .append(getTag() + ".healthmax", healthMax)
            .append(getTag() + ".manacurrent", manaCur)
            .append(getTag() + ".manamax", manaMax)

            .append(getTag() + ".strength", strength)
            .append(getTag() + ".dexterity", dexterity)
            .append(getTag() + ".constitution", constitution)
            .append(getTag() + ".intelligence", intelligence)
            .append(getTag() + ".wisdom", wisdom)
            .append(getTag() + ".charisma", charisma)

            .append(getTag() + ".unspentpoints", unspentPoints);
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private long lastDamaged = 0l;

    public void setLastDamaged(long time) {
        lastDamaged = time;
    }

    public long getLastDamaged() {
        return lastDamaged;
    }

    @Override
    public void create() {
        lastDamaged = 0l;
    }

    @Override
    public void destroy() {
        lastDamaged = 0l;
    }
}
