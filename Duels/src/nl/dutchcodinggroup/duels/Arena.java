package nl.dutchcodinggroup.duels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import nl.dutchcodinggroup.duels.utils.GameState;
import nl.mistermel.quickcraft.QuickCraft;
import nl.mistermel.quickcraft.utils.ArenaManager;

public class Arena {
	
	private String name;
	private GameState state;
	private Location loc1;
	private Location loc2;
	private boolean spawnedLoc1;
	private boolean spawnedLoc2;
	private boolean enabled;
	private ArrayList<UUID> players = new ArrayList<>();
	
	public Arena(String name, Location loc1, Location loc2) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		
		this.state = GameState.WAITING;
	}
	
	public void sendMessage(String message) {
		for (UUID u : players) {
			Player p = Bukkit.getPlayer(u);
			p.sendMessage(Main.PREFIX + message);
		}
	}
	
	public boolean inGame(Player p) {
		return players.contains(p.getUniqueId());
	}
	
	public boolean join(Player p) {
		if (!state.isJoinable())
			return false;
		if (!enabled)
			return false;
		players.add(p.getUniqueId());
		p.teleport(lobbyLoc);
		
		for(UUID pl : players) {
			Player player = Bukkit.getPlayer(pl);
			if(!spawnedLoc1) {
				player.teleport(loc1);
			} else {
				player.teleport(loc2);
			}
		}

		if (state == GameState.WAITING) {
			if (players.size() == 2) {
				state = GameState.STARTING;
				
				sendMessage(ChatColor.GOLD + "Starting countdown!");
			}
		}
		
		p.getInventory().clear();

		p.setScoreboard(board);

		return true;
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
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void stop() {
		
	}
}
