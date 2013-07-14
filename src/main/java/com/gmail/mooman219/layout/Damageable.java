package com.gmail.mooman219.layout;

public interface Damageable {
    public void damage(double amount);

    public double getHealth();

    public double getMaxHealth();

    public void heal(double amount);

    public boolean isDead();

    public void kill();

    public void resetHealth();

    public void setHealth(double amount);

    public void setMaxHealth(double amount);
}
