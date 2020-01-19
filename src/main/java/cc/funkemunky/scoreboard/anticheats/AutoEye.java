package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import com.heirteir.autoeye.api.AutoEyeInfractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "AutoEye")
public class AutoEye implements Listener {
    @EventHandler
    public void onCheat(AutoEyeInfractionEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent("AutoEye", event.getPlayer(),
                new CheckWrapper(event.getInfraction().getParent().getName(),
                        true, true, event.getInfraction().getParent().getType().getName()),
                event.getInfraction().getVL(), event.getMessage());

        Bukkit.getPluginManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }
}
