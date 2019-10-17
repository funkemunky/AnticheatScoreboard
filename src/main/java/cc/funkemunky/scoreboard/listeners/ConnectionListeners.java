package cc.funkemunky.scoreboard.listeners;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

@Init
public class ConnectionListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("as.scoreboard") || GeneralConfig.testMode) {
            val scoreboard = AnticheatScoreboard.INSTANCE.scoreboardManager.addScoreboard(event.getPlayer());
            scoreboard.activate();

            if(GeneralConfig.testMode) {
                AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.put(event.getPlayer().getUniqueId(), AnticheatScoreboard.INSTANCE.anticheatManager.anticheats.get(0).getAnticheatName());
                AnticheatScoreboard.INSTANCE.alerts.add(event.getPlayer());
            }
        }
    }
}