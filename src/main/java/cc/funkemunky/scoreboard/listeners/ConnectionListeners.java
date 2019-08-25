package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Init
public class ConnectionListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("as.scoreboard")) {
            val scoreboard = AnticheatScoreboard.INSTANCE.scoreboardManager.addScoreboard(event.getPlayer());
            scoreboard.activate();
        }
    }
}
