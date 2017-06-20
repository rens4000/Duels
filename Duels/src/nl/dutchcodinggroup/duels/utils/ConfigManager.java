package nl.dutchcodinggroup.duels.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.dutchcodinggroup.duels.Main;

public class ConfigManager {
	
	private File configFile;
	private FileConfiguration config;
	
	private File dataFile;
	private FileConfiguration data;
	
	private Main pl;
	
	public ConfigManager() {
		pl = Main.getInstance();
		
		dataFile = new File(pl.getDataFolder(), "data.yml");
		data = YamlConfiguration.loadConfiguration(dataFile);
		
		configFile = new File(pl.getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		
		if(!config.contains("servername")) {
			config.set("servername", "&6ExampleServer");
		}
	}
	
	public void setMainLobby(Location loc) {
		data.set("lobby.world", loc.getWorld().getName());
		data.set("lobby.x", loc.getBlockX());
		data.set("lobby.y", loc.getBlockY());
		data.set("lobby.z", loc.getBlockZ());
		save();
	}
	
	public boolean mainLobbySet() {
		return data.contains("lobby");
	}
	
	public Location getMainLobby() {
		return new Location(Bukkit.getWorld(data.getString("lobby.world")), data.getInt("lobby.x"), data.getInt("lobby.y"), data.getInt("lobby.z"));
	}
	
	public void save() {
		try {
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getData() {
		return dataFile;
	}
	
	public FileConfiguration getDataFile() {
		return data;
	}
	
	public FileConfiguration getConfigFile() {
		return config;
	}
}
