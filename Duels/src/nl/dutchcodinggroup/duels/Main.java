package nl.dutchcodinggroup.duels;

import org.bukkit.plugin.java.JavaPlugin;

import nl.dutchcodinggroup.duels.utils.ConfigManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static ConfigManager configManager;
	
	@Override
	public void onEnable() {
		instance = this;
		
		configManager = new ConfigManager();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ConfigManager getConfigManager() {
		return configManager;
	}
	
	public static Main getInstance() {
		return instance;
	}

}
