package com.sufurujhin.rpgdungeon.Events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;


public class PlayerRespawn implements Listener{

	
	private RPGDungeon plugin;
	public PlayerRespawn(RPGDungeon plugin){
		this.plugin = plugin;
	}
		
	@EventHandler(priority = EventPriority.HIGHEST)
	public void death(PlayerRespawnEvent event){
		Files files = new Files(plugin);
		files.reloadLocationConfig(plugin);
		boolean verificar = false;
		try {
			for(String dungeon : files.getLocationConfig().getConfigurationSection("").getKeys(false)){
				if (files.getLocationConfig().getConfigurationSection(dungeon) != null)
				for(String instance : files.getLocationConfig().getConfigurationSection(dungeon).getKeys(false)){
					if(files.getLocationConfig().getStringList(dungeon+"."+instance+".group").contains(event.getPlayer().getUniqueId().toString())){
						
						if(files.getLocationConfig().getBoolean(dungeon+"."+instance+".status")){
							event.setRespawnLocation((Location)files.getLocationConfig().get(dungeon+"."+instance+".spawnLocation"));
							verificar = true;
						}
						break;
					}
					if(verificar){
						
						break;
					}
				}
			}
			
			if(!verificar){
				Location loc = (Location) plugin.getConfig().get("SpawnLocation");
				
				if(loc !=null){
					event.setRespawnLocation(loc);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
