package nl.dutchcodinggroup.duels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;
import org.bukkit.Location;

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
		arenas.put(name, new Arena(name, null, null));
	}
	
	public static void setEnabled(String name, boolean enabled) {
		data.set("arenas." + name + ".enabled", enabled);
		Arena arena = arenas.get(name);
		arena.setEnabled(enabled);
		for(Player p : arena.getPlayers()) {
			p.sendMessage(Main.PREFIX + ChatColor.RED + "This arena has been disabled by a admin.");
			arena.leave(p);
		}
		Main.getConfigManager().save();
	}
	
	public static void setSpawn1(String name, Location loc) {
		arenas.get(name).setSpawn1(loc);
		save();
	}
	
	public static void setSpawn2(String name, Location loc) {
		arenas.get(name).setSpawn1(loc);
		save();
	}
	
	
	
	public static Arena getArena(Player p) {
		for(Arena arena : arenas.values()) {
			if(arena.inGame(p)) {
				return arena;
			}
		}
		return null;
	}
	
	public static Arena getArena(String name) {
		return arenas.get(name);
	}
	
	public void load() {
		for(String name : data.getConfigurationSection("arenas").getKeys(false)) {
			arenas.put(name, new Arena(name, null, null));
		}
	}
	
	public static void save() {
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
		configManager.save();
	}

	public static boolean exists(String string) {
		for(Arena a : arenas.values()) {
			if(a.getName() == string) return true;
		}
		return false;
	}	
}
