package com.sufurujhin.rpgdungeon.mobs;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.AttributeMobs;

import net.minecraft.server.v1_16_R3.World;

public class Magma_Cubes extends AttributeMobs{
	private ItemStack[] items = new ItemStack[5];
	private int[] slots = new int[5];
	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean baby = false;
	private boolean villager =false;
	private boolean twoHand = false;
	private int nv;
	private int tam;
	private Location loc;

	public Magma_Cubes(int tam, RPGDungeon rpg,World world,Location loc, ItemStack[] items, double attack, double speed, float health, String displayName, boolean baby, boolean villager, int nv, int[] slots, boolean twoHand) {
		super(rpg);
		this.slots = slots;
		this.nv = nv;
		this.tam = tam;
		this.items = items;
		this.speed = speed;
		this.health = health;
		this.displayName = displayName;
		this.villager = villager;
		this.baby = baby;
		this.loc = loc;
		this.twoHand = twoHand;
	}

	public LivingEntity spawnMagmaCube() {

		MagmaCube magma = (MagmaCube) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
		setNivel(nv);
		setEntity(magma);
		if(tam > 0){
			magma.setSize(tam);
		}
		if(slots.length > 0){
			setSlots(slots);
			setItems(items);
		}
		if(baby){
			setBaby(baby);
		}
		if(twoHand){
			setTwoHand(twoHand);
		}
		setSpeed(speed);
		setGlowing(villager);
		setDisplayName(displayName);
		
		setHealth(health);
		
		return getEntity();
	}
}