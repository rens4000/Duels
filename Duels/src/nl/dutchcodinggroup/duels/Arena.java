package nl.dutchcodinggroup.duels;

import java.util.ArrayList;
import java.util.UUID;

public class Arena {
	
	private String name;
	private int minPlayers;
	private int maxPlayers;
	private ArrayList<UUID> players = new ArrayList<>();
	
	public Arena(String name, int minPlayers, int maxPlayers) {
		this.name = name;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public ArrayList<UUID> getPlayers() {
		return players;
	}
	
	public void start() {
		
	}
	
	public void stop() {
		
	}
}
