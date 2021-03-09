package com.sufurujhin.rpgdungeon.Utils;

import java.util.Random;

import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;

public class CraftTagNBT {

		private ItemStack item;
		private double health;
		private double attack;
		private double attack_speed;
		private double speed;
		private int data;
		private double defense;
		private double chanceUnbreakable;
		
		public CraftTagNBT(ItemStack item, double health, double attack, double attack_speed, double speed, int data, double defense,double chanceUnbreakable) {
			this.item = item;
			this.attack = attack;
			this.attack_speed = attack_speed;
			this.speed = speed;
			this.health = health;
			this.data = data;
			this.defense = defense;
			this.chanceUnbreakable = chanceUnbreakable;
		}

		public void setTagNBT() {
			Random r = new Random();
			int uuidItem = r.nextInt(99999);
			uuidItem+=15555;
			net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
			NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
			NBTTagList modifiers = new NBTTagList();
			NBTTagCompound healthNBT = new NBTTagCompound();
			NBTTagCompound attackNBT = new NBTTagCompound();
			NBTTagCompound attack_speedNBT = new NBTTagCompound();
			NBTTagCompound speedNBT = new NBTTagCompound();
			NBTTagCompound defenseNBT = new NBTTagCompound();

			if (health > 0) {
				healthNBT.setString("AttributeName", ("generic.maxHealth"));
				healthNBT.setString("Name", ("HP Adicional"));
				healthNBT.setDouble("Amount", (health));
				healthNBT.setInt("Operation", (0));
				healthNBT.setInt("UUIDLeast", (uuidItem + data+1));
				healthNBT.setInt("UUIDMost", (uuidItem + data+1));
				healthNBT.setString("Slot", (getData()));
				modifiers.add(healthNBT);
			}
			if (attack > 0) {
				attackNBT.setString("AttributeName", ("generic.attackDamage"));
				attackNBT.setString("Name", ("Ataque Adicional"));
				attackNBT.setDouble("Amount", (attack));
				attackNBT.setInt("Operation", (0));
				attackNBT.setInt("UUIDLeast", (uuidItem + data+2));
				attackNBT.setInt("UUIDMost", (uuidItem + data+2));
				attackNBT.setString("Slot", (getData()));
				modifiers.add(attackNBT);
			}
			if (attack_speed > 0) {
				attack_speedNBT.setString("AttributeName", ("generic.attackSpeed"));
				attack_speedNBT.setString("Name", ("Velocidade de ataque Adicional"));
				attack_speedNBT.setDouble("Amount", (attack_speed));
				attack_speedNBT.setInt("Operation", (0));
				attack_speedNBT.setInt("UUIDLeast", (uuidItem + data+3));
				attack_speedNBT.setInt("UUIDMost", (uuidItem + data+3));
				attack_speedNBT.setString("Slot", (getData()));
				modifiers.add(attack_speedNBT);
			}
			if (speed > 0) {
				speedNBT.setString("AttributeName", ("generic.movementSpeed"));
				speedNBT.setString("Name", ("Vel movimento Adicional"));
				speedNBT.setDouble("Amount", (speed));
				speedNBT.setInt("Operation", (1));
				speedNBT.setInt("UUIDLeast", (uuidItem + data+4));
				speedNBT.setInt("UUIDMost", (uuidItem + data+4));
				speedNBT.setString("Slot", (getData()));
				modifiers.add(speedNBT);
			}
			if (defense > 0) {
				defenseNBT.setString("AttributeName", ("generic.armor"));
				defenseNBT.setString("Name", ("Defense Adicional"));
				defenseNBT.setDouble("Amount", (defense));
				defenseNBT.setInt("Operation", (0));
				defenseNBT.setInt("UUIDLeast", (uuidItem + data+4));
				defenseNBT.setInt("UUIDMost", (uuidItem + data+4));
				defenseNBT.setString("Slot", (getData()));
				modifiers.add(defenseNBT);
			}

			if(r.nextFloat() <=chanceUnbreakable){
				compound.setByte("Unbreakable", ((byte) 2));
			}
			compound.set("AttributeModifiers", modifiers);
			nmsStack.setTag(compound);
			item = CraftItemStack.asBukkitCopy(nmsStack);
			
		}

		public String getData() {
			String data2 = null;
			if (data == 0) {
				data2 = "mainhand";
			} else if (data == 1) {
				data2 = "head";
			} else if (data == 2) {
				data2 = "chest";
			} else if (data == 3) {
				data2 = "legs";
			} else if (data == 4) {
				data2 = "feet";
			} else if (data == 5) {
				data2 = "offhand";
			}
			return data2;
		}
		
		public ItemStack getItemTagNBT() {
			
			return item;

		}
	
}
