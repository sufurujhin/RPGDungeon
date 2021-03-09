package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.Utilities;

public class Click implements Listener {

	private RPGDungeon main;
	private Location loc;
	private Player player;
	private String dungeonId;

	public Click(RPGDungeon main) {
		this.main = main;
	}

	@EventHandler
	public void clickInventory(InventoryClickEvent event) {

		setPlayer((Player) event.getWhoClicked());
		Utilities.getUtilities().reloadLanguageConfig(main);
		if (event.getClickedInventory() != null) {
			String nomeParty = "";

			if (event.getClickedInventory().getItem(0) != null) {
				nomeParty = event.getClickedInventory().getItem(0).getItemMeta().getDisplayName();
			}

			if ((event.isLeftClick() | event.isRightClick()) & event.getView().getTitle().equalsIgnoreCase(Utilities.getUtilities().getLanguageConfig().getString("ConfigButtons.resquest").replaceAll("&", "§"))) {
				event.setCancelled(true);
				if ((event.isLeftClick() | event.isRightClick()) & event.getView().getTitle().equalsIgnoreCase(Utilities.getUtilities().getLanguageConfig().getString("ConfigButtons.resquest").replaceAll("&", "§"))) {
					event.setCancelled(true);
					if (event.isLeftClick()) {

						if (event.getSlot() == 3) {

							getPlayer().closeInventory();

							int slot = 0;
							for (ItemStack s : getPlayer().getInventory()) {
								if (s != null) {

									if (s.getItemMeta() != null) {
										if (s.getItemMeta().getDisplayName() != null) {
											if (s.getItemMeta().getDisplayName().equals(event.getInventory().getItem(0).getItemMeta().getDisplayName())) {

												break;
											}
										}
									}
								}
								slot++;
							}

							setDungeonId(Utilities.getUtilities().getLinesSpace(event.getInventory().getItem(0).getItemMeta().getDisplayName(), true));
							setLocation();

							if (getLocation() != null) {
								if (player.getInventory().getItem(slot).getAmount() == 1) {

									player.getInventory().clear(slot);

								} else {
									player.getInventory().getItem(slot).setAmount(player.getInventory().getItem(slot).getAmount() - 1);

								}
								String msg = Utilities.getUtilities().getLanguageConfig().getString("MessageDungeon.enterDungeonSucess").replaceAll("<dungeon>", event.getInventory().getItem(0).getItemMeta().getDisplayName());

								Utilities.getUtilities().teleport(player, getLocation(), msg);
								Utilities.getUtilities().reloadPartyConfig(main);

								if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups") != null) {
									for (String group1 : Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
										if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups." + group1 + ".status").getKeys(false).contains(player.getUniqueId().toString())) {

											Utilities.getUtilities().getPartyConfig().set("Groups." + group1 + ".status." + player.getUniqueId(), true);
											Utilities.getUtilities().savePartyConfig();
										}
									}

								}
							} else {
								player.sendMessage(Utilities.getUtilities().getLanguageConfig().getString("MessageDungeon.locationNoConfig").replaceAll("&", "§"));
								return;
							}

						}

						if (event.getSlot() == 5) {

							player.closeInventory();
						}

					}
				}
			}

			// sair da party-------------------------------------------------
			if ((event.isLeftClick() | event.isRightClick()) & event.getView().getTitle().equalsIgnoreCase(Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.leaveTheParty").replaceAll("&", "§"))) {
				event.setCancelled(true);

				if ((event.isLeftClick() | event.isRightClick()) & event.getView().getTitle().equalsIgnoreCase(Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.leaveTheParty").replaceAll("&", "§"))) {
					event.setCancelled(true);

					if (event.isLeftClick()) {

						if (event.getSlot() == 3) {

							player.closeInventory();
							Utilities.getUtilities().reloadPartyConfig(main);
							String msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.leftThePart");
							String msgPlayers = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.playerLeftThePart").replaceAll("<player>", player.getName());
							leaveParty(main, player, msgTarget, msgPlayers);

						}
						if (event.getSlot() == 5) {
							String msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.refuseInvite").replaceAll("<player>", event.getInventory().getItem(0).getItemMeta().getDisplayName());
							String msgPlayer = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.refuseInvite").replaceAll("<player>", event.getInventory().getItem(0).getItemMeta().getDisplayName());

							Utilities.getUtilities().sendMesage(player, msgTarget);
							Player player = Utilities.getUtilities().getPlayer(main, event.getInventory().getItem(0).getItemMeta().getDisplayName());
							if (player != null) {
								Utilities.getUtilities().sendMesage(player, msgPlayer);
							}

							player.closeInventory();
						}

					}
				}

			}

			// entrar na party
			if (nomeParty != null) {
				String inventoryName = null;
				if (Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.joinThePart").replaceAll("&", "§").replaceAll("<player>", nomeParty) != null) {
					inventoryName = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.joinThePart").replaceAll("&", "§").replaceAll("<player>", nomeParty);
				}
				String eventInventory = null;
				if (event.getInventory().getItem(0) != null) {
					if (event.getInventory().getItem(0).getItemMeta() != null) {
						if (event.getInventory().getItem(0).getItemMeta().getDisplayName() != null) {
							eventInventory = event.getView().getTitle().replaceAll("<player>", event.getInventory().getItem(0).getItemMeta().getDisplayName());
						}
					}
				}
				if (eventInventory != null) {
					if ((event.isLeftClick() | event.isRightClick()) & eventInventory.equalsIgnoreCase(inventoryName)) {
						event.setCancelled(true);

						if ((event.isLeftClick() | event.isRightClick()) & eventInventory.equalsIgnoreCase(inventoryName)) {
							event.setCancelled(true);

							if (event.isLeftClick()) {

								if (event.getSlot() == 3) {

									player.closeInventory();

									setParty(main, player, Utilities.getUtilities().getPlayer(main, event.getInventory().getItem(0).getItemMeta().getDisplayName()));

								}
								if (event.getSlot() == 5) {

									player.closeInventory();
								}

							}
						}
					} else {

						inventoryName = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.kickPlayerRequest").replaceAll("&", "§").replaceAll("<player>", ": " + nomeParty);

						if ((event.isLeftClick() | event.isRightClick()) & eventInventory.equalsIgnoreCase(inventoryName)) {
							event.setCancelled(true);

							if ((event.isLeftClick() | event.isRightClick()) & event.getView().getTitle().equalsIgnoreCase(inventoryName)) {
								event.setCancelled(true);

								if (event.isLeftClick()) {

									if (event.getSlot() == 3) {
										player.closeInventory();
										Utilities.getUtilities().reloadPartyConfig(main);
										Player kicked = Utilities.getUtilities().getPlayer(main, nomeParty);
										String msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.kickMsgTarget").replaceAll("<lider>", event.getWhoClicked().getName());
										String msgPlayers = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.kickMsgLider").replaceAll("<kick>", kicked.getName());
										leaveParty(main, kicked, msgTarget, msgPlayers);

									}
									if (event.getSlot() == 5) {

										player.closeInventory();
									}

								}
							}

						}
					}
				}

			}
		}

	}

	private void setLocation() {
		this.loc = Utilities.getUtilities().getLocation(dungeonId, main, "instance", player);
	}

	private Location getLocation() {
		return loc;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getDungeonId() {
		return dungeonId;
	}

	public void setDungeonId(String dungeonId) {
		this.dungeonId = dungeonId;
	}

	// -----------------------------------Leave the
	// party--------------------------------------------------------------
	public void leaveParty(RPGDungeon plugin, Player p, String msgTarget, String msgPlayers) {
		Utilities.getUtilities().reloadPartyConfig(plugin);

		if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups") != null) {
			if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false).size() > 0) {
				String path = null;
				boolean lider = false;
				for (String key : Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
					if (Utilities.getUtilities().getPartyConfig().getString("Groups." + key + ".members").contains(p.getUniqueId().toString())) {
						path = "Groups." + key;
						if (Utilities.getUtilities().getPartyConfig().getString("Groups." + key + ".lider").contains(p.getUniqueId().toString())) {
							lider = true;
						}
					}

				}

				if (path != null) {
					if (lider) {
						if (Utilities.getUtilities().getPartyConfig().getStringList(path + ".members").size() == 2) {
							List<String> players = new ArrayList<>();
							players = Utilities.getUtilities().getPartyConfig().getStringList(path + ".members");
							players.remove(p.getUniqueId().toString());

							Utilities.getUtilities().getPartyConfig().set(path, null);
							Utilities.getUtilities().savePartyConfig();

							Utilities.getUtilities().sendMesage(p, msgTarget);
							for (String msg : players) {

								for (Player player : plugin.getServer().getOnlinePlayers()) {
									if (player.getUniqueId().toString().equalsIgnoreCase(msg)) {
										Utilities.getUtilities().sendMesage(player, msgPlayers);
									}
								}

							}
							return;
						}
						if (Utilities.getUtilities().getPartyConfig().getStringList(path + ".members").size() >= 2) {
							List<String> players = new ArrayList<>();
							players = Utilities.getUtilities().getPartyConfig().getStringList(path + ".members");
							players.remove(p.getUniqueId().toString());
							Utilities.getUtilities().getPartyConfig().set(path + ".members", players);
							Utilities.getUtilities().getPartyConfig().set(path + ".lider", players.get(0));
							Utilities.getUtilities().savePartyConfig();
							Utilities.getUtilities().sendMesage(p, msgTarget);
							for (String msg : players) {

								for (Player player : plugin.getServer().getOnlinePlayers()) {
									if (player.getUniqueId().toString().equalsIgnoreCase(msg)) {
										Utilities.getUtilities().sendMesage(player, msgPlayers);
									}
								}

							}

						}

					} else {
						if (Utilities.getUtilities().getPartyConfig().getStringList(path + ".members").size() == 2) {
							List<String> players = new ArrayList<>();
							players = Utilities.getUtilities().getPartyConfig().getStringList(path + ".members");
							players.remove(p.getUniqueId().toString());

							Utilities.getUtilities().getPartyConfig().set(path, null);
							Utilities.getUtilities().savePartyConfig();
							Utilities.getUtilities().sendMesage(p, msgTarget);
							for (String msg : players) {

								for (Player player : plugin.getServer().getOnlinePlayers()) {
									if (player.getUniqueId().toString().equalsIgnoreCase(msg)) {
										Utilities.getUtilities().sendMesage(player, msgPlayers);
									}
								}

							}
							return;
						}
						if (Utilities.getUtilities().getPartyConfig().getStringList(path + ".members").size() >= 2) {
							List<String> players = new ArrayList<>();
							players = Utilities.getUtilities().getPartyConfig().getStringList(path + ".members");
							players.remove(p.getUniqueId().toString());
							Utilities.getUtilities().getPartyConfig().set(path + ".members", players);
							Utilities.getUtilities().savePartyConfig();
							Utilities.getUtilities().sendMesage(p, msgTarget);
							for (String msg : players) {

								for (Player player : plugin.getServer().getOnlinePlayers()) {
									if (player.getUniqueId().toString().equalsIgnoreCase(msg)) {
										Utilities.getUtilities().sendMesage(player, msgPlayers);
									}
								}

							}

						}
					}
					return;
				} else {
					msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.notIsAGroup");
					Utilities.getUtilities().sendMesage(p, msgTarget);
					return;
				}

			} else {
				String msgPlayer = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.notIsAGroup");
				Utilities.getUtilities().sendMesage(p, msgPlayer);
				return;
			}
		} else {
			msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.notIsAGroup");
			Utilities.getUtilities().sendMesage(p, msgTarget);
			return;
		}
	}

	public void setParty(RPGDungeon plugin, Player target, Player p) {
		Utilities.getUtilities().reloadPartyConfig(plugin);

		if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups") != null) {
			if (Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false).size() > 0) {
				String path = null;
				int x = 0;
				for (String key : Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
					if (Utilities.getUtilities().getPartyConfig().getString("Groups." + key) != null) {
						if (Utilities.getUtilities().getPartyConfig().getString("Groups." + key + ".lider").contains(p.getUniqueId().toString())) {
							path = "Groups." + key;
							break;
						}

					}
					x++;
				}

				int i = Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups").getKeys(false).size();

				if (path != null) {
					addMember(target, p, plugin, path, x);
					return;
				} else {
					addParty(target, p, plugin, i);
					return;
				}

			} else {
				addParty(target, p, plugin, 0);
				return;
			}
		} else {
			addParty(target, p, plugin, 0);
			return;
		}
	}

	public void addMember(Player target, Player lider, RPGDungeon plugin, String path, int i) {

		Utilities.getUtilities().reloadPartyConfig(plugin);
		Utilities.getUtilities().reloadLanguageConfig(plugin);
		List<String> players = new ArrayList<>();
		players = Utilities.getUtilities().getPartyConfig().getStringList(path + ".members");
		players.add(target.getUniqueId().toString());
		String s = "group" + (i + 1);
		Utilities.getUtilities().getPartyConfig().set("Groups." + s + ".lider", lider.getUniqueId().toString());
		Utilities.getUtilities().getPartyConfig().set("Groups." + s + ".members", players);
		Utilities.getUtilities().savePartyConfig();
		String msgLider = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.invite").replaceAll("<target>", target.getName());
		String msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.inviteTarget").replaceAll("<player>", lider.getName());
		Utilities.getUtilities().sendMesage(lider, msgLider);

		for (String msg : players) {

			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (player.getUniqueId().toString().equalsIgnoreCase(msg) && !player.getUniqueId().toString().equalsIgnoreCase(lider.getUniqueId().toString()) && !player.getUniqueId().toString().equalsIgnoreCase(target.getUniqueId().toString())) {
					Utilities.getUtilities().sendMesage(player, msgLider);
				}
			}

		}
		Utilities.getUtilities().sendMesage(target, msgTarget);
	}

	public void addParty(Player target, Player lider, RPGDungeon plugin, int i) {
		Utilities.getUtilities().reloadPartyConfig(plugin);
		Utilities.getUtilities().reloadLanguageConfig(plugin);
		List<String> players = new ArrayList<>();
		players.add(target.getUniqueId().toString());
		players.add(lider.getUniqueId().toString());
		Utilities.getUtilities().getPartyConfig().set("Groups." + "group" + (i + 1) + ".lider", lider.getUniqueId().toString());
		Utilities.getUtilities().getPartyConfig().set("Groups." + "group" + (i + 1) + ".members", players);
		Utilities.getUtilities().getPartyConfig().set("Groups." + "group" + (i + 1) + ".status." + target.getUniqueId(), false);
		if (!Utilities.getUtilities().getPartyConfig().getConfigurationSection("Groups." + "group" + (i + 1) + ".status").getKeys(false).contains(lider.getUniqueId())) {
			Utilities.getUtilities().getPartyConfig().set("Groups." + "group" + (i + 1) + ".status." + lider.getUniqueId(), false);
		}
		Utilities.getUtilities().savePartyConfig();
		String msgLider = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.invite").replaceAll("<target>", target.getName());
		String msgTarget = Utilities.getUtilities().getLanguageConfig().getString("PartyMesage.inviteTarget").replaceAll("<player>", lider.getName());
		Utilities.getUtilities().sendMesage(lider, msgLider);
		Utilities.getUtilities().sendMesage(target, msgTarget);
	}
}
