package com.gmail.mooman219.module.region.store;
import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.module.region.store.RegionCombatType;

public class BasicRegionInfo {
    // This identifies the region, don't fuck with it
    private final String uuid;

    private String id;
    private String name = "DefaultName";
    private String description = "Default description.";
    private RegionCombatType combatType = RegionCombatType.SAFE;

    public BasicRegionInfo(String id, String name) {
        this(MathHelper.nextUUID().toString(), id, name);
    }

    public BasicRegionInfo(String uuid, String id, String name) {
        this.uuid = uuid;

        this.id = id.toLowerCase();
        this.name = name;
    }

    public RegionCombatType getCombatType() {
        return combatType;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public BasicRegionInfo setCombatType(RegionCombatType combatType) {
        this.combatType = combatType;
        return this;
    }

    public BasicRegionInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public BasicRegionInfo setID(String id) {
        this.id = id;
        return this;
    }

    public BasicRegionInfo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicRegionInfo fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicRegionInfo.class);
    }
}