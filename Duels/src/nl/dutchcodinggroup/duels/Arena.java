package nl.dutchcodinggroup.duels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import nl.dutchcodinggroup.duels.utils.GameState;

public class Arena {
	
	private String name;
	private GameState state;
	private Location loc1;
	private Location loc2;
	private ArrayList<UUID> players = new ArrayList<>();
	
	public Arena(String name, Location loc1, Location loc2) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		
		this.state = GameState.WAITING;
	}
	
	public Location getSpawn1() {
		return loc1;
	}
	
	public Location getSpawn2() {
		return loc2;
	}
	
	public GameState getState() {
		return state;
	}

	public String getName() {
		return name;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for(UUID u : this.players) {
			players.add(Bukkit.getPlayer(u));
		}
		return players;
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
}
