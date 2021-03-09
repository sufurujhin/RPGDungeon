package com.sufurujhin.rpgdungeon.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.Utilities;

public class PlayerQuit extends Utilities implements Listener {

	private RPGDungeon rpg;

	public PlayerQuit(RPGDungeon rpg) {
		super(rpg);
		this.rpg = rpg;
	}

	@EventHandler
	public void quit(PlayerQuitEvent env) {
		Player p = env.getPlayer();

		reloadPartyConfig(rpg);
		reloadLocationConfig(rpg);
		if (getPartyConfig().getConfigurationSection("Groups") != null) {
			for (String group : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
				if (getPartyConfig().getConfigurationSection("Groups." + group) != null) {
					if (getPartyConfig().getConfigurationSection("Groups." + group + ".members") != null) {
						if (getPartyConfig().getStringList("Groups." + group + ".members")
								.contains(p.getUniqueId().toString())) {

							int x = 0;
							for (String member : getPartyConfig().getConfigurationSection("Groups." + group + ".status")
									.getKeys(false)) {
								if (getPartyConfig().getBoolean("Groups." + group + ".status." + member)) {

									x++;
								}
							}

							if (x >= 1) {
								if (x == 1) {
									getPartyConfig().set("Groups." + group, null);
									savePartyConfig();
								} else {
									getPartyConfig().set("Groups." + group + ".status." + p.getUniqueId().toString(),
											false);
									savePartyConfig();

								}

							} else {
								if (getLocationConfig() != null) {

									boolean verifica = false;
									getPartyConfig().set("Groups." + group + ".status." + p.getUniqueId().toString(),
											false);
									savePartyConfig();
									for (String dungeon : getLocationConfig().getConfigurationSection("")
											.getKeys(false)) {
										if (getLocationConfig().getConfigurationSection(dungeon) != null)
											for (String instance : getLocationConfig().getConfigurationSection(dungeon)
													.getKeys(false)) {
												if (getLocationConfig()
														.getStringList(dungeon + "." + instance + ".group") != null) {
													if (getLocationConfig()
															.getStringList(dungeon + "." + instance + ".group")
															.contains(p.getUniqueId().toString())) {
														verifica = true;
														despawnMob(rpg, (dungeon + "." + instance), p, dungeon);
														break;
													}
												}

											}
										if (verifica) {
											break;
										}
									}
								}

							}

						}
					}
				}
			}
		} else {
			boolean verifica = false;
			for (String dungeon : getLocationConfig().getConfigurationSection("").getKeys(false)) {
				if (getLocationConfig().getConfigurationSection(dungeon) != null) {
					for (String instance : getLocationConfig().getConfigurationSection(dungeon).getKeys(false)) {
						if (getLocationConfig().getStringList(dungeon + "." + instance + ".group") != null) {
							if (getLocationConfig().getStringList(dungeon + "." + instance + ".group")
									.contains(p.getUniqueId().toString())) {
								verifica = true;
								despawnMob(rpg, (dungeon + "." + instance), p, dungeon);
								break;
							}
						}

					}
					if (verifica) {
						break;
					}
				}
			}

		}

	}
}
