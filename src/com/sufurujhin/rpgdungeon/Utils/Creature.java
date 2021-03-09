package com.sufurujhin.rpgdungeon.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.mobs.Bats;
import com.sufurujhin.rpgdungeon.mobs.BlazeMonster;
import com.sufurujhin.rpgdungeon.mobs.Cave_Spiders;
import com.sufurujhin.rpgdungeon.mobs.Chickens;
import com.sufurujhin.rpgdungeon.mobs.Cows;
import com.sufurujhin.rpgdungeon.mobs.Creepers;
import com.sufurujhin.rpgdungeon.mobs.Donkeys;
import com.sufurujhin.rpgdungeon.mobs.Elder_Guaradians;
import com.sufurujhin.rpgdungeon.mobs.Ender_Dragons;
import com.sufurujhin.rpgdungeon.mobs.Endermans;
import com.sufurujhin.rpgdungeon.mobs.Endermites;
import com.sufurujhin.rpgdungeon.mobs.Evokers;
import com.sufurujhin.rpgdungeon.mobs.Foxs;
import com.sufurujhin.rpgdungeon.mobs.Ghasts;
import com.sufurujhin.rpgdungeon.mobs.GiantMonster;
import com.sufurujhin.rpgdungeon.mobs.Guardians;
import com.sufurujhin.rpgdungeon.mobs.Hoglins;
import com.sufurujhin.rpgdungeon.mobs.Horses;
import com.sufurujhin.rpgdungeon.mobs.Husks;
import com.sufurujhin.rpgdungeon.mobs.OcelotMonster;
import com.sufurujhin.rpgdungeon.mobs.Pandas;
import com.sufurujhin.rpgdungeon.mobs.Piglins;
import com.sufurujhin.rpgdungeon.mobs.Phantoms;
import com.sufurujhin.rpgdungeon.mobs.PigLinBrutos;
import com.sufurujhin.rpgdungeon.mobs.PigMob;
import com.sufurujhin.rpgdungeon.mobs.Pig_Zombie;
import com.sufurujhin.rpgdungeon.mobs.Pillagers;
import com.sufurujhin.rpgdungeon.mobs.Polar_Bear;
import com.sufurujhin.rpgdungeon.mobs.Rabbits;
import com.sufurujhin.rpgdungeon.mobs.Ravangers;
import com.sufurujhin.rpgdungeon.mobs.Rabbit;
import com.sufurujhin.rpgdungeon.mobs.Strays;
import com.sufurujhin.rpgdungeon.mobs.Sheeps;
import com.sufurujhin.rpgdungeon.mobs.Shulker;
import com.sufurujhin.rpgdungeon.mobs.SilverFish;
import com.sufurujhin.rpgdungeon.mobs.Skeleton;
import com.sufurujhin.rpgdungeon.mobs.Skeleton_Horse;
import com.sufurujhin.rpgdungeon.mobs.Skeletons_Wither;
import com.sufurujhin.rpgdungeon.mobs.Slimes;
import com.sufurujhin.rpgdungeon.mobs.SnowMans;
import com.sufurujhin.rpgdungeon.mobs.SpiderMonster;
import com.sufurujhin.rpgdungeon.mobs.Vexs;
import com.sufurujhin.rpgdungeon.mobs.Vindication;
import com.sufurujhin.rpgdungeon.mobs.Witchs;
import com.sufurujhin.rpgdungeon.mobs.Withers;
import com.sufurujhin.rpgdungeon.mobs.Wolfs;
import com.sufurujhin.rpgdungeon.mobs.Zoglins;
import com.sufurujhin.rpgdungeon.mobs.ZombieDrowned;
import com.sufurujhin.rpgdungeon.mobs.Squids;
import com.sufurujhin.rpgdungeon.mobs.ZombieMonster;
import com.sufurujhin.rpgdungeon.mobs.Zombies_Horse;
import com.sufurujhin.rpgdungeon.mobs.Zombies_Villager;
import com.sufurujhin.rpgdungeon.mobs.Illusioners;
import com.sufurujhin.rpgdungeon.mobs.Iron_Golems;
import com.sufurujhin.rpgdungeon.mobs.LLamas;
import com.sufurujhin.rpgdungeon.mobs.Mules;
import com.sufurujhin.rpgdungeon.mobs.Magma_Cubes;

import net.minecraft.server.v1_16_R3.World;

public class Creature extends Utilities {

	private RPGDungeon plugin;

	private List<String> itemsList = new ArrayList<>();
	private ItemStack[] items = new ItemStack[5];
	private int[] slots = new int[5];
	private double attack = 0.0;
	private double speed = 0.0;
	private float health = 0.0F;
	private String name = "";
	private int level = 0;
	private boolean baby = false;
	private boolean villager = false;
	private boolean twoHand = false;
	private int lenght = 0;
	private String color;
	private EntityType id;
	private String Joker  =  "";
	
	public Creature(RPGDungeon plugin) {
		super(plugin);

		this.plugin = plugin;

	}
	
	@SuppressWarnings("deprecation")@EventHandler(priority = EventPriority.HIGHEST)
	public LivingEntity createCreature(World world, String mobType, Location loc, String keyMob) {
				
		setAtribute(plugin, keyMob);
		String[] path = mobType.split(":");

		String mob = "";

		if (path.length == 1) {
			mob = path[0];
		} else {
			mob = path[0];
			color = path[1];
		}
		if (mob.matches("[0-9]{1,4}")) {

			id = EntityType.fromId((short) Integer.parseInt(mob));

		} else {

			id = EntityType.fromName(mob);
		}


		
		if (mobType.equalsIgnoreCase("FOX")) {
			Foxs FOX = new Foxs(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  FOX.spawnFOXs();

		}
		if (mobType.equalsIgnoreCase("HOGLIN")) {
			Hoglins HOGLIN = new Hoglins(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  HOGLIN.spawnHOGLINs();

		}
		if (mobType.equalsIgnoreCase("MULE") || mobType.equalsIgnoreCase("37")) {
			Mules MULE = new Mules(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  MULE.spawnMULEs();

		}
		if (mobType.equalsIgnoreCase("PANDA")) {
			Pandas PANDA = new Pandas(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  PANDA.spawnPANDAs();

		}
		
		if (mobType.equalsIgnoreCase("PIGLIN") || mobType.equalsIgnoreCase("37")) {
			Piglins PIGLIN = new Piglins(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  PIGLIN.spawnPIGLINs();

		}
		
		if (mobType.equalsIgnoreCase("PIGLIN_BRUTE") || mobType.equalsIgnoreCase("37")) {
			PigLinBrutos PIGLIN = new PigLinBrutos(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  PIGLIN.spawnPigLinBrutos();

		}
		
		if (mobType.equalsIgnoreCase("RAVAGER")) {
			Ravangers RAVAGER = new Ravangers(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  RAVAGER.spawnRAVAGERs();

		}
		
		if (mobType.equalsIgnoreCase("PILLAGER")) {
			Pillagers pill = new Pillagers(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  pill.spawnPillagers();

		}
		
		if (mobType.equalsIgnoreCase("RABBIT") ) {
			Rabbits RABBIT = new Rabbits(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return  RABBIT.spawnRABBITs();

		}
		
		
		
		if (mobType.equalsIgnoreCase("ZOGLIN")) {
			Zoglins ZOGLIN = new Zoglins(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ZOGLIN.spawnZOGLINs();

		}
		
		if (mobType.equalsIgnoreCase("illusion_illager") || mobType.equalsIgnoreCase("37")) {
			Illusioners illu = new Illusioners(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return illu.spawnIllusioners();

		}
		if (mobType.equalsIgnoreCase("evoker") || mobType.equalsIgnoreCase("34")) {
			Evokers illu = new Evokers(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return illu.spawnEvokers();

		}
		if (mobType.equalsIgnoreCase("vindication_illager")) {
			Vindication bats = new Vindication(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return bats.spawnVindicator();

		}
		if (id.equals(EntityType.BAT)) {
			Bats bats = new Bats(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return bats.spawnBats();

		}
		if (id.equals(EntityType.BLAZE)) {
			BlazeMonster blaze = new BlazeMonster(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return blaze.spawnBlaze();

		}
		if (id.equals(EntityType.CAVE_SPIDER)) {
			Cave_Spiders spider = new Cave_Spiders(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return spider.spawnSpider();

		}
		if (id.equals(EntityType.CHICKEN)) {
			Chickens Chicken = new Chickens(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return Chicken.spawnChickens();

		}

		if (id.equals(EntityType.COW)) {
			Cows COW = new Cows(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return COW.spawnCows();

		}
		if (id.equals(EntityType.CREEPER)) {
			Creepers Creepers = new Creepers(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return Creepers.spawnCreepers();

		}

		if (id.equals(EntityType.DONKEY)) {
			Donkeys donkeys = new Donkeys(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return donkeys.spawnDonkeys();

		}
		if (id.equals(EntityType.ELDER_GUARDIAN)) {
			Elder_Guaradians elder = new Elder_Guaradians(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return elder.spawnElder_Guaradians();

		}
		if (id.equals(EntityType.ENDER_DRAGON)) {
			Ender_Dragons ender = new Ender_Dragons(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ender.spawnEnder_Dragon();

		}
		if (id.equals(EntityType.ENDERMAN)) {
			Endermans ender = new Endermans(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ender.spawnEnderman();

		}
		if (mobType.equalsIgnoreCase("endemite") || mobType.equalsIgnoreCase("67")) {
			Endermites ender = new Endermites(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ender.spawnEndermites();

		}
		if (id.equals(EntityType.GHAST)) {
			Ghasts ghast = new Ghasts(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ghast.spawnGhasts();

		}

		if (id.equals(EntityType.GIANT)) {

			GiantMonster giant = new GiantMonster(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return giant.spawnGiant();

		}

		if (id.equals(EntityType.GUARDIAN)) {

			Guardians guardian = new Guardians(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return guardian.spawnGuardians();

		}
		if (id.equals(EntityType.HORSE)) {

			Horses horse = new Horses(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return horse.spawnHorses();

		}
		if (id.equals(EntityType.HUSK)) {

			Husks husk = new Husks(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return husk.spawnHusks();

		}

		if (id.equals(EntityType.IRON_GOLEM)) {

			Iron_Golems golens = new Iron_Golems(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return golens.spawnIron_Golems();

		}

		if (id.equals(EntityType.LLAMA)) {

			LLamas llama = new LLamas(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return llama.spawnLLamas();

		}

		if (id.equals(EntityType.MAGMA_CUBE)) {

			Magma_Cubes magma = new Magma_Cubes(lenght, plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return magma.spawnMagmaCube();

		}
		if (id.equals(EntityType.OCELOT)) {

			OcelotMonster Ocelot = new OcelotMonster(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);

			return Ocelot.spawnOcelot();

		}
		if (id.equals(EntityType.ZOMBIFIED_PIGLIN)) {
			Pig_Zombie pig = new Pig_Zombie(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return pig.spawnPig_Zombie();

		}
		if (id.equals(EntityType.PIG)) {
			PigMob pig = new PigMob(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return pig.spawnPig();

		}
		if (id.equals(EntityType.POLAR_BEAR)) {
			Polar_Bear bear = new Polar_Bear(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return bear.spawnPolar_Bear();

		}
		if (id.equals(EntityType.RABBIT)) {
			Rabbit rabbit = new Rabbit(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return rabbit.spawnRabbit();

		}

		if (id.equals(EntityType.SHEEP)) {
			Sheeps sheep = new Sheeps(color, plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return sheep.spawnSheep();

		}

		if (id.equals(EntityType.SHULKER)) {
			Shulker shulker = new Shulker(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return shulker.spawnShulker();

		}

		if (id.equals(EntityType.SILVERFISH)) {
			SilverFish SilverFish = new SilverFish(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return SilverFish.spawnSilverFish();

		}
		if (id.equals(EntityType.SKELETON_HORSE)) {
			Skeleton_Horse horse = new Skeleton_Horse(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return horse.spawnSkeleton_Horse();

		}
		if (id.equals(EntityType.SKELETON)) {
			Skeleton Skeleton = new Skeleton(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return Skeleton.spawnSkeleton();

		}
		if (id.equals(EntityType.WITHER_SKELETON)) {
			Skeletons_Wither Skeleton = new Skeletons_Wither(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return Skeleton.spawnSkeleton_Wither();

		}
		if (id.equals(EntityType.SLIME)) {
			Slimes horse = new Slimes(lenght, plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return horse.spawnSlimes();

		}
		if (id.equals(EntityType.SNOWMAN)) {
			SnowMans snow = new SnowMans(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return snow.spawnSnowMans();

		}
		if (id.equals(EntityType.SPIDER)) {
			SpiderMonster spider = new SpiderMonster(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return spider.spawnSpider();

		}
		if (id.equals(EntityType.SQUID)) {
			Squids squid = new Squids(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return squid.spawnSquids();

		}
		if (id.equals(EntityType.STRAY)) {
			Strays stray = new Strays(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return stray.spawnSTRAYs();

		}
		if (id.equals(EntityType.VEX)) {
			Vexs vex = new Vexs(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return vex.spawnVexs();

		}

		if (id.equals(EntityType.WITCH)) {

			Witchs Witch = new Witchs(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return Witch.spawnWitchs();

		}

		if (id.equals(EntityType.WITHER)) {

			Withers magma = new Withers(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return magma.spawnWithers();

		}

		if (id.equals(EntityType.WOLF)) {

			Wolfs magma = new Wolfs(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return magma.spawnWolfs();

		}

		if (id.equals(EntityType.PHANTOM)) {

			Phantoms ph = new Phantoms(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);
			return ph.spawnPhantoms();

		}

		if (id.equals(EntityType.ZOMBIE)) {

			ZombieMonster zombie = new ZombieMonster(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);

			return zombie.spawnZombie();
		}

		if (id.equals(EntityType.DROWNED)) {

			ZombieDrowned zombie = new ZombieDrowned(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);

			return zombie.spawnZombie();
		}
		if (id.equals(EntityType.ZOMBIE_HORSE)) {

			Zombies_Horse zombie = new Zombies_Horse(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);

			return zombie.spawnZombies_Horse();
		}
		if (id.equals(EntityType.ZOMBIE_VILLAGER)) {

			Zombies_Villager zombie = new Zombies_Villager(plugin, world, loc, items, attack, speed, health, name, baby, villager, level, slots, twoHand);

			return zombie.spawnZombies_Villager();
		}
		
		return null;
		
	}
	
	public void setAtribute(RPGDungeon plugin, String keyMob) {
		reloadMobConfig(plugin);

		if (getMobConfig().contains(keyMob)) {

			if (getMobConfig().getConfigurationSection(keyMob).contains("displayName")) {
				name = getMobConfig().getString(keyMob + ".displayName");
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("nv")) {
				level = getMobConfig().getInt(keyMob + ".nv");
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("health")) {
				health = Float.parseFloat("" + getMobConfig().getDouble(keyMob + ".health"));
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("attack")) {
				attack = getMobConfig().getDouble(keyMob + ".attack");
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("speed")) {
				speed = getMobConfig().getDouble(keyMob + ".speed");
			}

			if (getMobConfig().getConfigurationSection(keyMob).contains("baby")) {
				baby = getMobConfig().getBoolean(keyMob + ".baby");
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("glowing")) {
				villager = getMobConfig().getBoolean(keyMob + ".glowing");
			}

			if (getMobConfig().getConfigurationSection(keyMob).contains("twoHand")) {
				twoHand = getMobConfig().getBoolean(keyMob + ".twoHand");
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("age")) {
				lenght = getMobConfig().getInt(keyMob + ".age");
			}	
			if (getMobConfig().getConfigurationSection(keyMob).contains("mount")) {
				Joker = getMobConfig().getString(keyMob + ".mount");
				
			}
			if (getMobConfig().getConfigurationSection(keyMob).contains("equipment")) {
				itemsList = getMobConfig().getStringList(keyMob + ".equipment");

				reloadItensDungeonConfig(plugin);
				int i = 0;
				if (itemsList.size() > 0) {
					for (String item : itemsList) {

						items[i] = plugin.getListaItens().get(item);
						slots[i] = getItensDungeonConfig().getInt(item + ".slot");
						i++;
					}

				}

			}

		} else {
			return;
		}

	}
}
