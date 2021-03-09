package com.sufurujhin.rpgdungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sufurujhin.rpgdungeon.Events.Damage;
import com.sufurujhin.rpgdungeon.Events.CreateAnEntryDungeon;
import com.sufurujhin.rpgdungeon.Events.kill;
import com.sufurujhin.rpgdungeon.Events.Dungeon;
import com.sufurujhin.rpgdungeon.Events.PlayerDeath;
import com.sufurujhin.rpgdungeon.Events.PlayerDropIItemEvent;
import com.sufurujhin.rpgdungeon.Banco.ConexaoSQLite;
import com.sufurujhin.rpgdungeon.Banco.CriarBancoSQLite;
import com.sufurujhin.rpgdungeon.Events.Click;
import com.sufurujhin.rpgdungeon.Events.PlayerPickUpItem;
import com.sufurujhin.rpgdungeon.Events.PlayerQuit;
import com.sufurujhin.rpgdungeon.Events.PlayerRespawn;
import com.sufurujhin.rpgdungeon.Events.SlimesEvent;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.SummonerRunable;
import com.sufurujhin.rpgdungeon.Utils.Utilities;
import com.sufurujhin.rpgdungeon.commands.CommandsRPG;
import com.sufurujhin.rpgdungeon.commands.CommandsRPGDungeonParty;
import com.sufurujhin.rpgdungeon.commands.CommandsTabCompleter;
import com.sufurujhin.rpgdungeon.commands.CommandsTabCompleterParty;

import net.milkbowl.vault.economy.Economy;

public class RPGDungeon extends JavaPlugin implements Listener {

	public Logger log = Logger.getLogger("RPGDungeonReload");
	private static Economy economy = null;
	public SummonerRunable summone = null;
	public static HashMap<String, ItemStack> listaItens;
	public static HashMap<String, ItemStack> listaOtherDrops;

	public RPGDungeon() {
		listaItens = new HashMap<>();
		listaOtherDrops = new HashMap<>();
	}

	@Override
	public void onEnable() {
		this.load = this;
		Bukkit.getConsoleSender().sendMessage("§l§2[" + log.getName() + "§l§2]" + " Enabled!");
		registerEvents();
		files();
		registerCommands();
		if (getConfig().getBoolean("AtivateEconomy")) {
			setupEconomy();
		}
		resetDungeon(this);
		ConexaoSQLite con = new ConexaoSQLite();
		con.conectar();
		con.desconectar();
		CriarBancoSQLite table = new CriarBancoSQLite(con);
		table.StartTable();

		summone = new SummonerRunable(this);
		summone.spawnMob();
		summone.runTaskTimer(this, 0, 20);
	}

	@Override
	public void onDisable() {
		if (summone !=null)
		summone.RemoverEtities();
		HandlerList.unregisterAll();
		Bukkit.getConsoleSender().sendMessage("§l§4[" + log.getName() + "§l§4]" + " Disabled!");

	}

	public boolean startSpawner() {
		  
		if (summone == null) {
			summone = new SummonerRunable(this);
			summone.spawnMob();
			summone.runTaskTimer(this, 0, 20);
			return true;
		} else
			return false;
	}

	public boolean stopSpawner() {
		if (summone != null) {
		if (!summone.isCancelled()) {
			summone.cancel();
			summone.RemoverEtities();
			summone = null;
			return true;
		} else
			return false;
		} else
				return false;
	}

	public void registerCommands() {
		getCommand("rpgdungeon").setExecutor(new CommandsRPG(this));
		getCommand("dungeon").setExecutor(new CommandsRPGDungeonParty(this));
		getCommand("rpgdungeon").setTabCompleter(new CommandsTabCompleter(this));
		getCommand("dungeon").setTabCompleter(new CommandsTabCompleterParty(this));
	}

	@SuppressWarnings("null")
	public boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			return false;
		}
		economy = (Economy) economyProvider.getProvider();
		return economy != null;
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new CreateAnEntryDungeon(this), this);
		Bukkit.getPluginManager().registerEvents(new Dungeon(this), this);
		Bukkit.getPluginManager().registerEvents(new Click(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDropIItemEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new SlimesEvent(), this);
		Bukkit.getPluginManager().registerEvents(new Damage(this), this);
		Bukkit.getPluginManager().registerEvents(new kill(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerPickUpItem(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawn(this), this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	private Location loc1;
	private Location loc2;

	@EventHandler
	public void selectLocations(PlayerInteractEvent e) {

		if (e.getPlayer().hasPermission("rpgdungeonbr.configuration")) {
			if (e.getMaterial() == Material.WOODEN_HOE) {
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					setLoc2(e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(ChatColor.BLUE + "Posição 2 selecionada!");
				}

				if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
					setLoc1(e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(ChatColor.BLUE + "Posição 1 selecionada!");
				}
				e.setCancelled(true);
			}
		}
	}

	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}

	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}

	public Location getLoc1() {
		return loc1;
	}

	public Location getLoc2() {
		return loc2;
	}

	public void resetDungeon(RPGDungeon plugin) {
		Files files = new Files(plugin);
		Bukkit.getConsoleSender().sendMessage("§l§4[Dungeon]Resetando Dungeons");
		files.reloadLocationConfig(plugin);
		files.reloadPortaConfig(plugin);
		List<String> uuidMobs = new ArrayList<>();
		for (String dungeon : files.getPortaConfig().getConfigurationSection("").getKeys(false)) {

			for (String instance : files.getPortaConfig().getConfigurationSection(dungeon).getKeys(false)) {
				if (files.getPortaConfig().getConfigurationSection(dungeon + "." + instance).getKeys(false) != null)
					if (files.getPortaConfig().getBoolean(dungeon + "." + instance + ".status"))
						for (String door : files.getPortaConfig().getConfigurationSection(dungeon + "." + instance)
								.getKeys(false)) {
							// ALTERADO PARA STRING
							String id = files.getPortaConfig()
									.getString(dungeon + "." + instance + "." + door + ".block.id").toUpperCase();

							Location loc1 = (Location) files.getPortaConfig()
									.get(dungeon + "." + instance + "." + door + ".location1");
							Location loc2 = (Location) files.getPortaConfig()
									.get(dungeon + "." + instance + "." + door + ".location2");

							uuidMobs = files.getLocationConfig().getStringList(dungeon + "." + instance + ".listMobs");

							for (Entity uuid : plugin.getServer().getWorld(loc1.getWorld().getName()).getEntities()) {
								if (uuidMobs.contains(uuid.getUniqueId().toString())) {
									uuid.remove();
								}
							}
							Utilities.openDoor(id, loc1, loc2);
						}

				files.getLocationConfig().set(dungeon + "." + instance + ".status", false);
				files.getLocationConfig().set(dungeon + "." + instance + ".group", new ArrayList<>());
				files.getLocationConfig().set(dungeon + "." + instance + ".door", 1);
				files.getLocationConfig().set(dungeon + "." + instance + ".listMobs", new ArrayList<>());
				files.getLocationConfig().set(dungeon + "." + instance + ".lastBoss", "");
				files.saveLocationConfig();
			}
		}

		Bukkit.getConsoleSender().sendMessage("§l§d[Dungeon]Dungeons Resetada!");
	}

	private File ecoMob;
	private FileConfiguration em;
	public static RPGDungeon load;

	public FileConfiguration getEcoMob() {
		return em;
	}

	public void saveEcomob() {

		try {
			em.save(ecoMob);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(load().getEcoMob().getString("ecoMob.yml Nao esta na pasta"));
		}
	}

	public static RPGDungeon load() {
		return load;
	}

	public void loadEcoMob(Plugin plugin) {
		ecoMob = new File(plugin.getDataFolder(), "ecoMob.yml");
		if (!ecoMob.exists()) {
			plugin.saveResource("ecoMob.yml", false);
		}
		em = YamlConfiguration.loadConfiguration(ecoMob);
	}

	public void files() {
		Files files = new Files(this);
		files.reloadDungeonConfig(this);
		files.reloadLanguageConfig(this);
		files.reloadLocationConfig(this);
		files.reloadItensDungeonConfig(this);
		files.reloadPartyConfig(this);
		files.reloadMobConfig(this);
		files.reloadPortaConfig(this);
		files.getPartyConfig().set("Groups", null);
		files.savePartyConfig();
		files.reloadOtherDrops(this);

		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		Utilities util = new Utilities(this);
		util.createListItem(this, files);
	}

	public HashMap<String, ItemStack> getListaItens() {
		return listaItens;
	}

	@SuppressWarnings("static-access")
	public void setListaItens(HashMap<String, ItemStack> listaItens) {
		this.listaItens = listaItens;
	}
}
