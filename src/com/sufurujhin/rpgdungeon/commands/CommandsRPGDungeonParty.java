package com.sufurujhin.rpgdungeon.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Utils.Utilities;

public class CommandsRPGDungeonParty extends Utilities implements CommandExecutor {
	private RPGDungeon plugin;

	public CommandsRPGDungeonParty(RPGDungeon plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	public void getCommands(Player p, RPGDungeon plugin) {
		String commands = "";
		reloadLanguageConfig(plugin);
		for (String s : getLanguageConfig().getStringList("CommandList.party")) {
			commands += s + "\n";
		}

		p.sendMessage(commands.replaceAll("&", "§"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;
			reloadPartyConfig(plugin);
			reloadLanguageConfig(plugin);
			reloadLocationConfig(plugin);
			if (args.length >= 0) {

				// comando /dg
				if (args.length == 0) {

					getCommands(p, plugin);
					return false;
					// comando /pt sair
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("sair")) {
						if (getPartyConfig().getConfigurationSection("Groups") != null) {
							if (getPartyConfig().getConfigurationSection("Groups").getKeys(false).size() > 0) {
								boolean verifica = true;
								
								for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {

									if (getPartyConfig().getStringList("Groups." + key + ".members")
											.contains(p.getUniqueId().toString())) {
										if (getPartyConfig().getStringList("Groups." + key + ".members").size() > 1) {
											
											boolean isGroup =false;
											
											for(String dungeon : getLocationConfig().getConfigurationSection("").getKeys(false)){
												for(String instance : getLocationConfig().getConfigurationSection(dungeon).getKeys(false)){
													if(getLocationConfig().getStringList(dungeon+"."+instance+".group") !=null){
														if(getLocationConfig().getStringList(dungeon+"."+instance+".group").contains(p.getUniqueId().toString())){
															isGroup = getLocationConfig().getBoolean(dungeon+"."+instance+".status");
														}
													}
												}
											}
											
											if(!isGroup){
												String displayName = getLanguageConfig().getString("PartyMesage.leaveTheParty").replaceAll("&", "§");
												verifica = false;
												p.openInventory(getConfimation(plugin, p, null, displayName));
												return true;
											}else{
												String msg = getLanguageConfig().getString("PartyMesage.isGroupDungeon");
												sendMesage(p, msg);
												return false;
											}
										}
									}
								}
								if (verifica) {
									String msg = getLanguageConfig().getString("PartyMesage.notIsAGroup");
									sendMesage(p, msg);
									return false;
								}

							} else {
								String msg = getLanguageConfig().getString("PartyMesage.notIsAGroup");
								sendMesage(p, msg);
								return false;
							}
						} else {
							String msg = getLanguageConfig().getString("PartyMesage.notIsAGroup");
							sendMesage(p, msg);
							return false;
						}

					}
					if (args[0].equalsIgnoreCase("lista") || args[0].equalsIgnoreCase("l")
							|| args[0].equalsIgnoreCase("list")) {
						String party = "";
						if(getPartyConfig().getConfigurationSection("Groups")!=null){
						for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
							if (getPartyConfig().getStringList("Groups." + key + ".members")
									.contains(p.getUniqueId().toString())) {
								int i = 1;
								for (String s : getPartyConfig().getStringList("Groups." + key + ".members")) {
									if (i == getPartyConfig().getStringList("Groups." + key + ".members").size()) {

										party += getPlayer(plugin, s).getName();
									} else {
										party += getPlayer(plugin, s).getName() + ", ";
									}
									i++;
								}
								break;
							}
							
						}
					}
						if (party != null & party != "") {
							p.sendMessage(getLanguageConfig().getString("PartyMesage.listGroup")
									.replaceAll("<group>", party).replaceAll("&", "§"));
						} else {
							p.sendMessage(getLanguageConfig().getString("PartyMesage.notIsAGroup")
									.replaceAll("&", "§"));
						}
						
					} else {
						getCommands(p, plugin);
						return false;
					}

					// comando /pt invite [Invitado]
					// comando /pt expulsar [expulso]
				}

				if (args.length == 2) {

					if (args[0].equalsIgnoreCase("invite") | args[0].equalsIgnoreCase("i")) {

						Player target = getPlayer(plugin, args[1]);
						int qtdPt = plugin.getConfig().getInt("ConfigurationParty.maxParty");
						// jogador não encontrado
						if (target == null) {
							p.sendMessage(getLanguageConfig().getString("MessageCommands.partyPlayerNotFolder")
									.replaceAll("&", "§"));
							return false;
						}

						// Verifica se esta mandando pt para si mesmo
						if (target.equals(p)) {
							String msg = getLanguageConfig().getString("PartyMesage.groupForYourself").replaceAll("&",
									"§");
							sendMesage(p, msg);
							return false;
						}
						if(!getStatusMember(target)){
							if(!getStatusMember(p)){
								
								
						if (getPartyConfig().getConfigurationSection("Groups") != null) {

							if (getPartyConfig().getConfigurationSection("Groups").getKeys(false).size() > 0) {

								boolean lider = false;
								boolean member = false;

								for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {

									if (getPartyConfig().getConfigurationSection("Groups." + key) != null) {

										if (getPartyConfig().getStringList("Groups." + key + ".members")
												.contains(target.getUniqueId().toString())
												& getPartyConfig().getString("Groups." + key + ".lider")
														.contains(p.getUniqueId().toString())) {
											String msg = getLanguageConfig()
													.getString("PartyMesage.playerAlreadyYourGroup")
													.replaceAll("<target>", target.getName());
											sendMesage(p, msg);
											return false;
										} else if (getPartyConfig().getStringList("Groups." + key + ".members")
												.contains(target.getUniqueId().toString())) {
											String msg = getLanguageConfig().getString("PartyMesage.playerAlreadyGroup")
													.replaceAll("<target>", target.getName());
											sendMesage(p, msg);
											return false;
										} else if (getPartyConfig().getStringList("Groups." + key + ".members")
												.contains(p.getUniqueId().toString())
												& !getPartyConfig().getString("Groups." + key + ".lider")
														.contains(p.getUniqueId().toString())) {
											String msg = getLanguageConfig().getString("PartyMesage.youNotLider");
											sendMesage(p, msg);
											return false;
										} else if (getPartyConfig().getString("Groups." + key + ".lider")
												.contains(target.getUniqueId().toString())
												& getPartyConfig().getStringList("Groups." + key + ".member")
														.size() >= qtdPt) {

											lider = true;
										}
										if (getPartyConfig().getString("Groups." + key + ".lider")
												.contains(p.getUniqueId().toString())
												& getPartyConfig().getStringList("Groups." + key + ".member")
														.size() >= qtdPt) {
											String msg = getLanguageConfig().getString("PartyMesage.limitGroupExceeded")
													.replaceAll("<max>", "" + qtdPt);
											sendMesage(p, msg);
										}

									}
								}

								if (lider & !member) {
									String msg = getLanguageConfig().getString("PartyMesage.inviteTheparty")
											.replace("<target>", target.getName());
									sendMesage(p, msg);
									String displayName = getLanguageConfig().getString("PartyMesage.joinThePart")
											.replaceAll("&", "§").replaceAll("<player>", p.getName());
									target.openInventory(getConfimation(plugin, target,
											getitem(new ItemStack(Material.DIAMOND), p.getName(), p), displayName));
									return true;

								} else if (member & !lider) {
									String msg = getLanguageConfig().getString("PartyMesage.youNotLider");
									sendMesage(p, msg);
									return false;
								} else {
									String msg = getLanguageConfig().getString("PartyMesage.inviteTheparty")
											.replace("<target>", target.getName());
									sendMesage(p, msg);
									String displayName = getLanguageConfig().getString("PartyMesage.joinThePart")
											.replaceAll("&", "§").replaceAll("<player>", p.getName());
									target.openInventory(getConfimation(plugin, target,
											getitem(new ItemStack(Material.DIAMOND), p.getName(), p), displayName));
									return true;
								}

							} else {
								String msg = getLanguageConfig().getString("PartyMesage.inviteTheparty")
										.replace("<target>", target.getName());
								sendMesage(p, msg);
								String displayName = getLanguageConfig().getString("PartyMesage.joinThePart")
										.replaceAll("&", "§").replaceAll("<player>", p.getName());
								target.openInventory(getConfimation(plugin, target,
										getitem(new ItemStack(Material.DIAMOND), p.getName(), p), displayName));
								return true;
							}
						} else {
							String msg = getLanguageConfig().getString("PartyMesage.inviteTheparty").replace("<target>",
									target.getName());
							sendMesage(p, msg);
							String displayName = getLanguageConfig().getString("PartyMesage.joinThePart")
									.replaceAll("&", "§").replaceAll("<player>", p.getName());
							target.openInventory(getConfimation(plugin, target,
									getitem(new ItemStack(Material.DIAMOND), p.getName(), p), displayName));
							return true;
						}
						
						}else{
							String msg = getLanguageConfig().getString("PartyMesage.youAlreadyDungeon")
									.replace("<target>", target.getName());
							sendMesage(p, msg);
							return false;
						}
					}else{
						String msg = getLanguageConfig().getString("PartyMesage.playerAlreadyDungeon")
								.replace("<target>", target.getName());
						sendMesage(p, msg);
						return false;
					}

					}

					if (args[0].equalsIgnoreCase("expulsar") || args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("ex") || args[0].equalsIgnoreCase("k")) {
						Player expulsed = getPlayer(plugin, args[1]);
						if(expulsed !=null){
							boolean verifica = true;
							if(getPartyConfig().getConfigurationSection("Groups") !=null){
							for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {
								if (getPartyConfig().getStringList("Groups." + key + ".members").contains(expulsed.getUniqueId().toString())) {
									
									if(getPartyConfig().getString("Groups." + key + ".lider").contains(p.getUniqueId().toString()) & !p.getUniqueId().toString().equalsIgnoreCase(expulsed.getUniqueId().toString())){
										verifica = false;
										ItemStack item = new ItemStack(Material.DIAMOND);
										String displayName = getLanguageConfig().getString("PartyMesage.kickPlayerRequest").replaceAll("<player>", ": "+expulsed.getName()).replaceAll("&", "§");
										p.openInventory(getConfimation(plugin, expulsed, getitem(item, expulsed.getName(), expulsed), displayName));
										break;
									}else if (getPartyConfig().getString("Groups." + key + ".lider").contains(p.getUniqueId().toString()) & p.getUniqueId().toString().equalsIgnoreCase(expulsed.getUniqueId().toString())){
										sendMesage(p, getLanguageConfig().getString("PartyMesage.kickLider"));
										verifica = false;
										break;
									}else{
										verifica = false;
										sendMesage(p, getLanguageConfig().getString("PartyMesage.kickNotLider"));
										break;
									}
									
								}
								
							}
							}
							if(verifica){
								sendMesage(p, getLanguageConfig().getString("PartyMesage.notIsAGroup"));
							}
							
						}else{
							sendMesage(p, getLanguageConfig().getString("MessageCommands.partyPlayerNotFolder"));
						}
						
						
					} else {
						getCommands(p, plugin);
						return false;
					}

				}

			}
		}
		return false;
	}

	public boolean invite(RPGDungeon plugin, Player target, Player p) {
		reloadPartyConfig(plugin);
		reloadLanguageConfig(plugin);
		if (getPartyConfig().getConfigurationSection("Groups") != null) {
			for (String key : getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {

				if (getPartyConfig().getConfigurationSection("Groups." + key) != null) {
					if (getPartyConfig().getStringList("Groups." + key + ".members")
							.contains(target.getUniqueId().toString())) {

						if (getPartyConfig().getString("Groups." + key + ".lider")
								.contains(p.getUniqueId().toString())) {

							return true;
						} else {
							String msg = getLanguageConfig().getString("PartyMesage.youNotLider");
							sendMesage(p, msg);
							return false;
						}
					}
					return false;
				}
			}
			return false;
		}

		return false;
	}
	public boolean getStatusMember(Player p){
		reloadLocationConfig(plugin);
		
		for(String dungeon : getLocationConfig().getConfigurationSection("").getKeys(false)){
			if (getLocationConfig().getConfigurationSection(dungeon) != null)
			for(String instance : getLocationConfig().getConfigurationSection(dungeon).getKeys(false)){
				if(getLocationConfig().getStringList(dungeon+"."+instance+".group")!=null){
					if(getLocationConfig().getStringList(dungeon+"."+instance+".group").contains(p.getUniqueId().toString())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
