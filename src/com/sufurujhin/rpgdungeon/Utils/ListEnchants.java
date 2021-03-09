package com.sufurujhin.rpgdungeon.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ListEnchants {
	List<Enchantment> enchants = new ArrayList<>();

	public List<Enchantment> getEnchantsList() {

		enchants.add(Enchantment.ARROW_DAMAGE);
		enchants.add(Enchantment.ARROW_FIRE);
		enchants.add(Enchantment.ARROW_INFINITE);
		enchants.add(Enchantment.ARROW_KNOCKBACK);
		enchants.add(Enchantment.DAMAGE_ALL);
		enchants.add(Enchantment.DAMAGE_ARTHROPODS);
		enchants.add(Enchantment.DAMAGE_UNDEAD);
		enchants.add(Enchantment.DEPTH_STRIDER);
		enchants.add(Enchantment.DIG_SPEED);
		enchants.add(Enchantment.DURABILITY);
		enchants.add(Enchantment.FIRE_ASPECT);
		enchants.add(Enchantment.FROST_WALKER);
		enchants.add(Enchantment.KNOCKBACK);
		enchants.add(Enchantment.LOOT_BONUS_BLOCKS);
		enchants.add(Enchantment.LOOT_BONUS_MOBS);
		enchants.add(Enchantment.LUCK);
		enchants.add(Enchantment.LURE);
		enchants.add(Enchantment.MENDING);
		enchants.add(Enchantment.OXYGEN);
		enchants.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		enchants.add(Enchantment.PROTECTION_EXPLOSIONS);
		enchants.add(Enchantment.PROTECTION_FALL);
		enchants.add(Enchantment.PROTECTION_FIRE);
		enchants.add(Enchantment.PROTECTION_PROJECTILE);
		enchants.add(Enchantment.SILK_TOUCH);
		enchants.add(Enchantment.THORNS);
		enchants.add(Enchantment.WATER_WORKER);
		return enchants;
	}

	public List<String> getNameEnchant(ItemStack item) {
		List<String> listEnchant = new ArrayList<>();

		String name = null;
		if (item != null) {
			if (item.containsEnchantment(Enchantment.ARROW_DAMAGE)) {
				name = "Força " + item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.ARROW_FIRE)) {
				name = "Chama " + item.getEnchantmentLevel(Enchantment.ARROW_FIRE);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
				name = "Infinidade " + item.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
				name = "Impacto " + item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
				name = "Afiada " + item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
				name = "Ruina Atropódis " + item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
				name = "Julgamento " + item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DEPTH_STRIDER)) {
				name = "Passos PRofundos "+ item.getEnchantmentLevel(Enchantment.DEPTH_STRIDER);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DIG_SPEED)) {
				name = "Efficiencia" + item.getEnchantmentLevel(Enchantment.DIG_SPEED);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.DURABILITY)) {
				name = "Durabilidade " + item.getEnchantmentLevel(Enchantment.DURABILITY);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.FIRE_ASPECT)) {
				name = "Aspecto flame " + item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.FROST_WALKER)) {
				name = "Passos Congelados " + item.getEnchantmentLevel(Enchantment.FROST_WALKER);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.KNOCKBACK)) {
				name = "Repulsão " + item.getEnchantmentLevel(Enchantment.KNOCKBACK);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
				name = "Fortuna " + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
				name = "Pilhagem " + item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
				listEnchant.add(name);
			}

			if (item.containsEnchantment(Enchantment.LUCK)) {
				name = "Sorte do Mar " + item.getEnchantmentLevel(Enchantment.LUCK);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.LURE)) {
				name = "Isca " + item.getEnchantmentLevel(Enchantment.LURE);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.MENDING)) {
				name = "Remendo " + item.getEnchantmentLevel(Enchantment.MENDING);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.OXYGEN)) {
				name = "Oxigênio " + item.getEnchantmentLevel(Enchantment.OXYGEN);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				name = "P" + item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS)) {
				name = "P. Explosões " + item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.PROTECTION_FALL)) {
				name = "Peso Pena " + item.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.PROTECTION_FIRE)) {
				name = "P. Fogo " + item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE);
				listEnchant.add(name);
			}

			if (item.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
				name = "p. Projeteis " + item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.SILK_TOUCH)) {
				name = "Toque Suave " + item.getEnchantmentLevel(Enchantment.SILK_TOUCH);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.THORNS)) {
				name = "Espinhos " + item.getEnchantmentLevel(Enchantment.THORNS);
				listEnchant.add(name);
			}
			if (item.containsEnchantment(Enchantment.WATER_WORKER)) {
				name = "Afini. Aquatica " + item.getEnchantmentLevel(Enchantment.WATER_WORKER);
				listEnchant.add(name);
			}
		}
		return listEnchant;
	}
}
