package com.sufurujhin.rpgdungeon.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;

public class CommandsTabCompleter implements TabCompleter {
	private RPGDungeon rpg = null;
	private List<String> arguments = new ArrayList<>();

	public CommandsTabCompleter(RPGDungeon rpg) {
		// TODO Auto-generated constructor stub
		this.rpg = rpg;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (arguments.isEmpty())
			getTab1(arguments);
		List<String> result = new ArrayList<>();

		if (args.length == 1) {
			FiltrarArgs(args[0], result, arguments);
			return result;
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("setdungeon") | args[0].equalsIgnoreCase("setmob")
					| args[0].equalsIgnoreCase("setporta"))
				FiltrarArgs(args[1], result, getSetdungeon(args));
			if (args[0].equalsIgnoreCase("dungeon")) {
				result.add("set");
			}

			if (args[0].equalsIgnoreCase("item")) {
				result.add("list");
				result.add("give");
			}
			if (args[0].equalsIgnoreCase("mob")) {
				result.add("list");
				result.add("spawn");
			}
			if (args[0].equalsIgnoreCase("spawner")) {
				result.add("list");
				result.add("tp");
				result.add("remove");
				result.add("start");
				result.add("stop");
				FiltrarArgs(args[1], result, getMobs(args));
			}
			return result;
		}

		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("spawner")) {
				FiltrarArgs(args[1], result, getMobs(args));
				if (result.contains(args[1])) {
					result.clear();
					result.add("[cooldown in secunds] ex: 120");
				} else {
					result.clear();
				}
			}
			return result;
		}
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("setmob") | args[0].equalsIgnoreCase("setporta")) {
				FiltrarArgs(args[2], result, getIntance(args));
			}
			if (args[0].equalsIgnoreCase("dungeon"))
				if (args[1].equalsIgnoreCase("set"))
					FiltrarArgs(args[2], result, getSetdungeon(args));
			if (args[0].equalsIgnoreCase("item")) {
				if (args[1].equalsIgnoreCase("give"))
					FiltrarArgs(args[2], result, getItens(args));
			}
			if (args[0].equalsIgnoreCase("mob")) {
				if (args[1].equalsIgnoreCase("spawn"))
					FiltrarArgs(args[2], result, getMobs(args));
			}

			return result;
		}
		return result;
	}

	private void FiltrarArgs(String args, List<String> result, List<String> arguments) {
		for (String s : arguments) {
			if (s.toLowerCase().startsWith(args.toLowerCase()))
				result.add(s);
		}
	}

	private List<String> getTab1(List<String> arguments) {
		arguments.add("dungeon");
		arguments.add("item");
		arguments.add("mob");
		arguments.add("reload");
		arguments.add("spawner");
		arguments.add("setdungeon");
		arguments.add("setMob");
		arguments.add("setPorta");
		arguments.add("setspawn");
		arguments.add("teleport");
		return arguments;
	}

	private List<String> getSetdungeon(String[] args) {
		List<String> list = new ArrayList<>();
		Files files = new Files(rpg);
		files.reloadDungeonConfig(rpg);
		for (String s : files.getDungeonConfig().getConfigurationSection("").getKeys(false))
			list.add(s);
		return list;
	}

	private List<String> getMobs(String[] args) {
		List<String> list = new ArrayList<>();
		Files files = new Files(rpg);
		files.reloadMobConfig(rpg);
		for (String s : files.getMobConfig().getConfigurationSection("").getKeys(false))
			list.add(s);
		return list;
	}

	private List<String> getItens(String[] args) {
		List<String> list = new ArrayList<>();
		Files files = new Files(rpg);
		files.reloadItensDungeonConfig(rpg);
		for (String s : files.getItensDungeonConfig().getConfigurationSection("").getKeys(false))
			list.add(s);
		return list;
	}

	private List<String> getIntance(String[] args) {
		List<String> list = new ArrayList<>();
		Files files = new Files(rpg);
		files.reloadLocationConfig(rpg);
		if (files.getLocationConfig().getConfigurationSection("").contains(args[1]))
			for (String s : files.getLocationConfig().getConfigurationSection(args[1]).getKeys(false))
				list.add(s);
		return list;
	}

	public void send(String msg) {
		rpg.getServer().broadcastMessage(msg);
	}
}
