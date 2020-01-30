package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Init
public class BukkitListener implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        AnticheatScoreboard.INSTANCE.setupSB(event.getPlayer());
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        if(AnticheatScoreboard.INSTANCE.boards.containsKey(event.getPlayer().getUniqueId())) {
            AnticheatScoreboard.INSTANCE.boards.remove(event.getPlayer().getUniqueId());
        }
    }
}
