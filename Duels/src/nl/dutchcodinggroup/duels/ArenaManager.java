package nl.dutchcodinggroup.duels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import nl.dutchcodinggroup.duels.utils.ConfigManager;

public class ArenaManager {
	
	private Map<String, Arena> arenas = new HashMap<String, Arena>();
	
	private ConfigManager configManager;
	private FileConfiguration data;
	
	public ArenaManager() {
		configManager = Main.getConfigManager();
		data = configManager.getDataFile();
	}
	
	public void createArena(String name) {
		arenas.put(name, new Arena(name, null, null));
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
