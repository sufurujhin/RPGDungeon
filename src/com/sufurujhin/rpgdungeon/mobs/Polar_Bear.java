package com.sufurujhin.rpgdungeon.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PolarBear;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.AttributeMobs;

import net.minecraft.server.v1_16_R3.World;

public class Polar_Bear extends AttributeMobs {

	private double attack = 0.0;
	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean villager =false;
	private int nv;
	private Location loc;

	public Polar_Bear(RPGDungeon rpg,World world,Location loc, ItemStack[] items, double attack, double speed, float health, String displayName, boolean baby, boolean villager, int nv, int[] slots, boolean twoHand) {
		super(rpg);
		this.nv = nv;
		this.attack = attack;
		this.speed = speed;
		this.health = health;
		this.displayName = displayName;
		this.villager = villager;
		this.loc = loc;
	}

	public LivingEntity spawnPolar_Bear(){
		
		PolarBear bear = (PolarBear) loc.getWorld().spawnEntity(loc, EntityType.POLAR_BEAR);
		setNivel(nv);
		setEntity(bear);
		setSpeed(speed);
		setGlowing(villager);
		setDisplayName(displayName);
		setAttack(attack);
		setHealth(health);
		
		
		return getEntity();
	}

}

