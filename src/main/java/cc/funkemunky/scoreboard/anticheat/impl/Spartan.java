package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.anticheat.Anticheat;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;

public class Spartan extends Anticheat {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getHackType().name(), event.isCancelled(), vl -> vl.checkName.equals(event.getHackType().name()), vl -> vl.vlCount = event.getViolation());
    }

    @Override
    public String getAnticheatName() {
        return "Spartan";
    }
}
