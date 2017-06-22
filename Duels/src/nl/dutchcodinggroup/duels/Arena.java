package nl.dutchcodinggroup.duels;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import nl.dutchcodinggroup.duels.utils.GameState;

public class Arena {
	
	private String name;
	private static GameState state;
	private Location loc1;
	private Location loc2;
	private boolean spawnedLoc1;
	private static boolean pvp = false;
	private boolean enabled;
	private static ArrayList<UUID> players = new ArrayList<>();
	
	public Arena(String name, Location loc1, Location loc2) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		
		this.state = GameState.WAITING;
	}
	
	public static void sendMessage(String message) {
		for (UUID u : players) {
			Player p = Bukkit.getPlayer(u);
			p.sendMessage(Main.PREFIX + message);
		}
	}
	
	public static void sendTitle(String message, String Sub) {
		for (UUID u : players) {
			Player p = Bukkit.getPlayer(u);
			p.sendTitle(message, Sub);
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
				StartCountdown();
			}
		}
		
		p.getInventory().clear();


		return true;
	}
	
	static int i;
    public static void StartCountdown() {
        i = 300;
        BukkitTask task = new BukkitRunnable() {
        	int count = 10;
        	public void run() {
        		count--;
        		if(count == 10 | count == 5 | count == 4 | count == 3 | count == 2 | count == 1) {
        			sendTitle(ChatColor.AQUA + "" + count + " seconds left!", ChatColor.WHITE + "Until the game starts");
        			sendMessage("The arena begins in: " + count + " second(s)");
        			
        		}
        		if(count <= 0) {
        			if(players.size() != 2) {
        				sendMessage("Not enough players in the game! Countdown stopped!");
        				sendTitle(ChatColor.RED + "Not enough players!", ChatColor.WHITE + "The countdown has been stopped");
        				cancel();
        				return;
        			}
        			start();
        			cancel();
        		}
        	}
        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
	
	public Location getSpawn1() {
		return loc1;
	}
	
	public Location getSpawn2() {
		return loc2;
	}
	
	public void setSpawn1(Location loc) {
		loc1 = loc;
	}
	
	public void setSpawn2(Location loc) {
		loc2 = loc;
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
	
	public static void start() {
		pvp = true;
		state = GameState.IN_GAME;
		sendTitle(ChatColor.AQUA + "Game started!", "");
		for(UUID u : players) {
			Player p = Bukkit.getPlayer(u);
			ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
			item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			p.getInventory().addItem(item);
			p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
			p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
			p.getInventory().addItem(new ItemStack(Material.WOOD, 64));
		}
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean pvpState() {
		if(pvp == true) {
			return true;
		}
		return false;
	}
	
	public void stop() {
		
	}

	public boolean isEnabled() {
		if(enabled) return true;
		return false;
	}
}
