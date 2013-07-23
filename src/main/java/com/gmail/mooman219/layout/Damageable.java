package com.gmail.mooman219.layout;

public interface Damageable {
    public void damage(double amount);

    public double getHealth();

    public long getLastDamaged();

    public double getMaxHealth();

    public void heal(double amount);

    public boolean isDead();

    public boolean isOverflowing();

    public void kill();

    public void resetHealth();

    public void setHealth(double amount);

    public void setLastDamaged(long time);

    public void setMaxHealth(double amount);
}
