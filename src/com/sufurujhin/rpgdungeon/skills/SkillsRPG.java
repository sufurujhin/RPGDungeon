package com.sufurujhin.rpgdungeon.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sufurujhin.rpgdungeon.RPGDungeon;

public class SkillsRPG {

	@SuppressWarnings("unused")
	private RPGDungeon rpg;

	public SkillsRPG(RPGDungeon rpg) {
		this.rpg = rpg;
	}

	public List<Player> getPlayersRadious(int radious, RPGDungeon b, Entity e) {
		List<Player> playerlist = new ArrayList<>();
		
		for (Player player : b.getServer().getOnlinePlayers()) {
			if (radious != 0) {
				if (e.getLocation().getWorld() == player.getLocation().getWorld()) {
					if (e.getLocation().distance(player.getLocation()) < radious) {
						playerlist.add(player);
					}
				}
			}
		}
				
		return playerlist;
	}
	
	
}
