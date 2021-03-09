package com.sufurujhin.rpgdungeon.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.AttributeMobs;

import net.minecraft.server.v1_16_R3.World;

public class PigMob extends AttributeMobs{

	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean villager =false;
	private int nv;
	private Location loc;
	
	public PigMob(RPGDungeon rpg,World world, Location loc, ItemStack[] items, double attack, double speed, float health,
			String displayName, boolean baby, boolean villager, int nv, int[] slots, boolean twoHand) {
		super(rpg);
		this.nv = nv;
		this.speed = speed;
		this.health = health;
		this.displayName = displayName;
		this.villager = villager;
		this.loc = loc;
	
	}

	public LivingEntity spawnPig() {
		Pig pig = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
		
		setNivel(nv);
		setEntity(pig);
		setGlowing(villager);
		setSpeed(speed);
		setDisplayName(displayName);
		setHealth(health);
		return getEntity();
	}

}