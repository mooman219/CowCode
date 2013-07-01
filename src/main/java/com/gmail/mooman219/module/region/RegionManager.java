package com.gmail.mooman219.module.region;

import com.gmail.mooman219.module.region.store.BasicRegion;
import com.gmail.mooman219.module.region.store.StoreRegion;

public class RegionManager {
    public static BasicRegion addInfo(String id, String name) {
        BasicRegion information = getInfoByID(id);
        if(getInfoByID(id) == null) {
            information = new BasicRegion(id, name);
            StoreRegion.getRegions().put(information.getUUID(), information);
        }
        return information;
    }

    public static BasicRegion getInfo(String uuid) {
        BasicRegion info = StoreRegion.getRegions().get(uuid);
        return info != null ? info : StoreRegion.getGlobalInfo();
    }

    public static BasicRegion getInfoByID(String id) {
        if(id.equals("global")) {
            return StoreRegion.getGlobalInfo();
        }
        for(BasicRegion info : StoreRegion.getRegions().values()) {
            if(info.getID().equals(id)) {
                return info;
            }
        }
        return null;
    }
}
