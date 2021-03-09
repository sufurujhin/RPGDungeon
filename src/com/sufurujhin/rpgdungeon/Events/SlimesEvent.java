package com.sufurujhin.rpgdungeon.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;

public class SlimesEvent implements Listener{

	
	@EventHandler
	public void slimes(SlimeSplitEvent event){
		if(event !=null){
			if(event.getEntity() !=null){
				if(event.getEntity().getCustomName() !=null){
					event.setCancelled(true);
				}	
			}
		}
	}
}
