package com.gmail.mooman219.old.ai;

import org.bukkit.util.Vector;

public class EntityGoalMoveTo extends EntityGoal {
    public final EntityController controller;
    public final Vector position;
    public int ticksActive = 0;

    public EntityGoalMoveTo(final EntityController controller, final Vector position) {
        this.controller = controller;
        this.position = position;
    }

    @Override
    public boolean canExecute() {
        return controller.entity.getLocation().toVector().distanceSquared(position) > 2;
    }

    @Override
    public void execute() {
        ticksActive++;
        if(ticksActive == 1) {
            controller.turn(position);
        } else {
            if(ticksActive % 5 == 0) {
                controller.turn(position);
            }
            if(controller.handle.motX + controller.handle.motZ < 0.0001D) {
                controller.jump();
            }
            controller.move();
        }
    }
}