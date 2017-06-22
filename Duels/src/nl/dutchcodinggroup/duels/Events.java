package nl.dutchcodinggroup.duels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Events implements Listener {
	
	@EventHandler
	public void onSlaan(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(ArenaManager.getArena(p).pvpState() == false) {
				e.setCancelled(true);
			}
		}
	}

}
