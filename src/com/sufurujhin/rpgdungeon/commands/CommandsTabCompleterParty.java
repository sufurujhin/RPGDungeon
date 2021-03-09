package com.sufurujhin.rpgdungeon.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.sufurujhin.rpgdungeon.RPGDungeon;

public class CommandsTabCompleterParty implements TabCompleter {
	private RPGDungeon rpg ;
	private List<String> arguments = new ArrayList<>();
	public CommandsTabCompleterParty(RPGDungeon rpg) {
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
			if (args[0].equalsIgnoreCase("kick") | args[0].equalsIgnoreCase("invite"))
				FiltrarArgsPlayer(args[1], result, rpg.getServer().getOnlinePlayers());
			return result;
		}
		return null;
	}
	private List<String> getTab1(List<String> arguments) {
		arguments.add("invite");
		arguments.add("sair");
		arguments.add("kick");
		arguments.add("lista");
		return arguments;
	}
	private void FiltrarArgs(String args, List<String> result, List<String> arguments) {
		for (String s : arguments) {
			if (s.toLowerCase().startsWith(args.toLowerCase()))
				result.add(s);
		}
	}
	private void FiltrarArgsPlayer(String args, List<String> result, Collection<? extends Player> collection) {
		for (Player s : collection) {
			if (s.getName().toLowerCase().startsWith(args.toLowerCase()))
				result.add(s.getName());
		}
	}
}
