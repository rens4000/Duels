package nl.dutchcodinggroup.duels.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.dutchcodinggroup.duels.Main;

public class ConfigManager {
	
	private Main pl;
	
	private File dataFile;
	private FileConfiguration data;
	
	public ConfigManager() {
		pl = Main.getInstance();
		
		dataFile = new File(pl.getDataFolder(), "data.yml");
		data = YamlConfiguration.loadConfiguration(dataFile);
	}
	
	public void save() {
		try {
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getDataFile() {
		return data;
	}
	
}
