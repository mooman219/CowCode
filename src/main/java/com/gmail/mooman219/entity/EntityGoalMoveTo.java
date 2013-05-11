package com.gmail.mooman219.entity;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityGoalMoveTo {
    public final Entity entity;
    public final Vector position; 
    
    public EntityGoalMoveTo(Entity entity, Vector position) {
        this.entity = entity;
        this.position = position;
    }
    
    public boolean canExecute() {
        return entity.getLocation().toVector().distanceSquared(position) < 1;
    }
    public void execute() {
        
    }
}
