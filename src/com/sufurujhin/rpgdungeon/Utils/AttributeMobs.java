package com.sufurujhin.rpgdungeon.Utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;

public class AttributeMobs {

	private ItemStack[] items = new ItemStack[5];
	private int[] slots = new int[5];
	private double attack = 0.0;
	private double speed = 0.0;
	private float health = 0.0F;
	private String displayName = "";
	private boolean baby = false;
	
	public boolean isBaby() {
		return baby;
	}
	public void setBaby(boolean baby) {
		this.baby = baby;
	}
	public boolean isGlowing() {
		return glowing;
	}
	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
		getEntity().setGlowing(this.glowing);
	}
   public void setJoker(Entity joker){
	   getEntity().addPassenger(joker); 
   }

	private boolean glowing =false;
	private int nv;
	private boolean twoHand = false;
	private LivingEntity entity = null;
	private double taxaAttack = 0;
	private double taxaHealth = 0;
	private double taxaSpeed = 0;
	@SuppressWarnings("unused")
	private RPGDungeon rpg;
	public AttributeMobs(RPGDungeon rpg) {
		this.rpg = rpg;
		this.taxaAttack = rpg.getConfig().getDouble("RateIncreaseLevel.attack");
		this.taxaHealth = rpg.getConfig().getDouble("RateIncreaseLevel.health");
		this.taxaSpeed = rpg.getConfig().getDouble("RateIncreaseLevel.speed");
	}
	public LivingEntity getEntity() {
		
		return entity;
	}
	public void setPlugin(RPGDungeon rpg){
		this.rpg = rpg;
	}
	public ItemStack[] getItems() {
		return items;
	}

	public int[] getSlots() {
		return slots;
	}

	public double getAttack() {
		return attack;
	}

	public double getSpeed() {
		return speed;
	}

	public float getHealth() {
		return health;
	}

	public String getDisplayName() {
		return displayName;
	}


	public int getNv() {
		return nv;
	}

	public void setItems(ItemStack[] items) {
		
		this.items = items;
		if(this.items !=null){
			int x = 0;
			getEntity().setCanPickupItems(false);
			getEntity().getEquipment().setBootsDropChance(0.0f);
			getEntity().getEquipment().setHelmetDropChance(0.0f);
			getEntity().getEquipment().setChestplateDropChance(0.0f);
			getEntity().getEquipment().setItemInMainHandDropChance(0.0f);
			getEntity().getEquipment().setItemInOffHandDropChance(0.0f);
			for(ItemStack i : items){
				
				if(i !=null){
					if(slots[x] == 0){
						getEntity().getEquipment().setItemInMainHand(i);
						
						if(twoHand){
							getEntity().getEquipment().setItemInOffHand(i);
							
						}
					}
					if(slots[x] == 1){
						getEntity().getEquipment().setHelmet(i);
					}
					if(slots[x] == 2){
						getEntity().getEquipment().setChestplate(i);
					}
					if(slots[x] == 3){
						getEntity().getEquipment().setLeggings(i);
					}
					if(slots[x] == 4){
						getEntity().getEquipment().setBoots(i);
					}
				}
				x++;
			}
		}
	}

	public boolean isTwoHand() {
		return twoHand;
	}
	public void setTwoHand(boolean twoHand) {
		this.twoHand = twoHand;
	}
	public void setSlots(int[] slots) {
		this.slots = slots;
		
	}
	
	public void setAttack(double attack) {
		this.attack =  attack + (attack *taxaAttack *(nv));
		
		if(this.attack > 0){
			getEntity().getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(this.attack);
		}
	}

	public void setNivel(int nv){
		this.nv = nv;
	}
	public void setSpeed(double speed) {
		
		this.speed = speed + ((speed *taxaSpeed) *nv);
		if(this.speed > 0){
			getEntity().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(this.speed);
		}
	}

	public void setHealth(float health) {
		float taxa = Float.parseFloat(""+taxaHealth);
		this.health =  health + ((health * taxa) *nv);
		if(this.health > 0){
			getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(this.health);
			getEntity().setHealth(health);
		}
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		if(this.displayName!=null){
			getEntity().setCustomName(this.displayName.replaceAll("&", "§"));
			getEntity().setCustomNameVisible(true);
		}
	}

	
	public void setEntity(LivingEntity entity) {
		
		this.entity = entity;
		this.entity.setCollidable(true);
	}
	
}
