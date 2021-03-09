package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.SummonerRunable;
import com.sufurujhin.rpgdungeon.Utils.Utilities;

public class kill extends Utilities implements Listener {

	private RPGDungeon main;

	public kill(RPGDungeon main) {
		super(main);
		this.main = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void creatureDeathEvent(EntityDeathEvent e) {

		if (!(e.getEntity().getKiller() instanceof Player)) {
			return;
		} else {

			Files files = new Files(main);
			files.reloadItensDungeonConfig(main);
			files.reloadDungeonConfig(main);
			files.reloadMobConfig(main);
			files.reloadLanguageConfig(main);
			
			
			if (files.getItensDungeonConfig() != null) {
				if (e != null) {
					if (e.getEntity() != null) {
													
						Location loc = e.getEntity().getLocation();
						if (e.getEntity().getCustomName() != null) {

							for (String mob : files.getMobConfig().getConfigurationSection("").getKeys(false)) {
								
								String nameEnt = Utilities.getUtilities().getEntityName(e.getEntity());

								String nameMobConfig = files.getMobConfig().getString(mob + ".displayName").replace("&", "§");

								if (nameMobConfig.contains(nameEnt)) {
									Random r = new Random();

									e.getDrops().clear();
									int xp = 0;

									if (main.getConfig().getBoolean("AtivateEconomy"))
										if (main.getConfig().getBoolean("dungeons.economyDungeon")) {
										int moneyQtd = 0;

										if (files.getMobConfig().getConfigurationSection(mob).getKeys(false).contains("money")) {
											moneyQtd = files.getMobConfig().getInt(mob + ".money");

											dropMoney(null, loc, moneyQtd);
										}
									}

									if (files.getMobConfig().getConfigurationSection(mob).getKeys(false).contains("xp")) {
										xp = files.getMobConfig().getInt(mob + ".xp");

									}

									if (xp > 0) {
										e.setDroppedExp(xp);
									} else {
										e.setDroppedExp(xp);
									}

									for (int maxDrop = 0; maxDrop < main.getConfig().getInt("dungeons.maxDropItens"); maxDrop++) {

										List<String> listDrops = files.getMobConfig().getStringList(mob + ".drops");
										
										String itemBruto = "";
										if (listDrops.size() > 0) {
											itemBruto = listDrops.get(r.nextInt(listDrops.size()));

											float chanceDrop = 0f;

											String[] itemConfig = itemBruto.split(" ");

											if (itemConfig[0].equalsIgnoreCase("otherDrops")) {
												files.reloadOtherDrops(main);
												String itemOther = files.getOtherDrops().getStringList("OtherDrops").get(r.nextInt(files.getOtherDrops().getStringList("OtherDrops").size()));
												if (main.listaOtherDrops.containsKey(itemOther)) {
													String[] pathOther = itemOther.split(" ");

													if (pathOther.length == 2) {
														chanceDrop = Float.parseFloat(pathOther[1]);
														if (r.nextFloat() <= chanceDrop) {
															loc.getWorld().dropItem(loc, main.listaOtherDrops.get(itemOther));
														}
														continue;
													}

													if (pathOther.length == 3) {
														chanceDrop = Float.parseFloat(pathOther[2]);
														if (r.nextFloat() <= chanceDrop) {
															loc.getWorld().dropItem(loc, main.listaOtherDrops.get(itemOther));
														} else {
															if (main.getConfig().getBoolean("AtivateEconomy"))
																if (main.getConfig().getBoolean("dungeons.economyDungeon"))
															dropMoedas(e, loc);
														}

													}
												}
												continue;
											}
											if (main.getListaItens().containsKey(itemConfig[0])) {
					
												ItemStack item = main.getListaItens().get(itemConfig[0]);
												if(files.getItensDungeonConfig().getConfigurationSection("").contains(itemConfig[0])){
													if(files.getItensDungeonConfig().getConfigurationSection(itemConfig[0]).contains("options")){
														item = Utilities.getUtilities().setTagNBT(main, item, itemConfig[0]);
													}	
												}

												// drop item salvo na listaItens
												if (itemConfig.length == 1) {
													loc.getWorld().dropItem(loc, item);
													continue;
												} else {
													if (main.getConfig().getBoolean("AtivateEconomy"))
														if (main.getConfig().getBoolean("dungeons.economyDungeon"))
													dropMoedas(e, loc);
												}

												if (itemConfig.length == 2) {
													chanceDrop = Float.parseFloat(itemConfig[1]);
													if (r.nextFloat() <= chanceDrop) {

														loc.getWorld().dropItem(loc, item);
													} else {

														dropMoedas(e, loc);
													}

												}
												continue;
											}

											int qtd = 1;
																						
											
											String id = itemConfig[0].toUpperCase();
											try {
												chanceDrop = 1.0f;
												if (itemConfig[0].length() == 2)
													chanceDrop = Float.parseFloat(itemConfig[1]);

												if (itemConfig[0].length() == 3)
												{
													chanceDrop = Float.parseFloat(itemConfig[2]);
													qtd = Integer.parseInt(itemConfig[1]);
												}
												ItemStack item = new ItemStack(Material.getMaterial(id), qtd);
												
												
												if (r.nextFloat() <= chanceDrop) {

													loc.getWorld().dropItem(loc, item);
													
													
												} else {

													dropMoedas(e, loc);
												}

											} catch (Exception e2) {
												System.out.println("ATENÇÃO=" + e2.getMessage() + "-------------");
											}

										}
									}
									break;
								}

							}
							
						} else {
							if (main.getConfig().getBoolean("AtivateEconomy"))
								if (main.getConfig().getBoolean("dungeons.economyDungeon"))
							dropMoedas(e, loc);
						}

						List<String> lista = new ArrayList<>();
						reloadDungeonConfig(main);
						if (getDungeonConfig().getConfigurationSection("") != null) {
							for (String dungeon : getDungeonConfig().getConfigurationSection("").getKeys(false)) {
								lista.add(dungeon);
							}
						}
						if (lista.size() > 0) {
							Random r = new Random();

							String dg = lista.get(r.nextInt(lista.size()));
							float chance = Float.parseFloat("" + getDungeonConfig().getDouble(dg + ".dropRate"));
							if (r.nextFloat() <= chance) {
								loc.getWorld().dropItem(loc, main.getListaItens().get(dg));
							}
						}
					}
				}
			}
		}
	}

	private void dropMoedas(EntityDeathEvent e, Location loc) {
		if (main.getConfig().getBoolean("dungeons.economyMobs")) {
			main.loadEcoMob(main);
			String enti = e.getEntityType().toString().toLowerCase();
			for (String mob : main.getEcoMob().getConfigurationSection("EconomiaMob").getKeys(false)) {
				if (enti.contains(mob.toLowerCase())) {
					int money = main.getEcoMob().getInt("EconomiaMob." + mob + ".money");
					dropMoney(null, loc, money);
					break;
				}
			}

		}
	}

	public void dropMoney(ItemStack item, Location loc, int money) {

		String dispalyName = main.getConfig().getString("dungeons.moneyCoin").replace("&", "§");
		
		item = new ItemStack(Material.GOLD_NUGGET, 1);
		ItemMeta mi = item.getItemMeta();
		mi.setDisplayName(dispalyName +" x"+money);
		item.setItemMeta(mi);
		loc.getWorld().dropItem(loc, item);
		
//		if (money != 0) {
//			if (money > 81) {
//				int i = money / 81;
//				int resto = money % 81;
//				if (i > 64) {
//					int quant = i / 64;
//					int restoI = i % 64;
//					for (int c = 0; c < quant; c++) {
//						
//					}
//					if (restoI > 0) {
//						item = new ItemStack(Material.GOLD_BLOCK, restoI);
//						ItemMeta mi = item.getItemMeta();
//						mi.setDisplayName(dispalyName);
//						item.setItemMeta(mi);
//						loc.getWorld().dropItem(loc, item);
//					}
//
//				} else {
//					item = new ItemStack(Material.GOLD_BLOCK, i);
//					ItemMeta mi = item.getItemMeta();
//					mi.setDisplayName(dispalyName);
//					item.setItemMeta(mi);
//					loc.getWorld().dropItem(loc, item);
//				}
//				if (resto > 64) {
//					item = new ItemStack(Material.GOLD_NUGGET, 64);
//					ItemMeta mi = item.getItemMeta();
//					mi.setDisplayName(dispalyName);
//					item.setItemMeta(mi);
//					loc.getWorld().dropItem(loc, item);
//					resto -= 64;
//				}
//				if (resto > 0) {
//					item = new ItemStack(Material.GOLD_NUGGET, resto);
//					ItemMeta mi = item.getItemMeta();
//					mi.setDisplayName(dispalyName);
//					item.setItemMeta(mi);
//					loc.getWorld().dropItem(loc, item);
//				}
//				money = 0;
//			}
//			if (money > 0) {
//				if (money > 64) {
//					item = new ItemStack(Material.GOLD_NUGGET, 64);
//					ItemMeta mi = item.getItemMeta();
//					mi.setDisplayName(dispalyName);
//					item.setItemMeta(mi);
//					loc.getWorld().dropItem(loc, item);
//					money -= 64;
//				}
//				item = new ItemStack(Material.GOLD_NUGGET, money);
//				ItemMeta mi = item.getItemMeta();
//				mi.setDisplayName(dispalyName);
//				item.setItemMeta(mi);
//				loc.getWorld().dropItem(loc, item);
//
//			}
//		}

	}

	public ItemStack getItem(String id, int data, String qtd) {

		ItemStack item = new ItemStack(Material.getMaterial(id), (short) data);
		return item;
	}
}
