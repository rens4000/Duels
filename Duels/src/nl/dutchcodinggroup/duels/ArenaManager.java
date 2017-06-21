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
	
	private ConfigManager configManager;
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
	
	public static void setSpawn(String name, Location loc) {
		arenas.get(name).setSpawnLocation(loc);
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
	
	public Arena getArena(String name) {
		return arenas.get(name);
	}
	
	public void load() {
		for(String name : data.getConfigurationSection("arenas").getKeys(false)) {
			arenas.put(name, new Arena(name, null, null));
		}
	}
	
	public void save() {
		for(Arena arena : arenas.values()) {
			String name = arena.getName();
		}
		arenas.clear();
		configManager.save();
	}	
}
