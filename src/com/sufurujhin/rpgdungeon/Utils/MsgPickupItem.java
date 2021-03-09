package com.sufurujhin.rpgdungeon.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;

public class MsgPickupItem {

	public void sendMsgPickup(RPGDungeon plugin, ItemStack item, Player player, Files file, String tipoEvento) {

		file = new Files(plugin);
		file.reloadPartyConfig(plugin);

		if (file.getPartyConfig().getConfigurationSection("Groups") != null) {
			file.reloadLanguageConfig(plugin);
			if (item.getEnchantments().size() >0) {
				ListEnchants myListEnchant = new ListEnchants();
				List<String> membros = new ArrayList<>();
				Player target = null;
				for (String key1 : file.getPartyConfig().getConfigurationSection("Groups").getKeys(false)) {

					if (file.getPartyConfig().getStringList("Groups." + key1 + ".members")
							.contains(player.getUniqueId().toString())) {
						membros = file.getPartyConfig().getStringList("Groups." + key1 + ".members");
						break;
					}

				}
				List<String> lista = new ArrayList<>();
				lista = myListEnchant.getNameEnchant(item);
				String list = "";
				int x = 0;
				for (String s : lista) {
					if (x == 0) {
						list = s;
					}
					if (x != 0 && x < lista.size()) {
						list = list + ", " + s;
					}
					if (x == lista.size()) {
						list = list + s;
					}
					x++;
				}
				for (String uuid : membros) {

					for (Player p : plugin.getServer().getOnlinePlayers()) {

						if (p.getUniqueId().toString().equalsIgnoreCase(uuid)) {
							target = p;
							break;
						}
					}
					if (target != null) {
						String s = item.getItemMeta().getDisplayName();
						if(s ==null){
							s= item.getType().toString();
						}
						if(tipoEvento.equalsIgnoreCase("drop")){
							if (target.isOnline()) {
								file.reloadLanguageConfig(plugin);
								String msg = file.getLanguageConfig().getString("PartyMesage.memberDropItem").replaceAll("&", "§").replaceAll("<item>", s).replaceAll("<ench>", list);
								if (player.getUniqueId().toString().equals(uuid)) {

									msg = msg.replaceAll("<player>", "Você");

									target.sendMessage(msg);
								} else {
									msg = msg.replaceAll("<player>", player.getName());

									target.sendMessage(msg);
								}
							}
							
							
							
						}else if(tipoEvento.equalsIgnoreCase("pick")){
							
							if (target.isOnline()) {
								String msg = file.getLanguageConfig().getString("PartyMesage.memberPickupItem").replaceAll("&", "§").replaceAll("<qtd>", "" + item.getAmount()).replaceAll("<item>", s).replaceAll("<ench>", list);
								if (player.getUniqueId().toString().equals(uuid)) {

									msg = msg.replaceAll("<player>", "Você");

									target.sendMessage(msg);
								} else {
									msg = msg.replaceAll("<player>", player.getName());

									target.sendMessage(msg);
								}
							}
						}
						
						
					}

				}

			}
		}

	}
}
