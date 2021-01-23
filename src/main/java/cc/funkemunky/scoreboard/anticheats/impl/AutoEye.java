package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import com.heirteir.autoeye.api.AutoEyeInfractionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = "AutoEye")
public class AutoEye extends Anticheat implements Listener {

    public AutoEye() {
        super("AutoEye");
    }

    @EventHandler
    public void onCheat(AutoEyeInfractionEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent(this, event.getPlayer(),
                new CheckWrapper(event.getInfraction().getParent().getName(),
                        true, true, event.getInfraction().getParent().getType().getName()),
                event.getInfraction().getVL(), event.getMessage());

        Atlas.getInstance().getEventManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());
    }
}
