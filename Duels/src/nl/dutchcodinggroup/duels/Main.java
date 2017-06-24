package nl.dutchcodinggroup.duels;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.dutchcodinggroup.duels.utils.ConfigManager;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static ConfigManager configManager;
	private static ArenaManager arenaManager;
	
	public static final String PREFIX = ChatColor.YELLOW + "Duels" + ChatColor.GRAY + " >> ";
	public static final String ERROR = ChatColor.GRAY + "[" + ChatColor.RED + "!" + ChatColor.GRAY + "] ";
	
	@Override
	public void onEnable() {
		instance = this;
		
		configManager = new ConfigManager();
		arenaManager = new ArenaManager();
		
		arenaManager.refreshConfig();
		arenaManager.load();
		Bukkit.getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public void onDisable() {
		arenaManager.save();
	}
	
	/*
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used by players!");
					return true;
				}
	 */
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("duels")) {
			if(args.length == 0) {
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Duels");
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Made by " + ChatColor.DARK_AQUA + "rens4000" + ChatColor.GOLD + " & " + ChatColor.GOLD + "MisterMel");
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Use /duels help for command help.");
				return true; 
			}
			if(args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(PREFIX + ChatColor.GOLD + "/duels " + ChatColor.GRAY + " - Main command for the duels plugin.");
				sender.sendMessage(PREFIX + ChatColor.GOLD + "/duels help" + ChatColor.GRAY + " - Shows this message.");
				sender.sendMessage(PREFIX + ChatColor.GOLD + "/duels create <Name>" + ChatColor.GRAY + " - Creates an arena.");
				return true;
			}
			if(args[0].equalsIgnoreCase("setspawn1")) {
				if(!sender.hasPermission("duels.admin")) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Use: /duels setspawn1 <Name>");
					return true;
				}
				if(!ArenaManager.exists(args[1])) {
					sender.sendMessage(PREFIX + ChatColor.RED + "That arena does not exist!");
					return true;
				}
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used as a player!");
					return true;
				}
				if(ArenaManager.getArena(args[1]).isEnabled()) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This arena is currently enabled. To make changes, please disable it first.");
					return true;
				}
				Player p = (Player) sender;
				ArenaManager.setSpawn1(args[1], p.getLocation());
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Spawn1 set!");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("setspawn2")) {
				if(!sender.hasPermission("duels.admin")) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Use: /duels setspawn2 <Name>");
					return true;
				}
				if(!ArenaManager.exists(args[1])) {
					sender.sendMessage(PREFIX + ChatColor.RED + "That arena does not exist!");
					return true;
				}
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used as a player!");
					return true;
				}
				if(ArenaManager.getArena(args[1]).isEnabled()) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This arena is currently enabled. To make changes, please disable it first.");
					return true;
				}
				Player p = (Player) sender;
				ArenaManager.setSpawn2(args[1], p.getLocation());
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Spawn2 set!");
				return true;
			}
			if(args[0].equalsIgnoreCase("toggle")) {
				if(!sender.hasPermission("duels.admin")) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Use: /duels toggle <Name>");
					return true;
				}
				if(!ArenaManager.exists(args[1])) {
					sender.sendMessage(PREFIX + ChatColor.RED + "That arena does not exist!");
					return true;
				}
				boolean toggled = ArenaManager.getArena(args[1]).isEnabled();
				if(toggled) {
					ArenaManager.setEnabled(args[1], false);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Arena disabled.");
				} else {
					if(!ArenaManager.isCompleted(args[1])) {
						sender.sendMessage(PREFIX + ChatColor.RED + "This arena isnt completed yet!");
						return true;
					}
					if(ArenaManager.getMainSpawn() == null) {
						sender.sendMessage(PREFIX + ChatColor.RED + "There isn't a mainspawn!");
						return true;
					}
					ArenaManager.setEnabled(args[1], true);
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Arena enabled.");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				if(!sender.hasPermission("duels.admin")) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				ArenaManager.refreshConfig();
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Plugin reloaded.");
				return true;
			}
			if(args[0].equalsIgnoreCase("setmainspawn")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used as a player!");
					return true;
				}
				Player p = (Player) sender;
				if(!p.hasPermission("duels.admin")) {
					p.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				ArenaManager.setMainSpawn(p);
			}
			if(args[0].equalsIgnoreCase("join")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used as a player!");
					return true;
				}
				Player p = (Player) sender;
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Use: /duels join <Name>");
					return true;
				}
				if(!ArenaManager.exists(args[1])) {
					sender.sendMessage(PREFIX + ChatColor.RED + "That arena does not exist!");
					return true;
				}
				if(ArenaManager.isInGame(p)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You are already in a game!");
					return true;
				}
				if(!ArenaManager.join(args[1], p)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This game is currently not joinable.");
				} else {
					sender.sendMessage(PREFIX + ChatColor.GOLD + "Joined game!");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("leave")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be used as a player!");
					return true;
				}
				Player p = (Player) sender;
				if(!ArenaManager.isInGame(p)) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You aren't in a game!");
					return true;
				}
				ArenaManager.getArena(p).leave(p);
				return true;
			}
			if(args[0].equalsIgnoreCase("create")) {
				if(!sender.hasPermission("duels.admin")) {
					sender.sendMessage(PREFIX + ERROR + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ERROR + ChatColor.RED + "Use: /duels create <Name>");
					return true;
				}
				arenaManager.createArena(args[1]);
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Arena created.");
				return true;
			}
			sender.sendMessage(PREFIX + ERROR + ChatColor.RED + "Subcommand not found. Use /duels help for command help.");
			
		}
		return true;
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
