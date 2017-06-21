package nl.dutchcodinggroup.duels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import nl.dutchcodinggroup.duels.utils.ConfigManager;
import nl.mistermel.quickcraft.utils.ArenaManager;

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
		
		arenaManager.load();
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
			if(args.length == 1) {
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
			if(args[0].equalsIgnoreCase("setspawn")) {
				if(!sender.hasPermission("quickcraft.admin")) {
					sender.sendMessage(PREFIX + ChatColor.RED + "You dont have permission to use this command!");
					return true;
				}
				if(args.length == 1) {
					sender.sendMessage(PREFIX + ChatColor.RED + "Use: /qc setspawn <Name>");
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
				if(ArenaManager.isEnabled(args[1])) {
					sender.sendMessage(PREFIX + ChatColor.RED + "This arena is currently enabled. To make changes, please disable it first.");
					return true;
				}
				Player p = (Player) sender;
				ArenaManager.setSpawn(args[1], p.getLocation());
				sender.sendMessage(PREFIX + ChatColor.GOLD + "Spawn set!");
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
