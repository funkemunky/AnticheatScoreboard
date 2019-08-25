package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.anticheat.Anticheat;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;

public class AAC extends Anticheat {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getHackType().getName(), event.isCancelled(), vl -> vl.checkName.equals(event.getHackType().getName()), vl -> vl.vlCount = event.getViolations());
    }

    @Override
    public String getAnticheatName() {
        return "AAC";
    }
}
