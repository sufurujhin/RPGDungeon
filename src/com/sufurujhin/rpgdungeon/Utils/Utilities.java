package com.sufurujhin.rpgdungeon.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;

public class Utilities extends Files {

	private static Utilities utilities;

	public static Utilities getUtilities() {
		return utilities;
	}

	@SuppressWarnings("static-access")
	public Utilities(RPGDungeon rpg) {
		super(rpg);
		this.utilities = this;
		// TODO Auto-generated constructor stub
	}

	public void teleport(Player player, Location loc, String msg) {

		player.teleport(loc);
		player.sendMessage(msg.replaceAll("&", "§"));
	}

	public String getLinesPlacaString(Sign placa) {

		return (placa.getLine(1) + " " + placa.getLine(2) + " " + placa.getLine(3));

	}

	public String getLinesSpace(String lines, boolean status) {
		if (status) {
			return lines.replaceAll("\\s+", "");
		}
		return lines.replaceAll("\\s+", " ");
	}

	public Player getPlayer(RPGDungeon rpg, String nome) {

		for (Player player : rpg.getServer().getOnlinePlayers()) {
			if (player.getName().toString().equalsIgnoreCase(nome)) {
				return player;
			}
			if (player.getUniqueId().toString().equalsIgnoreCase(nome)) {
				return player;
			}
		}

		return null;
	}
	//pega o nome do mob customizado
	public String getEntityName(Entity ent) {
			String[] path = ent.getCustomName().split(" --- ");
			return path[0];
	}
	//barra de vida do mob da dungeon
	public void setEntityName(Entity ent, Double vidaAtual, Double vidaTotal) {
		if (ent.getCustomName() != null) {
			String path = getEntityName(ent);
			if (vidaAtual <= 0)
				vidaAtual = 0.00;
			double d = (vidaTotal / 3.0);
			if (vidaAtual >= (d + d))
				ent.setCustomName(path + " --- §a("+Bar(vidaAtual,vidaTotal,"a")+")" );
			else if ((vidaAtual < (d + d)) & (vidaAtual >= d))
				ent.setCustomName(path + " --- §e(" +Bar(vidaAtual,vidaTotal,"e")+")");
			else
				ent.setCustomName(path + " --- §4(" +Bar(vidaAtual,vidaTotal,"4")+")");
		}
	}
	public String Bar(Double vida, Double Max, String Cor){
		return ("§"+Cor+"§l" +(vida-(vida%1)) + "/" + (Max-(Max%1)));
	}
	// criar uma lista de items, salvo em memoria toda vez q o server é ligado
	public void createListItem(RPGDungeon rpg, Files files) {
		files.reloadItensDungeonConfig(rpg);
		files.reloadDungeonConfig(rpg);
		if (files.getItensDungeonConfig().getConfigurationSection("") != null) {
			for (String key : files.getItensDungeonConfig().getConfigurationSection("").getKeys(false)) {

				String displayName = files.getItensDungeonConfig().getString(key + ".name");
				String idData = files.getItensDungeonConfig().getString(key + ".itemId");

				List<String> lores = files.getItensDungeonConfig().getStringList(key + ".lores");

				rpg.getListaItens().put(key, craftItem(rpg, key, displayName, idData, lores, true));

			}
		}

		if (files.getDungeonConfig() != null) {
			if (files.getDungeonConfig().getConfigurationSection("") != null) {
				for (String key : files.getDungeonConfig().getConfigurationSection("").getKeys(false)) {

					String displayName = files.getDungeonConfig().getString(key + ".name");
					String idData = files.getDungeonConfig().getString(key + ".itemId");

					List<String> lores = files.getDungeonConfig().getStringList(key + ".lores");

					rpg.getListaItens().put(key, craftItem(rpg, key, displayName, idData, lores, false));

				}

			}
		}

		if (files.getOtherDrops() != null) {
			if (files.getOtherDrops().getStringList("OtherDrops") != null) {
				for (String key : files.getOtherDrops().getStringList("OtherDrops")) {

					String[] path = key.split(" ");

					int qtd = 1;

					String idUp = path[0].toUpperCase();

					ItemStack item = new ItemStack(Material.getMaterial(idUp), qtd);

					RPGDungeon.listaOtherDrops.put(key, item);

				}

			}
		}
	}

	// crafta um item
	@SuppressWarnings("deprecation")
	public ItemStack craftItem(RPGDungeon plugin, String keyItem, String displayName, String idData, List<String> lores,
			boolean nbt) {

		ItemStack key = new ItemStack(Material.getMaterial(idData));
		ItemMeta itemMeta = key.getItemMeta();
		itemMeta.setDisplayName(displayName.replaceAll("&", "§"));
		itemMeta.setLore(getLores(lores));

		List<String> listEnch = new ArrayList<>();
		boolean dg = false;
		reloadItensDungeonConfig(plugin);
		if (getItensDungeonConfig().getConfigurationSection("").getKeys(false).contains(keyItem)) {

			if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("enchants")) {
				listEnch = getItensDungeonConfig().getStringList(keyItem + ".enchants");

				for (String ench : listEnch) {
					String part[] = ench.split(":");
					int value = Integer.parseInt(part[1]);
					itemMeta.addEnchant(Enchantment.getByName(part[0]), value, true);

				}

			}

			if (getItensDungeonConfig().getConfigurationSection(keyItem).contains(".options.color")) {
				String color = getItensDungeonConfig().getString(keyItem + ".options.color");

				LeatherArmorMeta lea = (LeatherArmorMeta) itemMeta;
				String[] path = color.split(",");
				if (path.length == 1) {
					if (path[0].matches("[0-9]{1,3}")) {
						lea.setColor(Color.fromRGB(Integer.parseInt(path[0])));
					}
				} else {
					if (path[0].matches("[0-9]{1,3}")) {
						lea.setColor(Color.fromRGB(Integer.parseInt(path[0])));
						lea.setColor(Color.fromRGB(Integer.parseInt(path[0]), Integer.parseInt(path[1]),
								Integer.parseInt(path[2])));
					}
				}
				key.setItemMeta(lea);
				dg = true;
			}

		}
		if (!dg) {

			key.setItemMeta(itemMeta);

		}

		return key;

	}

	public ItemStack setTagNBT(RPGDungeon plugin, ItemStack key, String keyItem) {
		reloadItensDungeonConfig(plugin);
		if (keyItem != null || keyItem != "") {
			if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options")) {

				double health = 0;
				double attack = 0;
				double attack_speed = 0;
				double moveSpeed = 0;
				int data = getItensDungeonConfig().getInt(keyItem + ".slot");
				double defense = 0;
				double chanceUnbreakable = 0.0;
				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.attack")) {
					attack = getItensDungeonConfig().getDouble(keyItem + ".options.attack");
				}

				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.health")) {
					health = getItensDungeonConfig().getDouble(keyItem + ".options.health");
				}

				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.attack_speed")) {
					attack_speed = getItensDungeonConfig().getDouble(keyItem + ".options.attack_speed");
				}
				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.moveSpeed")) {
					moveSpeed = getItensDungeonConfig().getDouble(keyItem + ".options.moveSpeed");
				}
				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.defense")) {
					defense = getItensDungeonConfig().getDouble(keyItem + ".options.defense");

				}
				if (getItensDungeonConfig().getConfigurationSection(keyItem).contains("options.unbreakable")) {
					chanceUnbreakable = getItensDungeonConfig().getDouble(keyItem + ".options.unbreakable");

				}
				CraftTagNBT nbtTag = new CraftTagNBT(key, health, attack, attack_speed, moveSpeed, data, defense,
						chanceUnbreakable);
				nbtTag.setTagNBT();
				key = nbtTag.getItemTagNBT();
			}
		}
		return key;
	}

	public String getNameDUngeon(List<String> name) {

		String dg = name.get(0).replaceAll("\\s+", "");
		dg += name.get(0).replaceAll("\\s+", "");
		dg += name.get(0).replaceAll("\\s+", "");

		return dg;
	}

	public String getNomeDg(String dgName) {
		return dgName.replaceAll("§1", "").replaceAll("§2", "").replaceAll("§3", "").replaceAll("§4", "")
				.replaceAll("§5", "").replaceAll("§6", "").replaceAll("§7", "").replaceAll("§8", "")
				.replaceAll("§9", "").replaceAll("§a", "").replaceAll("§b", "").replaceAll("§c", "")
				.replaceAll("§l", "").replaceAll("§k", "").replaceAll("§l", "").replaceAll("§n", "")
				.replaceAll("§o", "").replaceAll("§m", "").replaceAll("§d", "").replaceAll("§e", "")
				.replaceAll("§r", "");
	}

	public Location getLocation(String nome, RPGDungeon plugin, String intanceOuSpawn, Player player) {
		reloadLocationConfig(plugin);
		if (intanceOuSpawn.equalsIgnoreCase("instance")) {
			String nomeDg = getNomeDg(nome);

			if (getLocationConfig().getConfigurationSection(nomeDg) != null) {
				// verifica se esta em grupo
				for (String instance : getLocationConfig().getConfigurationSection(nomeDg).getKeys(false)) {

					if (getLocationConfig().getString(nomeDg + "." + instance + ".group") != null) {
						if (getLocationConfig().getString(nomeDg + "." + instance + ".group")
								.contains(player.getUniqueId().toString())) {
							return (Location) getLocationConfig().get(nomeDg + "." + instance + ".spawnLocation");

						}
					}
				}

				// retorna localidade.
				for (String instance : getLocationConfig().getConfigurationSection(nomeDg).getKeys(false)) {
					if (!getLocationConfig().getBoolean(nomeDg + "." + instance + ".status")) {
						List<String> listMember = new ArrayList<>();
						reloadPartyConfig(plugin);
						boolean verificaPt = false;
						if (getPartyConfig().getConfigurationSection("Groups") != null) {
							for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
								if (getPartyConfig().getStringList("Groups." + key + ".members")
										.contains(player.getUniqueId().toString())) {
									listMember = getPartyConfig().getStringList("Groups." + key + ".members");
									verificaPt = true;
								}
							}
						}

						if (!verificaPt) {
							listMember.add(player.getUniqueId().toString());
						}

						getLocationConfig().set(nomeDg + "." + instance + ".group", listMember);
						getLocationConfig().set(nomeDg + "." + instance + ".status", true);
						saveLocationConfig();
						return (Location) getLocationConfig().get(nomeDg + "." + instance + ".spawnLocation");

					}
				}

			}
		}
		return null;

	}

	public String getDisplayName(String displayName) {

		return displayName.replaceAll("\\s+", " ");
	}

	public void sendMesage(Player player, String msg) {
		if (player.isOnline()) {
			player.sendMessage(msg.replaceAll("&", "§"));
		}
	}

	public List<String> getLores(List<String> lores) {
		List<String> lores2 = new ArrayList<>();
		for (String s : lores) {
			lores2.add(s.replaceAll("&", "§"));
		}

		return lores2;
	}

	public Inventory getConfimation(RPGDungeon plugin, Player p, ItemStack item, String displayName) {
		reloadLanguageConfig(plugin);

		Inventory fakeInventory = Bukkit.getServer().createInventory(null, 9, displayName);

		String idYes, idNo;
		String sim, nao;

		sim = getLanguageConfig().getString("ConfigButtons.yesButton");
		nao = getLanguageConfig().getString("ConfigButtons.noButton");

		idYes = plugin.getConfig().getString("dungeons.configButtons.yesButton.id").toUpperCase();

		idNo = plugin.getConfig().getString("dungeons.configButtons.noButton.id").toUpperCase();

		fakeInventory.setItem(3, getitem(new ItemStack(Material.getMaterial(idYes)), sim, p));

		fakeInventory.setItem(5, getitem(new ItemStack(Material.getMaterial(idNo)), nao, p));
		fakeInventory.setItem(0, item);

		return fakeInventory;
	}

	public ItemStack getitem(ItemStack item, String display, Player p) {

		ItemMeta itemMeta = item.getItemMeta();

		itemMeta.setDisplayName(display.replaceAll("&", "§"));

		item.setItemMeta(itemMeta);
		return item;
	}

	public ItemStack itemConfig(String item, RPGDungeon plugin, boolean nbt) {

		reloadItensDungeonConfig(plugin);

		String displayName = getItensDungeonConfig().getString(item + ".name");
		String idData = getItensDungeonConfig().getString(item + ".itemId");
		List<String> lores = getItensDungeonConfig().getStringList(item + ".lores");
		return craftItem(plugin, item, displayName, idData, lores, nbt);
	}

	public ItemStack itemDungeonConfig(String item, RPGDungeon plugin) {
		reloadDungeonConfig(plugin);
		String displayName = getDungeonConfig().getString(item + ".name");
		String idData = getDungeonConfig().getString(item + ".itemId");
		List<String> lores = getDungeonConfig().getStringList(item + ".lores");
		if (idData == null)
			return null ;
		else
		  return craftItem(plugin, item, displayName, idData, lores, false);
	}

	public boolean getEntitys(RPGDungeon rpg, World world, List<String> list) {
		for (Entity ent : rpg.getServer().getWorld(world.getName()).getEntities()) {
			if (list.contains(ent.getUniqueId().toString())) {
				return true;
			}
		}
		return false;
	}

	public Entity exitDungeon(RPGDungeon rpg, String key, Player p) {
		if (key != null) {
			reloadLocationConfig(rpg);

			for (Entity uuid : rpg.getServer().getWorld(p.getWorld().getName()).getEntities()) {
				if (getLocationConfig().getString(key + ".lastBoss") == null) {
					return null;
				}
				if (getLocationConfig().getString(key + ".lastBoss").contains(uuid.getUniqueId().toString())) {
					return uuid;
				}
			}
			return null;
		}
		return null;
	}

	public void despawnMob(RPGDungeon plugin, String key, Player p, String nameDungeon) {
		reloadLocationConfig(plugin);
		reloadPortaConfig(plugin);
		reloadDungeonConfig(plugin);
		for (Entity uuid : plugin.getServer().getWorld(p.getWorld().getName()).getEntities()) {
			if (getLocationConfig().getStringList(key + ".listMobs").contains(uuid.getUniqueId().toString())) {
				uuid.remove();
			}
		}

		Location loc = (Location) getDungeonConfig().get(nameDungeon + ".spawnPoint");

		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (getLocationConfig().getStringList(key + ".group").contains(player.getUniqueId().toString())) {

				player.teleport(loc);
			}
		}
		try {
			if (getPortaConfig() != null)
				if (getPortaConfig().getConfigurationSection(key) != null)
					for (int i = 1; i <= getPortaConfig().getConfigurationSection(key).getKeys(false).size(); i++) {
						openDoor(getId(key, plugin, i), getLoc1(key, plugin, i), getLoc2(key, plugin, i));
					}
		} catch (Exception e) {
			p.sendMessage("SpawnPoint não foi definido ou irregular!");
		}

		getLocationConfig().set(key + ".status", false);
		getLocationConfig().set(key + ".group", new ArrayList<>());
		getLocationConfig().set(key + ".door", 1);
		getLocationConfig().set(key + ".listMobs", new ArrayList<>());
		getLocationConfig().set(key + ".lastBoss", "");
		saveLocationConfig();
	}

	public void setDoor(String locationDungeon, String arg3, Location loc1, Location loc2, RPGDungeon plugin) {
		reloadPortaConfig(plugin);
		getPortaConfig().set(locationDungeon + (".door" + arg3) + ".door", arg3);
		getPortaConfig().set(locationDungeon + (".door" + arg3) + ".location1", loc1);
		getPortaConfig().set(locationDungeon + (".door" + arg3) + ".location2", loc2);
		getPortaConfig().set(locationDungeon + (".door" + arg3) + ".block.id", "glass");
		savePortaConfig();
		plugin.setLoc1(null);
		plugin.setLoc2(null);

	}

	public static void openDoor(String id, Location loc1, Location loc2) {

		double xMin = Math.min(loc1.getX(), loc2.getX());
		double yMin = Math.min(loc1.getY(), loc2.getY());
		double zMin = Math.min(loc1.getZ(), loc2.getZ());

		double xMax = Math.max(loc1.getX(), loc2.getX());
		double yMax = Math.max(loc1.getY(), loc2.getY());
		double zMax = Math.max(loc1.getZ(), loc2.getZ());
		Location loc;
		String i = id.toUpperCase();
		ItemStack item = new ItemStack(Material.getMaterial(i));

		for (int x = (int) xMin; x <= xMax; x++) {
			for (int y = (int) yMin; y <= yMax; y++) {
				for (int z = (int) zMin; z <= zMax; z++) {
					loc = new Location(loc1.getWorld(), x, y, z);
					loc.getWorld().getBlockAt(x, y, z).setType(item.getType());
				}
			}
		}

	}

	public String getId(String locationDungeon, RPGDungeon plugin, int numberDoor) {
		reloadPortaConfig(plugin);
		String id = getPortaConfig().getString(locationDungeon + (".door" + numberDoor) + ".block.id").toUpperCase();
		return id;
	}

	public Location getLoc1(String locationDungeon, RPGDungeon plugin, int numberDoor) {
		reloadPortaConfig(plugin);
		Location loc1 = (Location) getPortaConfig().get(locationDungeon + (".door" + numberDoor) + ".location1");
		return loc1;
	}

	public Location getLoc2(String locationDungeon, RPGDungeon plugin, int numberDoor) {
		reloadPortaConfig(plugin);
		Location loc2 = (Location) getPortaConfig().get(locationDungeon + (".door" + numberDoor) + ".location2");

		return loc2;
	}

	public void Passager(RPGDungeon plugin, LivingEntity Ent, String pathmob, List<String> listUUID,
			net.minecraft.server.v1_16_R3.World nmsworld, Location loc) {

		LivingEntity mount = null;
		LivingEntity mount2 = null;
		List<String> passager2 = new ArrayList<String>();
		String mob = "";
		Creature craft = new Creature(plugin);
		int countMount = 0;
		int countMount2 = 0;
		List<String> passager = null;
		if (Utilities.getUtilities().getMobConfig().getConfigurationSection(pathmob).contains("passanger"))
			passager = Utilities.getUtilities().getMobConfig().getStringList(pathmob + ".passanger");
		if (passager != null)
			while (countMount <= (passager.size() - 1)) {
				// checar se a montaria é ele mesmo para n causar loop, se
				// for
				// adiciona a montaria so uma vez

				if (Utilities.getUtilities().getMobConfig().contains(passager.get(countMount))) {
					mob = Utilities.getUtilities().getMobConfig().getString(passager.get(countMount) + ".type");
					mount = craft.createCreature(nmsworld, mob, loc, passager.get(countMount));
					passager2 = null;
					if (mount != null) {
						listUUID.add(mount.getUniqueId().toString());
						Ent.addPassenger(mount);
						if (Utilities.getUtilities().getMobConfig().getConfigurationSection(passager.get(countMount))
								.contains("passanger"));
						passager2 = Utilities.getUtilities().getMobConfig()
								.getStringList(passager.get(countMount) + ".passanger");

						if (passager2 != null)
							countMount2 = 0;
							while (countMount2 <= (passager2.size() - 1)) {
								// checar se a montaria é ele mesmo para n
								// causar
								// loop, se
								// for adiciona a montaria so uma vez
								if (Utilities.getUtilities().getMobConfig().contains(passager2.get(countMount2))) {
									mob = Utilities.getUtilities().getMobConfig()
											.getString(passager2.get(countMount2) + ".type");
									mount2 = craft.createCreature(nmsworld, mob, loc, passager2.get(countMount2));
									if (mount2 != null) {
										listUUID.add(mount2.getUniqueId().toString());
										mount.addPassenger(mount2);
									}
								} else {
									mount2 = craft.createCreature(nmsworld, passager2.get(countMount2), loc, "");
									listUUID.add(mount2.getUniqueId().toString());
									Ent.addPassenger(mount2);
								}

								countMount2 += 1;

							}

					} else {
						mount = craft.createCreature(nmsworld, passager.get(countMount), loc, "");
						listUUID.add(mount.getUniqueId().toString());
						Ent.addPassenger(mount);

					}
				} else {
					mob = passager.get(countMount);
					mount = craft.createCreature(nmsworld, mob, loc, "");
					Ent.addPassenger(mount);
				}
				countMount += 1;
			}
	}
}
