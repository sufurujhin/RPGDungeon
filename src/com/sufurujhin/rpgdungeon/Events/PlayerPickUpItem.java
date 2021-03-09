package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.MsgPickupItem;

import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("deprecation")
public class PlayerPickUpItem implements Listener {

	private RPGDungeon plugin;

	public PlayerPickUpItem(RPGDungeon plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void pickupItem(PlayerPickupItemEvent event) {

		if (event != null) {

			if (event.getItem() != null) {

				if (event.getItem() != null) {

					Player player = event.getPlayer();
					ItemStack item = (ItemStack) event.getItem().getItemStack();

					if (item.getItemMeta() != null) {

						// if (item.getItemMeta().getEnchants().size() >0) {
						String nameItem = item.getItemMeta().getDisplayName();
						if (nameItem == null) {
							nameItem = item.getType().toString();
						}
						String money = plugin.getConfig().getString("dungeons.moneyCoin").replaceAll("&", "§");

						String[] path = nameItem.split("x");

						Files file = new Files(plugin);
						file.reloadPartyConfig(plugin);
						if (path[0].trim().equals(money.trim()) & (path.length > 1)) {
							event.setCancelled(true);
							event.getItem().remove();

							RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager()
									.getRegistration(Economy.class);

							Economy economy = economyProvider.getProvider();

							plugin.loadEcoMob(plugin);
							boolean econ = plugin.getEcoMob().getBoolean("SendMsg");
							List<String> membros = new ArrayList<>();
							if (file.getPartyConfig().getConfigurationSection("") != null) {
								if (file.getPartyConfig().getConfigurationSection("Groups") != null) {
									for (String key1 : file.getPartyConfig().getConfigurationSection("Groups")
											.getKeys(false)) {

										if (file.getPartyConfig().getStringList("Groups." + key1 + ".members")
												.contains(player.getUniqueId().toString())) {
											membros = file.getPartyConfig().getStringList("Groups." + key1 + ".members");
											break;
										}

									}
								}
							}

							try {
								int amount = Integer.parseInt(path[path.length - 1]);

								if (membros.size() > 1 && amount >= membros.size()) {
									amount = amount / membros.size();
									for (String uuid : membros) {
										Player p = plugin.getServer().getPlayer(UUID.fromString(uuid));
										if (p != null)
											getCoin(econ, item, p, amount, economy);
									}
								} else {

									getCoin(econ, item, player, amount, economy);
								}
							} catch (Exception e) {
								// TODO: handle exception

							}

						} else {

							file.reloadLanguageConfig(plugin);

							MsgPickupItem msgPick = new MsgPickupItem();
							msgPick.sendMsgPickup(plugin, item, player, file, "pick");

						}
						// }
					}

				}
			}
		}

	}

	public void getCoin(boolean econ, ItemStack item, Player player, int amount, Economy economy) {

		if (item.getType() == Material.GOLD_NUGGET) {

			if (econ) {
				String msg = plugin.getEcoMob().getString("MsgDeGanho").replaceAll("&", "§");
				player.sendMessage(msg.replace("{amount}", "" + amount));
			}

			economy.depositPlayer(player.getName(), amount);

		} else if (item.getType() == Material.GOLD_BLOCK) {

			if (econ) {
				String msg = plugin.getEcoMob().getString("MsgDeGanho").replaceAll("&", "§");
				player.sendMessage(msg.replace("{amount}", "" + amount));
			}
			economy.depositPlayer(player.getName(), amount);
		}

	}
}
