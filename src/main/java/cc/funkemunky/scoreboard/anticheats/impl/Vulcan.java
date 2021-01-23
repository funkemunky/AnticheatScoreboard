package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import me.frep.api.VulcanFlagEvent;
import me.frep.vulcan.checks.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Init(requirePlugins = {"Vulcan"})
public class Vulcan extends Anticheat implements Listener {

    public Vulcan() {
        super("Vulcan");
    }

    @EventHandler
    public void onFlag(VulcanFlagEvent event) {
        AnticheatFlagEvent afe = new AnticheatFlagEvent(this, event.getPlayer(),
                new CheckWrapper(event.getCheck().getName(),
                        true,
                        false,
                        event.getCheck().getIdentifier()), event.getVl(), event.getCheck().getType().name());

        Atlas.getInstance().getEventManager().callEvent(afe);

        event.setCancelled(afe.isCancelled());

        if(afe.isCancelled()) {
            Check.violations.remove(event.getPlayer().getUniqueId());
        }
    }

    @Override
    public void enableForPlayer(Player player) {
        AnticheatScoreboard.INSTANCE.permission.playerAdd(player, "vulcan.bypass");

        super.enableForPlayer(player);
    }

    @Override
    public void disableForPlayer(Player player) {
        AnticheatScoreboard.INSTANCE.permission.playerRemove(player, "vulcan.bypass");

        super.disableForPlayer(player);
    }
}
