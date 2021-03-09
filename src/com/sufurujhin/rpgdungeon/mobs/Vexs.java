package com.sufurujhin.rpgdungeon.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vex;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.AttributeMobs;

import net.minecraft.server.v1_16_R3.World;

public class Vexs extends AttributeMobs {
	
	private ItemStack[] items = new ItemStack[5];
	private int[] slots = new int[5];
	private double attack = 0.0;
	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean villager =false;
	private boolean twoHand = false;
	private int nv;
	private Location loc;

	public Vexs(RPGDungeon rpg,World world,Location loc, ItemStack[] items, double attack, double speed, float health, String displayName, boolean baby, boolean villager, int nv, int[] slots, boolean twoHand) {
		super(rpg);
		this.slots = slots;
		this.nv = nv;
		this.items = items;
		this.attack = attack;
		this.speed = speed;
		this.health = health;
		this.displayName = displayName;
		this.villager = villager;
		this.loc = loc;
		this.twoHand = twoHand;
	}

	public LivingEntity spawnVexs() {

		Vex vex = (Vex) loc.getWorld().spawnEntity(loc, EntityType.VEX);
		vex.setGravity(true);
		setNivel(nv);
		setEntity(vex);
		setSlots(slots);
		setSpeed(speed);
		setTwoHand(twoHand);
		setGlowing(villager);
		setItems(items);
		setDisplayName(displayName);
		setAttack(attack);
		setHealth(health);
		
		return getEntity();
	}

}
