package com.sufurujhin.rpgdungeon.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.sufurujhin.rpgdungeon.RPGDungeon;

public class Files {

	private File mobConfig;
	private FileConfiguration mobFc;
	
	private File portaConfig;
	private FileConfiguration portaFc;
	
	private File filePartyConfig;
	private FileConfiguration fcPartyConfig;
	
	private File fileDungeonConfig;
	private FileConfiguration fcDungeonConfig;
	
	private File fileLanguage;
	private FileConfiguration fcLanguage;
	
	private File itensDungeon;
	private FileConfiguration fcItensDungeon;
	
	private File fileLocation;
	private FileConfiguration fcLocation;
	
	private File fileDB;
	
	private RPGDungeon rpg;
	public  Files(RPGDungeon rpg){
		this.rpg = rpg;
	}
	private static Files load ;
	
	//------------------------------------------------------------------------------
	public void reloadLocationConfig(Plugin plugin) {
		fileLocation = new File(plugin.getDataFolder(), "locationDungeon.yml");
        if (!fileLocation.exists()) {
            plugin.saveResource("locationDungeon.yml", false);
        }
        fcLocation = YamlConfiguration.loadConfiguration(fileLocation);
    }
	
	public void createDB(Plugin plugin) {
		fileDB = new File(plugin.getDataFolder(), "rpgdungeon.db");
        if (!fileDB.exists()) {
            plugin.saveResource("rpgdungeon.db", false);
        }
    }
	
	public FileConfiguration getLocationConfig() {
        return fcLocation;
    }
	public void saveLocationConfig() {
        try {
        	fcLocation.save(fileLocation);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(load().getDungeonConfig().getString("locationDungeon.yml" +" Não esta na pasta"));
        }
    }
	//------------------------------------------------------------------------------
	
	public static Files load(){
		return load;
	}
	
	public void reloadDungeonConfig(Plugin plugin) {
		fileDungeonConfig = new File(plugin.getDataFolder(), "dungeonConfig.yml");
        if (!fileDungeonConfig.exists()) {
            plugin.saveResource("dungeonConfig.yml", false);
        }
        fcDungeonConfig = YamlConfiguration.loadConfiguration(fileDungeonConfig);
    }

	public FileConfiguration getDungeonConfig() {
		if (fcDungeonConfig == null)
			reloadDungeonConfig(rpg);
        return fcDungeonConfig;
    }
	public void saveDungeonConfig() {

        try {
        	fcDungeonConfig.save(fileDungeonConfig);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(load().getDungeonConfig().getString("dungeonConfig.yml Não esta na pasta"));
        }
    }
	
	//-------------------------------------------------------------------------------------------------------------------------------
	
	public void reloadLanguageConfig(Plugin plugin) {
		String language = rpg.getConfig().getString("dungeons.language");
		fileLanguage = new File(plugin.getDataFolder(),  language+".yml");
        if (!fileLanguage.exists()) {
            plugin.saveResource(language+".yml", false);
        }
        fcLanguage = YamlConfiguration.loadConfiguration(fileLanguage);
    }

	public FileConfiguration getLanguageConfig() {
		if (fcLanguage == null)
			reloadLanguageConfig(rpg);
        return fcLanguage;
    }
	public void saveLanguageConfig() {
		String language = rpg.getConfig().getString("dungeons.language");
        try {
        	fcLanguage.save(fileLanguage);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(load().getLanguageConfig().getString(language+".yml" +" Não esta na pasta"));
        }
    }
	
	//ItensDungeon
	//-------------------------------------------------------------------------------------------------------------------------------
	
		public void reloadItensDungeonConfig(Plugin plugin) {
			
			itensDungeon = new File(plugin.getDataFolder(),  "itensDungeon.yml");
	        if (!itensDungeon.exists()) {
	            plugin.saveResource("itensDungeon.yml", false);
	        }
	        fcItensDungeon = YamlConfiguration.loadConfiguration(itensDungeon);
	    }

		public FileConfiguration getItensDungeonConfig() {
			if (fcItensDungeon == null)
				reloadItensDungeonConfig(rpg);
	        return fcItensDungeon;
	    }
		public void saveItensDungeonConfig() {
	        try {
	        	fcItensDungeon.save(itensDungeon);
	        } catch (IOException e) {
	            Bukkit.getServer().getLogger().severe(load().getItensDungeonConfig().getString("itensDungeon.yml" +" Não esta na pasta"));
	        }
	    }
		
		//Party config
		//-------------------------------------------------------------------------------------------------------------------------------
		
			public void reloadPartyConfig(Plugin plugin) {
				
				filePartyConfig = new File(plugin.getDataFolder(),  "partyConfig.yml");
		        if (!filePartyConfig.exists()) {
		            plugin.saveResource("partyConfig.yml", false);
		        }
		        fcPartyConfig = YamlConfiguration.loadConfiguration(filePartyConfig);
		    }

			public FileConfiguration getPartyConfig() {
				if (fcPartyConfig == null)
					reloadPartyConfig(rpg);
		        return fcPartyConfig;
		    }
			public void savePartyConfig() {
		        try {
		        	fcPartyConfig.save(filePartyConfig);
		        } catch (IOException e) {
		            Bukkit.getServer().getLogger().severe(load().getPartyConfig().getString("partyConfig.yml" +" Não esta na pasta"));
		        }
		    }
			//Mobs config
			//-------------------------------------------------------------------------------------------------------------------------------
			
			public void reloadMobConfig(Plugin plugin) {
				
				mobConfig = new File(plugin.getDataFolder(),  "mobConfig.yml");
		        if (!mobConfig.exists()) {
		            plugin.saveResource("mobConfig.yml", false);
		        }
		        mobFc = YamlConfiguration.loadConfiguration(mobConfig);
		    }

			public FileConfiguration getMobConfig() {
				if(mobFc == null){
					reloadMobConfig(rpg);
				}
		        return mobFc;
		    }
			public void saveMobConfig() {
		        try {
		        	mobFc.save(mobConfig);
		        } catch (IOException e) {
		            Bukkit.getServer().getLogger().severe(load().getMobConfig().getString("mobConfig.yml" +" Não esta na pasta"));
		        }
		    }
			//Portas config
			//-------------------------------------------------------------------------------------------------------------------------------
			
			public void reloadPortaConfig(Plugin plugin) {
				
				portaConfig = new File(plugin.getDataFolder(),  "portaConfig.yml");
		        if (!portaConfig.exists()) {
		            plugin.saveResource("portaConfig.yml", false);
		        }
		        portaFc = YamlConfiguration.loadConfiguration(portaConfig);
		    }

			public FileConfiguration getPortaConfig() {
				if (portaFc == null)
					reloadPortaConfig(rpg);
		        return portaFc;
		    }
			public void savePortaConfig() {
		        try {
		        	portaFc.save(portaConfig);
		        } catch (IOException e) {
		            Bukkit.getServer().getLogger().severe(load().getPortaConfig().getString("portaConfig.yml" +" Não esta na pasta"));
		        }
		    }
			//Outros Drops config
			//--------------------------------------------------------------------------------------------------
			private File otherDrops;
			private FileConfiguration otherDropsfc;
			
			public void reloadOtherDrops(Plugin plugin) {
				otherDrops = new File(plugin.getDataFolder(), "otherDrops.yml");
		        if (!otherDrops.exists()) {
		            plugin.saveResource("otherDrops.yml", false);
		        }
		        otherDropsfc = YamlConfiguration.loadConfiguration(otherDrops);
		    }
			public FileConfiguration getOtherDrops() {
				if (otherDropsfc == null)
					reloadOtherDrops(rpg);
		        return otherDropsfc;
		    }
			public void saveOtherDrops() {

		        try {
		        	otherDropsfc.save(otherDrops);
		        } catch (IOException e) {
		            Bukkit.getServer().getLogger().severe(load().getOtherDrops().getString("otherDrops.yml Não esta na pasta"));
		        }
		    }
			//Econima config
			//--------------------------------------------------------------------------------------------------
			
}
