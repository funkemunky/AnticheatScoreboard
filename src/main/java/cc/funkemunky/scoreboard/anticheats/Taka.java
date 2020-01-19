package cc.funkemunky.scoreboard.anticheats;

import TAKA.PlayerCheatEvent;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "TakaAC")
public class Taka implements Listener {

    @EventHandler
    public void onCheat(PlayerCheatEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("Taka",
                event.getPlayer(), new CheckWrapper(event.getHack().name(),
                true, true, event.getHack().name()), event.getViolation(), "");

        Bukkit.getPluginManager().callEvent(afe);
        event.setCancelled(afe.isCancelled());
    }
}