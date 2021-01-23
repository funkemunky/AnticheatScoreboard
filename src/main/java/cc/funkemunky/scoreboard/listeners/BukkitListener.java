package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Init
public class BukkitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerJoinEvent event) {
        AnticheatScoreboard.INSTANCE.setupSB(event.getPlayer());
        event.getPlayer().sendMessage(Color.Green + "Welcome! " + Color.Gray + "Use " + Color.White
                + "/as anticheat" + Color.Gray + " to switch anticheats!");
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        if(AnticheatScoreboard.INSTANCE.boards.containsKey(event.getPlayer().getUniqueId())) {
            AnticheatScoreboard.INSTANCE.boards.remove(event.getPlayer().getUniqueId());
        }
        Anticheat.anticheats.forEach(ac -> ac.inUse.remove(event.getPlayer()));
    }
}
