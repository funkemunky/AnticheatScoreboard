package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;

public class Spartan extends Anticheat {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getHackType().name(), event.isCancelled(), vl -> vl.checkName.equals(event.getHackType().name()), vl -> vl.vlCount = event.getViolation(), event.getMessage());

        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).equals(getAnticheatName()));
    }

    @Override
    public String getAnticheatName() {
        return "Spartan";
    }
}
