package com.gmail.mooman219.old.ai;

public class EntityAI {
    public EntityGoal goal;

    public void tick() {
        if(goal != null) {
            if(goal.canExecute()) {
                goal.execute();
            } else {
                goal = null;
            }
        }
    }
}
