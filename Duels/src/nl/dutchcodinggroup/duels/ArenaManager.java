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
		arenas.put(name, new Arena(name, 2, 2));
	}
	
	public Arena getArena(String name) {
		return arenas.get(name);
	}
	
	public void load() {
		for(String name : data.getConfigurationSection("arenas").getKeys(false)) {
			//TODO: Laad shit
			arenas.put(name, new Arena(name, 2, 2));
		}
	}
	
	public void save() {
		for(Arena arena : arenas.values()) {
			//TODO: Save shit
		}
		arenas.clear();
		configManager.save();
	}	
}
