package com.sufurujhin.rpgdungeon.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.classes.SpawnTimer;

import net.minecraft.server.v1_16_R3.World;

public class SummonerRunable extends BukkitRunnable {
	RPGDungeon plugin;
	public HashMap<String, Location> spawnersLocate;
	public HashMap<String, Integer> spawnersMap;
	public HashMap<String, Integer> newspawnersMap;

	public SummonerRunable(RPGDungeon plugin) {
		this.plugin = plugin;
		this.newspawnersMap = new HashMap<String, Integer>();
		this.spawnersMap = new HashMap<String, Integer>();
		this.spawnersLocate = new HashMap<String, Location>();
	}

	@Override
	public void run() {

		if (spawnersMap.size() > 0) {
			Location loc = null;
			for (String ent : spawnersMap.keySet()) {
				if (!plugin.getServer().getWorld("world").getEntities()
						.contains(plugin.getServer().getEntity(UUID.fromString(ent)))) {
					loc = spawnersLocate.get(ent);
//					Bukkit.getServer().broadcastMessage("X: "+loc.getChunk().getX());
//					Bukkit.getServer().broadcastMessage("X: "+loc.getChunk().getZ());
					if (loc != null)
					
						for (Entity e : loc.getChunk().getEntities()) {
							if (e.getType() == EntityType.PLAYER) {
								int time = spawnersMap.get(ent);
								if ((time - 1) > 0) {
									spawnersMap.replace(ent, time, time - 1);
									newspawnersMap.put(ent, spawnersMap.get(ent));
								} else {
									spawnMob(ent);
									spawnersMap.replace(ent, time, 0);
								}
								break;
							}
						}
				}
			}
			for (String ent : newspawnersMap.keySet()) {
				if (!spawnersMap.containsKey(ent))
					spawnersMap.put(ent, newspawnersMap.get(ent));
				if (spawnersMap.get(ent) <= 0){
					spawnersMap.remove(ent);
					spawnersLocate.remove(ent);
					}
			}

			newspawnersMap.clear();
		}

		// if (spawnersMap.size() > 0) {
		// if (spawnersMap != null) {
		// int i = 0;
		// while (i < spawnersMap.size()) {
		// // for (String uuid : spawners.keySet()) {
		//
		// if
		// (Bukkit.getServer().getWorld("world").getEntities().contains(spawnersMap.get(i)))
		// {
		//
		// if (spawnersMap.containsKey(spawners.get(i))) {
		// int count = spawnersMap.get(spawners.get(i)) - 1;
		// LivingEntity ent = spawners.get(i);
		// if (count <= 0) {
		// spawners.remove(ent);
		// spawnersMap.remove(ent);
		// spawnMob(ent.getUniqueId().toString());
		// } else {
		// spawnersMap.remove(ent);
		// spawnersMap.put(ent, count);
		// }
		// Bukkit.getServer().getConsoleSender().sendMessage("teste: " +
		// (Bukkit.getServer().getWorld("world").getEntities() !=null));
		// Bukkit.getServer().getConsoleSender().sendMessage("teste2: " +(
		// spawners.get(i) == null));
		// }
		// }
		// i++;
		// // }
		// }
		//
		// }
		// }

	}

	public void spawnMob(String uuid) {
		Creature craft = new Creature(plugin);
		SpawnTimer spawn = new SpawnTimer();

		if (spawn.Select(uuid)) {
			Files file = new Files(plugin);
			file.reloadMobConfig(plugin);
			if (file.getMobConfig().getConfigurationSection("").contains(spawn.observacao)) {
				if (file.getMobConfig().getConfigurationSection(spawn.observacao) != null) {
					Location loc = new Location(plugin.getServer().getWorld(spawn.location_world),
							(spawn.location_x + 0.5), (spawn.location_y + 0.5), (spawn.location_z + 0.5), 0.0f, 0.0f);

					World world = ((CraftWorld) loc.getWorld()).getHandle();
					craft.setAtribute(plugin, spawn.observacao);
					file.reloadMobConfig(plugin);
					LivingEntity ent = craft.createCreature(world,
							file.getMobConfig().getString(spawn.observacao + ".type"), loc, spawn.observacao);
					spawn.status = true;
					spawn.uuid = ent.getUniqueId().toString();
					spawn.update(spawn.spawn_id);
					// spawners.add(ent);
					newspawnersMap.put(ent.getUniqueId().toString(), spawn.secunds);
					spawnersLocate.put(ent.getUniqueId().toString(), loc);
				}
			}
		}

	}

	public void spawnMob() {
		Creature craft = new Creature(plugin);
		SpawnTimer spawn = new SpawnTimer();

		List<String> list = spawn.Select(true);

		RemoverEtities();

		if (list.size() > 0) {
			for (String uuid : list) {

				if (spawn.Select(uuid)) {

					Files file = new Files(plugin);
					file.reloadMobConfig(plugin);
					if (spawn.observacao != null)
						if (file.getMobConfig().getConfigurationSection("").contains(spawn.observacao)) {
							if (file.getMobConfig().getConfigurationSection(spawn.observacao) != null) {
								Location loc = new Location(plugin.getServer().getWorld(spawn.location_world),
										(spawn.location_x + 0.5), (spawn.location_y + 0.5), (spawn.location_z + 0.5),
										0.0f, 0.0f);

								World world = ((CraftWorld) loc.getWorld()).getHandle();

								craft.setAtribute(plugin, spawn.observacao);
								file.reloadMobConfig(plugin);
								LivingEntity ent = craft.createCreature(world,
										file.getMobConfig().getString(spawn.observacao + ".type"), loc,
										spawn.observacao);
								spawn.status = true;
								spawn.uuid = ent.getUniqueId().toString();
								spawn.update(spawn.spawn_id);
								// spawners.add(ent);
								spawnersMap.put(ent.getUniqueId().toString(), spawn.secunds);
								spawnersLocate.put(ent.getUniqueId().toString(), loc);
							}
						}
				}

			}

		}

	}

	public void RemoverEtities() {
		SpawnTimer spawn = new SpawnTimer();
		List<String> list = spawn.Select(true);
		if (list.size() > 0) {
			for (String uuid : list) {
				if (Bukkit.getServer().getEntity(UUID.fromString(uuid)) != null) {
					Bukkit.getServer().getEntity(UUID.fromString(uuid)).remove();
				}
			}
		}

	}
}
