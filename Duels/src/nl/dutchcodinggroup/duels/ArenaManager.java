package nl.dutchcodinggroup.duels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import nl.dutchcodinggroup.duels.utils.ConfigManager;

public class ArenaManager {
	
	
	private static Map<String, Arena> arenas = new HashMap<String, Arena>();
	
	private static ConfigManager configManager;
	private static FileConfiguration data;
	
	public ArenaManager() {
		configManager = Main.getConfigManager();
		data = configManager.getDataFile();
	}
	
	public void createArena(String name) {
		arenas.put(name, new Arena(name, null, null, false));
	}
	
	public static void setEnabled(String name, boolean enabled) {
		data.set("arenas." + name + ".enabled", enabled);
		Arena arena = arenas.get(name);
		arena.setEnabled(enabled);
		for(Player p : arena.getPlayers()) {
			p.sendMessage(Main.PREFIX + ChatColor.RED + "This arena has been disabled by a admin.");
			arena.leave(p);
		}
		Main.getConfigManager().save(Main.getConfigManager().getDataFile());
	}
	
	public static void setMainSpawn(Player p) {
		Location main = p.getLocation();
		data.set("main.spawnlocation.world", main.getWorld());
		data.set("main.spawnlocation.x", main.getX());
		data.set("main.spawnlocation.y", main.getY());
		data.set("main.spawnlocation.z", main.getZ());
		
	}
	
	public static Location getMainSpawn() {
		Location loc = new Location(Bukkit.getWorld(data.get("main.spawnlocation.world").toString()), (int) data.get("main.spawnlocation.x"),
				(int) data.get("main.spawnlocation.y"), (int) data.get("main.spawnlocation.z"));
		if(loc == null) return null;
		return loc;
	}
	
	public static void setSpawn1(String name, Location loc) {
		arenas.get(name).setSpawn1(loc);
		save();
	}
	
	public static void setSpawn2(String name, Location loc) {
		arenas.get(name).setSpawn1(loc);
		save();
	}
	
	public static boolean isCompleted(String name) {
		Location spawn = arenas.get(name).getSpawn1();
		Location lobby = arenas.get(name).getSpawn2();
		return spawn.getX() != 0 && spawn.getY() != 0 && spawn.getZ() != 0 && lobby.getX() != 0 && lobby.getY() != 0 && lobby.getZ() != 0;
	}
	
	public static Arena getArena(Player p) {
		for(Arena arena : arenas.values()) {
			if(arena.inGame(p)) {
				return arena;
			}
		}
		p.sendMessage(Main.ERROR + "Je zit niet in een arena!");
		return null;
	}
	
	public static Arena getArena(String name) {
		return arenas.get(name);
	}
	
	public void load() {
		for(String name : data.getConfigurationSection("arenas").getKeys(false)) {
			arenas.put(name, new Arena(name, getArena(name).getSpawn1(), getArena(name).getSpawn2(), true));
		}
	}
	
	public static void save() {
		try {
		for(Arena arena : arenas.values()) {
			String name = arena.getName();
			if(arena.getSpawn1() != null) {
				data.set("arenas." + name + ".spawn1.world", arena.getSpawn1().getWorld().getName());
				data.set("arenas." + name + ".spawn1.x", arena.getSpawn1().getX());
				data.set("arenas." + name + ".spawn1.y", arena.getSpawn1().getY());
				data.set("arenas." + name + ".spawn1.z", arena.getSpawn1().getZ());
			}
			if(arena.getSpawn2() != null) {
				data.set("arenas." + name + ".spawn2.world", arena.getSpawn2().getWorld().getName());
				data.set("arenas." + name + ".spawn2.x", arena.getSpawn2().getX());
				data.set("arenas." + name + ".spawn2.y", arena.getSpawn2().getY());
				data.set("arenas." + name + ".spawn2.z", arena.getSpawn2().getZ());
			}
			data.set("arenas." + name + ".enabled", arena.isEnabled());
			
		}
		arenas.clear();
		configManager.save(Main.getConfigManager().getDataFile());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean exists(String string) {
		for(Arena a : arenas.values()) {
			if(a.getName() == string) return true;
		}
		return false;
	}

	public static boolean isInGame(Player p) {
		for(Arena arena : arenas.values()) {
			if(arena.inGame(p)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean join(String name, Player p) {
		Arena arena = arenas.get(name);
		return arena.join(p);
	}

	public static void refreshConfig() {
		save();
		for(Arena arena : arenas.values()) {
			for(Player p : arena.getPlayers()) {
				arena.leave(p);
			}
		}
		arenas.clear();
		if(data.getConfigurationSection("arenas") == null)
			data.createSection("arenas");
		for(String key : data.getConfigurationSection("arenas").getKeys(false)) {
			Location loc1 = new Location(Bukkit.getWorld(data.getString("arenas." + key + ".spawn1.world")), data.getInt("arenas." + key + ".spawn1.x"), data.getInt("arenas." + key + ".spawn1.y"), data.getInt("arenas." + key + ".spawn1.z"));
			Location loc2 = new Location(Bukkit.getWorld(data.getString("arenas." + key + ".spawn2.world")), data.getInt("arenas." + key + ".spawn2.x"), data.getInt("arenas." + key + ".spawn2.y"), data.getInt("arenas." + key + ".spawn2.z"));
			Arena arena = new Arena(key, loc1, loc2, false);
			arenas.put(key, arena);
		}
	}
}
