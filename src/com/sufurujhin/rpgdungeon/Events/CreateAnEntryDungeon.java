package com.sufurujhin.rpgdungeon.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;

public class CreateAnEntryDungeon implements Listener {

	public RPGDungeon rpg;

	public CreateAnEntryDungeon(RPGDungeon rpg) {
		this.rpg = rpg;
	}

	@EventHandler
	public void CreateSign(SignChangeEvent event) {

		Files file = new Files(rpg);
		file.reloadLanguageConfig(rpg);
		file.reloadDungeonConfig(rpg);
		file.reloadLocationConfig(rpg);
		
		if (event.getPlayer().hasPermission("rpgdungeonbr.configuration")) {
			if (event != null) {
				if(event.getLine(0).length() >0){
					if (event.getLine(0).equalsIgnoreCase("d") | event.getLine(0).equalsIgnoreCase("dungeon")) {
						if(event.getLine(1).length() <1 && event.getLine(2).length() <1 && event.getLine(3).length() <1){
							event.getPlayer().sendMessage(file.getLanguageConfig().getString("CreateSign.nameDungeonError").replaceAll("&", "§"));
							event.setCancelled(true);
						}else{
							String path = event.getLine(1) +" "+ event.getLine(2) +" "+ event.getLine(3);
							
								
							event.getPlayer().sendMessage(file.getLanguageConfig().getString("CreateSign.sucessFullCreateSign").replaceAll("&", "§"));
							
							event.setLine(0, ChatColor.RED + rpg.getConfig().getString("dungeons.sign"));
							
							if(!file.getDungeonConfig().getConfigurationSection("").contains(path.replaceAll("\\s+", ""))){
							List<String> lores = new ArrayList<>();
							lores = file.getLanguageConfig().getStringList("LoresItems.loresKeyDungeon");
							lores.add("&4X: " + event.getPlayer().getLocation().getBlockX());
							lores.add("&4Z: " + event.getPlayer().getLocation().getBlockZ());
							lores.add("&4Mundo: " +event.getPlayer().getWorld().getName());
							
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".name", path.replaceAll("\\s+", " "));
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".itemId", "TRIPWIRE_HOOK");
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".dropRate", 0.001);
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".lores", lores);
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".mobs", "");
							file.getDungeonConfig().set(path.replaceAll("\\s+", "")+".lastBoss", "");
							
							file.reloadLocationConfig(rpg);
							file.getLocationConfig().set(path.replaceAll("\\s+", ""), "");
							file.saveLocationConfig();
							file.saveDungeonConfig();
							}
						}
						
					}else if(event.getLine(0).equalsIgnoreCase("door") | event.getLine(0).equalsIgnoreCase("porta")){
						
						if(event.getLine(1).length() <1 ){
							event.getPlayer().sendMessage(file.getLanguageConfig().getString("CreateSign.nameDoorError").replaceAll("&", "§"));
							event.setCancelled(true);
						}else{
							if(event.getLine(1).replaceAll("\\s+", "").matches("[0-9]{1,2}")){
								event.setLine(0, ChatColor.RED + "[Door]");
							
							}else{
								//use apenas numeros na segunda plinha da placa.
								event.setCancelled(true);
								file.reloadLanguageConfig(rpg);
								event.getPlayer().sendMessage(file.getLanguageConfig().getString("CreateSign.doorNumberError").replaceAll("&", "§"));
							}
							
							
						}
						
					}else if(event.getLine(0).equalsIgnoreCase("exit") | event.getLine(0).equalsIgnoreCase("e")){
						event.setLine(0, ChatColor.RED + "[Exit]");
					}
				}
				

			}
		}

	}
}
