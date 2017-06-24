package nl.dutchcodinggroup.duels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Events implements Listener {
	
	@EventHandler
	public void onSlaan(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(!ArenaManager.isInGame(p)) return;
			if(ArenaManager.getArena(p).pvpState() == false) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			if(!ArenaManager.isInGame(p)) return;
			Player k = p.getKiller();
			p.spigot().respawn();
			ArenaManager.getArena(p).stop(k, p);	
		}
	}

}
