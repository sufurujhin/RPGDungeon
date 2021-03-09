package com.sufurujhin.rpgdungeon.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.Creature;
import com.sufurujhin.rpgdungeon.Utils.Utilities;
import com.sufurujhin.rpgdungeon.classes.SpawnTimer;

import net.minecraft.server.v1_16_R3.World;

public class CommandsRPG extends Utilities implements CommandExecutor {

	private RPGDungeon plugin;

	public CommandsRPG(RPGDungeon rpg) {
		super(rpg);
		this.plugin = rpg;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		reloadLanguageConfig(plugin);
		// set Player Executor
		if (!(sender instanceof Player)) {
			reloadDungeonConfig(plugin);
			reloadItensDungeonConfig(plugin);
			reloadLocationConfig(plugin);
			reloadMobConfig(plugin);
			reloadPartyConfig(plugin);
			reloadPortaConfig(plugin);
			reloadLanguageConfig(plugin);
			Files files = new Files(plugin);
			plugin.getListaItens().clear();
			Utilities.getUtilities().createListItem(plugin, files);

			plugin.getServer().getConsoleSender()
					.sendMessage(getLanguageConfig().getString("MessageCommands.reloadConfig").replaceAll("&", "§"));
			return true;
		} else {
			Player player = (Player) sender;

			// haspermission
			if (player.hasPermission("rpgdungeonbr.configuration")) {
				if (args.length == 0) {
					getComands(plugin, player);
					return false;
				} else

				// args = 1
				// ================================================================================
				if (args.length == 1) {

					if (args[0].equalsIgnoreCase("setworld")) {
						plugin.getConfig().set("dungeons.worldSpawn",
								player.getLocation().getWorld().getSpawnLocation().getWorld().getName());

						plugin.saveConfig();
						player.sendMessage("Spawn para saida de dungeons definido!");
						return true;
					}

					if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {

						reloadDungeonConfig(plugin);
						reloadItensDungeonConfig(plugin);
						reloadLanguageConfig(plugin);
						reloadLocationConfig(plugin);
						reloadMobConfig(plugin);
						reloadOtherDrops(plugin);
						reloadPartyConfig(plugin);
						reloadPortaConfig(plugin);
						plugin.reloadConfig();
						plugin.loadEcoMob(plugin);

						Files files = new Files(plugin);
						plugin.getListaItens().clear();
						Utilities.getUtilities().createListItem(plugin, files);
						String msg = getLanguageConfig().getString("MessageCommands.reloadConfig");
						sendMesage(player, msg);
						return true;
					}
					if (args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("ss")) {

						getCommandSpawn(plugin, player);
						return true;
					}

					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {

						getCommandItem(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("d")) {
						getCommandDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("mob") || args[0].equalsIgnoreCase("m")) {
						getCommandMob(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("spawner")) {
						getCommandSpawner(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}

				} else

				// args = 2
				// ================================================================================
				if (args.length == 2) {

					if (args[0].equalsIgnoreCase("spawner")) {
						if (args[1].equalsIgnoreCase("list")) {
							List<String> list = new ArrayList<>();
							SpawnTimer spawn = new SpawnTimer();

							list = spawn.Select();
							if (list.size() > 0) {
								String msg = "";
								for (String s : list) {

									if (msg == "")
										msg = ("§C§l[RPGDungeon]§r§bSpawner Localizado = " + "§4§l [ §r§b" + s
												+ "§4§l ] §r§b");
									else
										msg = (msg + "§4§l [ §r§b" + s + "§4§l ] §r§b") + ",";

								}
								if (msg == "")
									sendMesage(player, "§C§l[RPGDungeon]§r§bSpawner não Localizados!");
								else
									sendMesage(player, msg);
								return true;
							} else {
								sendMesage(player, "§C§l[RPGDungeon]§r§bNão foi localizado spawners definidos!");
								return false;
							}
						} else if (args[1].equalsIgnoreCase("stop")) {
							if (plugin.stopSpawner()) {
								sendMesage(player, "§C§l[RPGDungeon]§r§bSpawners Pausados!");
								return true;
							} else {
								sendMesage(player, "§C§l[RPGDungeon]§r§bSpawners já estão pausados!");
								return false;
							}
						} else if (args[1].equalsIgnoreCase("start")) {
							if (plugin.startSpawner()) {
								sendMesage(player, "§C§l[RPGDungeon]§r§bSpawners Iniciados!");
								return true;
							} else {
								sendMesage(player, "§C§l[RPGDungeon]§r§bSpawners já estão ativos!");
								return false;
							}
						}
					}

					// Comando item
					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {

						// Comando List items
						if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("l")) {
							String listItens = "(";
							reloadItensDungeonConfig(plugin);
							if (getItensDungeonConfig() != null)
								for (String s : getItensDungeonConfig().getConfigurationSection("").getKeys(false)) {
									listItens += s + ", ";
								}
							reloadDungeonConfig(plugin);
							if (getDungeonConfig() != null)
								for (String s : getDungeonConfig().getConfigurationSection("").getKeys(false)) {
									listItens += s + ", ";
								}

							reloadLanguageConfig(plugin);
							if (listItens != null) {
								sendMesage(player, listItens + ")");
								return true;
							} else {
								sendMesage(player, getLanguageConfig().getString("MessageCommands.notItemConfig"));
								return false;
							}

							// comando Give
						} else if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("g")) {
							getCommandGive(plugin, player);
							return false;
						} else {
							getCommandItem(plugin, player);
							return false;
						}
					}
					// spawn mob
					if (args[0].equalsIgnoreCase("mob") || args[0].equalsIgnoreCase("m")) {

						if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("l")) {
							String listItens = "(";

							for (String s : getMobConfig().getConfigurationSection("").getKeys(false)) {
								listItens += s + ", ";
							}

							if (listItens != null) {
								sendMesage(player, "§6§l------MOBS CONFIG------");
								sendMesage(player, listItens + ")");
								return true;
							} else {
								sendMesage(player, getLanguageConfig().getString("MessageCommands.notMobConfig"));
								return false;
							}

						} else {
							getCommandSetMob(plugin, player);
							return false;
						}

					}

					if (args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("ss")) {
						if (getDungeonConfig() != null) {
							if (getDungeonConfig().getConfigurationSection("") != null) {
								if (getDungeonConfig().getConfigurationSection("").getKeys(false).contains(args[1])) {
									Location loc = player.getLocation();

									getDungeonConfig().set(args[1] + ".spawnPoint", loc);

									saveDungeonConfig();
									player.sendMessage("Spawn location save dungeon <" + args[1] + ">!");
									return true;

								} else {
									player.sendMessage("§4[§6DUNGEON§4]§6Dungeon not found : §4(§6" + args[1] + "§4)");
									return false;
								}
							} else {
								player.sendMessage("§4[§6DUNGEON§4]§6There is no dungeon!");
								return false;
							}
						} else {
							player.sendMessage("§4[§6DUNGEON§4]§6There is no dungeon!");
							return false;
						}

					}
					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("dg")) {
						getCommandDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}

				} else

				// args = 3
				// ================================================================================
				if (args.length == 3) {
					// Invocar monstro em um time;

					if (setSpawnTimer(sender, cmd, arg2, args, player))
						return true;
					else if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {

						// Comando List items
						if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("l")) {
							String listItens = "(";

							for (String s : plugin.getListaItens().keySet()) {
								listItens += s + ", ";
							}

							if (listItens != null) {
								sendMesage(player, listItens + ")");
								return true;
							} else {
								sendMesage(player, getLanguageConfig().getString("MessageCommands.notItemConfig"));
								return false;
							}

							// comando Give
						} else if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("g")) {

							if (plugin.getListaItens().containsKey(args[2])) {

								if (getItensDungeonConfig().getConfigurationSection("").getKeys(false)
										.contains(args[2])) {

									player.getInventory()
											.addItem(setTagNBT(plugin, RPGDungeon.listaItens.get(args[2]), args[2]));
									return true;
								}
								player.getInventory().addItem(RPGDungeon.listaItens.get(args[2]));

								return true;
							} else {

								String s = getLanguageConfig().getString("MessageCommands.itemNameDoesNotExist");
								sendMesage(player, s);
								return false;
							}

						} else {
							getCommandItem(plugin, player);
							return false;
						}
					}
					// setDungeon
					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {

						if (getDungeonConfig().contains(args[1])) {
							if (getLocationConfig().contains(args[1])) {
								if (getLocationConfig().getConfigurationSection(args[1]) != null) {
									if (!getLocationConfig().getConfigurationSection(args[1]).getKeys(false)
											.contains(args[2])) {
										getLocationConfig().set(args[1] + "." + args[2] + ".status", false);
										getLocationConfig().set(args[1] + "." + args[2] + ".group", new ArrayList<>());
										getLocationConfig().set(args[1] + "." + args[2] + ".door", 1);
										getLocationConfig().set(args[1] + "." + args[2] + ".listMobs",
												new ArrayList<>());
										getLocationConfig().set(args[1] + "." + args[2] + ".spawnLocation",
												player.getLocation());
										getLocationConfig().set(args[1] + "." + args[2] + ".spawns", new ArrayList<>());
										saveLocationConfig();
										String s = getLanguageConfig().getString("ConfigInstance.spawnInstanceSucess");
										sendMesage(player, s);
										return true;
									} else {
										String s = getLanguageConfig()
												.getString("CreateEntryDungeon.nameInstanceError");
										sendMesage(player, s);
										return false;
									}

								} else {

									getLocationConfig().set(args[1] + "." + args[2] + ".status", false);
									getLocationConfig().set(args[1] + "." + args[2] + ".group", new ArrayList<>());
									getLocationConfig().set(args[1] + "." + args[2] + ".door", 1);
									getLocationConfig().set(args[1] + "." + args[2] + ".listMobs", new ArrayList<>());
									getLocationConfig().set(args[1] + "." + args[2] + ".spawnLocation",
											player.getLocation());
									getLocationConfig().set(args[1] + "." + args[2] + ".spawns", new ArrayList<>());
									saveLocationConfig();
									String s = getLanguageConfig().getString("ConfigInstance.spawnInstanceSucess");
									sendMesage(player, s);
									return true;
								}

							} else {
								String s = getLanguageConfig().getString("CreateEntryDungeon.nameDungeonError");
								sendMesage(player, s);
								return false;
							}
						} else {
							String s = getLanguageConfig().getString("CreateEntryDungeon.nameDungeonError");
							sendMesage(player, s);
							return false;
						}
					}
					// spawn mob
					if (args[0].equalsIgnoreCase("mob") || args[0].equalsIgnoreCase("m")) {

						if (args[1].equalsIgnoreCase("spawn") || args[1].equalsIgnoreCase("s")) {
							Creature craft = new Creature(plugin);
							reloadMobConfig(plugin);
							String listItens = "(";
							if (getMobConfig().getConfigurationSection("").contains(args[2])) {

								craft.setAtribute(plugin, args[2]);
								World world = ((CraftWorld) player.getLocation().getWorld()).getHandle();
								LivingEntity ent = craft.createCreature(world,
										getMobConfig().getString(args[2] + ".type"), player.getLocation(), args[2]);
								List<String> listUUID = new ArrayList<>();
								Utilities.getUtilities().Passager(plugin, ent, args[2], listUUID, world,
										player.getLocation());
								listUUID = null;
								return true;
							} else {
								sendMesage(player,
										getLanguageConfig().getString("MessageCommands.mobNameDoesNotExist"));
								return false;
							}
						} else {
							getCommandSetMob(plugin, player);
							return false;
						}

					}
					// setmobs
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {

						getCommandSetMob(plugin, player);
						return false;
					}
					// dungeon
					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("d")) {
						getCommandDungeon(plugin, player);
						return false;
					}
					// teleporte dungeon
					if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("teleporte")) {

						if (getLocationConfig().getConfigurationSection("").getKeys(false).contains(args[1])) {
							if (getLocationConfig().getConfigurationSection(args[1]).getKeys(false).contains(args[2])) {
								Location loc = (Location) getLocationConfig()
										.get(args[1] + "." + args[2] + ".spawnLocation");
								player.teleport(loc);
								player.sendMessage("Teleportado para " + args[1] + "-" + args[2]);
								return true;
							} else {
								player.sendMessage("instancia não encontrada");
								return false;
							}
						} else {
							player.sendMessage("dungeon não encontrada");
							return false;
						}
					}
					// setporta
					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}

				} else
				// args = 4
				// ================================================================================
				if (args.length == 4) {

					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("d")) {

						getCommandDungeon(plugin, player);
						return false;
					}
					// Capturar Itens
					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
						if (args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("g")) {
							if (getDungeonConfig().getConfigurationSection("").getKeys(false).contains(args[2])) {

								Player target = getPlayer(plugin, args[3]);
								if (target != null) {
									target.getInventory().addItem(itemDungeonConfig(args[2], plugin));
									return true;
								} else {
									player.sendMessage(
											getLanguageConfig().getString("MessageCommands.giveItemToPlayerError"));
									return false;
								}

							} else if (getItensDungeonConfig().getConfigurationSection("").getKeys(false)
									.contains(args[2])) {
								Player target = getPlayer(plugin, args[3]);

								if (target != null) {
									target.getInventory().addItem(itemConfig(args[2], plugin, true));
									return true;
								} else {
									player.sendMessage(
											getLanguageConfig().getString("MessageCommands.giveItemToPlayerError"));
									return false;
								}
							} else {
								player.sendMessage(getLanguageConfig().getString("MessageCommands.itemNameDoesNotExist")
										.replaceAll("&", "§"));
								return false;
							}
						}
						if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("l")) {
							String listItens = "(";

							for (String s : plugin.getListaItens().keySet()) {

								listItens += s + ", ";
							}

							if (listItens != null) {
								sendMesage(player, listItens + ")");
								return true;
							} else {
								sendMesage(player, getLanguageConfig().getString("MessageCommands.notItemConfig"));
								return false;
							}

							// comando Give
						} else {
							getCommandItem(plugin, player);
							return false;
						}
					}

					// setDungeon
					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					// setMob
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}

					// setPorta
					if (args[0].equalsIgnoreCase("setdoor") || args[0].equalsIgnoreCase("setporta")
							|| args[0].equalsIgnoreCase("sp")) {

						reloadPortaConfig(plugin);
						String locationDungeon = "";

						boolean verifica = false;
						boolean key1V = false;

						if (getLocationConfig().getConfigurationSection("") != null) {
							for (String key1 : getLocationConfig().getConfigurationSection("").getKeys(false)) {

								if (key1.equalsIgnoreCase(args[1])) {
									key1V = true;

									for (String key2 : getLocationConfig().getConfigurationSection(key1)
											.getKeys(false)) {
										if (key2.equalsIgnoreCase(args[2])) {
											locationDungeon = key1 + "." + key2;

											verifica = true;
											break;
										}

									}

									if (verifica) {
										break;
									}
								}
							}

							if (verifica & key1V) {

								if (plugin.getLoc1() == null || plugin.getLoc2() == null) {
									player.sendMessage(getLanguageConfig().getString("MessageCommands.selectLocate")
											.replaceAll("&", "§"));
									return false;
								}

								setDoor(locationDungeon, args[3], plugin.getLoc1(), plugin.getLoc2(), plugin);
								sendMesage(player, getLanguageConfig().getString("MessageCommands.doorSaveSucess")
										.replaceAll("<dungeon>", args[1]).replaceAll("<instance>", args[2]));

								return true;
							} else {
								if (!key1V) {
									sendMesage(player, getLanguageConfig().getString("MessageCommands.doorDungeonError")
											.replaceAll("<dungeon>", args[1]).replaceAll("<instance>", args[2]));
									return false;
								} else if (!verifica) {
									sendMesage(player,
											getLanguageConfig().getString("MessageCommands.doorInstanceError")
													.replaceAll("<dungeon>", args[1])
													.replaceAll("<instance>", args[2]));
									return false;
								} else {
									sendMesage(player, getLanguageConfig().getString("MessageCommands.doorErrorCreate")
											.replaceAll("<dungeon>", args[1]).replaceAll("<instance>", args[2]));
									return false;
								}
							}
						} else {
							sendMesage(player, "locationConfig null");
							return false;
						}

					} else {
						getComands(plugin, player);
						return false;
					}

				} else

				// args = 5
				// ================================================================================
				if (args.length == 5) {
					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
						getCommandItem(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						if (getDungeonConfig().contains(args[1])) {
							if (getLocationConfig().contains(args[1])) {
								if (getLocationConfig().getConfigurationSection(args[1]) != null) {
									if (getLocationConfig().getConfigurationSection(args[1]).getKeys(false)
											.contains(args[2])) {

										if (getLocationConfig()
												.getConfigurationSection(args[1] + "." + args[2] + ".spawns") == null) {

											getLocationConfig().set(
													args[1] + "." + args[2] + ".spawns." + args[3] + ".location",
													player.getLocation());

											if (getDungeonConfig().getConfigurationSection(
													args[1].replaceAll("\\s+", "") + ".mobs") == null) {
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".door",
														args[4]);
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".list",
														"");
												saveDungeonConfig();
											} else if (!getDungeonConfig()
													.getConfigurationSection(args[1].replaceAll("\\s+", "") + ".mobs")
													.getKeys(false).contains(args[3])) {
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".door",
														args[4]);
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".list",
														"");
												saveDungeonConfig();
											}

											saveLocationConfig();

											player.sendMessage(getLanguageConfig()
													.getString("ConfigInstance.spawnMobInstanceSucess")
													.replaceAll("&", "§"));
											return true;
										} else if (!getLocationConfig()
												.getConfigurationSection(args[1] + "." + args[2] + ".spawns")
												.getKeys(false).contains(args[3])) {

											getLocationConfig().set(
													args[1] + "." + args[2] + ".spawns." + args[3] + ".location",
													player.getLocation());

											if (!getDungeonConfig()
													.getConfigurationSection(args[1].replaceAll("\\s+", "") + ".mobs")
													.getKeys(false).contains(args[3])) {
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".door",
														args[4]);
												getDungeonConfig().set(
														args[1].replaceAll("\\s+", "") + ".mobs." + args[3] + ".list",
														"");
												saveDungeonConfig();
											}

											saveLocationConfig();

											player.sendMessage(getLanguageConfig()
													.getString("ConfigInstance.spawnMobInstanceSucess")
													.replaceAll("&", "§"));

											return true;
										} else {
											player.sendMessage(
													getLanguageConfig().getString("MessageCommands.nameSpawnMobError")
															.replaceAll("&", "§"));
											return false;
										}
									} else {
										player.sendMessage(getLanguageConfig()
												.getString("MessageCommands.nameInstanceNull").replaceAll("&", "§"));
										return false;
									}
								} else {
									getLocationConfig().set(args[1] + "." + args[2] + ".spawnLocation",
											player.getLocation());
									saveLocationConfig();
									player.sendMessage(getLanguageConfig()
											.getString("ConfigInstance.spawnInstanceSucess").replaceAll("&", "§"));
									return false;
								}
							} else {
								player.sendMessage(getLanguageConfig().getString("CreateEntryDungeon.nameDungeonError")
										.replaceAll("&", "§"));
								return false;
							}
						} else {
							player.sendMessage(getLanguageConfig().getString("CreateEntryDungeon.nameDungeonError")
									.replaceAll("&", "§"));
							return false;
						}
					}

					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("d")) {

						getCommandDungeon(plugin, player);

						return false;
					}
					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}
				} else

				// args = 6
				// ================================================================================
				if (args.length == 6) {

					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
						getCommandItem(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("dungeon") || args[0].equalsIgnoreCase("d")) {

						if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("s")) {
							reloadLocationConfig(plugin);
							if (getLocationConfig().getConfigurationSection("").contains(args[2])) {

								if (getLocationConfig().getConfigurationSection(args[2]).contains(args[3])) {
									Location loc = (Location) getLocationConfig()
											.get(args[2] + "." + args[3] + ".spawnLocation");
									Location locPlayer = player.getLocation();

									int x = (int) (loc.getX() - (loc.getX() % 1));
									int xp = (int) (locPlayer.getX() - (locPlayer.getX() % 1));

									int z = (int) (loc.getZ() - (loc.getZ() % 1));
									int zp = (int) (locPlayer.getZ() - (locPlayer.getZ() % 1));

									if (z == zp) {
										int qtd = 0;
										try {
											qtd = Integer.parseInt(args[4]);
										} catch (Exception e) {
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.msgQtdInstanceNumber")
													.replaceAll("&", "§").replaceAll("<qtdInstancia>", args[4]));
										}
										int block = 0;
										try {
											block = Integer.parseInt(args[5]);
										} catch (Exception e) {
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.msgDistanceInstanceNumber")
													.replaceAll("&", "§").replaceAll("<qtdDistancia>", args[5]));
										}

										if ((x + block) == xp) {

											for (int i = 1; i <= qtd; i++) {

												Location locPlayer2 = new Location(locPlayer.getWorld(), xp,
														locPlayer.getY(), locPlayer.getZ(), locPlayer.getYaw(),
														locPlayer.getPitch());
												xp = xp + block;

												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".status",
														false);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".group",
														new ArrayList<>());
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".door", 1);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".listMobs",
														new ArrayList<>());
												getLocationConfig().set(
														args[2] + "." + (args[3] + i) + ".spawnLocation", locPlayer2);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns",
														new ArrayList<>());
												saveLocationConfig();

												for (String spawn : getLocationConfig()
														.getConfigurationSection(args[2] + "." + args[3] + ".spawns")
														.getKeys(false)) {

													Location locSpawn = ((Location) getLocationConfig().get(args[2]
															+ "." + args[3] + ".spawns." + spawn + ".location"));

													double lx = locSpawn.getX() + (block * i);
													double ly = locSpawn.getY();
													double lz = locSpawn.getZ();
													float lyaw = locSpawn.getYaw();
													float lpitch = locSpawn.getPitch();

													Location locSpawn2 = new Location(locSpawn.getWorld(), lx, ly, lz,
															lyaw, lpitch);

													getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns."
															+ spawn + ".location", locSpawn2);

												}

												try {
													for (String porta : getPortaConfig()
															.getConfigurationSection(args[2] + "." + args[3])
															.getKeys(false)) {
														Location loc1 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location1");
														Location loc2 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location2");

														double l1x = loc1.getX() + (block * i);
														double l1y = loc1.getY();
														double l1z = loc1.getZ();
														float l1yaw = loc1.getYaw();
														float l1pitch = loc1.getPitch();

														double l2x = loc2.getX() + (block * i);
														double l2y = loc2.getY();
														double l2z = loc2.getZ();
														float l2yaw = loc2.getYaw();
														float l2pitch = loc2.getPitch();
														Location loc11 = new Location(loc1.getWorld(), l1x, l1y, l1z,
																l1yaw, l1pitch);
														Location loc22 = new Location(loc2.getWorld(), l2x, l2y, l2z,
																l2yaw, l2pitch);
														String door = getPortaConfig().getString(
																args[2] + "." + args[3] + "." + porta + ".door");

														setDoor((args[2] + "." + args[3] + i), door, loc11, loc22,
																plugin);
														saveLocationConfig();
													}
												} catch (Exception e) {
													reloadLocationConfig(plugin);
													player.sendMessage("Portas não definidas!");
												}

											}
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.allInstancesWereMarked")
													.replaceAll("&", "§").replaceAll("<dungeon>", args[2]));

											saveLocationConfig();
											return true;
										} else

										if ((x - block) == xp) {

											for (int i = 1; i <= qtd; i++) {

												Location locPlayer2 = new Location(locPlayer.getWorld(),
														(double) xp + 0.5, locPlayer.getY(), locPlayer.getZ(),
														locPlayer.getYaw(), locPlayer.getPitch());
												xp = xp - block;

												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".status",
														false);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".group",
														new ArrayList<>());
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".door", 1);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".listMobs",
														new ArrayList<>());
												getLocationConfig().set(
														args[2] + "." + (args[3] + i) + ".spawnLocation", locPlayer2);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns",
														new ArrayList<>());
												saveLocationConfig();

												for (String spawn : getLocationConfig()
														.getConfigurationSection(args[2] + "." + args[3] + ".spawns")
														.getKeys(false)) {

													Location locSpawn = ((Location) getLocationConfig().get(args[2]
															+ "." + args[3] + ".spawns." + spawn + ".location"));

													double lx = locSpawn.getX() - (block * i);
													double ly = locSpawn.getY();
													double lz = locSpawn.getZ();
													float lyaw = locSpawn.getYaw();
													float lpitch = locSpawn.getPitch();

													Location locSpawn2 = new Location(locSpawn.getWorld(), lx, ly, lz,
															lyaw, lpitch);

													getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns."
															+ spawn + ".location", locSpawn2);

												}

												try {
													for (String porta : getPortaConfig()
															.getConfigurationSection(args[2] + "." + args[3])
															.getKeys(false)) {
														Location loc1 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location1");
														Location loc2 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location2");

														double l1x = loc1.getX() - (block * i);
														double l1y = loc1.getY();
														double l1z = loc1.getZ();
														float l1yaw = loc1.getYaw();
														float l1pitch = loc1.getPitch();

														double l2x = loc2.getX() - (block * i);
														double l2y = loc2.getY();
														double l2z = loc2.getZ();
														float l2yaw = loc2.getYaw();
														float l2pitch = loc2.getPitch();
														Location loc11 = new Location(loc1.getWorld(), l1x, l1y, l1z,
																l1yaw, l1pitch);
														Location loc22 = new Location(loc2.getWorld(), l2x, l2y, l2z,
																l2yaw, l2pitch);
														String door = getPortaConfig().getString(
																args[2] + "." + args[3] + "." + porta + ".door");

														setDoor((args[2] + "." + args[3]), door, loc11, loc22, plugin);
														saveLocationConfig();
													}
												} catch (Exception e) {
													reloadLocationConfig(plugin);
													player.sendMessage("Portas não definidas!");
												}

											}
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.allInstancesWereMarked")
													.replaceAll("&", "§").replaceAll("<dungeon>", args[2]));

											saveLocationConfig();
											return true;
										} else {
											player.sendMessage(
													"Não está na mesma linha do outro ponto da primeira instancia!");
										}

										return true;
									}

									if (x == xp) {
										int qtd = 0;
										try {
											qtd = Integer.parseInt(args[4]);
										} catch (Exception e) {
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.msgQtdInstanceNumber")
													.replaceAll("&", "§").replaceAll("<qtdInstancia>", args[4]));
										}
										int block = 0;
										try {
											block = Integer.parseInt(args[5]);
										} catch (Exception e) {
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.msgDistanceInstanceNumber")
													.replaceAll("&", "§").replaceAll("<qtdDistancia>", args[5]));
										}

										if ((z + block) == zp) {
											for (int i = 1; i <= qtd; i++) {
												Location locPlayer2 = new Location(locPlayer.getWorld(),
														locPlayer.getX(), locPlayer.getY(), (double) zp + 0.5,
														locPlayer.getYaw(), locPlayer.getPitch());
												zp = zp + block;

												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".status",
														false);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".group",
														new ArrayList<>());
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".door", 1);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".listMobs",
														new ArrayList<>());
												getLocationConfig().set(
														args[2] + "." + (args[3] + i) + ".spawnLocation", locPlayer2);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns",
														new ArrayList<>());
												saveLocationConfig();

												for (String spawn : getLocationConfig()
														.getConfigurationSection(args[2] + "." + args[3] + ".spawns")
														.getKeys(false)) {

													Location locSpawn = ((Location) getLocationConfig().get(args[2]
															+ "." + args[3] + ".spawns." + spawn + ".location"));

													double lx = locSpawn.getX();
													double ly = locSpawn.getY();
													double lz = locSpawn.getZ() + (block * i);
													float lyaw = locSpawn.getYaw();
													float lpitch = locSpawn.getPitch();

													Location locSpawn2 = new Location(locSpawn.getWorld(), lx, ly, lz,
															lyaw, lpitch);

													getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns."
															+ spawn + ".location", locSpawn2);

												}

												try {
													for (String porta : getPortaConfig()
															.getConfigurationSection(args[2] + "." + args[3])
															.getKeys(false)) {
														Location loc1 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location1");
														Location loc2 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location2");

														double l1x = loc1.getX();
														double l1y = loc1.getY();
														double l1z = loc1.getZ() + (block * i);
														float l1yaw = loc1.getYaw();
														float l1pitch = loc1.getPitch();

														double l2x = loc2.getX();
														double l2y = loc2.getY();
														double l2z = loc2.getZ() + (block * i);
														float l2yaw = loc2.getYaw();
														float l2pitch = loc2.getPitch();
														Location loc11 = new Location(loc1.getWorld(), l1x, l1y, l1z,
																l1yaw, l1pitch);
														Location loc22 = new Location(loc2.getWorld(), l2x, l2y, l2z,
																l2yaw, l2pitch);
														String door = getPortaConfig().getString(
																args[2] + "." + args[3] + "." + porta + ".door");

														setDoor((args[2] + "." + args[3] + i), door, loc11, loc22,
																plugin);
														saveLocationConfig();
													}
												} catch (Exception e) {
													reloadLocationConfig(plugin);
													player.sendMessage("Portas não definidas!");
												}

											}
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.allInstancesWereMarked")
													.replaceAll("&", "§").replaceAll("<dungeon>", args[2]));

											saveLocationConfig();
											return true;
										} else

										if ((z - block) == zp) {

											for (int i = 1; i <= qtd; i++) {

												Location locPlayer2 = new Location(locPlayer.getWorld(),
														locPlayer.getX(), locPlayer.getY(), (double) zp + 0.5,
														locPlayer.getYaw(), locPlayer.getPitch());
												zp = zp - block;

												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".status",
														false);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".group",
														new ArrayList<>());
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".door", 1);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".listMobs",
														new ArrayList<>());
												getLocationConfig().set(
														args[2] + "." + (args[3] + i) + ".spawnLocation", locPlayer2);
												getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns",
														new ArrayList<>());
												saveLocationConfig();

												for (String spawn : getLocationConfig()
														.getConfigurationSection(args[2] + "." + args[3] + ".spawns")
														.getKeys(false)) {

													Location locSpawn = ((Location) getLocationConfig().get(args[2]
															+ "." + args[3] + ".spawns." + spawn + ".location"));

													double lx = locSpawn.getX();
													double ly = locSpawn.getY();
													double lz = locSpawn.getZ() - (block * i);
													float lyaw = locSpawn.getYaw();
													float lpitch = locSpawn.getPitch();

													Location locSpawn2 = new Location(locSpawn.getWorld(), lx, ly, lz,
															lyaw, lpitch);

													getLocationConfig().set(args[2] + "." + (args[3] + i) + ".spawns."
															+ spawn + ".location", locSpawn2);

												}
												try {
													for (String porta : getPortaConfig()
															.getConfigurationSection(args[2] + "." + args[3])
															.getKeys(false)) {
														Location loc1 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location1");
														Location loc2 = (Location) getPortaConfig().get(
																args[2] + "." + args[3] + "." + porta + ".location2");

														double l1x = loc1.getX();
														double l1y = loc1.getY();
														double l1z = loc1.getZ() - (block * i);
														float l1yaw = loc1.getYaw();
														float l1pitch = loc1.getPitch();

														double l2x = loc2.getX();
														double l2y = loc2.getY();
														double l2z = loc2.getZ() - (block * i);
														float l2yaw = loc2.getYaw();
														float l2pitch = loc2.getPitch();
														Location loc11 = new Location(loc1.getWorld(), l1x, l1y, l1z,
																l1yaw, l1pitch);
														Location loc22 = new Location(loc2.getWorld(), l2x, l2y, l2z,
																l2yaw, l2pitch);
														String door = getPortaConfig().getString(
																args[2] + "." + args[3] + "." + porta + ".door");

														setDoor((args[2] + "." + args[3]), door, loc11, loc22, plugin);
														saveLocationConfig();
													}
												} catch (Exception e) {
													reloadLocationConfig(plugin);
													player.sendMessage("Portas não definidas!");
												}

											}
											player.sendMessage(getLanguageConfig()
													.getString("MessageCommands.allInstancesWereMarked")
													.replaceAll("&", "§").replaceAll("<dungeon>", args[2]));

											saveLocationConfig();
											return true;
										} else {

											player.sendMessage(
													"Não está na mesma linha do outro ponto da primeira instancia!");
										}

										return true;
									}

								} else {
									player.sendMessage(getLanguageConfig().getString("MessageCommands.nameInstaceError")
											.replaceAll("&", "§").replaceAll("<dungeo>", args[2]));
									return false;
								}
							} else {
								player.sendMessage(getLanguageConfig().getString("CreateEntryDungeon.nameDungeonError")
										.replaceAll("&", "§").replaceAll("<dungeo>", args[2]));
								return false;
							}

						} else {
							getCommandDungeon(plugin, player);
						}
						return false;
					}

					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						if (getLocationConfig().getConfigurationSection("").getKeys(false).contains(args[1])) {
							if (getLocationConfig().getConfigurationSection(args[1]).getKeys(false).contains(args[2])) {

								int qtd = Integer.parseInt(args[3]);
								int i = 1;
								for (String instance : getLocationConfig().getConfigurationSection(args[1])
										.getKeys(false)) {

									if (!instance.equalsIgnoreCase(args[2])) {
										reloadPortaConfig(plugin);
										for (String door : getPortaConfig()
												.getConfigurationSection(args[1] + "." + args[2]).getKeys(false)) {
											Location loc1 = (Location) getPortaConfig()
													.get(args[1] + "." + args[2] + "." + door + ".location1");
											Location loc2 = (Location) getPortaConfig()
													.get(args[1] + "." + args[2] + "." + door + ".location2");
											String porta = getPortaConfig()
													.getString(args[1] + "." + args[2] + "." + door + ".door");
											int id, data;
											id = getPortaConfig()
													.getInt(args[1] + "." + args[2] + "." + door + ".block.id");
											data = getPortaConfig()
													.getInt(args[1] + "." + args[2] + "." + door + ".block.data");

											Location locNew1 = new Location(loc1.getWorld(),
													(loc1.getX() + (Double.parseDouble(args[3]) * i)),
													(loc1.getY() + (Double.parseDouble(args[4]) * i)),
													(loc1.getZ() + (Double.parseDouble(args[5]) * i)), loc1.getYaw(),
													loc1.getPitch());
											Location locNew2 = new Location(loc2.getWorld(),
													(loc2.getX() + (Double.parseDouble(args[3]) * i)),
													(loc2.getY() + (Double.parseDouble(args[4]) * i)),
													(loc2.getZ() + (Double.parseDouble(args[5]) * i)), loc2.getYaw(),
													loc2.getPitch());

											getPortaConfig().set(args[1] + "." + instance + "." + door + ".location1",
													locNew1);
											getPortaConfig().set(args[1] + "." + instance + "." + door + ".location2",
													locNew2);
											getPortaConfig().set(args[1] + "." + instance + "." + door + ".door",
													porta);
											getPortaConfig().set(args[1] + "." + instance + "." + door + ".block.id",
													id);
											getPortaConfig().set(args[1] + "." + instance + "." + door + ".block.data",
													data);
											savePortaConfig();

										}
										i++;
									}

								}
								player.sendMessage("Portas salvas");
							} else {
								player.sendMessage("Instancia não encontrada");
								return false;
							}
						} else {
							player.sendMessage("Dungeon não encontrada");
							return false;
						}
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}
				} else

				// args = 7
				// ================================================================================

				/// rd setspawn RuinasdeQuimera instance1 15 0 0 0,= Ruina nome
				/// da dungeon, instance1 = nome da isntancia principal, 15
				/// quantidade de vezes de copia, 0= x, 0 = y, 0 = z
				if (args.length == 7) {
					if (args[0].equalsIgnoreCase("setlocation") || args[0].equalsIgnoreCase("sl")) {

						if (getLocationConfig().getConfigurationSection("").getKeys(false).contains(args[1])) {
							if (getLocationConfig().getConfigurationSection(args[1]).getKeys(false).contains(args[2])) {
								reloadLocationConfig(plugin);
								int qtd = Integer.parseInt(args[3]);

								for (int i = 1; i <= qtd; i++) {
									double x, y, z;
									float pitch, yaw;

									Location loc = (Location) getLocationConfig()
											.get(args[1] + "." + args[2] + ".spawnLocation");
									x = loc.getX();
									y = loc.getY();
									z = loc.getZ();
									pitch = loc.getPitch();
									yaw = loc.getYaw();

									Location spawnInstance = new Location(loc.getWorld(),
											x + (i * Integer.parseInt(args[4])), y + (i * Integer.parseInt(args[5])),
											z + (i * Integer.parseInt(args[6])), yaw, pitch);

									for (String spawn : getLocationConfig()
											.getConfigurationSection(args[1] + "." + args[2] + ".spawns")
											.getKeys(false)) {
										Location locSpawn = (Location) getLocationConfig()
												.get(args[1] + "." + args[2] + ".spawns." + spawn + ".location");
										x = locSpawn.getX();
										y = locSpawn.getY();
										z = locSpawn.getZ();
										pitch = locSpawn.getPitch();
										yaw = locSpawn.getYaw();
										Location spawnMob = new Location(locSpawn.getWorld(),
												x + (i * Integer.parseInt(args[4])),
												y + (i * Integer.parseInt(args[5])),
												z + (i * Integer.parseInt(args[6])), yaw, pitch);
										getLocationConfig().set(args[1] + "." + args[2] + i + ".status", false);
										getLocationConfig().set(args[1] + "." + args[2] + i + ".group",
												new ArrayList<>());
										getLocationConfig().set(args[1] + "." + args[2] + i + ".door", 1);
										getLocationConfig().set(args[1] + "." + args[2] + i + ".listMobs",
												new ArrayList<>());
										getLocationConfig().set(args[1] + "." + args[2] + i + ".spawnLocation",
												spawnInstance);
										getLocationConfig().set(
												args[1] + "." + args[2] + i + ".spawns." + spawn + ".location",
												spawnMob);
										saveLocationConfig();
									}
									player.sendMessage("Dungeon: " + args[1] + ", instancia: " + args[2] + i
											+ ", setada na config");
								}

							} else {
								player.sendMessage("Instancia não encontrada");
								return false;
							}
						} else {
							player.sendMessage("Dungeon não encontrada");
							return false;
						}

						return true;
					}
					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
						getCommandItem(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}
				} else

				// args = 8
				// ================================================================================
				if (args.length == 8) {

					if (args[0].equalsIgnoreCase("item") || args[0].equalsIgnoreCase("i")) {
						getCommandItem(plugin, player);
						return false;
					}

					if (args[0].equalsIgnoreCase("setdungeon") || args[0].equalsIgnoreCase("sd")) {
						getCommandSetDungeon(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setmob") || args[0].equalsIgnoreCase("sm")) {
						getCommandSetMob(plugin, player);
						return false;
					}
					if (args[0].equalsIgnoreCase("setporta") || args[0].equalsIgnoreCase("sp")) {
						getCommandSetPorta(plugin, player);
						return false;
					} else {
						getComands(plugin, player);
						return false;
					}
				} else {
					getComands(plugin, player);
					return false;
				}

				// fim
				// ================================================================================
			} else {
				player.sendMessage(getLanguageConfig().getString("MessageCommands.notPermission").replaceAll("&", "§"));
				return false;
			}

		}

	}

	private void getCommandDungeon(RPGDungeon plugin2, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.dungeon"), player, plugin);
	}

	public void getComands(RPGDungeon plugin, Player player) {

		reloadLanguageConfig(plugin);

		sendComandMSg(getLanguageConfig().getStringList("CommandList.list"), player, plugin);
	}

	public void sendComandMSg(List<String> comandos, Player player, RPGDungeon plugin) {
		reloadLanguageConfig(plugin);
		sendMesage(player, getLanguageConfig().getString("Commands.display"));
		for (String s : comandos) {
			sendMesage(player, s);
		}
	}

	public void getCommandItem(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.item"), player, plugin);
	}

	public void getCommandSpawn(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.setspawn"), player, plugin);
	}

	public void getCommandSetDungeon(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.setDungeon"), player, plugin);
	}

	public void getCommandSetMob(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.setMob"), player, plugin);
	}

	public void getCommandMob(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.mob"), player, plugin);
	}

	public void getCommandSetPorta(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.setPorta"), player, plugin);
	}

	public void getCommandGive(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.give"), player, plugin);
	}

	public void getCommandSpawner(RPGDungeon plugin, Player player) {
		reloadLanguageConfig(plugin);
		sendComandMSg(getLanguageConfig().getStringList("Commands.spawner"), player, plugin);
	}

	public boolean setSpawnTimer(CommandSender sender, Command cmd, String arg2, String[] args, Player player) {
		// args[0] = comando
		// args[1] = mob da config mob;
		// args[2] = tempo em segundo

		reloadMobConfig(plugin);
		if (args[0].equalsIgnoreCase("spawner")) {

			if (args[1].equalsIgnoreCase("tp") || args[1].equalsIgnoreCase("teleporte")) {
				SpawnTimer spawn = new SpawnTimer();
				if (spawn.Select(Integer.parseInt(args[2]))) {
					World world = (World) Bukkit.getServer().getWorld(spawn.location_world);
					Location loc = new Location((org.bukkit.World) world, (spawn.location_x + 0.5),
							(spawn.location_y + 0.5), (spawn.location_z + 0.5), 0.0f, 0.0f);
					player.teleport(loc);
					player.sendMessage("§C§l[RPGDungeon]§r§bTeleportado para o spawner de <" + spawn.observacao + ">");
					return true;
				} else {
					player.sendMessage("§C§l[RPGDungeon]§r§bSpawner com ID " + args[2] + " não localizado !");
					return false;
				}

			}

			if (args[1].equalsIgnoreCase("remove")) {
				SpawnTimer spawn = new SpawnTimer();
				if (spawn.Select(Integer.parseInt(args[2]))) {
					plugin.summone.spawnersMap.remove(Bukkit.getServer().getEntity(UUID.fromString(spawn.uuid)));
					String msg = spawn.delete(Integer.parseInt(args[2]));
					if (msg == "")
						player.sendMessage("§C§l[RPGDungeon]§r§bSpawner <" + spawn.observacao + "> foi removido!");
					else
						player.sendMessage("§C§l[RPGDungeon]§r§b" + msg + "!");
					return true;
				} else {
					player.sendMessage("§C§l[RPGDungeon]§r§bSpawner com ID " + args[2] + " não localizado !");
					return false;
				}

			}

			Creature craft = new Creature(plugin);
			if (getMobConfig().getConfigurationSection("").contains(args[1])) {

				craft.setAtribute(plugin, args[1]);
				World world = ((CraftWorld) player.getLocation().getWorld()).getHandle();
				LivingEntity ent = craft.createCreature(world, getMobConfig().getString(args[1] + ".type"),
						player.getLocation(), args[1]);

				SpawnTimer spawn = new SpawnTimer();
				spawn.mob_id = 0;
				spawn.location_x = player.getLocation().getBlockX();
				spawn.location_y = player.getLocation().getBlockY();
				spawn.location_z = player.getLocation().getBlockZ();
				spawn.location_world = player.getLocation().getWorld().getName();
				spawn.observacao = args[1];
				spawn.secunds = Integer.parseInt(args[2]);
				spawn.status = true;
				spawn.uuid = ent.getUniqueId().toString();
				spawn.insert();
				plugin.summone.spawnersMap.put(ent.getUniqueId().toString(),spawn.secunds/* .getUniqueId().toString() */);
				plugin.summone.spawnersLocate.put(ent.getUniqueId().toString(),player.getLocation());
				// plugin.summone.spawners.put(spawn.uuid, spawn.secunds);
				sendMesage(player, "&c[RPGDungeon]Spawner de [" + args[1] + "] definidos com sucesso!");
				return true;
			} else {
				sendMesage(player, getLanguageConfig().getString("MessageCommands.mobNameDoesNotExist"));
				return false;
			}

		}
		return false;
	}

}
