package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.Creature;
import com.sufurujhin.rpgdungeon.Utils.Utilities;

import net.minecraft.server.v1_16_R3.World;

public class Dungeon implements Listener {

	private Player player;
	private String name;
	private Sign sign;
	private ItemStack item;
	private String displayMenu;
	private RPGDungeon main;
	private Files files;

	public Dungeon(RPGDungeon main) {
		this.main = main;
		files = new Files(this.main);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void interactEvent(PlayerInteractEvent event) {

		setPlayer(event.getPlayer());

		if (event.getClickedBlock() != null) {

			if ("BIRCH_WALL_SIGN WARPED_WALL_SIGN CRIMSON_WALL_SIGN DARK_OAK_WALL_SIGN JUNGLE_WALL_SIGN SPRUCE_WALL_SIGN OAK_WALL_SIGN ACACIA_WALL_SIGN"
					.contains(event.getClickedBlock().getType().toString().toUpperCase())) {

				setSign((Sign) event.getClickedBlock().getState());

				if (getSign() != null) {

					String nameSignConfig = ChatColor.RED + getMain().getConfig().getString("dungeons.sign");

					if (signType().contains(nameSignConfig)) {
						
						files.reloadLanguageConfig(getMain());
						files.reloadLocationConfig(getMain());

						setName(Utilities.getUtilities()
								.getLinesSpace(Utilities.getUtilities().getLinesPlacaString(sign), true));

						if (checkDungeon()) {

							if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
								if (!event.getPlayer().hasPermission("rpgdungeonbr."+getName()))
								{
									event.getPlayer().sendMessage("[§6§lDungeon]§bSem permissão para entrar na Dungeon!");
									return;
								}
								
								setItem(Utilities.getUtilities().itemDungeonConfig(getName(), getMain()));

								if (checkInventory()) {

									setDisplayMenu(files.getLanguageConfig().getString("ConfigButtons.resquest")
											.replaceAll("&", "§"));
									openMenu();

								} else {
										Utilities.getUtilities().sendMesage(player,files.getLanguageConfig().getString("MessageDungeon.notHaveKey").replaceAll("<key>", getName()));
								}
							} else {
								// use o click direiro
								Utilities.getUtilities().sendMesage(player,
										files.getLanguageConfig().getString("MessageDungeon.useClickLeft"));
							}
						} else {

						}
						// ------------------Fim do da função
						// [Dungeon]---------------------
					} else if (signType().contains(ChatColor.RED + "[Door]")) {
						String locationDungeon = "";
						String dungeonLocation = "";
						boolean verifica = false;
						files.reloadLocationConfig(getMain());
						files.reloadLanguageConfig(getMain());
						List<String> listUUID = new ArrayList<>();
						String bossUuid = "";	
						if (files.getLocationConfig() != null) 
						if (files.getLocationConfig().getConfigurationSection("").getKeys(false) != null) {

							for (String key1 : files.getLocationConfig().getConfigurationSection("").getKeys(false)) {
	
								for (String key2 : files.getLocationConfig().getConfigurationSection(key1).getKeys(false)) {

									if (files.getLocationConfig().getStringList(key1 + "." + key2 + ".group") != null) {

										if (files.getLocationConfig().getStringList(key1 + "." + key2 + ".group").contains(player.getUniqueId().toString())
												&& files.getLocationConfig().getBoolean(key1 + "." + key2 + ".status")) {
											listUUID = files.getLocationConfig().getStringList(key1 + "." + key2 + ".listMobs");
											locationDungeon = key1 + "." + key2;
											dungeonLocation = key1;
											verifica = true;

											break;
										}
									}
								}
								if (verifica) {
									break;
								}
							}
						}
						if (locationDungeon != null & locationDungeon != "") {

							int number = Integer.parseInt(sign.getLine(1));
							int numberDoor = files.getLocationConfig().getInt(locationDungeon + ".door");
							if (number == numberDoor) { 

								if (!Utilities.getUtilities().getEntitys(main, player.getWorld(),
										files.getLocationConfig().getStringList(locationDungeon + ".listMobs"))) {

									summonCreature(listUUID, bossUuid, dungeonLocation, locationDungeon, number);
								} else {
	
									Utilities.getUtilities().sendMesage(player,
											files.getLanguageConfig().getString("CreateEntryDungeon.doorSpawnMob"));
								}
							}
						} else {
							if (!main.getConfig().getConfigurationSection("dungeons").contains("worldSpawn")){
								if (player.hasPermission("rpgdungeon.configuration"))
								player.sendMessage("§c§lteleporte de saída da Dungeon n foi denifido!"); 
								else
									files.getLanguageConfig().getString("MessageDungeon.YouNotAnyDungeon").replaceAll("&", "§");
								return;
							}
							Location locSpawn = main.getServer().getWorld(main.getConfig().getString("dungeons.worldSpawn")).getSpawnLocation();
							
							Utilities.getUtilities().teleport(player, locSpawn, files.getLanguageConfig().getString("MessageDungeon.YouNotAnyDungeon"));
						}
						// Fim da função [Door]
					} else if (signType().contains(ChatColor.RED + "[Exit]")) {
						files.reloadLocationConfig(main);
						String locationDungeon = "";
						String dungeon = "";
						boolean verifica = false;
						if (files.getLocationConfig().getConfigurationSection("") != null) {
							for (String key1 : files.getLocationConfig().getConfigurationSection("").getKeys(false)) {

								for (String key2 : files.getLocationConfig().getConfigurationSection(key1)
										.getKeys(false)) {

									if (files.getLocationConfig().getStringList(key1 + "." + key2 + ".group") != null) {
										if (files.getLocationConfig().getStringList(key1 + "." + key2 + ".group")
												.contains(player.getUniqueId().toString())
												&& files.getLocationConfig()
														.getBoolean(key1 + "." + key2 + ".status")) {

											locationDungeon = key1 + "." + key2;
											dungeon = key1;
											verifica = true;
											break;
										}
									}
								}

								if (verifica) {
									Entity et = Utilities.getUtilities().exitDungeon(main, locationDungeon, player);
									if (et == null) {
										Utilities.getUtilities().despawnMob(main, locationDungeon, player, dungeon);
										locationDungeon = locationDungeon.replace(".", ":");
										String[] path = locationDungeon.split(":");
										Utilities.getUtilities().sendMesage(player,
												files.getLanguageConfig().getString("MessageDungeon.dungeonSucess")
														.replaceAll("<dungeon>", "" + path[0]));
									} else {
										files.reloadLanguageConfig(main);
										Utilities.getUtilities().sendMesage(player,
												files.getLanguageConfig().getString("MessageDungeon.bossNotDeath")
														.replaceAll("<boss>", "" + et.getCustomName()));
									}

									break;
								}
							}
						}

					}

				}
			}

		}
	}

	private void summonCreature(List<String> listUUID, String bossUuid, String dungeonLocation, String locationDungeon,
			int number) {

		List<String> spawns = new ArrayList<>();

		files.reloadDungeonConfig(main);
		for (String key1 : files.getDungeonConfig().getConfigurationSection(dungeonLocation + ".mobs").getKeys(false)) {
			if (Integer.parseInt(
					files.getDungeonConfig().getString(dungeonLocation + ".mobs." + key1 + ".door")) == number) {
				spawns.add(key1);

			}
			/*
			 * if(Integer.parseInt(getDungeonConfig(
			 * ).getString(dungeonLocation+".mobs."+ key1+".door")) > number){
			 * break; }
			 */
		}

		for (String spawn : spawns) {
			Location loc = (Location) files.getLocationConfig().get(locationDungeon + ".spawns." + spawn + ".location");
			World nmsworld = ((CraftWorld) loc.getWorld()).getHandle();

			files.reloadMobConfig(main);
			List<String> listMobs = new ArrayList<String>();

			for (String mob : files.getDungeonConfig().getStringList(dungeonLocation + ".mobs." + spawn + ".list")) {
				listMobs.add(mob);
			}

			for (String mob : listMobs) {

				String path[] = mob.split(":");

				if (path.length == 2) {
					if (path[1].equals("final")) {
						Creature craft = new Creature(main);
						LivingEntity Ent =  craft.createCreature(nmsworld, getMobType(path[0], main), loc, path[0]);
						bossUuid = Ent.getUniqueId().toString();
						listUUID.add(bossUuid);
						Utilities.getUtilities().Passager(main,Ent,path[0],listUUID,nmsworld,loc);
					} else if (path[1].matches("[1-9]{1,3}")) {
						files.reloadMobConfig(main);

						if (Utilities.getUtilities().getMobConfig().contains(path[0])) {

							int qtd = Integer.parseInt(path[1]);

							for (int i = 0; i < qtd; i++) {
								Creature craft = new Creature(main);
								LivingEntity Ent = craft.createCreature(nmsworld, getMobType(path[0], main), loc, path[0]);
								listUUID.add(Ent.getUniqueId().toString());
								Utilities.getUtilities().Passager(main,Ent,path[0],listUUID,nmsworld,loc);
							}

						}
					}

				} else {

					if (Utilities.getUtilities().getMobConfig().contains(mob)) {

						Creature craft = new Creature(main);

						listUUID.add(craft.createCreature(nmsworld, getMobType(mob, main), loc, path[0]).getUniqueId()
								.toString());

					}
				}

			}

		}
		Utilities.getUtilities().openDoor("AIR", Utilities.getUtilities().getLoc1(locationDungeon, main, number), Utilities.getUtilities().getLoc2(locationDungeon, main, number));
		files.getLocationConfig().set(locationDungeon + ".door",
				(files.getLocationConfig().getInt(locationDungeon + ".door") + 1));
		files.getLocationConfig().set(locationDungeon + ".listMobs", listUUID);
		files.getLocationConfig().set(locationDungeon + ".lastBoss", bossUuid);
		files.saveLocationConfig();

	}

	public boolean checkInventory() {
		for (ItemStack itemStack : getPlayer().getInventory()) {
			if (itemStack != null & getItem()!=null) {
				if (itemStack.getItemMeta() != null) {
					if (itemStack.getItemMeta().getDisplayName() != null) {
						if (itemStack.getItemMeta().getDisplayName().equals(getItem().getItemMeta().getDisplayName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean checkDungeon() {
		if (getFiles().getLocationConfig().getConfigurationSection("").contains(getName())) {
			return true;
		}
		return false;
	}

	public String signType() {
		return getSign().getLine(0);
	}

	public void openMenu() {
		getPlayer().openInventory(
				Utilities.getUtilities().getConfimation(getMain(), getPlayer(), getItem(), getDisplayMenu()));
	}

	public String getMobType(String mob, RPGDungeon plugin) {
		getFiles().reloadMobConfig(plugin);
		String mobType = Utilities.getUtilities().getMobConfig().getString(mob + ".type");

		return mobType.toUpperCase();
	}

	@EventHandler
	public void useKey(BlockPlaceEvent e) {

		if (e != null) {
			if (e.getBlock().getType() == Material.TRIPWIRE_HOOK) {
				if (e.getItemInHand().getItemMeta() != null) {
					if (e.getItemInHand().getItemMeta().getDisplayName() != null) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sign getSign() {
		return sign;
	}

	public void setSign(Sign sign) {
		this.sign = sign;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public String getDisplayMenu() {
		return displayMenu;
	}

	public void setDisplayMenu(String displayMenu) {
		this.displayMenu = displayMenu;
	}

	public RPGDungeon getMain() {
		return main;
	}

	public void setMain(RPGDungeon main) {
		this.main = main;
	}

	public Files getFiles() {
		return files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}
//	@EventHandler
//    public void onTabComplete (TabCompleteEvent e) {
//		String[] args = e.getBuffer().split(" ");
//		if (args[0].equalsIgnoreCase("/rd")) {
//			
//			List<String> rdlist = new ArrayList<String>();
//			if (args.length == 0) {
//				rdlist = getTab1();
//				Collections.sort(rdlist);
//				e.setCompletions(rdlist) ;
//			} else 
//				getTab1SetDungeon(args, rdlist);
//			
//			e.setCompletions(rdlist) ;
//			return;
//		}
//		if (e.getSender().getName().equalsIgnoreCase("dungeon")) {
//			ArrayList<String> rdDungeon = new ArrayList<String>();
//			return;
//
//		}
//		return ;
//
//	}
//
//	private void getTab1SetDungeon(String[] args, List<String> rdlist) {
//		if (args.length < 2)
//			return;
//		if (args[1].equalsIgnoreCase("setdungeon")){
//			files.reloadDungeonConfig(main);
//			if (args.length ==3){
//				for (String s : files.getDungeonConfig().getConfigurationSection("").getKeys(false))
//					rdlist.add(s);
//			
//				Filtrar(rdlist,args[2]);
//			}
//			
//		} 
//		else if (args[1].equalsIgnoreCase("setMob")){
//			rdlist.add("");	
//		}
//		else if (args[1].equalsIgnoreCase("setPorta")){
//			rdlist.add("");	
//		}
//		else if (args[1].equalsIgnoreCase("item")){
//			files.reloadItensDungeonConfig(main);
//			for (String s : files.getItensDungeonConfig().getConfigurationSection("").getKeys(false))
//			rdlist.add("give "+s);	
//		}
//		else if (args[1].equalsIgnoreCase("dungeon")){
//			rdlist.add("set");	
//			rdlist.add("list");	
//		}
//		else if (args[1].equalsIgnoreCase("mob")){
//			files.reloadMobConfig(main);
//			if (args.length == 4){
//			for (String s : files.getMobConfig().getConfigurationSection("").getKeys(false))
//			rdlist.add("spawn "+s);	
//			Filtrar(rdlist,args[2]);
//			}
//		}
//		else if (args[1].equalsIgnoreCase("setspawn")){
//			rdlist.add("");	
//		}
//		else if (args[1].equalsIgnoreCase("spawner")){
//			rdlist.add("");	
//		}
//	}
//
//	private void Filtrar(List<String> rdlist, String filter) {
//		List<String> filtro = new ArrayList<>();
//		for (String entry : rdlist) {
//		    // filter values that start with B
//		    if (entry.startsWith(filter)) {
//		    	filtro.add(entry);
//		    }
//		}
//		rdlist = filtro;
//	}

	
}
