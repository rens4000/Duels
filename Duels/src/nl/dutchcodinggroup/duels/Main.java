package nl.dutchcodinggroup.duels;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dutchcodinggroup.duels.utils.ConfigManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static ConfigManager configManager;
	private static ArenaManager arenaManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		configManager = new ConfigManager();
		arenaManager = new ArenaManager();
		
		arenaManager.load();
	}
	
	@Override
	public void onDisable() {
		arenaManager.save();
	}
	
	public static ConfigManager getConfigManager() {
		return configManager;
	}
	
	public static ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	public static Main getInstance() {
		return instance;
	}

}
