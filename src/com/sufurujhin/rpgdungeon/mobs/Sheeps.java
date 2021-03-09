package com.sufurujhin.rpgdungeon.mobs;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.AttributeMobs;

import net.minecraft.server.v1_16_R3.World;

public class Sheeps extends AttributeMobs{

	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean villager =false;
	private int nv;
	private Location loc;
	private String color;
	
	public Sheeps(String color, RPGDungeon rpg,World world, Location loc, ItemStack[] items, double attack, double speed, float health,
			String displayName, boolean baby, boolean villager, int nv, int[] slots, boolean twoHand) {
		super(rpg);
		this.nv = nv;
		this.color = color;
		this.speed = speed;
		this.health = health;
		this.displayName = displayName;
		this.villager = villager;
		this.loc = loc;
	
	}

	public LivingEntity spawnSheep() {
		Sheep sheep = (Sheep) loc.getWorld().spawnEntity(loc, EntityType.SHEEP);
		
		setNivel(nv);
		setEntity(sheep);
		setSpeed(speed);
		setGlowing(villager);
		setDisplayName(displayName);
		setHealth(health);

		if(color !=null && color !=""){
		
			sheep.setColor(DyeColor.valueOf(color.toUpperCase()));
		}
		
		return getEntity();
	}

}