package com.sufurujhin.rpgdungeon.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;


public class PlayerDeath implements Listener{

	
	private RPGDungeon plugin;
	public PlayerDeath(RPGDungeon plugin){
		this.plugin = plugin;
	}
		
	@EventHandler(priority = EventPriority.HIGHEST)
	public void death(PlayerDeathEvent event){
		Player p = (Player) event.getEntity();
		Files files = new Files(plugin);
		files.reloadLocationConfig(plugin);
		boolean verificar = false;
		for(String dungeon : files.getLocationConfig().getConfigurationSection("").getKeys(false)){
			if ( files.getLocationConfig().getConfigurationSection(dungeon) != null)
			for(String instance : files.getLocationConfig().getConfigurationSection(dungeon).getKeys(false)){
				if(files.getLocationConfig().getStringList(dungeon+"."+instance+".group") !=null)
				if(files.getLocationConfig().getStringList(dungeon+"."+instance+".group").contains(p.getUniqueId().toString())){
					
					if(files.getLocationConfig().getBoolean(dungeon+"."+instance+".status")){
						if(plugin.getConfig().getBoolean("dungeons.dropInventory")){
							event.setKeepInventory(true);
						}
					}
					break;
				}
				if(verificar){
					break;
				}
			}
		}
		
		
	}
}
