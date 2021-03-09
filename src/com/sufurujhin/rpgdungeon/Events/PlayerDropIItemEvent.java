package com.sufurujhin.rpgdungeon.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;
import com.sufurujhin.rpgdungeon.Utils.MsgPickupItem;

public class PlayerDropIItemEvent extends Files implements Listener {

	public PlayerDropIItemEvent(RPGDungeon rpg) {
		super(rpg);
		this.plugin = rpg;
	}

	private RPGDungeon plugin;

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {

		Player p = e.getPlayer();
		reloadLanguageConfig(plugin);
		String inventoryName = getLanguageConfig().getString("PartyMesage.joinThePart").replaceAll("&", "§");
		String inventory1 = getLanguageConfig().getString("ConfigButtons.resquest").replaceAll("&", "§");
		String inventory3 = getLanguageConfig().getString("PartyMesage.leaveTheParty").replaceAll("&", "§");
		String inventoryKick = getLanguageConfig().getString("PartyMesage.kickPlayerRequest").replaceAll("&", "§");
		
		String pathKick[] = inventoryKick.split("<player>");
		String path[] = inventoryName.split(":");
		String pathInentory[] = p.getOpenInventory().getTitle().split(":");
		String pathInentoryKick[] = p.getOpenInventory().getTitle().split(":");
		
		if (pathKick[0].equalsIgnoreCase(pathInentoryKick[0]) | p.getOpenInventory().getTitle().equals(inventory1) || pathInentory[0].equals(path[0]) || p.getOpenInventory().getTitle().equals(inventory3)) {

			e.getItemDrop().getItemStack().setAmount(0);
			p.closeInventory();
			e.setCancelled(true);
		}
		
		if(e.getItemDrop().getItemStack()!=null){
			if(e.getItemDrop().getItemStack().getItemMeta()!=null){
				
				if(e.getItemDrop().getItemStack().getEnchantments().size() >0){
					Files file = new Files(plugin);
					file.reloadLanguageConfig(plugin);
					
					MsgPickupItem msgPick = new MsgPickupItem();
					msgPick.sendMsgPickup(plugin, e.getItemDrop().getItemStack(), p,file, "drop");
				}	
			}
			
		}
		

	}
}
