package com.gmail.mooman219.frame;

import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityIronGolem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityOcelot;
import net.minecraft.server.EntityPig;
import net.minecraft.server.EntitySheep;
import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.EntitySnowman;
import net.minecraft.server.EntityVillager;
import net.minecraft.server.EntityWitch;
import net.minecraft.server.EntityWither;
import net.minecraft.server.EntityWolf;
import net.minecraft.server.EntityZombie;
import net.minecraft.server.IMonster;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PathfinderGoal;
import net.minecraft.server.PathfinderGoalArrowAttack;
import net.minecraft.server.PathfinderGoalAvoidPlayer;
import net.minecraft.server.PathfinderGoalBeg;
import net.minecraft.server.PathfinderGoalBreakDoor;
import net.minecraft.server.PathfinderGoalBreed;
import net.minecraft.server.PathfinderGoalDefendVillage;
import net.minecraft.server.PathfinderGoalEatTile;
import net.minecraft.server.PathfinderGoalFleeSun;
import net.minecraft.server.PathfinderGoalFloat;
import net.minecraft.server.PathfinderGoalFollowOwner;
import net.minecraft.server.PathfinderGoalFollowParent;
import net.minecraft.server.PathfinderGoalHurtByTarget;
import net.minecraft.server.PathfinderGoalJumpOnBlock;
import net.minecraft.server.PathfinderGoalLeapAtTarget;
import net.minecraft.server.PathfinderGoalLookAtPlayer;
import net.minecraft.server.PathfinderGoalMeleeAttack;
import net.minecraft.server.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.PathfinderGoalOcelotAttack;
import net.minecraft.server.PathfinderGoalOfferFlower;
import net.minecraft.server.PathfinderGoalOwnerHurtByTarget;
import net.minecraft.server.PathfinderGoalOwnerHurtTarget;
import net.minecraft.server.PathfinderGoalPanic;
import net.minecraft.server.PathfinderGoalPassengerCarrotStick;
import net.minecraft.server.PathfinderGoalRandomLookaround;
import net.minecraft.server.PathfinderGoalRandomStroll;
import net.minecraft.server.PathfinderGoalRandomTargetNonTamed;
import net.minecraft.server.PathfinderGoalRestrictSun;
import net.minecraft.server.PathfinderGoalSelector;
import net.minecraft.server.PathfinderGoalSit;
import net.minecraft.server.PathfinderGoalSwell;
import net.minecraft.server.PathfinderGoalTempt;

import org.bukkit.craftbukkit.entity.CraftChicken;
import org.bukkit.craftbukkit.entity.CraftCow;
import org.bukkit.craftbukkit.entity.CraftCreeper;
import org.bukkit.craftbukkit.entity.CraftIronGolem;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftOcelot;
import org.bukkit.craftbukkit.entity.CraftPig;
import org.bukkit.craftbukkit.entity.CraftSheep;
import org.bukkit.craftbukkit.entity.CraftSkeleton;
import org.bukkit.craftbukkit.entity.CraftSnowman;
import org.bukkit.craftbukkit.entity.CraftWitch;
import org.bukkit.craftbukkit.entity.CraftWither;
import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.craftbukkit.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class PathfinderHelper {
    /**
        this.bI = 0.23F; // Speed
        this.getNavigation().b(true); // SetBreakDoors
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalBreakDoor(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, this.bI, false));
        this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, EntityVillager.class, this.bI, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, this.bI));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, this.bI, false));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
     */
    public static void setupPathfinder(Zombie zombie) { // Applies to EntityPigZombie
        EntityZombie entityZombie = ((CraftZombie) zombie).getHandle();

    }

    /**
        private PathfinderGoalArrowAttack d = new PathfinderGoalArrowAttack(this, 0.25F, 20, 60, 15.0F);
        private PathfinderGoalMeleeAttack e = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.31F, false);

        this.bI = 0.25F; // Speed
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
        this.goalSelector.a(3, new PathfinderGoalFleeSun(this, this.bI));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        if (world != null && !world.isStatic) {
            this.goalSelector.a((PathfinderGoal) this.e);
            this.goalSelector.a((PathfinderGoal) this.d);
            ItemStack itemstack = this.bG();

            if (itemstack != null && itemstack.id == Item.BOW.id) {
                this.goalSelector.a(4, this.d);
            } else {
                this.goalSelector.a(4, this.e);
            }
        }
     */
    public static void setupPathfinder(Skeleton skeleton) {
        EntitySkeleton entitySkeleton = ((CraftSkeleton) skeleton).getHandle();
    }

    /**
        this.a(1.4F, 2.9F); // SetSize(Width, Height)
        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 0.25F, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.22F, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.16F, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.16F));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.16F));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false, true, IMonster.a));
     */
    public static void setupPathfinder(IronGolem ironGolem) {
        EntityIronGolem entityIronGolem = ((CraftIronGolem) ironGolem).getHandle();
    }

    /**
        this.a(0.3F, 0.7F); // SetSize(Width, Height)
        this.j = this.random.nextInt(6000) + 6000;
        float f = 0.25F;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.SEEDS.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.28F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
     */
    public static void setupPathfinder(Chicken chicken) {
        EntityChicken entityChicken = ((CraftChicken) chicken).getHandle();
    }

    /*
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 0.25F, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
     */
    public static void setupPathfinder(Creeper creeper) {
        EntityCreeper entityCreeper = ((CraftCreeper) creeper).getHandle();
    }

    /**
        this.a(0.9F, 1.3F); // SetSize(Width, Height)
        //
        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 0.2F));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.WHEAT.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
     */
    public static void setupPathfinder(Cow cow) { // Applies to EntityMushroomCow
        EntityCow entityCow = ((CraftCow) cow).getHandle();
    }

    /*
        private PathfinderGoalEatTile g = new PathfinderGoalEatTile(this);

        this.a(0.9F, 1.3F); // SetSize(Width, Height)
        float f = 0.23F;

        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 0.25F, Item.WHEAT.id, false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 0.25F));
        this.goalSelector.a(5, this.g);
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
     */
    public static void setupPathfinder(Sheep sheep) {
        EntitySheep entitySheep = ((CraftSheep) sheep).getHandle();
    }

    /**
        this.a(0.4F, 1.8F); // SetSize(Width, Height)
        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 0.25F, 20, 10.0F));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.2F));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, true, false, IMonster.a));
     */
    public static void setupPathfinder(Snowman snowman) {
        EntitySnowman entitySnowman = ((CraftSnowman) snowman).getHandle();
    }

    /**
        this.bI = 0.25F; // Speed
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bI, 60, 10.0F));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
     */
    public static void setupPathfinder(Witch witch) {
        EntityWitch entityWitch = ((CraftWitch) witch).getHandle();
    }

    /**
        this.a(0.9F, 4.0F); // SetSize(Width, Height)
        this.fireProof = true;
        this.bI = 0.6F; // Speed
        this.getNavigation().e(true); // SetCanSwim
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, this.bI, 40, 20.0F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 30.0F, 0, false, false, bK));
        this.be = 50; // Experience value
     */
    public static void setupPathfinder(Wither wither) {
        EntityWither entityWither = ((CraftWither) wither).getHandle();
    }

    /**
        protected PathfinderGoalSit d = new PathfinderGoalSit(this);

        this.a(0.6F, 0.8F); // SetSize(Width, Height)
        this.bI = 0.3F; // Speed
        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, this.bI, true));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, this.bI, 10.0F, 2.0F));
        this.goalSelector.a(6, new PathfinderGoalBreed(this, this.bI));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, this.bI));
        this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
        this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
        this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntitySheep.class, 16.0F, 200, false));
     */
    public static void setupPathfinder(Wolf wolf) {
        EntityWolf entityWolf = ((CraftWolf) wolf).getHandle();
    }

    /**
        protected PathfinderGoalSit d = new PathfinderGoalSit(this);

        this.a(0.6F, 0.8F); // SetSize(Width, Height)
        this.getNavigation().a(true); // SetAvoidsWater
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, this.e = new PathfinderGoalTempt(this, 0.18F, Item.RAW_FISH.id, true));
        this.goalSelector.a(4, new PathfinderGoalAvoidPlayer(this, EntityHuman.class, 16.0F, 0.23F, 0.4F));
        this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 0.3F, 10.0F, 5.0F));
        this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 0.4F));
        this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
        this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
        this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.23F));
        this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.23F));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
        this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, 14.0F, 750, false));
     */
    public static void setupPathfinder(Ocelot ocelot) {
        EntityOcelot entityOcelot = ((CraftOcelot) ocelot).getHandle();
    }
    
    /**
        this.a(0.9F, 0.9F); // SetSize(Width, Height)
        this.d = new PathfinderGoalPassengerCarrotStick(this, 0.34F);
        this.getNavigation().a(true); // SetAvoidsWater
        float f = 0.25F;
        
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 0.38F));
        this.goalSelector.a(2, this.d);
        this.goalSelector.a(3, new PathfinderGoalBreed(this, f));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3F, Item.CARROT_STICK.id, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 0.3F, Item.CARROT.id, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 0.28F));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, f));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
     */
    public static void setupPathfinder(Pig pig) {
        EntityPig entitPig = ((CraftPig) pig).getHandle();
    }
}
