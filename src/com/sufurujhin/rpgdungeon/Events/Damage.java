package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.Utilities;
import com.sufurujhin.rpgdungeon.skills.SkillsRPG;

public class Damage implements Listener {

	private RPGDungeon rpg;

	public Damage(RPGDungeon rpg) {
		this.rpg = rpg;
	}

	@EventHandler
	public void attack(EntityDamageByEntityEvent event) {

		if (event != null) {
			if (event.getCause() != null) {
				if (event.getDamager().getType() == EntityType.PLAYER
						|| event.getDamager().getType() == EntityType.ARROW) {

					if (event.getEntity().getCustomName() != null) {
						LivingEntity ent = (LivingEntity) event.getEntity();
						Utilities.getUtilities().setEntityName(event.getEntity(), (ent.getHealth() - event.getDamage()), ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						Files file = new Files(rpg);
						file.reloadMobConfig(rpg);
						List<String> skills = new ArrayList<>();

						boolean efect = false;
						String nameEfect = "";

						for (String key : file.getMobConfig().getConfigurationSection("").getKeys(false)) {

							if (Utilities.getUtilities().getEntityName(ent).equals(file.getMobConfig().getString(key + ".displayName"))) {
								if (file.getMobConfig().getConfigurationSection(key).getKeys(false)
										.contains("effect")) {
									nameEfect = file.getMobConfig().getString(key + ".effect");
									efect = true;
								}
								if (file.getMobConfig().getConfigurationSection(key).getKeys(false)
										.contains("skills")) {

									if (file.getMobConfig().getStringList(key + ".skills") != null) {
										skills = file.getMobConfig().getStringList(key + ".skills");
										break;
									}

								}

							}
						}
						if (efect) {
							int x, y, z;

							Location loc = event.getEntity().getLocation();
							x = loc.getBlockX();
							y = loc.getBlockY() + 1;
							z = loc.getBlockZ();
							loc = new Location(loc.getWorld(), x, y, z);
							String[] path = nameEfect.split(":");
							event.getEntity().getWorld().playEffect(loc, Effect.valueOf(path[0]),
									Integer.parseInt(path[1]));

						}

						if (skills != null) {
							Random r = new Random();
							for (String s : skills) {
								String[] path = s.split(" ");
								if (path[0].equalsIgnoreCase("potion")) {
									float sort = r.nextFloat();
									if (sort <= Float.parseFloat(path[2])) {
										String[] settings = path[1].split(":");
										SkillsRPG skill = new SkillsRPG(rpg);
										List<Player> getPlayersRadious = new ArrayList<>();
										getPlayersRadious = skill.getPlayersRadious(Integer.parseInt(settings[0]), rpg,
												event.getEntity());
										for (Player p : getPlayersRadious) {

											PotionEffect pe = new PotionEffect(PotionEffectType.getByName(settings[1]),
													Integer.parseInt(settings[2]) * 20,
													Integer.parseInt(settings[3]) - 1);
											((LivingEntity) p).addPotionEffect(pe);
										}

									}
								}

								if (path[0].equalsIgnoreCase("teleport")) {

									float sort = r.nextFloat();
									if (sort <= Float.parseFloat(path[2])) {

										if (event.getDamager().getType() == EntityType.PLAYER) {

											event.getEntity().teleport(event.getDamager().getLocation());
											Player p = (Player) event.getDamager();
											p.damage(Integer.parseInt(path[1]));

										} else if (event.getDamager().getType() == EntityType.ARROW) {

											Arrow a = (Arrow) event.getDamager();
											if (a.getShooter().equals(EntityType.PLAYER)) {
												Player p = (Player) a.getShooter();
												event.getEntity().teleport(p.getLocation());
												p.damage(Integer.parseInt(path[1]));
											}
											
										}
									}
								}

								if (path[0].equalsIgnoreCase("fire")) {
									float sort = r.nextFloat();
									if (sort <= Float.parseFloat(path[3])) {
										String[] settings = path[4].split("=");
										String msg = settings[1];

										SkillsRPG skill = new SkillsRPG(rpg);
										List<Player> getPlayersRadious = new ArrayList<>();
										getPlayersRadious = skill.getPlayersRadious(Integer.parseInt(path[1]), rpg,
												event.getEntity());
										for (Player p : getPlayersRadious) {
											if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
												p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
											}
											p.setFireTicks(Integer.parseInt(path[2]) * 20);
											p.sendMessage(msg.replaceAll("&", "§").replaceAll("_", " ")
													.replaceAll("<boss>", Utilities.getUtilities().getEntityName(ent)));
										}

									}
								}

							}

						} else {
							return;
						}

					}

				}

			}
		}
	}
}
